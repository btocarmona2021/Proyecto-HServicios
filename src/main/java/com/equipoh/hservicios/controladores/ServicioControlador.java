/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ServicioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pc
 */
@Controller
@RequestMapping("/servicio")
public class ServicioControlador {

    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/registrar")
    public String registrarServicio() {
        return "registrar_servicio.html";
    }

    @PostMapping("/registro")
    public String registroServicio(String rubro, MultipartFile archivo, ModelMap modelo) {
        System.out.println("imagen: "+archivo);
        try {
            servicioServicio.registrarServicio(rubro, archivo);
            modelo.put("exito", "El servicio fue registrado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar_servicio.html";
        }

        return "panel";
    }

    @GetMapping("/lista")
    public String listarServicios (ModelMap modelo) {
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "listar_servicio.html";
    }


    @GetMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, ModelMap modelo) {
        modelo.put("servicio", servicioServicio.getOne(id));
        return "modificar_servicio.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(String id,String rubro,MultipartFile archivo, Boolean estado, ModelMap modelo) {
        try {
            servicioServicio.actualizarServicio(id, rubro, estado, archivo);
            modelo.put("exito", "Servicio actualizado correctamente");
            return "redirect:/servicio/lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar_servicio.html";
        }

    }
}
