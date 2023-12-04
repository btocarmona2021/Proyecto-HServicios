package com.equipoh.hservicios.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jorge
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @GetMapping("/dashboard")
    public String panelAdmin() {
        return "panel";
    }

    @GetMapping("/imagendefault")
    public String imagendefault() {
        return "cargaimagen";
    }

    @GetMapping("/pcensuradas")
    public String pcensuradas() {
        return "palabrascensuradas";
    }

}
