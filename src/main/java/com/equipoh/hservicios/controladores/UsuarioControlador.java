/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.excepciones.MisExcepciones;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author manie
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        System.out.println("IMPRIME 31");
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String direccion, @RequestParam String telefono,
            @RequestParam String correo, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {
        /*
        **********************
        * ¿Es necesario la 'password2' durante el registro?
        **********************
        */
        System.out.println("IMPRIME 45");
        try {
            usuarioServicio.registrarUsuario(nombre, apellido, direccion, telefono, correo, password, password2);
            modelo.put("exito", "El usuario ha sido cargado con éxito");
        } catch (MisExcepciones ex) {
            modelo.put("error", ex.getMessage());
            return "index.html";
        }
        return "index.html";
    }
}
