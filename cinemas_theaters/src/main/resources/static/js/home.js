var allShows = {};
var filterOn = false;

$(document).ready(function(){
	//getAllShows();
});


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


$(document).on('click', '#login-button', function(e){
    e.preventDefault();
    window.location.replace("login.html")
});

$(document).on('click', '#sign-up-button', function(e){
    e.preventDefault();
    window.location.replace("registration.html")
});

$(document).ready(function(){
    if(sessionStorage.getItem("show")==undefined){
        getTheaters();
    }else if (sessionStorage.getItem("show") == "home"){
        getTheaters();
    }else if (sessionStorage.getItem("show") == "profile"){

        alert("Profil refresovan -> " + sessionStorage.getItem("profile"));
        forward_profile(sessionStorage.getItem("profile"), showProfile);
    }
    

// if (sessionStorage.getItem('profile') != undefined){
//         document.getElementById("cinema-name").innerHTML=JSON.parse(sessionStorage.getItem('profile'))["name"];
//         // dodati ostale info u profil bioskopa
//         showProfile();
//     }
//     else{
//         if (sessionStorage.getItem('cinemas')!= undefined){
//             forward_theatres(JSON.parse(sessionStorage.getItem('cinemas')));
//         }else{
//             $.ajax({
//                     url: "/theatre/getAllTheatres",
//                     type: "GET",
//                     dataType: "json",
//                     success: function(data){
//                         sessionStorage.setItem('cinemas', JSON.stringify(data));
//                         forward_theatres(data, showHomePage);
//                     },
//                     error: function (response) {
//                         alert("Ne radi: "+ response.status)
//                     }
//                 });            
//         }

//     }

    $(document).on('click', '.card',function(e){
        e.preventDefault(); 
        alert("Kliknuto na bioskop");
        var name = this.querySelector(".card-title").innerHTML;
        var t = this;
        var id = this.id.split("theater_id_")[1];
        getAllShows(parseInt(id));
        getProfileData(name, forward_profile);
        return false;
    });




});

$(document).on('click', '#home-btn',function(e){
            alert("Nazad na home page");
            e.preventDefault(); 
            sessionStorage.setItem("profile", null); 
            getTheaters();
            showHomePage();
            return false;
        });

function getTheaters(){
    $.ajax({
                url: "/theatre/getAllTheatres",
                type: "GET",
                dataType: "json",
                success: function(data){
                    sessionStorage.setItem("cinemas", JSON.stringify(data));
                    forward_theatres(JSON.stringify(data), showHomePage);
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });        
}


function getProfileData(name, callback){
    
        alert("dobavljanje bioskopa iz baze");
        $.ajax({
        url: "theatre/visit/"+name,
        type: "GET",
        dataType: "json",
        success: function(data){
            callback(JSON.stringify(data), showProfile);
        },
        error: function (response) {
            alert("Ne radi profil: "+ response.status);
            return null;
        }
        });
}

 function forward_profile(data, callback){
        sessionStorage.setItem('profile',data);
        dataJSON = JSON.parse(data);
        fillProfileWithInfo(dataJSON);    
        callback();
    }


function fillProfileWithInfo(data){
    document.getElementById("cinema-name").innerHTML=dataJSON["name"];
    document.getElementById("cinema-text").innerHTML=dataJSON["description"];
    document.getElementById("cinema-address").innerHTML=dataJSON["address"];

}

function showProfile(){
    sessionStorage.setItem("show","profile");
    $(".cards").hide();
    $("#profile").show();
}

function showHomePage(){
    sessionStorage.setItem("show","home");
    $(".cards").show();
    $("#profile").hide();

}


function forward_theatres(theaters, callback){
    // poceo da menjam da upacuje html u dom
    // var newTheater = "<div class='card' style='width: 20%;'>"+
    //     "<img class='card-img-top' src='images/Arena.jpg' alt='Card image cap'>"+
    //     "<div class='card-body' style='height: 140px;'>"+
    //     "<h5 class='card-title'>"+element["name"]+"</h5>"+
    //     "<p class='card-text'>"+element["description"]+"</p></div>"
    //     "<div id='rateYo'></div></div>"
    sessionStorage.setItem("cinemas",theaters);
    theatersJSON = JSON.parse(theaters);
    $card = $(".card").first();
    $(".cards").empty();
    theatersJSON.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $card.find("#visit-btn").attr("value","")
        $card.attr("id", "theater_id_" + element["id"]);
        $card.data("id", element["id"]);
        $card.find("#rateYo").rateYo({ // Sutra cu rate da ubacim u beanove, zasad su default vrednosti.
        starWidth: "20px",
        multiColor: {
          "startColor": "#FF0000", //RED
          "endColor"  : "#00FF00"  //GREEN
        },
        rating: element["rate"],
        readOnly: true
    });

        if(element["type"] == "Cinema"){
                    $("#cinemas").append($card);
                }else{
                    $("#theaters").append($card);
                }
        $card = $(".card").first().clone();
    });
    callback();
}


$("#nav-repertoire-tab").on('click', function(){

    $("#nav-tabContent").hide();
    showRepertoire();

});


function showRepertoire(){
    var showsList = [];
    if(filterOn == false)
        showsList = (allShows == null) ? [] : (allShows instanceof Array ? allShows : [allShows]);
    else
        showsList = filterShowsList;

    $('#repertoire-container-div').empty();

    if(showsList.length){
        var numRows = Math.ceil(showsList.length/3);
        for(var i=0; i<numRows; i++)
        {
            $('#repertoire-container-div').append("<div class='row " + i + "' style='padding-bottom: 2%'></div>");
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
                    "<img class='card-img-top' src='" + showsList[j].avatarUrl + "' style='width: 100%'>" +
                    "<div class='card-block'>" +
                        "<h4 class='card-title'><b>" + showsList[j].title + "</b></h4>" +
                        "<p class='card-text'>" + showsList[j].genre + "</p>" +
                    "</div>" +
                    "<ul class='list-group list-group-flush'>" +
                        "<li class='list-group-item'><b>Address: </b>" + showsList[j].duration + "</li>";
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


            // ukoliko je registrovan korisnik, ispisuje se i ocena
            // koju su dali on i njegovi prijatelji
            if(localStorage.getItem("currentUserRole") == "RegisteredUser") {
                if(showsList[j].averageFriendRating != 0) {
                    var averageRatingFriends = showsList[j].averageFriendRating;
                    var rowFriends = "<div class='row' style='margin-left: 0px!important;'>" +
                        "<b>Ocena Vaših prijatelja i Vas: </b>";
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
            if(localStorage.getItem("currentUserRole") == "Registered")
                newCard += "<span class='glyphicon glyphicon-plus-sign'></span> Reservation";
            else if(localStorage.getItem("currentUserRole") == "Manager")
                newCard += "<span class='glyphicon glyphicon-info-sign'></span> More info";
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