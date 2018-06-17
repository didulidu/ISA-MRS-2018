var invitations = []

$(document).ready(function(){
        getCurrentUserInvitations();
});

function getCurrentUserInvitations(){

    $.ajax({
            url: 'registeredUser/getAllRegisteredUserInvitations',
            type: 'GET',
            dataType: 'json',
            beforeSend: function(request){
                request.setRequestHeader('Authorization', localStorage.getItem('currentUserToken'));
            },
            success: function(data, textStatus, response){
                localStorage.setItem('currentUserToken', response.getResponseHeader('Authorization'));
                renderPersonalInvitations(data);
            },
            error: function(response){
                if(response.status == 401)
                    getToastr("Not authorized for this activity", "Greška", 3);
                else
                    getToastr("An error occured while fetchng reservations! \nStatus: " + response.status, "", 3);
            }
        });

}

function renderPersonalInvitations(data){
    var invitationList = (data == null) ? [] : (data instanceof Array ? data : [data]);

    var reservationTableBody = $('#invitations-table').find("tbody");
    $('#rinvitations-table').find("thead").find("tr").append("<th class='col-md-1'> </th>");

    $.each(invitationList, function (index, invitation) {
        var dateObject = new Date(invitation.date);
        var date = dateObject.getDate().toString() + "/" + (dateObject.getMonth() + 1).toString() + "/" + dateObject.getFullYear().toString();
        //var timeObject = new Date(reservation.startTime);
        var time = dateObject.getHours().toString() + ":" + dateObject.getMinutes().toString();
        var trReservation = "<tr>" +
                "<td>" + invitation.invitersName + "</td>" +
                "<td>" + invitation.invitersLastname + "</td>" +
                "<td>" + invitation.theatreName + "</td>" +
                "<td>" + invitation.projectionShowTitle + "</td>" +
                "<td id='dateReservation'>" + date + "</td>" +
                "<td id='timeReservation'>" + time + "</td>";
        if (invitation.status == "Pending"){
                trReservation += "<td id='" + invitation.reservationId + "'>" +
                                     "<form>" +
                                         "<input type='hidden' value='" + invitation.id + "'>" +
                                         "<button id = 'accept-invitation-button' class='btn btn-danger reservation-remove-button'>" +
                                             "<span class='glyphicon glyphicon-remove'></span> Accept" +
                                         "</button>" +
                                     "</form>" +
                                 "</td>" +
                                 "<td>" +
                                     "<form>" +
                                         "<input type='hidden' value='" + invitation.id + "'>" +
                                         "<button id = 'reject-invitation-button'>" +
                                             "Cancel" +
                                         "</button>" +
                                     "</form>" +
                                 "</td>" +
                             "</tr>";
        }else{
            trReservation += "<td id='" + invitation.reservationId + "'>" +
                                "<form>" +
                                    "<input type='hidden' value='" + invitation.id + "'>" +
                                    "<button id = 'remove-invitation-button' class='btn btn-danger reservation-remove-button'>" +
                                        "<span class='glyphicon glyphicon-remove'></span> Remove" +
                                    "</button>" +
                                "</form>" +
                            "</td>";
        }
        reservationTableBody.append(trReservation);
    });
}

function acceptInvitation(idReservation, idInvite, td, tdIgnore){
    $.ajax({
        url: 'registeredUser/acceptInvitation',
        type: 'PUT',
        contentType: 'text/plain',
        data: idInvite,
        beforeSend: function(request){
            request.setRequestHeader('Authorization', localStorage.getItem('currentUserToken'));
        },
        success: function(data, textStatus, response){
            localStorage.setItem('currentUserToken', response.getResponseHeader('Authorization'));
            tdIgnore.remove();
            td.empty();
            td.append("<form>" +
                    "<input type='hidden' value='" + idReservation + "'>" +
                    "<button class='btn btn-primary invite-details-button'>" +
                        "<span class='glyphicon glyphicon-info-sign'></span> Detaljnije" +
                    "</button>" +
                "</form>");
            var tr = td.parents("tr");
            tr.append("<td>" +
                    "<form>" +
                        "<input type='hidden' value='" + idInvite + "'>" +
                        "<button class='btn btn-danger invite-remove-button'>" +
                            "<span class='glyphicon glyphicon-remove'></span> Otkaži" +
                        "</button>" +
                    "</form>" +
                "</td>");
        },
        error: function(response){
            if(response.status == 400)
                getToastr("Nepostojeći poziv na rezervaciju/Poziv na rezervaciju već prihvaćen!", "Greška", 3);
            else if(response.status == 401)
                getToastr("Nedostatak privilegije za ovu aktivnost!", "Greška", 3);
            else if(response.status == 406)
                getToastr("Ne možete prihvatiti poziv na rezervaciju, jer je do njenog početka preostalo 30min ili manje!", "Greška", 3);
            else
                getToastr("Neuspešno prihvatanje poziva na rezervaciju! \nStatus: " + response.status, "", 3);
        }
    });
}

function removeInvitation(inviteId, tr){
    $.ajax({
        url: 'registeredUser/removeInvitation',
        type: 'DELETE',
        contentType: 'text/plain',
        data: inviteId,
        beforeSend: function(request){
            request.setRequestHeader('Authorization', localStorage.getItem('currentUserToken'));
        },
        success: function(data, textStatus, response) {
            localStorage.setItem('currentUserToken', response.getResponseHeader('Authorization'));
            tr.remove();
        },
        error: function(response){
            if(response.status == 400)
                getToastr("Nepostojeći poziv na rezervaciju/Poziv na rezervaciju još uvek nije prihvaćen!", "Greška", 3);
            else if(response.status == 401)
                getToastr("Nedostatak privilegije za ovu aktivnost!", "Greška", 3);
            else if(response.status == 406)
                getToastr("Ne možete otkazati svoj dolazak na rezervaciju, jer je do njenog početka preostalo 30min ili manje!", "Greška", 3);
            else
                getToastr("Neuspešno otkazivanje dolaska na rezervaciju! \nStatus: " + response.status, "", 3);
        }
    })
}

function rejectInvitation(idInvite, tr){
    $.ajax({
        url: 'registeredUser/ignoreInvitation',
        type: 'PUT',
        contentType: 'text/plain',
        data: idInvite,
        beforeSend: function(request){
            request.setRequestHeader('Authorization', localStorage.getItem('currentUserToken'));
        },
        success: function(data, textStatus, response){
            localStorage.setItem('currentUserToken', response.getResponseHeader('Authorization'));
            tr.remove();
        },
        error: function(response){
            if(response.status == 400)
                getToastr("Nepostojeći poziv na rezervaciju/Poziv na rezervaciju već prihvaćen!", "Greška", 3);
            else if(response.status == 401)
                getToastr("Nedostatak privilegije za ovu aktivnost!", "Greška", 3);
            else
                getToastr("Neuspešno odbijanje poziva na rezervaciju! \nStatus: " + response.status, "", 3);
        }
    });
}


$(document).on('click', '#remove-invitation-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var inviteId = form.find("input[type=hidden]").val();

    var td = form.parent();
    var tr = td.parent();
    removeInvitation(inviteId, tr);
});

$(document).on('click', '#accept-invitation-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var td = form.parent();
    var reservationId = td.attr('id');
    var tdIgnore = td.next();

    var idInvite = form.find("input[type=hidden]").val();
    acceptInvitation(reservationId, idInvite, td, tdIgnore);
});

$(document).on('click', '#reject-invitation-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var tr = form.parents("tr");

    var idInvite = form.find("input[type=hidden]").val();
    rejectInvitation(idInvite, tr);
});