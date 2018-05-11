var projection = {}

$(document).ready(function(){
    var index = document.URL.indexOf("?id=");
    if(index != -1){
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


    seats_array = []
    max_chairs = getMaxChairs(seats);

    for (var s=0; s<seats.length; s++){

        if (seats[s].id == seats[seats.length-1].id){
            seats_array[seats[s].chairRow-1] = "a".repeat(seats[s].chairNumber);
            if (seats[s].chairNumber < max_chairs){
                for (var i = seats[s].chairNumber; i<max_chairs; i++){
                    seats_array[seats[s].chairRow-1]+="_";
                }
            }
            break;
        }


        if(seats[s].chairRow < seats[s+1].chairRow){
            seats_array[seats[s].chairRow-1] = "a".repeat(seats[s].chairNumber);
            if (seats[s].chairNumber < max_chairs){
                for (var i = seats[s].chairNumber; i< max_chairs; i++){
                    seats_array[seats[s].chairRow-1]+="_";
                }

            }
        }
    }

    return seats_array;
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
									return 'available';
							} else if (this.status() == 'unavailable') { //sold
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});
					//sold seat
					sc.get(['1_2', '4_4','4_5','6_6','6_7','8_5','8_6','8_7','8_8', '10_1', '10_2']).status('unavailable');
						
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