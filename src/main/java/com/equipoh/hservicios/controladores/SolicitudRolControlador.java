/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.entidades.SolicitudRol;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ServicioServicio;
import com.equipoh.hservicios.servicios.SolicitudRolServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jorge
 */
@Controller
@RequestMapping("/solicitudRol")
public class SolicitudRolControlador {

    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private SolicitudRolServicio solicitudRolServicio;

    //Prueba Cambio de Rol
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/cambioRol")
    public String cambioRol(HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.put("usuario", usuario);
        return "solicitud_cambio_rol";
    }

    @PostMapping("/cambioRol")
    public String crearCambioRol(String idProveedor, ModelMap modelo) {
        System.out.println("CONTROLADOR CAMBIO ROL");
        System.out.println("id Proveedor:" + idProveedor);
        try {
            solicitudRolServicio.crearSolicitudRolUsuario(idProveedor);
            modelo.put("exito", "La solicitud ha sido cargada con éxito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "solicitud_cambio_rol";
        }
        return "panel";
    }

    @GetMapping("/lista")      
    public String listar(ModelMap modelo) {

        List<SolicitudRol> solicitudes = solicitudRolServicio.listarSolicitudesRol();

        System.out.println("SOLICITUDES: "+solicitudes);
        modelo.addAttribute("solicitudes", solicitudes);

        return "listar_solicitudes";
    }
    
    @PostMapping("/actualizar")
    public String actualizarEstado(String idSolicitud, ModelMap modelo){
        System.out.println("idSolicitud: "+idSolicitud);
        try {
            solicitudRolServicio.actualizarSolicitudRol(idSolicitud);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../lista";
        }
    }
}
