/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Imagen;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.enumeracion.Rol;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrarUsuario(MultipartFile archivo, String nombre, String apellido, String direccion,
            String telefono, String correo, String password, String password2) throws MiException {
        
        List<Usuario> existe = usuarioRepositorio.buscarCorreoUsuarioActivo(correo);
        /*
        quiero que la siguiente linea llame al usuarioRepositorio.buscarCorreoUsuarioActivo(correo) que se fija si el correo no aparece en
        la lista de proveedores, y reemplazar el if actual por 
        if ((existe.size()==2) || (!existe.isEmpty())) {
        */
        if ((!existe.isEmpty())) {
            System.out.println("Ya existe el usuario.");
            throw new MiException("El usuario no ha podido ser registrado porque el correo ya ha sido registrado.");
        } else {
            // Manejo de Excepciones
            validar(nombre, apellido, direccion, telefono, correo, password, password2);

            Usuario usuario = new Usuario();
            
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDireccion(direccion);
            usuario.setTelefono(telefono);
            usuario.setCorreo(correo);
            usuario.setFechaAlta(new Date());

            /****************************
             * Modificar para encriptar *
             ****************************/
            usuario.setPassword(password);
            
            usuario.setImagen(imagenServicio.guardarImagen(archivo));
            
            // Las siguientes lineas buscan si hay algun usuario registrado y al primer usuario registrado le da el rol de ADMIN
            List<Usuario> respuesta = usuarioRepositorio.findAll();
            if (respuesta.isEmpty()) {
                usuario.setRol(Rol.ADMIN);
            } else {
                usuario.setRol(Rol.USUARIO);
            }
            
            usuario.setAlta(Boolean.TRUE);

            // Guardar la variable
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void actualizarUsuario(MultipartFile archivo, String id, String nombre, String apellido,
            String direccion, String telefono, String correo, String password, String password2) throws MiException {

        // Manejo de Excepciones
        validar(nombre, apellido, direccion, telefono, correo, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDireccion(direccion);
            usuario.setTelefono(telefono);
            usuario.setCorreo(correo);

            /**
             * **************************
             * Modificar para encriptar *
             ***************************
             */
            usuario.setPassword(password);
            
            // ********** INICIO ACTUALIZACIÓN DE LA IMAGEN **********
            // Creo una variable que va a guardar la ID de la imagen (para qwue no me de error cuando vaya a 'guardar')
            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            
            usuario.setImagen(imagenServicio.actualizarImagen(archivo, idImagen));
            // ********** FIN ACTUALIZACIÓN DE LA IMAGEN **********
            
            /**
             * *************************************
             * ESPACIO RESERVADO CAMBIO DE USUARIO *
             **************************************
             */
            
            /*****************************
             * La siguiente linea sirve SOLO si se quiere CONSERVAR el rol. 
             * Caso contrario, ver de que el no se altere el ADMIN al intercambiar
             * entre USUARIO y PROVEEDOR y viceversa
             * ***************************
            */
            usuario.setRol(usuario.getRol());

            usuario.setAlta(Boolean.TRUE);

            // Guardar el objeto
            usuarioRepositorio.save(usuario);
        }

    }
    
    /****************************************************
     * RESERVADO PARA CAMBIO DE TIPO DE USUARIO         *
     * NOTA: el método para cambio de tipo de usuario,  *
     * crearía un usuario de tipo proveedor, y llamaría *
     * al método bajaUsuario para setear alta a FALSE.  *
     ****************************************************/
    
    @Transactional
    public void bajaUsuario (String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(Boolean.FALSE);
            usuarioRepositorio.save(usuario);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MiException("No se pudo dar de baja el Usuario " + usuarioRepositorio.getById(id).getNombre() + ". El usuario no fue encontrado.");
        }
    }

    public List<Usuario> listarUsuario() {
        // La lista va a recuperar a todos los usuarios para mostrar en la pagina a todos ellos
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    private void validar(String nombre, String apellido, String direccion, String telefono, String correo, String password, String password2) throws MiException {
        if ((nombre.isEmpty()) || (nombre == null)) {     // Si el nombre ESTÁ VACÍO o es NULO
            throw new MiException("El nombre no puede estar vacío o ser nulo.");
        }
        if ((apellido.isEmpty()) || (apellido == null)) {     // Si el apellido ESTÁ VACÍO o es NULO
            throw new MiException("El nombre no puede estar vacío o ser nulo.");
        }
        if ((direccion.isEmpty()) || (direccion == null)) {     // Si direccion ESTÁ VACÍA o es NULA
            throw new MiException("El nombre no puede estar vacío o ser nulo.");
        }
        if ((correo.isEmpty()) || (correo == null)) {     // Si el correo ESTÁ VACÍO o es NULO
            throw new MiException("El correo electrónico no puede estar vacío o ser nulo.");
        }
        // VER EL TAMAÑO DE LA CONTRASEÑA
        if ((password.isEmpty()) || (password == null) || (password.length() < 5)) {     // Si la contraseña ESTÁ VACÍA o es NULL
            throw new MiException("La contraseña no puede estar vacía, y debe tener, al menos, 5 caracteres.");
        }
        if (!password.equals(password2)) {     // Si las contraseñas no coinciden
            throw new MiException("Las contraseñas ingresadas NO son iguales.");
        }
    }
    
    
    @Transactional
    // Método que devuelve el usuario por id
    public Usuario buscarUsuario(String id) {
        return usuarioRepositorio.buscarUsuario(id);
    }
    
    // Este metodo busca un 'DATO' en la base de datos una información solicitada por el BUSCADOR
    public List<Usuario> buscarDato(String dato) {
        return usuarioRepositorio.buscarDato(dato);
    }

    /**************************** 
     * Método invocado por el metodo CONTROLADOR de IMAGEN que recibe las solicitudes del
     * perfil de usuario para cargar la imagen y devuelve la imagen al http
     * ****************************/
    public Usuario obtenerUsuario(String id) {
        return buscarUsuario(id);
    }
}
