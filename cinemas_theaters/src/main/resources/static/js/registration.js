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
                window.setTimeout(function() {window.location.href = "index.html";}, 4000);
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
                    window.setTimeout(function() {window.location.href = "index.html";}, 4000);
                }
                else
                    getToastr("Registration unsuccessful! \nStatus: " + response.status, "", 3);
            }
        });
    }
    else
        return false;
}

function validateRegistrationData(data){
    var name = data["name"];
    var lastname = data["lastname"];
    var username = data["username"];
    var password = data["password"];
    var repeatedPassword = data["repeatedPassword"];
    if(localStorage.getItem("registrationType") == null ||  localStorage.getItem("registrationType") == "theatre-manager" ||
                                                           localStorage.getItem("registrationType") == "system-manager")
       var email = data["email"];

    if(name.trim() == "") {
       getToastr("Name field is required!", "Registration form not complete.", 2);
       return false;
    }

    if(name.length < 2 || name.length > 20) {
       getToastr("Name must contatin between 2 and 20 characters!", "Invalid registration data", 2);
       return false;
    }

    if(lastname.trim() == "") {
       getToastr("Last name field is required!", "Registration form not complete.", 2);
       return false;
    }

    if(lastname.trim().length < 2 || lastname.trim().length > 30) {
       getToastr("Last name must contain between 2 and 20 characters!!", "Invalid registration data", 2);
       return false;
    }

    if(localStorage.getItem("registrationType") == null ||  localStorage.getItem("registrationType") == "theatre-manager" ||
                                                           localStorage.getItem("registrationType") == "system-manager"){
       if(!validateEmail(email.trim())) {
           getToastr("Invalid e-mail address format!", "Invalid registration data", 2);
           return false;
       }
    }

    if(username.trim() == "") {
       getToastr("Username field is required!", "Registration form not complete.", 2);
       return false;
    }

    if(username.trim().length < 3) {
       getToastr("Username must contain at least 3 characters!", "Invalid registration data", 2);
       return false;
    }

    if(password.trim() == "") {
       getToastr("Password field is required!", "Registration form not complete.", 2);
       return false;
    }

    if(password.trim().length < 3) {
       getToastr("Password must contain at least 3 characters!", "Invalid registration data", 2);
       return false;
    }

    if(password.trim() != repeatedPassword.trim()) {
       getToastr("First and second passwords must match!", "Invalid registration data", 2);
       return false;
    }

    if(localStorage.getItem("registrationType") == null)
       localStorage.removeItem("registrationType");
    return true;
}

$(document).on('click', '#home', function(e){
    e.preventDefault();
    if(localStorage.getItem("currentUserToken") == null)
        window.location.href = "index.html";
});

$(document).on('click', '#registrationForm :button', function(e){
    e.preventDefault();
    registration();
});