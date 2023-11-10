/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ProveedorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author jorge
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/registrar")
    public String registrarProveedor() {
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registroProveedor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String direccion,
            @RequestParam String telefono, @RequestParam String correo, @RequestParam String password, String password2, String rol,
            String experiencia, Double precioXHora, String servicio, ModelMap modelo) {
        try {
            proveedorServicio.registrarProveedor(nombre, apellido, direccion, telefono, correo, password, password2, rol, experiencia, precioXHora, servicio);
            modelo.put("exito", "El Proveedor fue registrado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar.html";
        }

        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listarProveedores(ModelMap modelo){
        List<Proveedor> proveedores = proveedorServicio.listaProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "lista.html";
    }
    
    
   @GetMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, ModelMap modelo) {
        modelo.put("proveedor", proveedorServicio.getOne(id));

        return "actualizar.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(String id, String nombre, String apellido, String direccion,
            String telefono, String correo, String password, String password2, String rol,
            String experiencia, Double precioXHora, String servicio, String alta, ModelMap modelo) {
        try {
            proveedorServicio.actualizar(id, nombre, apellido, direccion, telefono, correo, password, password2, rol,
                    experiencia, precioXHora, servicio, alta);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "lista.html";
        }
    }

}
