function getPersonalReservation(){
    $.ajax({
        url: 'registeredUser/getAllPersonalReservation',
        type: 'GET',
        dataType: 'json',
        beforeSend: function(request){
            request.setRequestHeader('Authorization', localStorage.getItem('currentUserToken'));
        },
        success: function(data, textStatus, response){
            localStorage.setItem('currentUserToken', response.getResponseHeader('Authorization'));
            renderPersonalReservation(data);
        },
        error: function(response){
            if(response.status == 401)
                getToastr("Not authorized for this activity", "Greška", 3);
            else
                getToastr("An error occured while fetchng reservations! \nStatus: " + response.status, "", 3);
        }
    });
}

function renderPersonalReservation(data){
    var reservationList = (data.reservations == null) ? [] : (data.reservations instanceof Array ? data.reservations : [data.reservations]);

    var reservationTableBody = $('#reservation-list-tickets').find("tbody");
    $('#reservation-list-table').find("thead").find("tr").append("<th class='col-md-1'> </th>");

    $.each(reservationList, function (index, reservation) {
        var dateObject = new Date(reservation.date);
        var date = dateObject.getDate().toString() + "/" + (dateObject.getMonth() + 1).toString() + "/" + dateObject.getFullYear().toString();
        var timeObject = new Date(reservation.startTime);
        var time = timeObject.getHours().toString() + ":" + timeObject.getMinutes().toString();
        var trReservation = "<tr>" +
                "<td>" + reservation.restaurant.name + "</td>" +
                "<td>" + reservation.restaurant.address + "</td>" +
                "<td>" + reservation.restaurant.city + "</td>" +
                "<td id='dateReservation'>" + date + "</td>" +
                "<td id='timeReservation'>" + time + "</td>" +
                "<td>" +
                    "<form>" +
                        "<input type='hidden' value='" + reservation.id + "'>" +
                        "<button class='btn btn-primary reservation-details-button'>" +
                            "<span class='glyphicon glyphicon-info-sign'></span> Detailed" +
                        "</button>" +
                    "</form>" +
                "</td>" +
                "<td>" +
                    "<form>" +
                        "<input type='hidden' value='" + reservation.id + "'>" +
                        "<button class='btn btn-danger reservation-remove-button'>" +
                            "<span class='glyphicon glyphicon-remove'></span> Cancel" +
                        "</button>" +
                    "</form>" +
                "</td>" +
            "</tr>";
        reservationTableBody.append(trReservation);
    });
}

function removeReservation(reservationId, tr){
    $.ajax({
        url: '/registeredUser/removeReservation',
        type: 'DELETE',
        contentType: 'text/plain',
        data: reservationId,
        beforeSend: function(request){
            request.setRequestHeader('Authorization', localStorage.getItem('currentUserToken'));
        },
        success: function(data, textStatus, response) {
            localStorage.setItem('currentUserToken', response.getResponseHeader('Authorization'));
            tr.remove();
            getToastr("Uspešno ste otkazali svoju rezervaciju!", "", 1);
        },
        error: function(response){
            if(response.status == 400)
                getToastr("Ne možete ukloniti svoju rezervaciju, jer je do početka iste preostalo 30min ili manje!", "Greška", 3);
            else if(response.status == 401)
                getToastr("Nedostatak privilegije za ovu aktivnost!", "Greška", 3);
            else if(response.status == 403)
                getToastr("Nepostojeća rezervacija u listi Vaših ličnih rezervacija", "Greška", 3);
            else
                getToastr("Neuspešno otkazivanje rezervacije! \nStatus: " + response.status, "", 3);
        }
    });
}

function datecompare(date1, sign, date2) {
    var day1 = date1.getDate();
    var mon1 = date1.getMonth();
    var year1 = date1.getFullYear();
    var day2 = date2.getDate();
    var mon2 = date2.getMonth();
    var year2 = date2.getFullYear();
    if (sign === '===') {
        if (day1 === day2 && mon1 === mon2 && year1 === year2) return true;
        else return false;
    }
    else if (sign === '>') {
        if (year1 > year2) return true;
        else if (year1 === year2 && mon1 > mon2) return true;
        else if (year1 === year2 && mon1 === mon2 && day1 > day2) return true;
        else return false;
    }
}

$(document).on('click', '.reservation-details-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var reservationId = form.find("input[type=hidden]").val();
    window.location.replace("personalReservation.html?id=" + reservationId);
});

$(document).on('click', '.reservation-remove-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var tr = form.parents("tr");

    var dateString = tr.find("td[id=dateReservation]").text();
    var dateTokens = dateString.split("/");
    var date = new Date(parseInt(dateTokens[2]), parseInt(dateTokens[1])-1, parseInt(dateTokens[0]));
    var dateNow = new Date();
    var dateTimeNow = new Date();
    dateNow.setHours(0,0,0,0);

    if(datecompare(dateNow, '>', date)){
        getToastr("Datum rezervacije koju želite da otkažete je prošao!", "", 3);
        return false;
    }

    else if(datecompare(dateNow, '===', date)){
        var time = tr.find("td[id=timeReservation]").text();
        var timeTokens = time.split(":");
        var reservationMinutes = parseInt(timeTokens[0])*60 + parseInt(timeTokens[1]);
        var nowMinutes = parseInt(dateTimeNow.getHours())*60 + parseInt(dateTimeNow.getMinutes());

        if(reservationMinutes - nowMinutes <= 30){
            getToastr("Rezervacija se ne može otkazati 30min pre njenog početka!", "", 3);
            return false;
        }
    }

    var reservationId = form.find("input[type=hidden]").val();
    removeReservation(reservationId, tr);
});


$(document).ready(function(){
    var reservationType = localStorage.getItem('reservationType');
    localStorage.removeItem('reservationType');
    if(reservationType == "personal")
        getPersonalReservation();
    else if(reservationType == "invited")
        getFriendsReservation();
});