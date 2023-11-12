/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MisExcepciones;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/registrar")    // localhost:8080/usuario/registrar
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
        try {
            usuarioServicio.registrarUsuario(nombre, apellido, direccion, telefono, correo, password, password2);
            modelo.put("exito", "El usuario ha sido cargado con éxito");
        } catch (MisExcepciones ex) {
            modelo.put("error", ex.getMessage());
            return "index.html";
        }
        return "inicio.html";
    }
    
    @GetMapping("/listar")       // localhost:8080/usuario/listar 
    public String listar(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);

        return "listar_usuario.html";
    }
    
    @GetMapping("/actualizar/{id}")      // localhost:8080/usuario/actualizar/{id}
    public String actualizar(@PathVariable String id, ModelMap modelo) {
        modelo.put("usuario", usuarioServicio.buscarUsuario(id));
        return "modificar_usuario.html";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, String nombre, String apellido, String direccion, String telefono, String correo, String password, String password2, ModelMap modelo) {
        try {
            usuarioServicio.actualizarUsuario(id,  nombre, apellido, direccion, telefono, correo, password, password2);
            return "redirect:../lista";
        } catch (MisExcepciones ex) {
            modelo.put("error", ex.getMessage());
            return "modificar_usuario.html";
        }

    }
}
