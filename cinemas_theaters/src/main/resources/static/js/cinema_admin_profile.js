$(document).ready(function(){
	getAdminTheaters();
    fillAdminInfo();
});

function fillAdminInfo(){
    var user2 = JSON.parse(localStorage.getItem('currentUser'));
    alert(localStorage.getItem('currentUser'));
    alert("nakon prethodnog");
    var ime = user2["name"];
    var prezime = user2["lastname"];
    var email = user2["email"];
    document.getElementById("name-lastname").innerHTML = ime+" " + prezime;
    document.getElementById("email").innerHTML =email;
}




$(document).on('click', '#edit-info-btn', function(e){
    var name =document.getElementById("admin-name").value;
    var lastname = document.getElementById("admin-lastname").value;
    var password = document.getElementById("admin-password").value;
    

    
    var jsss = {
        'name': name,
        'lastname' : lastname,
        'password' : password,
    }

    var url = "/registeredUser/admin-edit/"+ JSON.parse(localStorage.getItem('currentUser'))['id'] ;
    if (name == "" || lastname == "" || password== ""){
      getToastr('Empty Field',"Dont't hurry!", 2);
        return;
    }

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
        $('#adminInfoModal').modal('toggle');
        var user = {
            "name" :data['name'],
            "lastname":data['lastname'],
            "password" : data['password'],
            "type" : JSON.parse(localStorage.getItem('currentUser'))['type'],
            "username": JSON.parse(localStorage.getItem('currentUser'))['username'],
            "id":  JSON.parse(localStorage.getItem('currentUser'))['id'],
            "email":JSON.parse(localStorage.getItem('currentUser'))['email']

        }


        // OVO GOVNO NECE DA ME SLUSA. NECE DA UPDATUJE localstorage ......prso sam... ode tri sata
        localStorage.setItem('currentUser', JSON.stringify(user));
        document.getElementById("name-lastname").innerHTML = data["name"]+ " " + data["lastname"];
      },
      error: function(response){
        if(response.status == 401){
          getToastr('you can\'t edit this show, it\'s not yours!','Oh no!',  3);
        }
        if(response.status == 409){
          getToastr('you can\'t edit this show, someone has ticket for it!','Oh no!',  3);
        }
        else{
            if(response.status == 401){
          getToastr('you can\'t edit these info, it\'s not yours!','Oh no!',  3);
        }
        }
      }
  });

});

function getAdminTheaters(){
    $.ajax({
                url: "/theatre/getTheatersByAdmin",
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



function forward_admins_theaters(theaters){
	$card = $(".theater-card").first();
    $(".cards").empty();
    theaters.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $card.find("#visit-btn").attr("value","")
        $card.attr("id", "theater_id_" + element["id"]);
        $card.find("#rateYo").rateYo({ 
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
}




