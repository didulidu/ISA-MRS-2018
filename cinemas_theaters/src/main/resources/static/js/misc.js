function getToastr(text1, text2, flag){
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    if(flag == 1)
        toastr.success(text1, text2);
    else if(flag == 2)
        toastr.warning(text1, text2);
    else
        toastr.error(text1, text2);
}

function getFormData(dom_query){
    var formData = {};
    var s_data = $(dom_query).serializeArray();
    //transformacija u jednostavne kljuc/vrednost objekte
    for(var i = 0; i<s_data.length; i++){
        var record = s_data[i];
        formData[record.name] = record.value;
    }
    return formData;
}

function validateEmail(email) {
    var regex = new RegExp("^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))$");
    if(regex.test(email))
        return true;
    else
        return false;
}

function validateRegistrationData(data){
       var name = data["name"];
       var lastname = data["lastname"];
       var username = data["username"];
       var password = data["password"];
       var repeatedPassword = data["repeatedPassword"];
       if(localStorage.getItem("registrationType") == null ||  localStorage.getItem("registrationType") == "restaurant-manager" ||
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

       if(localStorage.getItem("registrationType") == null ||  localStorage.getItem("registrationType") == "restaurant-manager" ||
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

   $(document).on('click', '.btn', function(){
       $(this).blur();
   });