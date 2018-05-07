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
        alert("prvi put uciavanje stranice");
        getTheaters();
    }else if (sessionStorage.getItem("show") == "home"){
        alert("nakon refresovanja home stranice");
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
        alert("Kliknuto na bioskop");
        var name = this.querySelector(".card-title").innerHTML;
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
    sessionStorage.setItem("cinemas",theaters);
    theatersJSON = JSON.parse(theaters);
    alert("za prikaz: " + theaters);
    $card = $(".card").first();
    $(".cards").empty();
    theatersJSON.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $card.find("#visit-btn").attr("value","")
        $(".cards").append($card)
        $card = $(".card").first().clone();
    });
    callback();
}


