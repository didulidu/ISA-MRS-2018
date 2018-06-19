$(document).on('click', '#login-button', function(e){
    e.preventDefault();
    window.location.replace("login.html")
});

$(document).on('click', '#sign-up-button', function(e){
    e.preventDefault();
    window.location.replace("registration.html")
});

$(document).on('click', '#shop-button', function(e){
   e.preventDefault();
   window.location.replace("shop.html")
});

$(document).on('click', '#friends-button', function(e){
   e.preventDefault();
   window.location.replace("registeredUserFriends.html")
});

$(document).on('click', '#reservations-button', function(e){
   e.preventDefault();
   window.location.replace("registeredUserReservations.html")
});

$(document).on('click', '#invitations-button', function(e){
   e.preventDefault();
   window.location.replace("registeredUserInvitations.html")
});

$(document).on('click', '#user-profile-button', function(e){
   e.preventDefault();
   window.location.replace("registeredUserProfile.html")
});

$(document).ready(function(){
        if(document.URL.indexOf("friends.html") == -1)
            getTheaters();
        getRegisteredUserData();
});
    

function logout()
{
    $.ajax({
        url: "/user/logout",
        type: "GET",
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            localStorage.removeItem("currentUserToken");
        },
        success: function() {
            window.location.replace("index.html");
        },
        error: function(response){
            if(response.status == 401)
                getToastr("No authorization for this activity!", "Error", 3);
            else
                getToastr("Logout unsuccessful! \nStatus: " + response.status, "", 3);
        }
    });
}



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
    //     "<div id='rateYo'></div></div>" ovo nikad necu uraditi izgleda xD
    theatersJSON = JSON.parse(theaters);
    $card = $(".card").first();
    $(".cards").empty();
    theatersJSON.forEach(function(element) {
        $card.find(".card-title").html(element["name"])
        $card.find(".card-text").html(element["description"])
        $card.find("#visit-btn").attr("value","")
        $card.attr("id", "theater_id_" + element["id"]);
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

function getRegisteredUserData(){
    $.ajax({
            url: "/user/getCurrentUser",
            type: "GET",
            dataType: "json",
            beforeSend: function(request){
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function(data, textStatus, response){
                var currentUser = data;
                localStorage.setItem("currentUserToken",response.getResponseHeader("Authorization"));
                localStorage.setItem("currentUserRole", currentUser.type);

                $("#current-user-username").val(currentUser.username);

                if(currentUser.type == "RegisteredUser") {
                    openConnectionFriends(currentUser);

                    if(document.URL.indexOf("registeredUserFriends.html") != -1)
                        displayRegisteredUsersFriends(currentUser);
                    else if (document.URL.indexOf("registeredUserProfile.html") != -1){
                        displayCurrentUserProfile(currentUser);
                        displayVisitations();
                    }

                    //if(document.URL.indexOf("index.html") != -1)
                        //setCurrentUsername(currentUser);



                }
            }
    });
}

function openConnectionFriends(currentUser){
    var socket = new SockJS('/friendsEndpoint');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe("/topic/friend" + currentUser.username, function (data) {

            var message = JSON.parse(data.body);
            var event = message.eventType;
            var friendName = message.friendName;
            var friendUsername = message.friendUsername;
            var friendLastname = message.friendLastname;

            notificationNumber++;
            $('#user-notifications').find("b").empty();

            if(localStorage.getItem("notificationNumber") == null)
                localStorage.setItem("notificationNumber", notificationNumber);

            else{
                notificationNumber = notificationNumber + parseInt(localStorage.getItem("notificationNumber"));
                localStorage.setItem("notificationNumber", notificationNumber);
            }
            $('#user-notifications').find("b").append(notificationNumber);

            if(localStorage.getItem("notifications") == null){
                var notifications = [];
                localStorage.setItem("notifications", JSON.stringify(notifications));
            }

            if(event == "addFriend") {

                if (document.URL.indexOf('friends.html') != -1) {

                    var requestTable = $("#friend-request-table");
                    var trFriendRequest = "<tr id='" + friendUsername + "'>" +
                        "<td>" + friendName + "</td>" +
                        "<td>" + friendLastname + "</td>" +
                        "<td>" + friendUsername + "</td>" +
                        "<td>" +
                            "<form class='form-inline form-accept-request'>" +
                                "<div class='form-group' style='margin-right: 2%;'>" +
                                    "<input type='hidden' value='" + friendUsername + "'>" +
                                    "<button class='btn btn-warning btn-accept-friend-request' id='accept-request-button'>" +
                                        "<span class='glyphicon glyphicon-check'></span> Accept </button>" +
                                "</div>" +
                                "<div class='form-group'>" +
                                    "<input type='hidden' value='" + friendUsername + "'>" +
                                    "<button class='btn btn-danger btn-delete-friend-request' id='delete-request-button'>" +
                                        "<span class='glyphicon glyphicon-remove'></span> Decline </button>" +
                                "</div>"
                            "</form>" +
                        "</td>" +
                    "</tr>";
                    requestTable.find("tbody").append(trFriendRequest);

                    var tr = userTableBody.find("tr[id=" + friendUsername +"]");
                    console.log(tr);
                    var td = tr.children().last();
                    td.empty();
                    td.append("Request response required!");
                }
            }

            else if (event == "acceptRequest") {

                if (document.URL.indexOf('registeredUserFriends.html') != -1) {

                    var friendTable = $("#friend-table");
                    var tr = friendTable.find("tr[id=" + friendUsername + "]");
                    var td = tr.children().last();
                    td.empty();
                    td.append("<form class='form-group form-delete-friend'>" +
                        "<input type='hidden' value='" + friendUsername + "'>" +
                        "<button class='btn btn-danger btn-delete-friend' id='delete-friend-button'>" +
                        "<span class='glyphicon glyphicon-remove'></span> Delete </button>" +
                        "</form>");

                    var trUser = userTableBody.find("tr[id=" + friendUsername +"]");
                    var tdUser = trUser.children().last();
                    tdUser.empty();
                    tdUser.append("<form class='form-group form-delete-friend'>" +
                            "<input type='hidden' value='" + friendUsername + "'>" +
                            "<button class='btn btn-danger btn-delete-friend btn-search-delete-friend' id='delete-friend-button'>" +
                                "<span class='glyphicon glyphicon-remove'></span> Delete </button>" +
                        "</form>");
                }
            }

            else if (event == "declineRequest") {

                if (document.URL.indexOf('friends.html') != -1) {

                    var friendTable = $("#friend-table");
                    var tr = friendTable.find("tr[id=" + friendUsername + "]");
                    tr.remove();

                    var trUser = userTableBody.find("tr[id=" + friendUsername +"]");
                    var tdUser = trUser.children().last();
                    tdUser.empty();
                    tdUser.append("<form class='form-group form-add-friend'>" +
                            "<input type='hidden' value='" + friendUsername + "'>" +
                            "<button class='btn btn-warning btn-add-friend' id='add-friend-button'>" +
                                "<span class='glyphicon glyphicon-plus'></span> Add </button>" +
                        "</form>");
                }
            }

        });
    });
}





