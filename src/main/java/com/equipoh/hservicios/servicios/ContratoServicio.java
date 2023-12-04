package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Contrato;
import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ContratoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class ContratoServicio {

    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ComentarioServicio comentarioServicio;

    @Transactional
    public void registrarContrato(String idProveedor, String idUsuario, String descTrabajo, String contenido) throws MiException {
        Proveedor proveedor = proveedorServicio.getOne(idProveedor);
        Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
        Contrato contrato = new Contrato();
        contrato.setProveedor(proveedor);
        contrato.setUsuario(usuario);
        contrato.setDescripcionTrabajo(descTrabajo);
        contrato.setFechaSolicitud(new Date());
        contrato.setFechaInicio(null);
        contrato.setFechaFinal(null);
        contrato.setSolicitudT(false);
        contrato.setInicioT(false);
        contrato.setFinT(false);
        contrato.setPuntuacion(0);
        contrato.setComentario(comentarioServicio.ingresaComentario(contenido));
        contratoRepositorio.save(contrato);
    }

    @Transactional
    public void aceptacionContrato(String idContrato) throws MiException {
        Optional<Contrato> respuesta = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();
        if (respuesta.isPresent()) {
            contrato = respuesta.get();
        }
        contrato.setFechaInicio(new Date());
        contrato.setSolicitudT(true);
        contrato.setInicioT(true);
        contratoRepositorio.save(contrato);
    }

    @Transactional
    public void enviapresuspuesto(String idContrato, String presupuesto, Integer horasestimadas) throws MiException {
        Optional<Contrato> respuesta = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();
        if (respuesta.isPresent()) {
            contrato = respuesta.get();
        }
        contrato.setPresupuesto(presupuesto);
        contrato.setHorasestimadas(horasestimadas);
        contratoRepositorio.save(contrato);
    }


    @Transactional
    public void cancelaContrato(String idContrato) throws MiException {
        contratoRepositorio.deleteById(idContrato);
    }

    @Transactional
    public void finalizaTrabajo(String idContrato) throws MiException {

        Optional<Contrato> respuesta = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();
        if (respuesta.isPresent()) {
            contrato = respuesta.get();
        }
        contrato.setFechaFinal(new Date());
        Long milisegundos = contrato.getFechaFinal().getTime() - contrato.getFechaInicio().getTime();
        long horas = TimeUnit.MILLISECONDS.toHours(milisegundos);
        contrato.setFinT(true);
        contratoRepositorio.save(contrato);
    }

    public void valoracionProveedor(String id, Integer puntuacion) {
        Contrato contrato = new Contrato();
        Optional<Contrato> respuesta = contratoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            contrato = respuesta.get();
        }
        contrato.setPuntuacion(puntuacion);
        contratoRepositorio.save(contrato);
    }


    public Contrato obtenerContrato(String id) {
        Contrato contrato = contratoRepositorio.getOne(id);
        return contrato;
    }

}

    

    
