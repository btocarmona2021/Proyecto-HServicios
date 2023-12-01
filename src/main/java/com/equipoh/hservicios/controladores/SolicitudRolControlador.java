/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.SolicitudRol;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ServicioServicio;
import com.equipoh.hservicios.servicios.SolicitudRolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        return "solicitud_cambio_rol";
    }

    @PostMapping("/cambioRol")
    public String crearCambioRol(String idProveedor, String rol, String idServicio, Double precioXHora, String experiencia, ModelMap modelo) {
        System.out.println("CONTROLADOR CAMBIO ROL");
        System.out.println("id Proveedor:" + idProveedor);
        System.out.println("TIPO DE USUARIO: " + rol);
        System.out.println("Experiencia: " + experiencia);
        System.out.println("precioXHora: " + precioXHora);
        System.out.println("idServicio: " + idServicio);
        if (rol.equalsIgnoreCase("PROVEEDOR")) {
            try {
                System.out.println("INGRESA COMO PROVEEDOR A CAMBIO USUARIO");
                solicitudRolServicio.crearSolicitudRolUsuario(idProveedor);
                modelo.put("exito", "La solicitud ha sido cargada con éxito");
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
                return "solicitud_cambio_rol";
            }
        } else {
            try {
                System.out.println("INGRESA COMO USUARIO A CAMBIO PROVEEDOR");
                solicitudRolServicio.crearSolicitudRolProveedor(idProveedor, idServicio, precioXHora, experiencia);
                modelo.put("exito", "La solicitud ha sido cargada con éxito");
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
                return "solicitud_cambio_rol";
            }
        }

        return "panel";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<SolicitudRol> solicitudes = solicitudRolServicio.listarSolicitudesRol();

        modelo.addAttribute("solicitudes", solicitudes);

        return "listar_solicitudes";
    }

    @PostMapping("/actualizar")
    public String actualizarEstado(String idSolicitud, ModelMap modelo) {
        System.out.println("idSolicitud: " + idSolicitud);
        try {
            solicitudRolServicio.actualizarSolicitudRol(idSolicitud);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../lista";
        }
    }
}