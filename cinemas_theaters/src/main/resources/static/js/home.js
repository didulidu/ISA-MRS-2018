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
        $.ajax({
                url: "/theatre/getAllTheatres",
                type: "GET",
                dataType: "json",
                success: function(data){
                    sessionStorage.setItem('cinemas', JSON.stringify(data));
                    forward_theatres(JSON.stringify(data), showHomePage);
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });                    
    }else if (sessionStorage.getItem("show") == "home"){
        forward_theatres(sessionStorage.getItem("cinemas"), showHomePage);            
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
        var name = this.querySelector(".card-title").innerHTML;
        getProfileData(name, forward_profile);
        return false;
    });

    $(document).on('click', '#home-btn',function(e){
            e.preventDefault(); 
            sessionStorage.setItem("profile", null); 
            forward_theatres(sessionStorage.getItem("cinemas"), showHomePage);
            return false;
        });


});


function getProfileData(name, callback){
    
    
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
        // Dodaj info u html, strpaj u funkciju
        dataJSON = JSON.parse(data);
        document.getElementById("cinema-name").innerHTML=dataJSON["name"];
        document.getElementById("cinema-text").innerHTML=dataJSON["description"];
    
        callback();
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
    theatersJSON = JSON.parse(theaters);
    $card = $(".card").first();
    theatersJSON.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $card.find("#visit-btn").attr("value","")
        $(".cards").append($card)
        $card = $(".card").first().clone();
    });
    callback();
}


