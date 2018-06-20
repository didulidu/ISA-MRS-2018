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