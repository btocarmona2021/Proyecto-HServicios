/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.enumeracion.Rol;
import com.equipoh.hservicios.excepciones.MisExcepciones;
import com.equipoh.hservicios.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrarUsuario(String nombre, String apellido, String direccion,
            String telefono, String correo, String password, String password2) throws MisExcepciones {

        // Manejo de Excepciones
        validar(nombre, correo, password, password2);

        Usuario user = new Usuario();

        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setDireccion(direccion);
        user.setTelefono(telefono);
        user.setCorreo(correo);
        user.setFechaAlta(new Date());

        /****************************
         * Modificar para encriptar *
         ****************************/
        user.setPassword(password);

        /************************************
         * ESPACIO RESERVADO PARA LA IMAGEN *
         ************************************/
        
        // Las siguientes lineas buscan si hay algun usuario registrado y al primer usuario registrado le da el rol de ADMIN
        List<Usuario> respuesta = usuarioRepositorio.findAll();
        if (respuesta.isEmpty()) {
            user.setRol(Rol.ADMIN);
        } else {
            user.setRol(Rol.USUARIO);
        }
        

        user.setAlta(Boolean.TRUE);

        // Guardar la variable
        usuarioRepositorio.save(user);
    }

    @Transactional
    public void actualizarUsuario(String id, String nombre, String apellido,
            String direccion, String telefono, String correo, String password,
            String password2) throws MisExcepciones {

        // Manejo de Excepciones
        validar(nombre, correo, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDireccion(direccion);
            usuario.setTelefono(telefono);
            usuario.setCorreo(correo);

            /****************************
             * Modificar para encriptar *
             ****************************/
            usuario.setPassword(password);

            /************************************
             * ESPACIO RESERVADO PARA LA IMAGEN *
             ************************************/
            /***************************************
             * ESPACIO RESERVADO CAMBIO DE USUARIO *
             ***************************************/
            usuario.setRol(Rol.USUARIO);
            usuario.setAlta(Boolean.TRUE);

            // Guardar la variable
            usuarioRepositorio.save(usuario);
        }

    }

    @Transactional
    public void bajaUsuario(String id) throws MisExcepciones {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuarioRepositorio.getById(id).setAlta(Boolean.FALSE);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MisExcepciones("No se pudo dar de baja el Usuario " + usuarioRepositorio.getById(id).getNombre() + ". El usuario no fue encontrado.");
        }
    }

    public List<Usuario> listarUsuario() {
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
