/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Contrato;
import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ContratoServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/contrato")
public class ContratoControlador {
    
    @Autowired
    private ContratoServicio contratoServicio;

    @GetMapping("/registrar")
    public String registrarContrato() {
        return "registrar_contrato.html";
    }
    
     @PostMapping("/registro")
    public String registroContrato(Date fechaInicio, Date fechaFinal, Boolean solicitudT, Boolean inicioT, Boolean finT,Integer puntuacion, Proveedor proveedor, Usuario usuario,  ModelMap modelo) {
        
        try {
            contratoServicio.registrarContrato(fechaInicio, fechaFinal, solicitudT, inicioT, finT, puntuacion, proveedor, usuario);
            modelo.put("exito", "El contrato fue registrado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar_contrato.html";
        }

        return "index.html";
    }
    
    @GetMapping("/lista")
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

    }
}
