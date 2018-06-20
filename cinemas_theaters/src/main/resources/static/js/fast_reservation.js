$(document).ready(function(){
	fillWithFastTickets();
});

function fillWithFastTickets(){
	 $.ajax({
                url: "/theatre/getFastTickets",
                type: "GET",
                dataType: "json",
                success: function(data){
                    forward_fast_tickets(data);
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });   
}

// this.hallName = hallName;
//         this.projectionDate = projectionDate;
//         this.discount = discount;
//         this.title = title;
//         this.price = price;
//         this.id = id;
//         this.seatNumber = seatNumber;
//         this.rowNumber = rowNumber;
//         this.theatreId = theatreId;
function forward_fast_tickets(data){
	$("#quick-ticket-table").empty();
	data.forEach(function(ticket){
		$('#quick-ticket-table').append("<tr><td>"+ticket["title"]+"</td><td>"+ticket["projectionDate"]+"</td><td>"+ticket["hallName"]+"</td><td>"+ticket["seatNumber"]+"-"+ticket["rowNumber"]+"</td><td>"+ticket["price"]+"</td> <td>"+ticket["discount"]+"</td>"+"<td>"+ticket["id"]+"</td>"+" </tr>")
	});
}
