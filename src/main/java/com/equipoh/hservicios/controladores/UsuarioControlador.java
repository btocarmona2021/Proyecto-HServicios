/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
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
        return "registrar";
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
            return "index";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Usuario usuario = usuarioServicio.obetenerUsuario(id);
        modelo.put("usuario", usuario);
        return "modificar_usuario.html";
    }

    @PostMapping("/modificado/{id}")
    public String usuarioModificado(String id, String nombre, String apellido,
                                    String direccion, String telefono,
                                    String correo, String password,
                                    String password2, ModelMap modelo) {
        Usuario usuario = usuarioServicio.obetenerUsuario(id);
        try {
            usuarioServicio.actualizarUsuario(id, nombre, apellido, direccion, telefono, correo, password, password2);
            modelo.put("exito", "El Usuario se ha modificado correctamente");
            System.out.println("Modificacion con exito");
            //return "redirect:/usuario/lista";
            return "listar_usuario";
            
        } catch (MiException e) {
            modelo.put("usuario", usuario);
            modelo.put("error", e.getMessage());
            return "modificar_usuario.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "listar_usuario";
    }

}
