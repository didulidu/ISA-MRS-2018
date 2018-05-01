function activateUser(token)
{
    var tokenJson = {
        jwtToken:token
    };
    $.ajax({
        url: "/registeredUser/activation",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(tokenJson),
        error: function (response) {
            if(response.status == 400)
                getToastr("Account already activated!", "", 2);
            else if(response.status == 403)
                getToastr("Activation link has expired!\nPlease register again.", "Error!", 3);
            else
                getToastr("Error! \nStatus: " + response.status, "", 3);
        }
    });
}

function validateLoginData(formData){
    var username = formData["username"];
    var password = formData["password"];

    if(username.trim() == "") {
        getToastr("Username can't be empty!", "Username is a required field", 2);
        return false;
    }

    if(password.trim() == "") {
        getToastr("Password can't be empty!", "Password is a required field", 2);
        return false;
    }

    return true;
}

function login(){
    var formData = getFormData($("#loginForm"));
    var success = Boolean(validateLoginData(formData));

    if(success){
        var user = JSON.stringify(formData);

        $.ajax({
            url: "/user/login",
            type: "POST",
            contentType: "application/json",
            data: user,
            success: function(data, textStatus, response){
                //localStorage.setItem("currentUserToken",response.getResponseHeader("Authorization"));
                window.location.replace("home.html");
            },
            error: function (response) {
                if(response.status == 401)
                    getToastr("Unknown username/password!", "Error", 3);
                else
                    getToastr("Error while logging in! \nStatus: " + response.status, "", 3);
            }
        });
    }
    else
        return false;
}

$(document).on('click', '#loginForm :button', function(e){
    e.preventDefault();
    login();
});

$(document).ready(function(){
    if(localStorage.getItem("currentUserToken") == null && document.URL.indexOf("?activate=") != -1)
    {
        var token = document.URL.substr(document.URL.indexOf("?activate=")+10);
        activateUser(token);
    }
    if(localStorage.getItem("currentUserToken") != null)
        window.location.replace("home.html");
});