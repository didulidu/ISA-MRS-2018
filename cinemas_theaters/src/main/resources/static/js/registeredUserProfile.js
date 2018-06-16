var updatedName = "";
var updatedLastName = "";
var updatedEmail = "";
var passwordChanged = "";
var oldPassword = "";
var updatedPassword1 = "";
var updatedPassword2 = "";
var updatedTelephoneNumber = "";
var updatedAddress = "";
var currentUser = "";
var dataChanged;

function showPersonalData(currentUserParam){
    currentUser = currentUserParam;

    if(currentUser.type == "RegisteredUser"){
        $('#div-profile-email').show();
        $("#profile-user-email").val(currentUser.email);
        $("#profile-user-address").val(currentUser.address);
        $("#profile-user-telephone").val(currentUser.telephoneNumber);
    }
    $("#profile-user-name").val(currentUser.name);
    $("#profile-user-lastname").val(currentUser.lastname);
    $("#profile-user-username").val(currentUser.username);
}

function updateData()
{
    var updatedData = "";
    updatedData = {
        "updatedName":updatedName,
        "updatedLastname":updatedLastName,
        "updatedEmail":updatedEmail,
        "passwordChanged": passwordChanged,
        "oldPassword":oldPassword,
        "updatedPassword1":updatedPassword1,
        "updatedPassword2":updatedPassword2,
        "updatedTelephoneNumber": updatedTelephoneNumber,
        "updatedAddress": updatedAddress
    }

    $.ajax({
        url: "/registeredUser/updateDataAndPassword",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedData),
        dataType: "json",
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data, textStatus, response){
            currentUser = data;
            localStorage.setItem("currentUserToken",response.getResponseHeader("Authorization"));
            getToastr("", "Data successfully updated!", 1);
            $('#profile-user-password').val("");
            $('#profile-user-new-password').val("");
            $('#profile-user-new-password-2').val("");
        },
        error: function(response){
            if(response.status == 401)
                getToastr("Not authorized for this activity!", "Error", 3);
            else if(response.status == 400)
                getToastr("Invalid password input!", "Invalid form input", 3);
            else
                getToastr("Error while updating data! \nStatus: " + response.status, "", 3);
        }
    });
}

$(document).on('click', '#profile-change-save', function(e){
    e.preventDefault();
    updatedName = $('#profile-user-name').val().trim();
    if(updatedName == "")
    {
        getToastr("First name is a required field!", "Invalid form input", 2);
        return false;
    }
    else if(updatedName != currentUser.name)
    {
        if(updatedName.length < 2 || updatedName.length > 20) {
            getToastr("First name must contain between 2 and 20 characters!", "Invalid form input", 3);
            return false;
        }
    }

    updatedLastName = $('#profile-user-lastname').val().trim();
    if(updatedLastName == "")
    {
        getToastr("Last name is a required field!", "Invalid form input", 2);
        return false;
    }
    else if(updatedLastName != currentUser.lastname)
    {
        if(updatedLastName.trim().length < 2 || updatedLastName.trim().length > 30) {
            getToastr("Last name must contain between 2 and 20 characters!", "Invalid form input", 3);
            return false;
        }
    }

    updatedEmail = $('#profile-user-email').val().trim();
    if(updatedEmail == "")
    {
        getToastr("Email is a required field!", "Invalid form input", 2);
        return false;
    }
    else if(updatedEmail != currentUser.email)
    {
        if(!validateEmail(updatedEmail)) {
            getToastr("Invalid email address form!", "Invalid form input", 3);
            return false;
        }
    }

    oldPassword = $('#profile-user-password').val().trim();
    updatedPassword1 = $('#profile-user-new-password').val().trim();
    updatedPassword2 = $('#profile-user-new-password-2').val().trim();

    if(oldPassword != "" && updatedPassword1 == "" && updatedPassword2 == "")
    {
        getToastr("Password field is required when changing the password!", "Invalid form input", 2);
        return false;
    }

    if((updatedPassword1 != "" && oldPassword == "") || (updatedPassword2 != "" && oldPassword == ""))
    {
        getToastr("Old password field is required when changing the password!", "Invalid form input", 2);
        return false;
    }

    if(updatedPassword1 != "" && updatedPassword2 == "")
    {
        getToastr("You haven't retyped the password!", "Invalid form input", 2);
        return false;
    }

    if(updatedPassword2 != "" && updatedPassword1 == "")
    {
        getToastr("New password field is required when changing the password!", "Invalid form input", 2);
        return false;
    }

    if(updatedPassword1 != "" && updatedPassword1.length < 3) {
        getToastr("Password must contain at least 3 characters", "Invalid form input", 3);
        return false;
    }

    if(updatedPassword1 != updatedPassword2)
    {
        getToastr("Repeated password inputs don't match!", "Invalid form input", 3);
        return false;
    }
    else if(updatedPassword1 != oldPassword && oldPassword != "")
        passwordChanged = true;
    else
    {
        passwordChanged = false;
    }

    updatedAddress = $('#profile-user-address').val().trim();
    updatedTelephoneNumber = $('#profile-user-telephone').val().trim();

    updateData();
});

$(document).on('click', '#profile-change-reset', function(e){
    e.preventDefault();
    $('#profile-user-name').val("");
    $('#profile-user-lastname').val("");
    $('#profile-user-email').val("");
    $('#profile-user-username').val(currentUser.username);
    $('#profile-user-password').val("");
    $('#profile-user-new-password').val("");
    $('#profile-user-new-password-2').val("");
});

