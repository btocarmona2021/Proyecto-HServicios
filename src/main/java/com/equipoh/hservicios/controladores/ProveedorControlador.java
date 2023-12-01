/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ProveedorServicio;
import com.equipoh.hservicios.servicios.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jorge
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ServicioServicio servicioServicio;
    
    @GetMapping("/registrar")
    public String registrarProveedor(ModelMap modelo) {
        List<Servicio> servicio = servicioServicio.listarServicios();
        modelo.addAttribute("servicio", servicio);
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registroProveedor(MultipartFile archivo, String nombre, String apellido, String direccion,
                                    String telefono, String correo, String password, String password2, String rol,
                                    String experiencia, Double precioXHora, List<Servicio> servicio, ModelMap modelo) {

        try {
            proveedorServicio.registrarProveedor(archivo, nombre, apellido, direccion, telefono, correo, password, password2, rol, experiencia, precioXHora, servicio);
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
        List<Servicio> servicio = servicioServicio.listarServicios();
        modelo.addAttribute("servicio", servicio);
        modelo.put("proveedor", proveedorServicio.getOne(id));
        return "modificar_proveedor.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(MultipartFile archivo, String id, String nombre, String apellido, String direccion,
                             String telefono, String correo, String password, String password2, String rol,
                             String experiencia, Double precioXHora, List<Servicio> servicio, String alta, ModelMap modelo) {
        Proveedor proveedor = proveedorServicio.getOne(id);
        try {
            proveedorServicio.actualizar(archivo, id, nombre, apellido, direccion, telefono, correo, password, password2, rol, experiencia, precioXHora, servicio, alta);
            return "redirect:/proveedor/lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.addAttribute("proveedor", proveedor);
            return "modificar_proveedor.html";
        }

    }
    
     

}
