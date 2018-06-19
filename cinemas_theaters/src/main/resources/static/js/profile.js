var allShows = {};
var filterOn = false;


function addTheaterAdminWidgets(){
    if(localStorage.getItem("currentUser") !=undefined){
        if(JSON.parse(localStorage.getItem("currentUser"))["type"]=="TheaterAndCinemaAdmin"){
            $("#nav-repertoire").append("<button id=\"add-show\" type='button' data-toggle=\"modal\" data-target=\"#exampleModal\" class='btn btn-success add-new-projection' >New</button>")
            $("#nav-tab").append("<a class=\"nav-item nav-link\" id=\"nav-profile-tab\" data-toggle=\"tab\" href=\"#nav-profile\" role=\"tab\" aria-controls=\"nav-profile\" aria-selected=\"false\">Hall configuration</a>");
        }
    }
}
$(document).on('click', ".show-card", function(){
    var show_id = $(this).attr('id');
    var title = $(this).find('.card-title').html();
    window.location.replace("show_page.html?id="+show_id+"&title="+title);
});



$(document).ready(function(){
    
    var id = localStorage.getItem("theater");
    if(localStorage.getItem("currentUser") !=undefined){
        if(JSON.parse(localStorage.getItem("currentUser"))["type"]=="TheaterAndCinemaAdmin"){
            $("#nav-tab").append("<button type='button' class='btn btn-warning' id='settings-btn'data-toggle=\"modal\" data-target=\"#settingsModal\"><i class=\"fab fa-whmcs\"></i> Settings</button>")
        }
    }
       // localStorage.setItem("theater", undefined);
        getProfileData(id, forward_profile);
        $('#nav-tab a').on('click', function (e) {
            e.preventDefault()
            addTheaterAdminWidgets();
            getAllShows(id, showRepertoire);
            $(this).tab('show')
        })
});

// // OVO MENJAJ
// $(document).on('click', '#settings-btn',function(e){
//     e.preventDefault();
//     if(localStorage.getItem("currentUser")!=undefined)
//         if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "TheaterAndCinemaAdmin")
//             window.location.replace("cinema_admin_profile.html");
//         else
//             window.location.replace("index.html");
//     else
//         window.location.replace("index.html");
//     return false;
// });


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
        url: "/theatre/"+id,
        type: "GET",
        dataType: "json",
        success: function(data){
            callback(JSON.stringify(data));
        },
        error: function (response) {
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
            var Kartica = "<div class=\"card show-card\" style=\"width: 20%;margin: 5px;\" id=\""+show["id"]+"\">"
            +"<img class=\"card-img-top\"  style=\"max-height: 140px;\"  src=\""+show["posterURL"]+"\" alt=\"Card image cap\">"
            +"<div class=\"card-body\" style=\"height: 140px;\">"
            +"<h5 class=\"card-title\">"+show["title"]+"</h5>"
            +"<p class=\"card-text\"><h7>Genre: </h7>"+show["genre"]+"</p>"
            +"<p class=\"card-duration\"><h7>Duration: </h7>"+show["duration"]+"</p>"
            +"</div>"
            +"<div id=\"rateYo\"></div>"
            +"</div>";
            if(localStorage.getItem("currentUser") !=undefined){
             if(JSON.parse(localStorage.getItem("currentUser"))["type"]=="TheaterAndCinemaAdmin"){

            Kartica+="<div><button type='button' class='btn btn-danger' onclick='deleteShow("+show["id"]+")' id=\"delete-show\">Remove</button></div>"
            Kartica+="<div><button type='button' id = \"edit-show\" onclick='editShowWrapper("+show["id"]+")' data-toggle=\"modal\" data-target=\"#editShowModal\" class='btn btn-warning edit-show' >Edit</button></div>"
            }}
            Kartice+=Kartica;
        });
        Kartice+="</div>";
        $("#nav-repertoire").append(Kartice);
}


var chosenId = undefined;
function editShowWrapper(id) {
  chosenId =id;
}
$(document).on('click', '#create-show',function(e) {
   
    var title =document.getElementById("th-name").value;
    var description = document.getElementById("th-description").value;
    var actors = document.getElementById("th-actors").value;
    var genre = document.getElementById("th-genre").value;
    var directors = document.getElementById("th-directors").value;
    var duration = document.getElementById("th-duration").value;
    var poster_url =document.getElementById("th-posterurl").value;


    var podaci = "{\"title\": \""+title + "\""
    podaci+=", \"description\":  \""+description+ "\""
    podaci+=", \"actors\": \""+actors+ "\""
    podaci+=", \"directors\":\""+directors+ "\""
    podaci+=", \"duration\": "+duration
    podaci+=", \"poster_url\": \""+poster_url+ "\""
    
    var jsss = {
        'title': title,
        'description' : description,
        'actors' : actors,
        'genre' : genre,
        'directors' : directors,
        'duration' : duration,
        'posterURL' : poster_url 
    }

    podaci+=", \"genre\": \""+genre+"\"}"
    var url = "/show/save";

    if (title == null || description == null || actors == "" || genre=="" || directors=="" || duration=="" || poster_url==""){
        getToastr('Empty Field',"Dont't hurry!", 2);
    }

    else{

    $.ajax({
      type: "POST",
      url: url+"/"+localStorage.getItem("theater"),
      contentType: "application/json",
      data: JSON.stringify(jsss),
      dataType: "json",
      beforeSend: function(request){
        request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
      },
      success: function(data){
          getToastr('Show created','Done!',  1);     
          $('#settingsModal').modal('toggle'); 

           $('#nav-repertoire').empty();  
          addCards(data);
      },
      error: function(response){
        getToastr('Show with that name already exists','Oops!',  3);
      }
  });}
});



function deleteShow(id){
  var podaci="{ \"theatreId\":\""+localStorage.getItem("theater")+"\"}"
  $.ajax({
      type: "POST",
      url: "/show/remove/"+id,
      contentType: "application/json",
      data: podaci,
      dataType: "json",
      beforeSend: function(request){
        request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
      },
      success: function(data){
          getToastr('Show deleted','Done!',  1); 
           $('#nav-repertoire').empty();
          addCards(data);
      },
      error: function(response){
        getToastr('This show owns projections and could not be deleted','Oops!',  3);
      }
  });
}





$(document).on('click', '#edit-show-btn',function(e) {
    var title =document.getElementById("th-name-edit").value;
    var description = document.getElementById("th-description-edit").value;
    var actors = document.getElementById("th-actors-edit").value;
    var genre = document.getElementById("th-genre-edit").value;
    var directors = document.getElementById("th-directors-edit").value;
    var duration = document.getElementById("th-duration-edit").value;
    var poster_url =document.getElementById("th-posterurl-edit").value;


    var podaci = "{\"title\": \""+title + "\""
    podaci+=", \"description\":  \""+description+ "\""
    podaci+=", \"actors\": \""+actors+ "\""
    podaci+=", \"directors\":\""+directors+ "\""
    podaci+=", \"duration\": "+duration
    podaci+=", \"poster_url\": \""+poster_url+ "\""
    
    var jsss = {
        'id':chosenId,
        'title': title,
        'description' : description,
        'actors' : actors,
        'genre' : genre,
        'directors' : directors,
        'duration' : duration,
        'posterURL' : poster_url 
    }

    podaci+=", \"genre\": \""+genre+"\"}"
    var url = "/show/edit/"+ localStorage.getItem('theater') ;


    $.ajax({
      type: "POST",
      url: url,
      contentType: "application/json",
      data: JSON.stringify(jsss),
      dataType: "json",
      beforeSend: function(request){
        request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
      },
      success: function(data){
        getToastr('Show edited','Done!',  1);
        $('#editShowModal').modal('toggle');
        $('#nav-repertoire').empty();  
        addCards(data);
      },
      error: function(response){
        if(response.status == 401){
          getToastr('you can\'t edit this show, it\'s not yours!','Oh no!',  3);
        }
        if(response.status == 409){
          getToastr('you can\'t edit this show, someone has ticket for it!','Oh no!',  3);
        }
        else{
           getToastr('Conething went wrong','Oh no!',  3);   
        }
      }
  });
});