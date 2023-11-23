/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ContratoServicio;
import com.equipoh.hservicios.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/enviocontrato/{id}")
    public String nuevoConotrato(@PathVariable String id, HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Proveedor proveedor = proveedorServicio.getOne(id);
        modelo.put("usuario", usuario);
        modelo.put("proveedor", proveedor);
        return "registrar_contrato";
    }

    @PostMapping("/solicitudTrabajo")
    public String registroContrato(String idProveedor, HttpSession session, String descTrabajo, String contenido, ModelMap modelo) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        try {
            contratoServicio.registrarContrato(idProveedor, usuario.getId(), descTrabajo, contenido);
            modelo.put("exito", "La solicitud fue enviada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar_contrato";
        }

        return "panel";
    }

    @GetMapping("/aceptacontrato")
    public String aceptacontrato() {
        return "aceptacontrato";
    }

    @PostMapping("/contratoaceptado/{id}")
    public String contratoAceptado(@PathVariable String idContrato, ModelMap modelo) {
        try {
            contratoServicio.aceptacionContrato(idContrato);
            modelo.put("exito", "El trabajo ha sido aceptado");
            return "aceptacontrato";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "aceptacontrato";
        }
    }

    
   /* @GetMapping("/lista")
    public String listarContratos (ModelMap modelo) {
        List<Contrato> contratos = contratoServicio.listarContratos();
        modelo.addAttribute("contratos", contratos);
        return "listar_contrato.html";
    }


    @GetMapping("/actualizar/{id}")
    public String actualizarContrato(@PathVariable String id, ModelMap modelo) {
        modelo.put("contrato", contratoServicio.getOne(id));
        return "modificar_contrato.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarContrato(String id,Date fechaInicio, Date fechaFinal, Boolean solicitudT, Boolean inicioT, Boolean finT,Integer puntuacion, Proveedor proveedor, Usuario usuario, ModelMap modelo) {
        try {
            contratoServicio.actualizarContrato(id, fechaInicio, fechaFinal, solicitudT, inicioT, finT, puntuacion, proveedor, usuario);
            modelo.put("exito", "Contrato actualizado correctamente");
            return "redirect:/contrato/lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar_contrato.html";
        }

    }*/
}
