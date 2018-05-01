function registration(){
    var formData = getFormData($("#registrationForm"));
    var success = Boolean(validateRegistrationData(formData));

    if(success){
        var newUser = JSON.stringify(formData);
        $.ajax({
            url: "/registeredUser/registration",
            type: "POST",
            contentType: "application/json",
            data: newUser,
            dataType: 'json',
            success: function(){
                getToastr(formData["name"] + " " + formData["lastname"] + ", \nan activation has been sent to your e-mail adress!", "", 1);
                window.setTimeout(function() {window.location.replace("index.html");}, 4000);
            },
            error: function (response) {
                if(response.status == 409){
                    getToastr("Error while sending an activation mail!", "Registration unsuccessful", 3);
                    return false;
                }
                else if(response.status == 403)
                {
                    getToastr("Username already taken!", "Registration unsuccessful", 3);
                    return false;
                }
                else if(response.status == 400)
                {
                    getToastr("Invalid data entered or password aren't matching!", "Registration unsuccessful", 3);
                    return false;
                }
                else if(response.status == 201)
                {
                    getToastr(formData["name"] + " " + formData["lastname"] + ", \nan activation has been sent to your e-mail adress!", "", 1);
                    window.setTimeout(function() {window.location.replace("index.html");}, 4000);
                }
                else
                    getToastr("Registration unsuccessful! \nStatus: " + response.status, "", 3);
            }
        });
    }
    else
        return false;

}

$(document).on('click', '#home', function(e){
    e.preventDefault();
    if(localStorage.getItem("currentUserToken") == null)
        window.location.replace("index.html");
    else
        window.location.replace("home.html");
});

$(document).on('click', '#registrationForm :button', function(e){
    e.preventDefault();
    registration();
});