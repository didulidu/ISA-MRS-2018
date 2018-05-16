var allShows = {};
var filterOn = false;


function addTheaterAdminWidgets(){
    if(localStorage.getItem("currentUser") !=undefined){
        if(JSON.parse(localStorage.getItem("currentUser"))["type"]=="TheaterAndCinemaAdmin"){
            $("#nav-repertoire").append("<button type='button' class='btn btn-success' id='add-show'>Add Show</button>")
        }
    }
}

$(document).on('click', ".card", function(){
    alert("Saimaaaa");
});

$("#add-show").on('click', function(){
    alert("Dodati funkcije za dodavanje filmova");
});


$(document).ready(function(){
        var id = localStorage.getItem("theater");
       // localStorage.setItem("theater", undefined);
        getProfileData(id, forward_profile);
        $('#nav-tab a').on('click', function (e) {
            e.preventDefault()
            addTheaterAdminWidgets();
            getAllShows(id, showRepertoire);
            $(this).tab('show')
        })
});


$(document).on('click', '#home-btn',function(e){
            e.preventDefault();
                if(localStorage.getItem("currentUser")!=undefined)
                    if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "TheaterAndCinemaAdmin")
                        window.location.replace("cinema_admin_profile.html");
                    else
                        window.location.replace("index.html");
            else
                window.location.replace("index.html");
            return false;
        });

// dobavlja sve show-ove za teatar
function getAllShows(id,callback){
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
            callback();
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
    addTheaterAdminWidgets();
    if(showsList.length){
        addCards(showsList);
    }
    else {
        if(filterOn == false)
            getToastr("No registered cinemas/theaters!", "", 2);
    }

}


function addCards(showsList){
        var Kartice = "<div class=\"cards\" id = \"shows\" style=\"display:flex; flex-wrap: wrap;\">"
           showsList.forEach(function(show){
            alert(JSON.stringify(show));
            var Kartica = "<div class=\"card\" style=\"width: 20%;\" id=\""+show["id"]+"\">"
            +"<img class=\"card-img-top\" src=\""+show["posterURL"]+"\" alt=\"Card image cap\">"
            +"<div class=\"card-body\" style=\"height: 140px;\">"
            +"<h5 class=\"card-title\">"+show["title"]+"</h5>"
            +"<p class=\"card-text\">"+show["genre"]+"</p>"
            +"<p class=\"card-duration\">"+show["duration"]+"</p>"
            +"</div>"
            +"<div id=\"rateYo\"></div>"
            +"</div>";
            Kartice+=Kartica;
        });
        Kartice+="</div>";
        $("#nav-repertoire").append(Kartice);
}