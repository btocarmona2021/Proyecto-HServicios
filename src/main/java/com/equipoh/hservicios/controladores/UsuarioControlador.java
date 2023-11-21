/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ServicioServicio;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author manie
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
     @Autowired
    private ServicioServicio servicioServicio;
     
    @GetMapping("/registrar")    // localhost:8080/usuario/registrar
    public String registrar(ModelMap modelo) {
        List<Servicio> servicio = servicioServicio.listarServicios();
        modelo.addAttribute("servicio", servicio);
        System.out.println("IMPRIME 31");
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(String nombre, String apellido,
            String direccion, String telefono,
            String correo, String password,
            String password2, MultipartFile archivo, ModelMap modelo) {
        
        System.out.println("nombre: "+nombre);
        System.out.println("apellido: "+apellido);
        System.out.println("direccion: "+direccion);
        System.out.println("telefono: "+telefono);
        System.out.println("correo: "+correo);
        System.out.println("password: "+password);
        System.out.println("password2: "+password2);
        System.out.println("imagen: "+archivo);
        
        
        try {
            usuarioServicio.registrarUsuario(archivo, nombre, apellido, direccion, telefono, correo, password, password2);
            modelo.put("exito", "El usuario ha sido cargado con éxito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")       // localhost:8080/usuario/listar 
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
    public String actualizar(@PathVariable String id, String nombre, String apellido, String direccion, String telefono, String correo, String password, String password2, MultipartFile archivo, ModelMap modelo) {
        try {
            usuarioServicio.actualizarUsuario(archivo, id, nombre, apellido, direccion, telefono, correo, password, password2);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificar_usuario.html";
        }

    }
    /*
    @GetMapping("/buscador")       // localhost:8080/usuario/buscador 
    public String listar(@PathVariable String dato,  ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.buscarDato(dato);

        modelo.addAttribute("dato", usuarios);

        return "listar_usuario.html";
    }
     */
}
