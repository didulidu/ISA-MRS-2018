$(document).on('click', '.theater-card',function(e){
    e.preventDefault();
    var name = this.querySelector(".card-title").innerHTML;
    var id = this.id.split("theater_id_")[1];
    localStorage.setItem("theater",id);
    alert("ID poz: " + id)
    window.location.href = "profile.html";

});

