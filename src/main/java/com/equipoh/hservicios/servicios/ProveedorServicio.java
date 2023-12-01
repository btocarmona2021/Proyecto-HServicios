/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.entidades.SolicitudRol;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.enumeracion.Rol;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ImagenRepositorio;
import com.equipoh.hservicios.repositorios.ProveedorRepositorio;
import com.equipoh.hservicios.repositorios.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author jorge
 */
@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private SolicitudRolServicio solicitudRolServicio;

    @Transactional
    public void registrarProveedor(MultipartFile archivo, String nombre, String apellido, String direccion,
            String telefono, String correo, String password, String password2, String rol,
            String experiencia, Double precioXHora, String idServicio) throws MiException {

        validar(nombre, correo, password, password2);
        Optional<Servicio> respuestaServicio = servicioRepositorio.findById(idServicio);
        Servicio servicio = new Servicio();
        if (respuestaServicio.isPresent()) {
            servicio = respuestaServicio.get();
        }
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setExperiencia(experiencia);
        proveedor.setPrecioXHora(precioXHora);
        proveedor.setServicio(servicio);
        if (archivo.isEmpty()) {
            proveedor.setImagen(imagenRepositorio.imagenXDefecto());
        } else {
            proveedor.setImagen(imagenServicio.guardarImagen(archivo));
        }
        proveedor.setFechaAlta(new Date());
        proveedor.setAlta(true);

        proveedorRepositorio.save(proveedor);

    }

    @Transactional
    public void actualizar(MultipartFile archivo, String id, String nombre, String apellido, String direccion,
            String telefono, String correo, String password, String password2, String rol,
            String experiencia, Double precioXHora, String idServicio, String alta) throws MiException {

        validar(nombre, correo, password, password2);

        Proveedor proveedor = new Proveedor();
        Servicio servicio = servicioRepositorio.getById(idServicio);
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            proveedor = respuesta.get();
        }

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setExperiencia(experiencia);
        proveedor.setPrecioXHora(precioXHora);
        proveedor.setServicio(servicio);
        if (proveedor.getImagen().getNombre().equalsIgnoreCase("defecto_image_service.png")) {
            proveedor.setImagen(imagenServicio.guardarImagen(archivo));
        } else {
            proveedor.setImagen(imagenServicio.actualizarImagen(archivo, proveedor.getImagen().getId()));
        }
        proveedor.setFechaAlta(new Date());
        if (alta.equalsIgnoreCase("ALTA")) {
            proveedor.setAlta(true);
        } else {
            proveedor.setAlta(false);
        }
        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void cambioDeRol(String idProveedor, String idSolicitud) throws MiException {
        Usuario usuario = usuarioServicio.getOne(idProveedor);
        List<Proveedor> existe = proveedorRepositorio.buscarCorreoProveedorInactivo(usuario.getCorreo());

        if (existe.isEmpty()) {
            System.out.println("El Proveedor NO EXISTE - CREA UNO NUEVO");
            Proveedor proveedor = new Proveedor();

            proveedor.setNombre(usuario.getNombre());
            proveedor.setApellido(usuario.getApellido());
            proveedor.setTelefono(usuario.getTelefono());
            proveedor.setDireccion(usuario.getDireccion());
            proveedor.setCorreo(usuario.getCorreo());
            proveedor.setPassword(usuario.getPassword());
            proveedor.setImagen(usuario.getImagen());
            proveedor.setAlta(true);
            proveedor.setFechaAlta(usuario.getFechaAlta());
            proveedor.setRol(Rol.PROVEEDOR);

            SolicitudRol solicitudRol = solicitudRolServicio.getOne(idSolicitud);
            proveedor.setExperiencia(solicitudRol.getExperiencia());
            proveedor.setPrecioXHora(solicitudRol.getPrecioXHora());
            proveedor.setServicio(solicitudRol.getServicio());

            proveedorRepositorio.save(proveedor);
        } else {
            System.out.println("El Proveedor EXISTE - DOY DE ALTA");
            System.out.println(proveedorRepositorio.buscarCorreoInactivo(usuario.getCorreo()));
            Proveedor proveedor = proveedorRepositorio.buscarCorreoInactivo(usuario.getCorreo());
            System.out.println("USUARIO INACTIVO: " + usuario);

            SolicitudRol solicitudRol = solicitudRolServicio.getOne(idSolicitud);
            String experiencia = solicitudRol.getExperiencia();
            Double precioXHora = solicitudRol.getPrecioXHora();
            Servicio servicio = solicitudRol.getServicio();
            String id = proveedor.getId();
            altaProveedor(id, experiencia, precioXHora, servicio);
        }
    }

    public Proveedor getOne(String id) {
        return proveedorRepositorio.getOne(id);
    }

    @Transactional
    public void bajaProveedor(String id) throws MiException {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(Boolean.FALSE);
            proveedorRepositorio.save(proveedor);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MiException("No se pudo dar de baja el Usuario " + proveedorRepositorio.getById(id).getNombre() + ". El usuario no fue encontrado.");
        }
    }
//ALTA
    @Transactional
    public void altaProveedor(String id, String experiencia, Double precioXHora, Servicio servicio) throws MiException {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setExperiencia(experiencia);
            proveedor.setPrecioXHora(precioXHora);
            proveedor.setServicio(servicio);
            proveedor.setAlta(Boolean.TRUE);
            proveedorRepositorio.save(proveedor);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MiException("No se pudo dar de baja el Usuario " + proveedorRepositorio.getById(id).getNombre() + ". El usuario no fue encontrado.");
        }
    }

    @Transactional
    public void bajaProveedor(String id) throws MiException {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(Boolean.FALSE);
            proveedorRepositorio.save(proveedor);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MiException("No se pudo dar de baja el Usuario " + proveedorRepositorio.getById(id).getNombre() + ". El usuario no fue encontrado.");
        }
    }

    @Transactional
    public void altaProveedor(String id, String experiencia, Double precioXHora, Servicio servicio) throws MiException {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setExperiencia(experiencia);
            proveedor.setPrecioXHora(precioXHora);
            proveedor.setServicio(servicio);
            proveedor.setAlta(Boolean.TRUE);
            proveedorRepositorio.save(proveedor);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MiException("No se pudo dar de baja el Usuario " + proveedorRepositorio.getById(id).getNombre() + ". El usuario no fue encontrado.");
        }
    }

    public List<Proveedor> listaProveedores() {
        List<Proveedor> proveedores = new ArrayList();
        proveedores = proveedorRepositorio.buscarProveedores();
//        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }

    private void validar(String nombre, String correo, String password, String password2) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (correo.isEmpty() || correo == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("El password no puede ser nulo o estar vacio y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }
}
