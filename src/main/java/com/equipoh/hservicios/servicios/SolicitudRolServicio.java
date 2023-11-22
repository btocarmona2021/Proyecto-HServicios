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
    private SolicitudRolRepositorio solicitudRolRepositorio;

    @Transactional
    public void crearSolicitudRolUsuario(String id, MultipartFile archivo) throws MiException {
        /* ESTA ES UNA OPCION A PROBAR PARA BUSCAR SI EXISTE UNA SOLICITUD X MEDIO DEL ID DEL PROVEEDOR
        List<SolicitudRol> respuestaSol = solicitudRolRepositorio.buscarSolicitudXProveedor(id);
        if (respuestaSol.isEmpty()) {
            SolicitudRol solicitudRol = new SolicitudRol();
            solicitudRol.setFechaSolicitud(new Date());
        } else {
            SolicitudRol solicitudRol = respuestaSol;
        }*/

        SolicitudRol solicitudRol = new SolicitudRol();
        solicitudRol.setFechaSolicitud(new Date());

        //Una vez creado el nuevo Usuario, doy de baja el Proveedor 
        proveedorServicio.bajaProveedor(id);
        solicitudRol.setEstado(false);

        //Busco un proveedor a partir del Id 
        Proveedor proveedor = new Proveedor();
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            proveedor = respuesta.get();
        }
        //Creo un Usuario nuevo a partir de los datos obtenidos del Proveedor  
        usuarioServicio.registrarUsuario(
                archivo,
                proveedor.getNombre(),
                proveedor.getApellido(),
                proveedor.getDireccion(),
                proveedor.getTelefono(),
                proveedor.getCorreo(),
                proveedor.getPassword(),
                proveedor.getPassword()); // Repito el mismo password
        //Seteo el proveedor que solicitó el cambio en la solicitud
        solicitudRol.setProveedor(proveedor);

        //Guardo la solicitud en la base de datos
        solicitudRolRepositorio.save(solicitudRol);

    }

    public void crearSolicitudRolProveedor(String id, MultipartFile archivo) throws MiException {

    }

    public List<SolicitudRol> listarSolicitudesRol() {
        List<SolicitudRol> solicitudes = solicitudRolRepositorio.findAll();
        return solicitudes;
    }

    @Transactional
    public void actualizarSolicitudRol(String id) throws MiException {
        Optional<SolicitudRol> respuesta = solicitudRolRepositorio.findById(id);
        if (respuesta.isPresent()) {
            SolicitudRol solicitudRol = respuesta.get();
            solicitudRol.setEstado(Boolean.TRUE);
            solicitudRolRepositorio.save(solicitudRol);
        } else {
            // En el supuesto que no existiera el usuario...
            throw new MiException("No se pudo dar de baja la solicitud " + solicitudRolRepositorio.getById(id) + ". La solicitud no fue encontrada.");
        }
    }
}
