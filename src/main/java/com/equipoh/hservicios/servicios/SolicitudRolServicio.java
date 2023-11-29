/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.SolicitudRol;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ProveedorRepositorio;
import com.equipoh.hservicios.repositorios.SolicitudRolRepositorio;
import com.equipoh.hservicios.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jorge
 */
@Service
public class SolicitudRolServicio {

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private SolicitudRolRepositorio solicitudRolRepositorio;

    public void crearSolicitudRolUsuario(String idProveedor) throws MiException {
        System.out.println("SOLICITUD GENERADA POR UN PROVEEDOR A USUARIO");
        SolicitudRol solicitudRol = new SolicitudRol();
        solicitudRol.setFechaSolicitud(new Date());
        solicitudRol.setEstado(false);
        
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            solicitudRol.setProveedor(proveedor);
            solicitudRolRepositorio.save(solicitudRol);
        }else{
           throw new MiException("No se pudo crear la Solicitud"); 
        }
    }

    public void crearSolicitudRolProveedor(String idProveedor, String idServicio, String precioXHora, String experiencia) throws MiException {
        System.out.println("SOLICITUD GENERADA POR UN USUARIO A PROVEEDOR");
        SolicitudRol solicitudRol = new SolicitudRol();
        solicitudRol.setFechaSolicitud(new Date());
        solicitudRol.setEstado(false);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idProveedor);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            solicitudRol.setUsuario(usuario);
            solicitudRolRepositorio.save(solicitudRol);
        }else{
           throw new MiException("No se pudo crear la Solicitud"); 
        }
    }

    public List<SolicitudRol> listarSolicitudesRol() {
        List<SolicitudRol> solicitudes = solicitudRolRepositorio.findAll();
        return solicitudes;
    }

    @Transactional
    public void actualizarSolicitudRol(String idSolicitud) throws MiException {
        Optional<SolicitudRol> respuesta = solicitudRolRepositorio.findById(idSolicitud);
        if (respuesta.isPresent()) {
            SolicitudRol solicitudRol = respuesta.get();
            solicitudRol.setEstado(Boolean.TRUE);
           
            String idProveedor = solicitudRol.getProveedor().getId();
            proveedorServicio.bajaProveedor(idProveedor);
            usuarioServicio.cambioDeRol(idProveedor);
            solicitudRolRepositorio.save(solicitudRol);
        } else {
            throw new MiException("No se pudo dar de alta la solicitud ");
        }
    }
}
