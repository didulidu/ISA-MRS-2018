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
                    getToastr("Not authorized for this activity", "Error", 3);
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
        var currentDate = new Date();
        var date = dateObject.getDate().toString() + "/" + (dateObject.getMonth() + 1).toString() + "/" + dateObject.getFullYear().toString();
        //var timeObject = new Date(reservation.startTime);
        var time = dateObject.getHours().toString() + ":" + dateObject.getMinutes().toString();
        var trInvitation = "<tr>" +
                "<td>" + invitation.invitersName + "</td>" +
                "<td>" + invitation.invitersLastname + "</td>" +
                "<td>" + invitation.theatreName + "</td>" +
                "<td>" + invitation.projectionShowTitle + "</td>" +
                "<td id='dateReservation'>" + date + "</td>" +
                "<td id='timeReservation'>" + time + "</td>";
        if(dateObject>currentDate){
            if (invitation.status == "Pending"){
                    trInvitation += "<td id='" + invitation.reservationId + "'>" +
                                         "<form>" +
                                             "<input type='hidden' value='" + invitation.id + "'>" +
                                             "<button id = 'accept-invitation-button' class='btn btn-danger reservation-remove-button'>" +
                                                 "<i class='fas fa-check-circle'></i>" +
                                             "</button>" +
                                         "</form>" +
                                     "</td>" +
                                     "<td>" +
                                         "<form>" +
                                             "<input type='hidden' value='" + invitation.id + "'>" +
                                             "<button id = 'cancel-invitation-button' class='btn btn-danger reservation-remove-button'>" +
                                                "<i class='fas fa-minus-circle'></i>" +
                                             "</button>" +
                                         "</form>" +
                                     "</td>" +
                                 "</tr>";
            }else{
                    trInvitation += "<td id='" + invitation.reservationId + "'>" +
                                    "<form>" +
                                        "<input type='hidden' value='" + invitation.id + "'>" +
                                        "<button id = 'remove-invitation-button'  class='btn btn-danger reservation-remove-button'>" +
                                            "<i class='fas fa-minus-circle'></i>" +
                                        "</button>" +
                                    "</form>" +
                                "</td>";
            }
        }
        reservationTableBody.append(trInvitation);
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
            td.append("<td>" +
                    "<form>" +
                        "<input type='hidden' value='" + idInvite + "'>" +
                        "<button id='remove-invitation-button' class='btn btn-danger reservation-remove-button'>" +
                            "<i class='fas fa-minus-circle'></i>" +
                        "</button>" +
                    "</form>" +
                "</td>");
        },
        error: function(response){
            if(response.status == 400)
                getToastr("Invitation already accepted or non existent!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized!", "Error", 3);
            else if(response.status == 406)
                getToastr("Invitation can't be accepted 30 minutes before the reservation!", "Error", 3);
            else
                getToastr("Invitation not accepted! \nStatus: " + response.status, "", 3);
        }
    });
}

function cancelInvitation(inviteId, tr){
    $.ajax({
        url: 'registeredUser/cancelInvitation',
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
                getToastr("Invitation not accepted or non existent!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized!", "Error", 3);
            else if(response.status == 406)
                getToastr("Invitation can't be declined 30 minutes before the reservation!!", "Error", 3);
            else
                getToastr("Invitation not removed! \nStatus: " + response.status, "", 3);
        }
    })
}

function removeInvitation(idInvite, tr){
    $.ajax({
        url: 'registeredUser/removeInvitation',
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
                getToastr("Invitation not accepted or non existent!!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized!", "Error", 3);
            else
                getToastr("Invitation not declined! \nStatus: " + response.status, "", 3);
        }
    });
}


$(document).on('click', '#cancel-invitation-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var inviteId = form.find("input[type=hidden]").val();

    var td = form.parent();
    var tr = td.parent();
    cancelInvitation(inviteId, tr);
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

$(document).on('click', '#remove-invitation-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var tr = form.parents("tr");

    var idInvite = form.find("input[type=hidden]").val();
    removeInvitation(idInvite, tr);
});

$(document).on('click', '#home-button', function(e){
   e.preventDefault();
   window.location.href = "index.html";
});