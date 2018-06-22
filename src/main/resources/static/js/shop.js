$(document).ready(function () {

    $(document).on('click', ".official-items-btn-edit", function () {
        var parent = $(this).parent();
        var div = $(parent).next();

        if($(div).css("display") === "none"){
            $(div).slideDown();
        }else{
            $(div).slideUp();
        }
    });

    $(document).on('click', ".official-items-edit-save-btn", function () {

        var input = $(this).next();
        var id = $(input).val();

        var name = $("#"+id+"-edit-name-official").val();
        var descr = $("#"+id+"-edit-description-official").val();
        var image = $("#"+id+"-edit-image-official").val();
        var price = $("#"+id+"-edit-price-official").val();
        var quantity = $("#"+id+"-edit-quantity-official").val();

        $.ajax({
            url: "/items/theatre/" + id,
            type:"PUT",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "name": name,
                "description": descr,
                "imagePath": image,
                "price": price,
                "quantity": quantity
            }),
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });

    $(document).on('click', ".official-items-btn-delete", function () {

        var parent = $(this).parent();
        var input = $(parent).prev();
        var id = $(input).val();

        $.ajax({
            url: "/items/" + id,
            type: "DELETE",
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });


    $(document).on('click', ".my-offers-btn-edit", function () {

        var parent = $(this).parent();
        var div = $(parent).next();

        if($(div).css("display") === "none"){
            $(div).slideDown();
        }else{
            $(div).slideUp();
        }

    });

    $(document).on('click', ".my-offers-edit-save-btn", function () {
       var input = $(this).next();
       var id = $(input).val();

       var name = $("#"+id+"-name-edit").val();
       var descr = $("#"+id+"-description-edit").val();
       var image = $("#"+id+"-image-edit").val();
       var date = $("#"+id+"-date-edit").val();
       var time = $("#"+id+"-time-edit").val();

       $.ajax({
          url: "/items/user/" + id,
           type:"PUT",
           contentType: "application/json",
           dataType: "json",
           data: JSON.stringify({
               "name": name,
               "description": descr,
               "imagePath": image,
               "duration": date + " " + time
           }),
           beforeSend: function (request) {
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
           success: function (data) {
               console.log(data);
           },
           error: function (response) {
               alert(response);
           }
       });
    });



    $(document).on('click', ".my-offers-btn-delete", function () {

        var parent = $(this).parent();
        var input = $(parent).prev();
        var id = $(input).val();

        $.ajax({
           url: "/items/" + id,
           type: "DELETE",
           contentType: "application/json",
           beforeSend: function (request) {
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });

    });


    $(document).on('click', ".my-bids-edit-save-btn", function () {

        var parent = $(this).prev();
        var input = $(parent).children()[0];
        var bid = $(input).val();

        var idInput = $(this).next();
        var id = $(idInput).val();

        $.ajax({
           url: "/bid/" + id + "/" + bid,
           type: "PUT",
           contentType: "application/json",
           beforeSend: function(request){
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });

    });

    $(document).on('click', ".my-bids-btn-delete", function () {

        var parent = $(this).parent();
        var input = $(parent).prev();
        var id = $(input).val();

        $.ajax({
            url: "/bid/" + id,
            type: "DELETE",
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }

        });
    });


    $(document).on('click', ".my-offers-offers-btn-accept", function () {

        var parent = $(this).parent();
        var input = $(parent).next();
        var id = $(input).val();

        $.ajax({
           url: "/bid/accept/" + id,
           type: "POST",
           contentType:"application/json",
           beforeSend: function (request) {
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });

    $(document).on('click', ".my-offers-offers-btn-reject", function () {

        var parent = $(this).parent();
        var input = $(parent).next();
        var id = $(input).val();

        $.ajax({
            url: "/bid/reject/" + id,
            type: "POST",
            contentType:"application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });


    $(document).on('click', ".purchase-btn-bid", function () {

        var input = $(this).next();
        var id = $(input).val();

        var bidInput = $(this).prev();
        var input2 = $(bidInput).children()[0];
        var bid = $(input2).val();

        $.ajax({
           url:"/bid",
           type:"POST",
           dataType:"json",
           data: JSON.stringify({
               "item_id": id,
               "bid": bid
           }) ,
            contentType:"application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });

    $(document).on('click', ".purchase-btn-buy", function () {

        var input = $(this).next();
        var id = $(input).val();

        $.ajax({
           url:"/purchase/" + id,
           type:"POST",
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });


    $("#new-item-btn").click(function () {

        $.ajax({
            url:"/items/user",
            type:"POST",
            contentType:"application/json",
            dataType:"json",
            data: JSON.stringify({
                "name": $("input[name='new-item-name']").val(),
                "description": $("textarea[name='new-item-description']").val(),
                "duration": $("input[name='new-item-date']").val() + " " + $("input[name='new-item-time']").val(),
                "imagePath": $("input[name='new-item-image']").val()
            }),
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                console.log(response);
            }
        })
    });

    $("#new-official-item-btn").click(function () {

        $.ajax({
            url:"/items/theatre",
            type:"POST",
            contentType:"application/json",
            dataType:"json",
            data: JSON.stringify({
                "name": $("input[name='new-official-item-name']").val(),
                "description": $("textarea[name='new-official-item-description']").val(),
                "price": $("input[name='new-official-item-price']").val(),
                "quantity": $("input[name='new-official-item-quantity']").val(),
                "imagePath": $("input[name='new-official-item-image']").val(),
                "theatreId": 132
            }),
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
            },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                console.log(response);
            }
        })
    });

    $(document).on('click', ".evaluate-offers-btn-accept",function () {

        var parent = $(this).parent();
        var input = $(parent).prev();
        var id = $(input).val();

        $.ajax({
           url:"/items/evaluate/accept/" + id,
           type:"POST",
           beforeSend: function (request) {
               request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
           },
            success: function (data) {
                console.log(data);
            },
            error: function (response) {
                alert(response);
            }
        });
    });

    $(document).on('click', ".evaluate-offers-btn-reject", function () {

       var parent = $(this).parent();
       var input = $(parent).prev();
       var id = $(input).val();

       $.ajax({
          url:"/items/evaluate/reject/" + id,
          type:"POST",
          beforeSend: function (request) {
              request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
          },
           success: function (data) {
               console.log(data);
           },
           error: function (response) {
               alert(response);
           }
       });
    });
});

var loadBidsOnItem = function (id, container) {

    $.ajax({
       url:"/items/myoffers/bids/" + id,
       type:"GET",
       contentType: "application/json",
       beforeSend: function (request) {
           request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
       } ,
        success: function (data) {
            console.log(data);

            $(container).empty();

            $.each(data, function (index, item) {

                var itemDiv = makeMyOfferBidItem(item);
                $(container).append(itemDiv);
            });
        },
        error: function (response) {
            alert(response);
        }
    });
}

var loadOfficialItems = function () {

    $.ajax({
       url: "/items/theatre",
       type: "GET",
       contentType: "application/json",
       beforeSend: function (request) {
           request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
       },
        success: function (data) {
            console.log(data);

            $("#official-items-item-wrapper").empty();

            $.each(data, function (index, item) {

                var itemDiv = makeOfficialItem(item);

                $("#official-items-item-wrapper").append(itemDiv);
            });
        },
        error: function (response) {
            alert(response);
        }
    });
};

var makeOfficialItem = function (item) {

    return $("<div class=\"official-items-item\">\n" +
        "                        <span class=\"official-items-item-content\">"+ item.name + "</span>\n" +
        "                        <input type='hidden' value=\"" + item.id + " \">\n" +
        "                        <div class=\"official-items-btns\">\n" +
        "                            <button class=\"official-items-btn official-items-btn-edit\">E</button>\n" +
        "                            <button class=\"official-items-btn official-items-btn-delete\">D</button>\n" +
        "                        </div>\n" +
        "                        <div class='official-items-edit-container'>\n" +
        "                            Name:\n" +
        "                            <div class='my-bids-edit-row'><input type='text' class='my-offers-edit-row' id='"+ item.id+  "-edit-name-official'></div>\n" +
        "                            Description:\n" +
        "                            <div class='my-bids-edit-row'><textarea rows='10' cols='60' class='my-offers-edit-row' id='"+item.id+"-edit-description-official'></textarea></div>\n" +
        "                            Price:\n" +
        "                            <div class='my-bids-edit-row'><input type='number' min=\"0\" class='my-offers-edit-row' id='"+item.id+"-edit-price-official'></div>\n" +
        "                            Quantity:\n" +
        "                            <div class=\"my-bids-edit-row\"> <input type=\"number\" min=\"0\" class='my-offers-edit-row' id='"+item.id+"-edit-quantity-official'></div>\n" +
        "                            Image:\n" +
        "                            <div class='my-bids-edit-row'> <input type='file' id='"+item.id+"-edit-image-official'></div>\n" +
        "                            <button class='official-items-edit-save-btn'>SAVE</button>\n" +
        "                            <input type='hidden' value=\"" + item.id + "\">\n" +
        "                        </div>\n" +
        "                    </div>");
};


var makeMyOfferBidItem = function (item) {
    return $("<div class=\"my-offers-offers-item\">\n" +
        "                            <span class=\"my-offers-offers-content\">" + item.bid + "</span>\n" +
        "                            <div class=\"my-offers-offers-btns\">\n" +
        "                                <button class=\"my-offers-offers-btn my-offers-offers-btn-accept\">A</button>\n" +
        "                                <button class=\"my-offers-offers-btn my-offers-offers-btn-reject\">R</button>\n" +
        "                            </div>" +
        "                           <input type='hidden' value=" + item.id + ">\n" +
        "                        </div>");
}

var loadMyBids = function () {

    $.ajax({
       url:"/bid",
       type:"GET",
       contentType: "application/json",
       beforeSend: function (request) {
           request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
       },
       success: function (data) {
           console.log(data);

           $("#my-bids-item-wrapper").empty();

           $.each(data, function (index, item) {

               var itemDiv = makeMyBidItem(item);
               $("#my-bids-item-wrapper").append(itemDiv);
           });
       },
        error: function (response) {
            alert(response);
        }
    });

};

$(document).on('click', ".my-bids-btn-edit", function () {

    var parent = $(this).parent();
    var editContainer = $(parent).next();

    if ($(editContainer).css("display") === "none"){
        $(editContainer).slideDown();
    }else{
        $(editContainer).slideUp();
    }
});


var makeMyBidItem = function (item) {
    return $("<div class=\"my-bids-item\">\n" +
        "                        <span class=\"my-bids-item-content\">" + item.item.name + "</span>\n" +
        "                        <span class=\"my-bids-item-bid\">Bid: " + item.bid + "</span>\n" +
        "                        <input type=\"hidden\" value=" + item.id + ">\n" +
        "                        <div class=\"my-bids-btns\">\n" +
        "                            <button class=\"my-bids-btn my-bids-btn-edit\">E</button>\n" +
        "                            <button class=\"my-bids-btn my-bids-btn-delete\">D</button>\n" +
        "                        </div>" +
        "       <div class=\"my-bids-edit-container\">\n" +
        "" +
        "          Bid:     <div class=\"my-bids-edit-row\"><input type='number' value=" + item.bid + " name='my-bids-edit-field'></div>\n" +
        "          <button class=\"my-bids-edit-save-btn\">SAVE</button> <input type='hidden' value=" + item.id + ">\n" +
        "      </div>\n" +
        "\n" +
        "                    </div>");
};

var loadMyOffers = function () {


    $.ajax({
       url:"/items/myoffers",
       type:"GET",
       contentType: "application/json",
       beforeSend: function (request) {
           request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
       },
        success: function (data) {
            console.log(data);

            $("#my-offers-item-wrapper").empty();

            $.each(data, function (index, item) {

                var itemDiv = makeMyOfferItem(item);
                $("#my-offers-item-wrapper").append(itemDiv);
            });
        },
        error: function (response) {
            alert(response);
        }
    });
};

var makeMyOfferItem = function (item) {
    return $("<div class=\"my-offers-item\">\n" +
        "                        <span class=\"my-offers-item-content\">" + item.name + "</span>\n" +
        "                        <input type='hidden' value=" + item.id + ">" +
    "                           <div class=\"my-offers-btns\">\n" +
        "                            <button class=\"my-offers-btn my-offers-btn-edit\">E</button>\n" +
        "                            <button class=\"my-offers-btn my-offers-btn-delete\">D</button>\n" +
        "                        </div> " +
        "<div class='my-offers-edit-container'> " +
        "Name: " +
        "<div class='my-bids-edit-row'><input type='text' id='" + item.id + "-name-edit'></div> " +
        "Description: " +
        "<div class='my-bids-edit-row'><textarea rows='10' cols='60' id='" + item.id + "-description-edit' class='my-offers-edit-row'></textarea></div>" +
        "Expiration date:" +
        "<div class='my-bids-edit-row'><input type='date' id='"+item.id+"-date-edit'> <input type='time' id='"+item.id+"-time-edit'></div>" +
        "Image:" +
        "<div class='my-bids-edit-row'> <input type='file' id='" + item.id + "-image-edit'></div>" +
        "<button class='my-offers-edit-save-btn'>SAVE</button>" +
        "<input type='hidden' value=" + item.id + ">" +
        "</div>\n" +
        "                    </div> <div class='my-offers-offers'></div>");
}

var loadEvaluateOffers = function () {

    $.ajax({
        url:"items/evaluate",
        type:"GET",
        contentType:"application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function (data) {
            console.log(data);
            $("#evaluate-offers-wrapper").empty();
            $.each(data, function (index, item) {

                var itemDiv = makeEvaluateOfferItem(item);
                $("#evaluate-offers-wrapper").append(itemDiv);
            });
        },
        error: function (response) {
            alert(response);
        }
    })

};

var loadItems = function () {

    $.ajax({
        url:"/items",
        type:"GET",
        contentType:"application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function (data) {
            console.log(data);
            $("#official-items-container").empty();
            $.each(data.theatreItems, function (index, item) {

                var itemDiv = makeTheatreItem(item);
                $("#official-items-container").append(itemDiv);
            });

            $("#user-items-container").empty();
            $.each(data.userItems, function(index, item){

                var itemDiv = makeUserItem(item);
                $("#user-items-container").append(itemDiv);
            });
        },
        error: function (response) {
            console.log(response);
        }
    })
};

var makeEvaluateOfferItem = function (item) {
  return $(         "<div class=\"evaluate-offers-item\">\n" +
"                        <span class=\"evaluate-offers-item-content\">" + item.name + "</span>" +
"                        <input type='hidden' value=" + item.id +">\n" +
"                        <div class=\"evaluate-offers-btns\">\n" +
"                            <button class=\"evaluate-offers-btn evaluate-offers-btn-accept\">A</button>\n" +
"                            <button class=\"evaluate-offers-btn evaluate-offers-btn-reject\">R</button>\n" +
"                        </div>\n" +
"                    </div>");
};

var makeUserItem = function (item) {
    return $("<div class=\"item\">\n" +
        "                    <div class=\"item-img\">\n" +
        "                        <img src=" + item.imagePath + ">\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"item-content-wrapper\">\n" +
        "                        <div class=\"item-content-opacity\"></div>\n" +
        "                        <div class=\"item-content\">\n" +
        "                            <div class=\"item-content-btn hidden\"> > </div>\n" +
        "                            <div class=\"item-content-container\">\n" +
        "                                <p class=\"item-content-name\">\n" +
                                                item.name +
        "                                </p>\n" +
        "                                <p class=\"item-content-description\">\n" +
        "                                    <i>Description:</i>\n" +
        "                                    <span>\n" +
                                                item.description +
        "                                    </span>\n" +
        "                                </p>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"purchase\">\n" +
        "                        <div class=\"purchase-price purchase-price-bid\">\n" +
        "                            <input type=\"number\" name=\"item-bid\">\n" +
        "                        </div>\n" +
        "                        <button class=\"purchase-btn purchase-btn-bid\">BID</button>\n" +
        "<input type='hidden' value=" + item.id + ">" +
        "                    </div>\n" +
        "                </div>")
}


var makeTheatreItem = function (item) {
    return $("<div class=\"item\">\n" +
        "                    <div class=\"item-img\">\n" +
        "                        <img src=" + item.imagePath + ">\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"item-content-wrapper\">\n" +
        "                        <div class=\"item-content-opacity\"></div>\n" +
        "                        <div class=\"item-content\">\n" +
        "                            <div class=\"item-content-btn hidden\"> > </div>\n" +
        "                            <div class=\"item-content-container\">\n" +
        "                                <p class=\"item-content-name\">\n" +
        "                                    " + item.name +"" +
        "                                </p>\n" +
        "                                <p class=\"item-content-description\">\n" +
        "                                    <i>Description:</i>\n" +
        "                                    <span>\n" +
                                                item.description +
        "                                    </span>\n" +
        "                                </p>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"purchase\">\n" +
        "                        <div class=\"purchase-price\">\n" +
        "                            <span class=\"integer\">" + item.price + "</span>,<span class=\"decimals\">99</span>\n" +
        "                        </div>\n" +
        "                        <button class=\"purchase-btn purchase-btn-buy\">BUY</button>" +
        "                           <input type='hidden' value=" + item.id + ">" +
        "                    </div>\n" +
        "                </div>");
}


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
