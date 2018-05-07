$(document).on('click', '#login-button', function(e){
    e.preventDefault();
    window.location.replace("login.html")
});

$(document).on('click', '#sign-up-button', function(e){
    e.preventDefault();
    window.location.replace("registration.html")
});

$(document).ready(function(){
    if (sessionStorage.getItem('profile') != null){
        document.getElementById("cinema-name").innerHTML=JSON.parse(sessionStorage.getItem('profile'))["name"];
        showProfile();
    }
    else{

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
    }
    $(document).on('click', '.card',function(e){
        e.preventDefault(); 
        var name = this.querySelector(".card-title").innerHTML;
        getProfileData(name, forward_profile);

        return false;
    });

    $(document).on('click', '#home-btn',function(e){
            e.preventDefault(); 
            showHomePage();
            sessionStorage.setItem('profile', null); 
            return false;
        });


});


function getProfileData(name, callback){
    $.ajax({
        url: "theatre/visit/"+name,
        type: "GET",
        dataType: "json",
        success: function(data){
            callback(data, showProfile);
        },
        error: function (response) {
            alert("Ne radi profil: "+ response.status);
            return null;
        }
    });
}

 function forward_profile(data, callback){
        sessionStorage.setItem('profile', JSON.stringify(data));
        // Dodaj info u html, strpaj u funkciju

        document.getElementById("cinema-name").innerHTML=data["name"];
        document.getElementById("cinema-text").innerHTML=data["description"];
    
        callback();
    }


function showProfile(){
    $(".cards").hide();
    $("#profile").show();
}

function showHomePage(){
    $(".cards").show();
    $("#profile").hide();

}


function forward_theatres(theaters){
    $card = $(".card").first();
    showHomePage();
    theaters.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $card.find("#visit-btn").attr("value","")
        $(".cards").append($card)
        $card = $(".card").first().clone();
    });
}


