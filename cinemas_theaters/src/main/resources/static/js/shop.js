
/*
$(document).ready(function () {
   getItems();

   $(document).on('click', ".accept-btn", function () {

       var id = $(this).prev().val();

       $.ajax({
           url: "/items/evaluate/accept/"+id,
           type:"POST",
           beforeSend: function(request){
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
           success: function (data) {
              // console.log(data);
           },
           error: function (response) {
              // console.log(response);
           }
       })
   });

   $("#new-theatre-item-btn").click(function () {

       $.ajax({
           url:"/items/theatre",
           type:"POST",
           dataType:"son",
           contentType:"application/json",
           data:JSON.stringify({
               "name": $("input[name=theatre-item-name]").val(),
               "description": $("input[name=theatre-item-description]").val(),
               "theatreId": 132,
               "price": $("input[name=theatre-item-price]").val(),
               "quantity": $("input[name=theatre-item-quantity]").val()
           }),
           beforeSend: function (request) {
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
           success: function (data) {
               
           },
           error: function (response) {
               
           }
       })
   })

   $(document).on('click', ".reject-btn", function () {

      var id = $(this).prev().prev().val();
      
      $.ajax({
          url:"/items/evaluate/reject/"+id,
          type:"POST",
          beforeSend: function (request) {
              request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
          },
          success: function (data) {
              
          },
          error: function (response) {
              
          }
      })
   });
   
   
    $(document).on('click', ".item-content-btn", function() {

        var parent = $(this).parent();
        var div = $(parent).parent();
        var opacity = $(parent).prev();

        if ($(this).hasClass("hidden")) {

            opacity.css("display", "none");

            div.animate({height: '215'}, 500, function () {
                var div = $(this).children()[1];
                var show = $(div).children()[0];
                $({deg: 90}).animate({deg: 270}, {
                    duration: 500,
                    step: function (now) {
                        $(show).css({
                            transform: "rotate(" + now + "deg)"
                        });
                    }
                });

                var content = $(div).children()[1];
                $(content).css("overflow", "auto");
            });

            $(this).removeClass("hidden");

        } else {
            opacity.css("display", "inline-block");

            var content = $(this).next();
            $(content).css("overflow", "hidden");

            div.animate({height: '115'}, 500, function () {

                var div = $(this).children()[1];
                var show = $(div).children()[0];
                $({deg: 270}).animate({deg: 90}, {
                    duration: 500,
                    step: function (now) {
                        $(show).css({
                            transform: "rotate(" + now + "deg)"
                        });
                    }
                });
            });

            $(this).addClass("hidden");
        }
    });


    $("#evaluate-items-btn").click(function () {
        $.ajax({
            url:"/items/evaluate",
            type:"GET",
            contentType:"application/json",
            beforeSend: function(request){
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                $.each(data, function (index, item) {
                    console.log(item);
                    var itemDiv = makeDiv2(item);
                    $("#evaluate-items").append(itemDiv);
                })
            },
            error: function (response) {
                console.log(response);
            }
        })
    })

    $("#upload-image-btn").click(function () {

        var data = new FormData();
        data.append('file', $("input[name='image']")[0].files[0]);

        $.ajax({
            url:"/upload",
            type:"POST",
            data:data,
            cache:false,
            contentType:false,
            processData:false,

            success:function (data) {
                console.log(data);
            },
            error:function (response) {
                console.log(response);
            }
        })
    });
    
    $("#new-item-btn").click(function () {
        var name = $("input[name='item-name']").val();
        var description = $("input[name='item-description']").val();

        $.ajax({
            url: "/items/user",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "name": name,
                "description": description,
                "imagePath": $("input[name=image]").val(),
                "type":"user"
            }),
            beforeSend: function(request){
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },

            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                console.log(response);
            }
        })
    })

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




    $(".item-content-purchase-btn").click(function(){
        alert("hii");
    });
});


var makeDiv2 = function (item) {
    return $("<div>" + item.name +" " + item.description + " " + item.user + "<input type='hidden' name='id' value=" + item.id +">" +
        "<button class='accept-btn'>ACCEPT</button><button class='reject-btn'>REJECT</button></div>")
}


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
    return $("<div class=\"item\">\n" +
        "            <div class=\"item-img\">\n" +
        "                <img src=\"slika.png\">\n" +
        "            </div>\n" +
        "\n" +
        "            <div class=\"item-content-wrapper\">\n" +
        "                <div class=\"item-content-opacity\"></div>\n" +
        "                <div class=\"item-content\">\n" +
        "                    <div class=\"item-content-btn hidden\"> > </div>\n" +
        "                    <div class=\"item-content-container\">\n" +
        "                        <p class=\"item-content-name\">\n" +
                                    item.name        +
        "                        </p>\n" +
        "                        <p class=\"item-content-description\">\n" +
        "                            <i>Description:</i>\n" +
        "                            <span>\n" +
                                        item.description +
        "                            </span>\n" +
        "                        </p>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "\n" +
        "            </div>\n" +
        "\n" +
        "            <div class=\"purchase\">\n" +
        "                <div class=\"purchase-price\">\n" +
        "                    <span class=\"integer\">" + item.price + "</span>,<span class=\"decimals\">99</span>\n" +
        "                </div>\n" +
        "                <button class=\"purchase-btn\">BUY</button>\n" +
        "            </div>\n" +
        "        </div>")
}
*/
