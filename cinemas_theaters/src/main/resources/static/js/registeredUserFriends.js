var currentUser;

function displayRegisteredUsersFriends(user){
    currentUser = user;
    showFriends();
}

function sortFriendByName(direction){
    currentUser.friendships.sort(function(f1,f2) {
        if(direction == "asc") {
            if (f1.firstname.toLowerCase() < f2.firstname.toLowerCase())
                return -1;
            if (f1.firstname.toLowerCase() > f2.firstname.toLowerCase())
                return 1;
        }
        else if(direction == "desc"){
            if (f1.firstname.toLowerCase() > f2.firstname.toLowerCase())
                return -1;
            if (f1.firstname.toLowerCase() < f2.firstname.toLowerCase())
                return 1;
        }
    });
    showFriends();
}

function sortByLastname(direction){
    currentUser.friendships.sort(function(f1,f2) {
        if(direction == "asc") {
            if (f1.lastname.toLowerCase() < f2.lastname.toLowerCase())
                return -1;
            if (f1.lastname.toLowerCase() > f2.lastname.toLowerCase())
                return 1;
        }
        else if(direction == "desc"){
            if (f1.lastname.toLowerCase() > f2.lastname.toLowerCase())
                return -1;
            if (f1.lastname.toLowerCase() < f2.lastname.toLowerCase())
                return 1;
        }
    });
    showFriends();
}

function sortByUsername(direction){
    currentUser.friendships.sort(function(f1,f2) {
        if(direction == "asc") {
            if (f1.username.toLowerCase() < f2.username.toLowerCase())
                return -1;
            if (f1.username.toLowerCase() > f2.username.toLowerCase())
                return 1;
        }
        else if(direction == "desc"){
            if (f1.username.toLowerCase() > f2.username.toLowerCase())
                return -1;
            if (f1.username.toLowerCase() < f2.username.toLowerCase())
                return 1;
        }
    });
    showFriends();
}

function addFriend(friendUsername,td){
    $.ajax({
        url: "/registeredUser/addFriend",
        type: "PUT",
        contentType: "text/plain",
        data: friendUsername,
        dataType: "json",
        beforeSend: function(request){
          request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data, textStatus, response){
            currentUser = data;
            localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
            td.empty();
            td.append("Waiting for a response");
            getToastr("Friend request for " + friendUsername + " sent successfully!", "", 1);
            showFriends();
        },
        error: function(response){
            if(status == 400)
                getToastr("User " + friendUsername + " is already in your friend list!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized for this activity!", "Error", 3);
            else if(response.status == 403)
                getToastr("User you are trying to add doesn't exist!", "Error", 3);
            else
                getToastr("Error while sending a request! \nStatus: " + response.status, "", 3);
        }
    });
}

function deleteFriend(friendUsername, td){
    $.ajax({
        url: "/registeredUser/deleteFriend",
        type: "PUT",
        contentType: "text/plain",
        dataType: "json",
        data: friendUsername,
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data, textStatus, response){
            currentUser = data;
            localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
            if(td != ""){
                td.empty();
                td.append("<form class='form-group form-add-friend'>" +
                        "<input type='hidden' value='" + friendUsername + "'>" +
                        "<button class='btn btn-warning btn-add-friend' id='add-friend-button'>" +
                            "<span class='glyphicon glyphicon-plus'></span> Add " +
                        "</button>" +
                    "</form>");
            }
            getToastr("User " + friendUsername + " successfully removed from friend list!", "", 1);
            showFriends();
        },
        error: function(response){
            if(status == 400)
                getToastr("User " + friendUsername + " is not in your friend list!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized for this activity!", "Error", 3);
            else if(response.status == 403)
                getToastr("User you are trying to delete doesn't exist!", "Error", 3);
            else
                getToastr("Error while removing a friend from a friend list! \nStatus: " + response.status, "", 3);
        }
    });
}

function searchUsers(parameter){
    $.ajax({
        url: "/registeredUser/searchUsers/" + parameter,
        type: "GET",
        dataType: "json",
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data, textStatus, response){
            var foundUsers = data.users;
            currentUser = data.currentUser;
            localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
            if(foundUsers.length == 0)
                getToastr("No users matching your query: " + parameter, "", 2);
            else
                showUsers(foundUsers);
        },
        error: function(response){
            if(response.status == 401)
                getToastr("Not authorized for this activity!", "Error", 3);
            else
                getToastr("Error when searching for a user! \nStatus: " + response.status, "", 3);
        }
    });
}

function acceptRequest(username){
    $.ajax({
        url: "/registeredUser/acceptRequest",
        type: "PUT",
        contentType: "text/plain",
        data: username,
        dataType: "json",
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data, textStatus, response){
            currentUser = data;
            localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
            showFriends();
        },
        error: function(response){
            if(status == 400)
                getToastr(username + " is already your friend!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized for this activity!", "Error", 3);
            else if(response.status == 403)
                getToastr("Not possible to decline a nonexistent request!", "Error", 3);
            else
                getToastr("Error when accepting a friend request! \nStatus: " + response.status, "", 3);
        }
    });
}

function deleteRequest(username){
    $.ajax({
        url: "/registeredUser/deleteRequest",
        type: "PUT",
        contentType: "text/plain",
        data: username,
        dataType: "json",
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data, textStatus, response){
            currentUser = data;
            localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
            showFriends();
        },
        error: function(response){
            if(status == 400)
                getToastr(username + " is already your friend!", "Error", 3);
            else if(response.status == 401)
                getToastr("Not authorized for this activity!", "Error", 3);
            else if(response.status == 403)
                getToastr("Not possible to decline a nonexistent request!", "Error", 3);
            else
                getToastr("Error when declining a friend request! \nStatus: " + response.status, "", 3);
        }
    });
}

function showFriends(){
    var friendshipList = (currentUser.friendships == null) ? [] : (currentUser.friendships instanceof Array ? currentUser.friendships : [currentUser.friendships]);
    var friendTable = $("#friend-table");
    friendTable.find("tbody").empty();
    var requestTable = $("#friend-request-table");
    requestTable.find("tbody").empty();

    $.each(friendshipList, function(index, friend){
        var tableBody = friendTable.find("tbody");
        if(friend.status == "Accepted") {
            var trFriend = "<tr id='" + friend.username + "'>" +
                "<td>" + friend.name + "</td>" +
                "<td>" + friend.lastname + "</td>" +
                "<td>" + friend.username + "</td>" +
                "<td>" +
                    "<form class='form-group form-delete-friend'>" +
                        "<input type='hidden' value='" + friend.username + "'>" +
                        "<button class='btn btn-danger btn-delete-friend' id='delete-friend-button'>" +
                        "<span class='glyphicon glyphicon-remove'></span> Remove </button>" +
                    "</form>" +
                "</td>" +
            "</tr>";
            tableBody.append(trFriend);
        }
        else if(friend.status == "Pending"){
            var trWaiting = "<tr id='" + friend.username + "'>" +
                    "<td>" + friend.firstname + "</td>" +
                    "<td>" + friend.lastname + "</td>" +
                    "<td>" + friend.username + "</td>" +
                    "<td> Waiting for a response </td>" +
                "</tr>";
            tableBody.append(trWaiting);
        }
        else {
            var trFriendRequest = "<tr id='" + friend.username + "'>" +
                    "<td>" + friend.firstname + "</td>" +
                    "<td>" + friend.lastname + "</td>" +
                    "<td>" + friend.username + "</td>" +
                    "<td>" +
                        "<form class='form-inline form-accept-request'>" +
                            "<div class='form-group' style='margin-right: 2%;'>" +
                                "<input type='hidden' value='" + friend.username + "'>" +
                                "<button class='btn btn-warning btn-accept-friend-request' id='accept-request-button'>" +
                                    "<span class='glyphicon glyphicon-check'></span> Accept </button>" +
                            "</div>" +
                            "<div class='form-group'>" +
                                "<input type='hidden' value='" + friend.username + "'>" +
                                "<button class='btn btn-danger btn-delete-friend-request' id='delete-request-button'>" +
                                    "<span class='glyphicon glyphicon-remove'></span> Remove </button>" +
                            "</div>"
                        "</form>" +
                    "</td>" +
                "</tr>";
            requestTable.find("tbody").append(trFriendRequest);
        }
    });
}

function showUsers(users){
    var userList = (users == null) ? [] : (users instanceof Array ? users : [users]);
    $.each(userList, function(index, user){
        var alreadyFriends = false;
        var status = null;
        $.each(currentUser.friendships, function(indexF, friend){
            if(friend.username == user.username){
                alreadyFriends = true;
                status = friend.status;
                return false;
            }
        });

        var trUser = "<tr id='" + user.username + "'>" +
                "<td>" + user.name + "</td>" +
                "<td>" + user.lastname + "</td>" +
                "<td>" + user.username + "</td>" +
                "<td>";
                if(!alreadyFriends)
                   trUser += "<form class='form-group form-add-friend'>" +
                        "<input type='hidden' value='" + user.username + "'>" +
                        "<button class='btn btn-warning btn-add-friend' id='add-friend-button'>" +
                        "<span class='glyphicon glyphicon-plus'></span> Add </button>" +
                    "</form>";
                else {
                    if(status == null)
                        trUser += "Request response required";
                    else if(status == "Pending")
                        trUser += "Waiting for a response from a user";
                    else
                        trUser += "<form class='form-group form-delete-friend'>" +
                            "<input type='hidden' value='" + user.username + "'>" +
                            "<button class='btn btn-danger btn-delete-friend btn-search-delete-friend' id='delete-friend-button'>" +
                            "<span class='glyphicon glyphicon-remove'></span> Remove </button>" +
                        "</form>";
                }
                trUser += "</td>" +
            "</tr>";
        $("#modal-search-user-table").find("tbody").append(trUser);
    });
}

$(document).on('click', '#search-user-button', function(e){
    e.preventDefault();
    var form = $(this).parent("form");
    var parameter = $("#search-user-form :text").val();
    $("#modal-search-user-table").find("tbody").empty();
    if(parameter.trim() != "") {
        searchUsers(parameter);
    }
});

$(document).on('click', '#close-modal-search-users', function(){
    $("#modal-search-user-table").find("tbody").empty();
    $("#search-user-form :text").val("");
});

$(document).on('click', '#add-friend-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var td = form.parent();
    var friendUsername = form.find("input[type=hidden]").val();
    addFriend(friendUsername, td);
});

$(document).on('click', '#delete-friend-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var td = "";
    if($(this).hasClass("btn-search-delete-friend"))
        td = form.parent();
    var friendUsername = form.find("input[type=hidden]").val();
    deleteFriend(friendUsername, td);
});

$(document).on('click', '#accept-request-button', function(e){
   e.preventDefault();
   var form = $(this).parents("form");
   var friendUsername = form.find("input[type=hidden]").val();
   acceptRequest(friendUsername);
});

$(document).on('click', '#delete-request-button', function(e){
    e.preventDefault();
    var form = $(this).parents("form");
    var friendUsername = form.find("input[type=hidden]").val();
    deleteRequest(friendUsername);
});

$(document).on('click', '#sort-friend-name-asc', function(){
    sortFriendByName("asc");
});

$(document).on('click', '#sort-friend-name-desc', function(){
    sortFriendByName("desc");
});

$(document).on('click', '#sort-friend-lastname-asc', function(){
    sortByLastname("asc");
});

$(document).on('click', '#sort-friend-lastname-desc', function(){
    sortByLastname("desc");
});

$(document).on('click', '#sort-friend-username-asc', function(){
    sortByUsername("asc");
});

$(document).on('click', '#sort-friend-username-desc', function(){
    sortByUsername("desc");
});
