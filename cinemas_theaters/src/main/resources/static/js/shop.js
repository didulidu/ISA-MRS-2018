$(document).ready(function () {
   getItems();
});

$(document).on('click', '.buy-btn', function(){
   var id = this.id.split("_")[1];
   $.ajax({
       url: "/offer",
       type: "POST",
       dataType: "json",
       contentType: "application/json",
       data: JSON.stringify({
           "item_id": id,
           "bid": 200.00
       }),
       success: function (response) {
           console.log(response.status);
       },
       error: function (response) {
           console.log(response.status);
       }
   })
});


var getItems = function () {

    $.ajax({
        url: "/items",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, item) {
                console.log(item);
                var itemDiv = makeDiv(item);
                $("#items-container").append(itemDiv);
            })
        },
        error: function (response) {
            alert(response.status);
        }
    });
};

function makeDiv(item){
    return $("<div class='item-post'>" +
                "<p> Name: " + item.name + "</p><br>" +
                "<p> Description: " + item.description + "</p><br>" +
                "<p> Price: " + item.price + "</p>" +
                "<button class='buy-btn' id='item_" + item.id + "_btn'> BUY </button>" +
            "</div>")
}