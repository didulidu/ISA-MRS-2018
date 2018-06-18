var show_id;
var title;
$(document).ready(function(){
  var elements = document.URL.split("&");
  show_id = elements[0].split("=")[1];
  title = elements[1].split("=")[1];
  document.getElementById("title").innerHTML = title;

  $.ajax({
		url: "show/getProjection/"+show_id,
        type: "GET",
        dataType: "json",
        success: function(data){
          if(localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="RegisteredUser")
          addAdminWidgets()
          getHalls();
        	forward_projections(data);
        },
        error: function (response) {
            alert("Ne radi profil filma: "+ response.status);
            return null;
        }
	});
});

function getHalls(){
  alert("hall/getHalls/"+localStorage.getItem("theater"));
  $.ajax({
    url: "projection/getHalls/"+localStorage.getItem("theater"),
        type: "GET",
        dataType: "json",
        success: function(data){
          alert(JSON.stringify(data))
          forward_halls(data);
        },
        error: function (response) {
            alert("Ne rade sale: "+ response.status);
            return null;
        }
  });
}




function addAdminWidgets(){
  $('.body').append("<button type='button' class='btn btn-success add-projection' >Add projection</button>")
}

function sendProjection(projectionJson){
      $.ajax({
                url: "/theatre/add",
                type: "POST",
                dataType: "json",
                beforeSend: function(request){
                   request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
                },
                success: function(data){
                    forward_admins_theaters(data);
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });        

}

function forward_projections(data){
	var table = "<table border=\"2\">"
	+"<tr>"
    	+"<th>Date and time</th>"
    	+"<th>Hall</th>"
    	+"<th>Price</th>"
  		if(localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="RegisteredUser")
			table+="<th>"+" :) "+"</th>"
      else if (localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="TheaterAndCinemaAdmin"){
      table+="<th>"+"<button type='button' data-toggle=\"modal\" data-target=\"#exampleModal\" class='btn btn-success add-new-projection' >New</button>"+"</th>"        
      }
		table+="</tr>"
  	data.forEach(function(projection){
  		table+="<tr>"
    			+"<td>"+projection["date"]+"</td>"
    			+"<td>"+projection["hall"]["name"]+"</td>"
    			+"<td>"+projection["price"]+"</td>"
		if(localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="RegisteredUser")
			table+="<td>"+"<button type='button' onclick='foo("+projection["id"]+")' class='btn btn-success add-reservation' >Reservation</button>"+"</td>"
		else if(localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="TheaterAndCinemaAdmin"){
      table+="<td>"+"<button type='button' onclick='editProjectionWrapper("+projection["id"]+")' data-toggle=\"modal\" data-target=\"#editProjectionModal\" class='btn btn-warning edit-projection' >Edit</button>"+"</td>"
      table+="<td>"+"<button type='button' onclick='deleteProjection("+projection["id"]+")' class='btn btn-danger delete-projection' >Delete</button>"+"</td>"
    }

    table+="</tr>"
  	});
  	table+="</table>"
    $("#projection-container").empty();
  	$("#projection-container").append(table);
}

function forward_halls(data){
  data.forEach(function(element){
    $(".hall").append("<option value='"+element["id"]+"'>"+element.name+"</option>")
  
  });
}




function foo(id){
	if(localStorage.getItem("currentUser")!=undefined){
        if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "RegisteredUser")
            window.location.replace("seat_selection.html?id=" + id);  
	}
}



$(document).on('click', '#edit-projection',function(e) {
    var date = new Date(document.getElementById("date-time-edit").value)
    var hall = $("#hall-edit :selected").val();
    var priceprj = document.getElementById("price-edit").value;
    var datetime = document.getElementById("date-time-edit").value;
  
    var date = new Date(document.getElementById("date-time-edit").value)
    if(date.getTime()<=new Date().getTime()){
      getToastr('Please come back in '+new Date(),'Oops! Looks like you live in past!',  3);
      return;
    }
    var podaci = "{\"hallId\": "+hall
    podaci+=", \"price\": "+priceprj
    podaci+=", \"showId\":"+show_id
    podaci+=", \"date\": \""+datetime+"\"}"
    var url = "/projection/edit/"+chosenId; 

    $.ajax({
      type: "POST",
      url: url,
      contentType: "application/json",
      data: podaci,
      dataType: "json",
      beforeSend: function(request){
        request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
      },
      success: function(data){
          getToastr('Projection edited','Done!',  1);
          $('#editProjectionModal').modal('toggle');
          forward_projections(data);
      },
      error: function(response){
        if(response.status == 401){
          getToastr('you can\'t edit this projection, it\'s not yours!','Oh no!',  3);
        }
        if(response.status == 409){
          getToastr('you can\'t edit this projection, someone has ticket for it!','Oh no!',  3);
        }
      }
  });
});

function deleteProjection(id){
  var podaci="{ \"showId\":"+show_id+"}"

  $.ajax({
      type: "POST",
      url: "/projection/remove/"+id,
      contentType: "application/json",
      data: podaci,
      dataType: "json",
      beforeSend: function(request){
        request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
      },
      success: function(data){
          forward_projections(data);
      },
      error: function(response){
        alert("Termin zauzet")
      }
  });
}

var chosenId = undefined;
function editProjectionWrapper(id) {
  chosenId =id;
}


$(document).on('click', '#create-projection',function(e) {
    var date = new Date(document.getElementById("date-time").value)
    var hall = $("#hall :selected").val();
    var priceprj = document.getElementById("price").value;
    var datetime = document.getElementById("date-time").value;
  
    var podaci = "{\"hallId\": "+hall
    podaci+=", \"price\": "+priceprj
    podaci+=", \"showId\":"+show_id
    podaci+=", \"date\": \""+datetime+"\"}"
    var url = "/projection/save";

    if(date.getTime()<=new Date().getTime())
      getToastr('Please come back in '+new Date(),'Oops! Looks like you live in past!',  3);
    else if (hall == null || priceprj == null || priceprj == "" || datetime==""){
      getToastr('Empty Field',"Dont't hurry!", 2);
    }
    else{
    $.ajax({
      type: "POST",
      url: url,
      contentType: "application/json",
      data: podaci,
      dataType: "json",
      beforeSend: function(request){
        request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
      },
      success: function(data){
      getToastr('Projection created','Done!',  1);     
      $('#exampleModal').modal('toggle');   
          forward_projections(data);
      },
      error: function(response){
      getToastr('Hall is already taken by another projection on time you specified','Oops!',  3);
      }
  });}
});

