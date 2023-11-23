package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.PcensuradaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/palabras")
public class PcensuradaControlador {
    @Autowired
    private PcensuradaServicio pcensuradaServicio;

    @GetMapping("/ingresar")
    public String ingresaPalabras() {
        return "palabrascensuradas";
    }


    @PostMapping("/ingresado")
    public String palabrasingresadas(String palabras, ModelMap modelo) {
        String[] palabrasRecibidas = palabras.split("[\n,\\s]+");
        try {
            for (String palabra : palabrasRecibidas) {
                pcensuradaServicio.ingresarPalabra(palabra);
                modelo.put("exito", "La palabra se ha ingresado correctamente");
            }
            modelo.put("exito", "Palabras ingresadas correctamente");
            return "palabrascensuradas";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
        }
        return "palabrascensuradas";
    }
}
