var show_id;
$(document).ready(function(){
	var index = document.URL.indexOf("?id=");
	show_id = document.URL.substring(index+4);
	$.ajax({
		url: "show/getProjection/"+show_id,
        type: "GET",
        dataType: "json",
        success: function(data){
        	alert(JSON.stringify(data));
        	forward_projections(data);
        },
        error: function (response) {
            alert("Ne radi profil filma: "+ response.status);
            return null;
        }
	});
});


function forward_projections(data){
	var table = "<table border=\"2\">"
	+"<tr>"
    	+"<th>Date and time</th>"
    	+"<th>Hall</th>"
    	+"<th>Price</th>"
  		if(localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="RegisteredUser")
			table+="<th>"+" :) "+"</th>"
		table+="</tr>"
  	data.forEach(function(projection){
  		table+="<tr>"
    			+"<td>"+projection["date"]+"</td>"
    			+"<td>"+projection["hall"]["name"]+"</td>"
    			+"<td>"+projection["price"]+"</td>"
		if(localStorage.getItem("currentUser")!=undefined && JSON.parse(localStorage.getItem("currentUser"))["type"]=="RegisteredUser")
			table+="<td>"+"<button type='button' onclick='foo("+projection["id"]+")' class='btn btn-success add-projection' >Reservation</button>"+"</td>"
		table+="</tr>"
  	});
  	table+="</table>"
  	$("#projection-container").append(table);
}

// Izmeniti da ne radi sa show id nego sa projection id. Dodati projection id i hall id u tabelu
 function foo(id){
	alert("buja")
	if(localStorage.getItem("currentUser")!=undefined){
        if(JSON.parse(localStorage.getItem("currentUser"))["type"] == "RegisteredUser")
            window.location.replace("seat_selection.html?id=" + id);  
	}

}