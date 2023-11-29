const formulario = document.getElementById('formulario');
const inputs = document.querySelectorAll('#formulario input');


const expresiones = {
	
    nombre: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    nombreu: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    apellido: /^[a-zA-Z0-9 ]{3,16}$/, // Letras, numeros, guion y guion_bajo
    apellidou: /^[a-zA-Z0-9 ]{3,16}$/, // Letras, numeros, guion y guion_bajo
    correo: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
    correou: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
    direccion: /^[A-Za-z0-9 ]+$/, // Letras y espacios, pueden llevar acentos.
    direccionu: /^[A-Za-z0-9 ]+$/, // Letras y espacios, pueden llevar acentos.
    servicio: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    experiencia: /^[A-Za-z0-9 ]{50,200}$/, // Letras y espacios, pueden llevar acentos.
    precioXHora: /^\d{1,14}$/, // 7 a 14 numeros.
    password: /^.{5,12}$/, // 4 a 12 digitos.
    passwordu: /^.{5,12}$/, // 4 a 12 digitos.
    telefono: /^\d{7,14}$/, // 7 a 14 numeros.
    telefonou: /^\d{7,14}$/ // 7 a 14 numeros.
}

const campos = {
    nombre: false,
     nombreu: false,
    apellido: false,
    apellidou: false,
    password: false,
    passwordu: false,
    correo: false,
    correou: false,
    direccion: false,
    direccionu: false,
    servicio: false,
    experiencia: false,
    precioXHora: false,
    telefono: false,
    telefonou: false
};

const validarFormulario = (e) => {
	switch (e.target.name) {
		case "apellido":
			validarCampo(expresiones.apellido, e.target, 'apellido');
		break;
		case "nombre":
			validarCampo(expresiones.nombre, e.target, 'nombre');
		break;
		case "password":
			validarCampo(expresiones.password, e.target, 'password');
			validarPassword2();
		break;
		case "password2":
			validarPassword2();
		break;
		case "correo":
			validarCampo(expresiones.correo, e.target, 'correo');
		break;
		case "telefono":
			validarCampo(expresiones.telefono, e.target, 'telefono');
		break;
                case "direccion":
			validarCampo(expresiones.direccion, e.target, 'direccion');
		break;
                case "servicio":
			validarCampo(expresiones.servicio, e.target, 'servicio');
		break;
                case "precioXHora":
			validarCampo(expresiones.precioXHora, e.target, 'precioXHora');
		break;
                 case "experiencia":
			validarCampo(expresiones.experiencia, e.target, 'experiencia');
		break;
	}
};

const validarFormulariou = (e) => {
	switch (e.target.name) {
		case "apellidou":
			validarCampo(expresiones.apellidou, e.target, 'apellidou');
		break;
		case "nombreu":
			validarCampo(expresiones.nombreu, e.target, 'nombreu');
		break;
		case "passwordu":
			validarCampo(expresiones.passwordu, e.target, 'passwordu');
			validarPassword2u();
		break;
		case "password2u":
			validarPassword2u();
		break;
		case "correou":
			validarCampo(expresiones.correou, e.target, 'correou');
		break;
		case "telefonou":
			validarCampo(expresiones.telefonou, e.target, 'telefonou');
		break;
                case "direccionu":
			validarCampo(expresiones.direccionu, e.target, 'direccionu');
		break;
              
	}
};

const validarCampo = (expresion, input, campo) => {
	if(expresion.test(input.value)){
		document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-incorrecto');
		document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-correcto');
		document.querySelector(`#grupo__${campo} i`).classList.add('fa-check-circle');
		document.querySelector(`#grupo__${campo} i`).classList.remove('fa-times-circle');
		document.querySelector(`#grupo__${campo} .formulario__input-error`).classList.remove('formulario__input-error-activo');
		campos[campo] = true;
	} else {
		document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-incorrecto');
		document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-correcto');
		document.querySelector(`#grupo__${campo} i`).classList.add('fa-times-circle');
		document.querySelector(`#grupo__${campo} i`).classList.remove('fa-check-circle');
		document.querySelector(`#grupo__${campo} .formulario__input-error`).classList.add('formulario__input-error-activo');
		campos[campo] = false;
	}
};

const validarPassword2 = () => {
	const inputPassword1 = document.getElementById('password');
	const inputPassword2 = document.getElementById('password2');

	if(inputPassword1.value !== inputPassword2.value){
		document.getElementById(`grupo__password2`).classList.add('formulario__grupo-incorrecto');
		document.getElementById(`grupo__password2`).classList.remove('formulario__grupo-correcto');
		document.querySelector(`#grupo__password2 i`).classList.add('fa-times-circle');
		document.querySelector(`#grupo__password2 i`).classList.remove('fa-check-circle');
		document.querySelector(`#grupo__password2 .formulario__input-error`).classList.add('formulario__input-error-activo');
		campos['password'] = false;
	} else {
		document.getElementById(`grupo__password2`).classList.remove('formulario__grupo-incorrecto');
		document.getElementById(`grupo__password2`).classList.add('formulario__grupo-correcto');
		document.querySelector(`#grupo__password2 i`).classList.remove('fa-times-circle');
		document.querySelector(`#grupo__password2 i`).classList.add('fa-check-circle');
		document.querySelector(`#grupo__password2 .formulario__input-error`).classList.remove('formulario__input-error-activo');
		campos['password'] = true;
	}
};

const validarPassword2u = () => {
	const inputPassword1 = document.getElementById('passwordu');
	const inputPassword2 = document.getElementById('password2u');

	if(inputPassword1.value !== inputPassword2.value){
		document.getElementById(`grupo__password2u`).classList.add('formulario__grupo-incorrecto');
		document.getElementById(`grupo__password2u`).classList.remove('formulario__grupo-correcto');
		document.querySelector(`#grupo__password2u i`).classList.add('fa-times-circle');
		document.querySelector(`#grupo__password2u i`).classList.remove('fa-check-circle');
		document.querySelector(`#grupo__password2u .formulario__input-error`).classList.add('formulario__input-error-activo');
		campos['passwordu'] = false;
	} else {
		document.getElementById(`grupo__password2u`).classList.remove('formulario__grupo-incorrecto');
		document.getElementById(`grupo__password2u`).classList.add('formulario__grupo-correcto');
		document.querySelector(`#grupo__password2u i`).classList.remove('fa-times-circle');
		document.querySelector(`#grupo__password2u i`).classList.add('fa-check-circle');
		document.querySelector(`#grupo__password2u .formulario__input-error`).classList.remove('formulario__input-error-activo');
		campos['passwordu'] = true;
	}
};

inputs.forEach((input) => {
	input.addEventListener('keyup', validarFormulario);
	input.addEventListener('blur', validarFormulario);
        input.addEventListener('keyup', validarFormulariou);
	input.addEventListener('blur', validarFormulariou);
});


