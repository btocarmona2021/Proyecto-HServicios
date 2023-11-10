

package com.equipoh.hservicios.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

   

   
    @GetMapping("/")
    public String index() {
     return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
    @GetMapping("/panel")
    public String panel() {
        return "panel.html";
    }
    
}
