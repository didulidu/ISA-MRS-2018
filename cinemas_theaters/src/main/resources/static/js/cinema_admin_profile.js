$(document).ready(function(){
	getAdminTheaters();
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
	$card = $(".card").first();
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



