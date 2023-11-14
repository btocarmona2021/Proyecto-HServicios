/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.servicios;


import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Transactional
    public void registrarServicio(String id, String rubro, Boolean estado) throws MiException {

        validar(rubro);

        Servicio servicio = new Servicio();

        servicio.setRubro(rubro);
        servicio.setEstado(true);

        servicioRepositorio.save(servicio);


    }

    @Transactional
    public void actualizarServicio(String id, String rubro, Boolean estado) throws MiException {

        validar(rubro);
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Servicio servicio = respuesta.get();
            servicio.setRubro(rubro);
            servicio.setEstado(estado);

            servicioRepositorio.save(servicio);

        }

    }

    @Transactional
    public void bajaServicio(String id, String rubro, Boolean estado) throws MiException {
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            servicioRepositorio.getById(id).setEstado(Boolean.FALSE);
        } else {
            throw new MiException("No se pudo dar de baja el servicio" + servicioRepositorio.getById(id).getRubro());
        }
    }

    public Servicio getOne(String id) {
        return servicioRepositorio.getOne(id);
    }

    public List<Servicio> listarServicios() {
        List<Servicio> rubros = new ArrayList<>();

        rubros = servicioRepositorio.findAll();

        return rubros;
    }

    private void validar(String rubro) throws MiException {
        if (rubro.isEmpty() || rubro == null) {
            throw new MiException("El rubro no puede ser nulo o estar vacio");
        }
    }


}
