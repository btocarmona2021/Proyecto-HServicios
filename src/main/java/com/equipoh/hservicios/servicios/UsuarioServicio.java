/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.enumeracion.Rol;
import com.equipoh.hservicios.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrarUsuario(String nombre, String apellido, String direccion, String telefono, String correo, Date fechaAlta, String password, String password2) throws MiException {
        // Manejo de Excepciones
        validar(nombre, correo, password, password2);

        Usuario user = new Usuario();

        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setDireccion(direccion);
        user.setTelefono(telefono);
        user.setCorreo(correo);
        user.setFechaAlta(fechaAlta);
        /**
         * *************************
         * Modificar para encriptar *************************
         */
        user.setPassword(password);
        user.setRol(Rol.USUARIO);
        user.setAlta(Boolean.TRUE);
        // Guardar la variable
        usuarioRepositorio.save(user);
    }

    @Transactional
    public void actualizarUsuario(String id, String nombre, String apellido, String direccion, String telefono, String correo, Date fechaAlta, String password, String password2) throws MisExcepciones {
        // Manejo de Excepciones
        validar(nombre, correo, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario user = respuesta.get();
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setDireccion(direccion);
            user.setTelefono(telefono);
            user.setCorreo(correo);
            user.setFechaAlta(fechaAlta);
            /****************************
             * Modificar para encriptar *
             ****************************/
            user.setPassword(password);
            /**
             * **********************************
             * ESPACIO RESERVADO PARA LA IMAGEN *
             * **********************************
             */
            /**
             * *************************************
             * ESPACIO RESERVADO CAMBIO DE USUARIO *
             * *************************************
             */
            user.setRol(Rol.USUARIO);

            user.setAlta(Boolean.TRUE);

            // Guardar la variable
            usuarioRepositorio.save(user);
        }

    }

    /**
     * *************************
     * Dejar este metodo para más tarde: CREAR ACTUALIZAR y ELIMINAR ANTES
     */
    public List<Usuario> listarUsuarios() {
        // La lista va a recuperar a todos los usuarios para mostrar en la pagina a todos ellos
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    private void validar(String nombre, String correo, String password, String password2) throws MisExcepciones {
        if ((nombre.isEmpty()) || (nombre == null)) {     // Si el nombre ESTÁ VACÍO o es NULO
            throw new MisExcepciones("El nombre no puede estar vacío o ser nulo.");
        }
        if ((correo.isEmpty()) || (correo == null)) {     // Si el correo ESTÁ VACÍO o es NULO
            throw new MisExcepciones("El correo electrónico no puede estar vacío o ser nulo.");
        }
        // VER EL TAMAÑO DE LA CONTRASEÑA
        if ((password.isEmpty()) || (password == null) || (password.length() < 5)) {     // Si la contraseña ESTÁ VACÍA o es NULL
            throw new MisExcepciones("La contraseña no puede estar vacía, y debe tener, al menos, 5 caracteres.");
        }
        if (!password.equals(password2)) {     // Si las contraseñas no coinciden
            throw new MisExcepciones("Las contraseñas ingresadas NO son iguales.");
        }
    }

}
