$(document).ready(function(){
	fillWithFastTickets();
});

function fillWithFastTickets(){
	 $.ajax({
                url: "/theatre/getFastTickets",
                type: "GET",
                dataType: "json",
                success: function(data){
                    forward_fast_tickets(JSON.stringify(data));
                },
                error: function (response) {
                    alert("Ne radi: "+ response.status)
                }
            });   
}
