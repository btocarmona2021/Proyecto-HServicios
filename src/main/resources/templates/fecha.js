function fecha(){ 
    var fecha = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    document.getElementById('fecha').innerHTML = month + "/" + day + "/" + year;
    console.log(fecha);
}