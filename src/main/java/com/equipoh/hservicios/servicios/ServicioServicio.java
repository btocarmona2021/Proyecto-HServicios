package com.equipoh.hservicios.servicios;


import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrarServicio(String rubro, MultipartFile archivo) throws MiException {

        validar(rubro);

        Servicio servicio = new Servicio();
        servicio.setImagen(imagenServicio.guardarImagen(archivo));
        servicio.setRubro(rubro);
        servicio.setEstado(true);


        servicioRepositorio.save(servicio);
    }

    @Transactional
    public void actualizarServicio(String id, String rubro, Boolean estado, MultipartFile archivo) throws MiException {

        validar(rubro);
        Optional<Servicio> respuesta = servicioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Servicio servicio = respuesta.get();
            servicio.setRubro(rubro);
            servicio.setEstado(estado);

            String idImagen = null;
            if (servicio.getImagen() != null) {
                idImagen = servicio.getImagen().getId();

            }
            if (archivo.isEmpty()) {
                servicio.setImagen(servicio.getImagen());
            } else {
                servicio.setImagen(imagenServicio.actualizarImagen(archivo, servicio.getImagen().getId()));
            }
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
        List<Servicio> rubro = new ArrayList<>();
        rubro = servicioRepositorio.findAll();
        return rubro;
    }

    private void validar(String rubro) throws MiException {
        if (rubro.isEmpty() || rubro == null) {
            throw new MiException("El rubro no puede ser nulo o estar vacio");
        }
    }

    @Transactional

    public Servicio buscarServicio(String id) {
        return servicioRepositorio.buscarServicio(id);
    }

    public Servicio obtenerServicio(String id) {
        return buscarServicio(id);
    }

}
