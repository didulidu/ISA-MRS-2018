


$(document).on('click', '#login-button', function(e){
    e.preventDefault();
    window.location.replace("login.html")
});

$(document).on('click', '#sign-up-button', function(e){
    e.preventDefault();
    window.location.replace("registration.html")
});

$(document).ready(function(){
        getTheaters();
    });
    

$(document).on('click', '.card',function(e){
    e.preventDefault();
    alert("Kliknuto na bioskop");
    var name = this.querySelector(".card-title").innerHTML;
    var id = this.id.split("theater_id_")[1];
    alert("pre odlaska"+id);
    localStorage.setItem("theater",id);
    window.location.replace("profile.html")

});




function getTheaters(){
    $.ajax({
                url: "/theatre/getAllTheatres",
                type: "GET",
                dataType: "json",
                success: function(data){
                    forward_theatres(JSON.stringify(data));
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });        
}


function forward_theatres(theaters){
    // poceo da menjam da upacuje html u dom
    // var newTheater = "<div class='card' style='width: 20%;'>"+
    //     "<img class='card-img-top' src='images/Arena.jpg' alt='Card image cap'>"+
    //     "<div class='card-body' style='height: 140px;'>"+
    //     "<h5 class='card-title'>"+element["name"]+"</h5>"+
    //     "<p class='card-text'>"+element["description"]+"</p></div>"
    //     "<div id='rateYo'></div></div>"
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
}





