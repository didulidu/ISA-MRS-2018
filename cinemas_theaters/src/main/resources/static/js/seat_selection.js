var projection = {}
var seatIds = []
var unavailable_seats = [];

$(document).ready(function(){    if(index != -1){
    var index = document.URL.indexOf("?id=");

        id = document.URL.substring(index+4);
        id = 10;
        fetchProjectionData(id);
    }
});



function fetchProjectionData(id){

    $.ajax({
    		url: "/projection/findProjection/" + id,
    		type: "POST",
    		dataType: "json",
    		//data: {id: id},
    		//beforeSend: function(request){
    		//          request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
    		//        },
    		success: function(data){
    			//localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
                projection = data;
                
                displayProjectionInfo(projection);

    			displaySeats(projection);
    		},
    		error: function(response){
    			if(response.status == 401)
    				getToastr("Not authorized for the selected activity!", "Error", 3);
    			else
    				getToastr("Seats couldn't be fetched! \nStatus: " + response.status, "", 3);
    		}
    	});

}

function displayProjectionInfo(projection){
    $projInfo = $(".book-right");

    $projInfo.append(
        "<li>: " +  projection.showTitle + "</li>" +
        "<li>: " + projection.date  + "</li>" +
        "<li>: <span id='counter'>0</span></li>" +
        "<li>: <b><span id='total'>0</span> <i>RSD</i></b></li>"
    );
}

function getMaxChairs(seats){

    max_chairs = seats[0].chairNumber;

    for (var s=0; s<seats.length; s++){
            
            if (seats[s].id == seats[seats.length-1].id){
                if (chairsInARow > max_chairs)
                    max_chairs = chairsInARow;
                break;
            }
            
            if(seats[s].chairRow < seats[s+1].chairRow)
                var chairsInARow = seats[s].chairNumber;
                if (chairsInARow > max_chairs)
                    max_chairs = chairsInARow;
    }

    return max_chairs;
}

function formSeats(projection){
    var seats = projection.hall.seats;
    //var sc = $('#seat-map').seatCharts;



    seats_array = []
    max_chairs = getMaxChairs(seats);

    for (var s=0; s<seats.length; s++){

        if (seats[s].available == false){
            unavailable_seats.push(seats[s].chairRow + "_" + seats[s].chairNumber);
        }

        if (seats[s].id == seats[seats.length-1].id){
            seats_array[seats[s].chairRow-1] = "a".repeat(seats[s].chairNumber);
            if (seats[s].chairNumber < max_chairs){
                for (var i = seats[s].chairNumber; i<max_chairs; i++){
                    seats_array[seats[s].chairRow-1] += "_";
                }
            }
            break;
        }


        if(seats[s].chairRow < seats[s+1].chairRow){
            seats_array[seats[s].chairRow-1] = "a".repeat(seats[s].chairNumber);
            if (seats[s].chairNumber < max_chairs){
                for (var i = seats[s].chairNumber; i< max_chairs; i++){
                    seats_array[seats[s].chairRow-1] += "_";
                }

            }
        }
    }

    //$('#seat-map').seatCharts.get(unavailable_seats).status('unavailable');

    return seats_array;
}

$(document).on("click", "#book-ticket-btn", function(){

      console.log("clicked");

      var ticket = {
          "showTitle": projection.showTitle,
          "projectionDate": projection.date,
          "projectionId": projection.id + "",
          //"issueDate": projection.date
          "seatIds": seatIds
      };



      ticket = JSON.stringify(ticket);

     $.ajax({
        url: "/ticket/reservation",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: ticket,
        beforeSend: function(request){
            request.setRequestHeader("Authorization", localStorage.getItem("currentUserToken"));
        },
        success: function(data){
            //localStorage.setItem("currentUserToken", response.getResponseHeader("Authorization"));
             alert("Great success!")
             location.reload();
        },
        error: function(response){
            if(response.status == 401)
                getToastr("Not authorized for the selected activity!", "Error", 3);
            else
                getToastr("Seats couldn't be fetched! \nStatus: " + response.status, "", 3);
        }
    });


});

function addSeatId(row, num){
    var seats = projection.hall.seats;

    for (var s=0; s<seats.length; s++){

        if (seats[s].chairRow == row + 1 && seats[s].chairNumber == num)
            seatIds.push(seats[s].id + "");
    }
}

function removeSeatId(row, num){
    var seats = projection.hall.seats;

    for (var s=0; s<seats.length; s++){

        if (seats[s].chairRow == row + 1 && seats[s].chairNumber == num){
            var index = seatIds.indexOf(seats[s].id + "");
            seatIds.splice(index, 1);
        }
    }
}

function displaySeats(projection){
    var price = projection.price; //price
				$(document).ready(function() {
					var $cart = $('#selected-seats'), //Sitting Area
					$counter = $('#counter'), //Votes
                    $total = $('#total'); //Total money
                    
                    var seats = formSeats(projection);
                    
                    var ss = [  //Seating chart
                        'aaaaaaaaaa',
                        'aaaaaaaaaa',
                        '__________',
                        'aaaaaaaa__',
                        'aaaaaaaaaa',
                        'aaaaaaaaaa',
                        'aaaaaaaaaa',
                        'aaaaaaaaaa',
                        'aaaaaaaaaa',
                        '__aaaaaa__'
                    ];

					var sc = $('#seat-map').seatCharts({
						map: seats,
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return column;
							}
						},
						legend : { //Definition legend
							node : $('#legend'),
							items : [
								[ 'a', 'available',   'Available' ],
								[ 'a', 'unavailable', 'Sold'],
								[ 'a', 'selected', 'Selected']
							]					
						},
						click: function () { //Click event
							if (this.status() == 'available') { //optional seat
								$('<li>Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);

								addSeatId(this.settings.row, this.settings.label);

								$counter.text(sc.find('selected').length+1);
								$total.text(recalculateTotal(sc)+projection.price);
											
								return 'selected';
							} else if (this.status() == 'selected') { //Checked
									//Update Number
									$counter.text(sc.find('selected').length-1);
									//update totalnum
									$total.text(recalculateTotal(sc)-projection.price);
										
									//Delete reservation
									$('#cart-item-'+this.settings.id).remove();
									//optional

									 removeSeatId(this.settings.row, this.settings.label);

									return 'available';
							} else if (this.status() == 'unavailable') { //sold
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});
					//sold seat
					sc.get(unavailable_seats).status('unavailable');
						
				});
				//sum total money
				function recalculateTotal(sc) {
					var total = 0;
					sc.find('selected').each(function () {
						total += price;
					});
							
					return total;
				}
    }