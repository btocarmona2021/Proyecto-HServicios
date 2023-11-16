/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.enumeracion.Rol;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ProveedorRepositorio;
import com.equipoh.hservicios.repositorios.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jorge
 */
@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Transactional
    public void registrarProveedor(String nombre, String apellido, String direccion,
                                   String telefono, String correo, String password, String password2, String rol,
                                   String experiencia, Double precioXHora, Servicio servicio) throws MiException {

        validar(nombre, correo, password, password2);

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);
        proveedor.setPassword(password);
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setExperiencia(experiencia);
        proveedor.setPrecioXHora(precioXHora);
        proveedor.setServicio(servicio);
        proveedor.setFechaAlta(new Date());
        proveedor.setAlta(true);

        proveedorRepositorio.save(proveedor);

    }

    @Transactional
    public void actualizar(String id, String nombre, String apellido, String direccion,
                           String telefono, String correo, String password, String password2, String rol,
                           String experiencia, Double precioXHora, Servicio servicio, String alta) throws MiException {

        validar(nombre, correo, password, password2);

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();

            proveedor.setNombre(nombre);
            proveedor.setApellido(apellido);
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
            proveedor.setCorreo(correo);
            proveedor.setPassword(password);
            proveedor.setRol(Rol.PROVEEDOR);
            proveedor.setExperiencia(experiencia);
            proveedor.setPrecioXHora(precioXHora);
            //proveedor.setServicio();
            proveedor.setServicio(servicio);
            proveedor.setFechaAlta(new Date());
            if (alta.equalsIgnoreCase("ALTA")) {
                proveedor.setAlta(true);
            } else {
                proveedor.setAlta(false);
            }

            proveedorRepositorio.save(proveedor);
        }
    }

    public Proveedor getOne(String id) {
        return proveedorRepositorio.getOne(id);
    }

    public List<Proveedor> listaProveedores() {
        List<Proveedor> proveedores = new ArrayList();
        proveedores = proveedorRepositorio.buscarProveedores();
//        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }


    private void validar(String nombre, String correo, String password, String password2) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (correo.isEmpty() || correo == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("El password no puede ser nulo o estar vacio y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }
}
