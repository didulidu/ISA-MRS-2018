var allShows = {};
var filterOn = false;



$(document).ready(function(){
        var id = localStorage.getItem("theater");
        localStorage.setItem("theater", undefined);
        getProfileData(id, forward_profile);
        getAllShows(id);

});

$("a[href='#nav-repertoire']").on('shown.bs.tab', function(){
    alert("uso ovde")
    $("#nav-tabContent").hide();
    showRepertoire();

});

$(document).on('click', '#home-btn',function(e){
            e.preventDefault();
            if(localStorage.getItem("currentUser")!=undefined){
                if(localStorage.getItem("currentUser")!=undefined)
                    if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "TheaterAndCinemaAdmin")
                        window.location.replace("cinema_admin_profile.html");
                    else
                        window.location.replace("index.html");
            }
            else
                window.location.replace("index.html");
            return false;
        });



// dobavlja sve show-ove za teatar
function getAllShows(id){
	$.ajax({
		url: "/show/getRepertoire/" + id,
		type: "PUT",
		dataType: "json",
		//data: {id: id},
		//beforeSend: function(request){
		//          request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
		//        },
		success: function(data){
			//localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
			allShows = data;
			showRepertoire();
		},
		error: function(response){
			if(response.status == 401)
				getToastr("Not authorized for the selected activity!", "Error", 3);
			else
				getToastr("Theaters couldn't be fetched! \nStatus: " + response.status, "", 3);
		}
	});
}

function getProfileData(id, callback){
        $.ajax({
        url: "theatre/"+id,
        type: "GET",
        dataType: "json",
        success: function(data){
            callback(JSON.stringify(data));
        },
        error: function (response) {
            alert("Ne radi profil: "+ response.status);
            return null;
        }
        });
}

function forward_profile(data){
        dataJSON = JSON.parse(data);
        fillProfileWithInfo(dataJSON);
}


function fillProfileWithInfo(data){
    document.getElementById("cinema-name").innerHTML=dataJSON["name"];
    document.getElementById("cinema-text").innerHTML=dataJSON["description"];
    document.getElementById("cinema-address").innerHTML=dataJSON["address"];

}

$(document).on('click', '#button-show-details', function(){
   var form = $(this).parents("form");
   var show_id = form.find("input[type=hidden]").val();
   if(localStorage.getItem("currentUser")!=undefined){
       if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "RegisteredUser")
           window.location.replace("seat_selection.html?id=" + show_id);
       
    }
});

function showRepertoire(){


    var showsList = [];
    if(filterOn == false)
        showsList = (allShows == null) ? [] : (allShows instanceof Array ? allShows : [allShows]);
    else
        showsList = filterShowsList;

    $('#nav-repertoire').empty();

    if(showsList.length){
        var numRows = Math.ceil(showsList.length/3);
        for(var i=0; i<numRows; i++)
        {
            $('#nav-repertoire').append("<div class='row " + i + "' style='padding-bottom: 2%'></div>");
        }

        var numberOfRow = 0;
        var numberOfCardInRow = 0;
        for(var j=0; j<showsList.length; j++){
            if(numberOfCardInRow == 3)
            {
                numberOfRow++;
                numberOfCardInRow = 0;
            }
            var newCard = "<div class='card col-sm-4' id='repertoire-card-" + showsList[j].id + "'>" +
                    "<img class='card-img-top' src='" + showsList[j].posterURL + "' style='height: 5%; width: 5%; vertical-align: left;'>" +
                    "<div class='card-block'>" +

                        "<h4 class='card-title'><b>" + showsList[j].title + "</b></h4>" +
                        "<p class='card-text'>" + showsList[j].genre + "</p>" +
                    "</div>" +
                    "<ul class='list-group list-group-flush'>" +
                        "<li class='list-group-item'><b>Duration: </b>" + showsList[j].duration + "</li>";
            //provera da li je show ocenjen
            if(showsList[j].numberOfRates != 0) {
                var averageRating = showsList[j].averageRating;
                var row = "<div class='row' style='margin-left: 0px!important;'>" +
                    "<b>Show rating: </b>";
                var starFull = true;
                var starHalf = false;
                for(var k=1; k<=5; k++)
                {
                    if(k == 1 && averageRating < 1) {
                        row += "<img src='img/star-half.png' style='width: 5%; height: 5%; vertical-align: top'>";
                        starFull = false;
                    }
                    else {
                        if(starFull)
                            row += "<img src='img/star-on.png' style='width: 5%; height: 5%; vertical-align: top'/>";
                        else if(starHalf)
                            row += "<img src='img/star-half.png' style='width: 5%; height: 5%; vertical-align: top'>";
                        else
                            row += "<img src='img/star-off.png' style='width: 5%; height: 5%; vertical-align: top'>";

                        averageRating--;
                        if (averageRating >= 1) {
                            starFull = true;
                            starHalf = false;
                        }
                        else if (averageRating > 0) {
                            starFull = false;
                            starHalf = true;
                        }
                        else
                        {
                            starFull = false;
                            starHalf = false;
                        }
                    }
                }
                row += "</div>";
                newCard += "<li class='list-group-item'>" + row + "</li>";
            }
            else
                newCard += "<li class='list-group-item'><b>Show rating: </b>Show not yet rated!</li>";

            //newCard += "<button id = " + showsList[j].id + " class = 'show-profile-button'>  </button>";

            // ukoliko je registrovan korisnik, ispisuje se i ocena
            // koju su dali on i njegovi prijatelji
            if(localStorage.getItem("currentUserRole") == "RegisteredUser") {
                if(showsList[j].averageFriendRating != 0) {
                    var averageRatingFriends = showsList[j].averageFriendRating;
                    var rowFriends = "<div class='row' style='margin-left: 0px!important;'>" +
                        "<b>Your and your friend's rating: </b>";
                    var starFull = true;
                    var starHalf = false;
                    for(var k=1; k<=5; k++)
                    {
                        if(k == 1 && averageRatingFriends < 1) {
                            rowFriends += "<img src='img/star-half.png' style='width: 5%; height: 5%; vertical-align: top'>";
                            starFull = false;
                        }
                        else {
                            if(starFull)
                                rowFriends += "<img src='img/star-on.png' style='width: 5%; height: 5%; vertical-align: top'/>";
                            else if(starHalf)
                                rowFriends += "<img src='img/star-half.png' style='width: 5%; height: 5%; vertical-align: top'>";
                            else
                                rowFriends += "<img src='img/star-off.png' style='width: 5%; height: 5%; vertical-align: top'>";

                            averageRatingFriends--;
                            if (averageRatingFriends >= 1) {
                                starFull = true;
                                starHalf = false;
                            }
                            else if (averageRatingFriends > 0) {
                                starFull = false;
                                starHalf = true;
                            }
                            else
                            {
                                starFull = false;
                                starHalf = false;
                            }
                        }
                    }
                    rowFriends += "</div>";
                    newCard += "<li class='list-group-item'>" + rowFriends + "</li>";
                }
                else
                    newCard += "<li class='list-group-item'><b>Your friend's and yours rating: </b>Show not yet rated</li>";
            }

            newCard += "</ul>" +
                    "<div class='card-block'>" +
                        "<form class='form-inline'>" +
                            "<input type='hidden' class='form-control' value='" + showsList[j].id + "'>" +
                            "<button type='button' class='btn btn-warning' id='button-show-details'>";
            if(localStorage.getItem("currentUser")!=undefined){
            if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "RegisteredUser")
                newCard += "<span class='glyphicon glyphicon-plus-sign'></span> Reservation";
            else if(localStorage.getItem("currentUserRole") == "Manager")
                newCard += "<span class='glyphicon glyphicon-info-sign'></span> More info";
            }
            newCard += "</button>" +
                        "</form>" +
                    "</div>" +
                "</div>";
            $("." + numberOfRow).append(newCard);
            numberOfCardInRow++;
        }
    }
    else {
        if(filterOn == false)
            getToastr("No registered cinemas/theaters!", "", 2);
    }
}