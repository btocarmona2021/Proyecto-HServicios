/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jorge
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/registrar")
    public String registrarProveedor() {
        return "registrarProveedor.html";
    }

    @PostMapping("/registro")
    public String registroProveedor(String nombre, String apellido, String direccion,
                                    String telefono, String correo, String password, String password2, String rol,
                                    String experiencia, Double precioXHora, Servicio servicio, ModelMap modelo) {
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
    public String listarProveedores(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listaProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "listar_proveedor.html";
    }


    @GetMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, ModelMap modelo) {
        modelo.put("proveedor", proveedorServicio.getOne(id));
        return "modificar_proveedor.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(String id, String nombre, String apellido, String direccion,
                             String telefono, String correo, String password, String password2, String rol,
                             String experiencia, Double precioXHora, Servicio servicio, String alta, ModelMap modelo) {
        Proveedor proveedor = proveedorServicio.getOne(id);
        try {
            proveedorServicio.actualizar(id, nombre, apellido, direccion, telefono, correo, password, password2, rol,
                    experiencia, precioXHora, servicio, alta);
            return "redirect:/proveedor/lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.addAttribute("proveedor", proveedor);
            return "modificar_proveedor.html";
        }

    }

}
