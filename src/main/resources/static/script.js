//Ejecutando funciones
document.getElementById("btn__iniciar-sesion").addEventListener("click", iniciarSesion);
document.getElementById("btn__registrarse").addEventListener("click", register);
window.addEventListener("resize", anchoPage);

//Declarando variables
var formulario_login = document.querySelector(".formulario__login");
var formulario_register = document.querySelector(".formulario__register");
var contenedor_login_register = document.querySelector(".contenedor__login-register");
var caja_trasera_login = document.querySelector(".caja__trasera-login");
var caja_trasera_register = document.querySelector(".caja__trasera-register");

    //FUNCIONES

function anchoPage(){

    if (window.innerWidth > 850){
        caja_trasera_register.style.display = "block";
        caja_trasera_login.style.display = "block";
    }else{
        caja_trasera_register.style.display = "block";
        caja_trasera_register.style.opacity = "1";
        caja_trasera_login.style.display = "none";
        formulario_login.style.display = "block";
        contenedor_login_register.style.left = "0px";
        formulario_register.style.display = "none";   
    }
}

anchoPage();


    function iniciarSesion(){
        if (window.innerWidth > 850){
            formulario_login.style.display = "block";
            contenedor_login_register.style.left = "10px";
            formulario_register.style.display = "none";
            caja_trasera_register.style.opacity = "1";
            caja_trasera_login.style.opacity = "0";
        }else{
            formulario_login.style.display = "block";
            contenedor_login_register.style.left = "0px";
            formulario_register.style.display = "none";
            caja_trasera_register.style.display = "block";
            caja_trasera_login.style.display = "none";
        }
    }

    function register(){
        if (window.innerWidth > 850){
            formulario_register.style.display = "block";
            contenedor_login_register.style.left = "410px";
            formulario_login.style.display = "none";
            caja_trasera_register.style.opacity = "0";
            caja_trasera_login.style.opacity = "1";
        }else{
            formulario_register.style.display = "block";
            contenedor_login_register.style.left = "0px";
            formulario_login.style.display = "none";
            caja_trasera_register.style.display = "none";
            caja_trasera_login.style.display = "block";
            caja_trasera_login.style.opacity = "1";
        }
}
/// navbar
  const navToggle = document.querySelector(".nav-toggle");
const navMenu = document.querySelector(".nav-menu");

navToggle.addEventListener("click", () => {
  navMenu.classList.toggle("nav-menu_visible");

  if (navMenu.classList.contains("nav-menu_visible")) {
    navToggle.setAttribute("aria-label", "Cerrar menú");
  } else {
    navToggle.setAttribute("aria-label", "Abrir menú");
  }
}); 
//// iamgen previsulizacion////

 const $seleccionArchivos = document.querySelector("#seleccionArchivos"),
                    $imagenPrevisualizacion = document.querySelector("#imagenPrevisualizacion");
                    $seleccionArchivos.addEventListener("change", () => {
                        const archivos = $seleccionArchivos.files;
                            if (!archivos || !archivos.length) {
                                $imagenPrevisualizacion.src = "";
                                return;
                            }
  
                        const primerArchivo = archivos[0];
                        const objectURL = URL.createObjectURL(primerArchivo);
                        $imagenPrevisualizacion.src = objectURL;
                    });
 const $seleccionArchivosu = document.querySelector("#seleccionArchivosu"),
                    $imagenPrevisualizacionu = document.querySelector("#imagenPrevisualizacionu");
                    $seleccionArchivosu.addEventListener("change", () => {
                        const archivos = $seleccionArchivosu.files;
                            if (!archivos || !archivos.length) {
                                $imagenPrevisualizacionu.src = "";
                                return;
                            }
  
                        const primerArchivo = archivos[0];
                        const objectURL = URL.createObjectURL(primerArchivo);
                        $imagenPrevisualizacionu.src = objectURL;
                    });                    
 
                    