
$(document).on('click', '#login-button', function(e){
    e.preventDefault();
    window.location.replace("login.html")
});

$(document).on('click', '#sign-up-button', function(e){
    e.preventDefault();
    window.location.replace("registration.html")
});




$(document).ready(function(){

    $.ajax({
                url: "/theatre/getAllTheatres",
                type: "GET",
                dataType: "json",
                success: function(data){
                    forward_theatres(data)
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });
});

function forward_theatres(theaters){
    $card = $(".card").first();

    theaters.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $(".cards").append($card)
        $card = $(".card").first().clone();
    });

}