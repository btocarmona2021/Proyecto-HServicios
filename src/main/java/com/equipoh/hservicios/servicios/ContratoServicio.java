/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public void cancelaContrato(String idContrato) throws MiException {
        Optional<Contrato> respuesta = contratoRepositorio.findById(idContrato);
        Contrato contrato = new Contrato();
        if (respuesta.isPresent()) {
            contrato = respuesta.get();
        }
        contrato.setFechaInicio(null);
        contrato.setInicioT(false);
        contratoRepositorio.save(contrato);
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






 /*   @Transactional
    public void actualizarContrato(String id, Date fechaInicio, Date fechaFinal, Boolean solicitudT, Boolean inicioT, Boolean finT, Integer puntuacion, Proveedor proveedor, Usuario usuario) throws MiException {

        validar(id, fechaInicio, fechaFinal, proveedor, usuario);
        Optional<Contrato> respuesta = contratoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Contrato contrato = respuesta.get();

            contrato.setFechaInicio(fechaInicio);
            contrato.setFechaFinal(fechaFinal);
            contrato.setSolicitudT(Boolean.TRUE);
            contrato.setInicioT(Boolean.TRUE);
            contrato.setFinT(Boolean.TRUE);
            contrato.setPuntuacion(Integer.MIN_VALUE);
            contrato.setProveedor(proveedor);
            contrato.setUsuario(usuario);
            contratoRepositorio.save(contrato);


        }

    }*/


/*    @Transactional
    public void bajaContrato(String id, Date fechaInicio, Date fechaFinal, Boolean solicitudT, Boolean inicioT, Boolean finT, Integer puntuacion, Proveedor proveedor, Usuario usuario) throws MiException {
        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            contratoRepositorio.getById(id).setId(id);
        } else {
            throw new MiException("No se pudo dar de baja el contrato");
        }
    }*/
/*
    public Contrato getOne(String id) {
        return contratoRepositorio.getOne(id);
    }

    public List<Contrato> listarContratos() {
        List<Contrato> contratos = new ArrayList<>();

        contratos = contratoRepositorio.findAll();

        return contratos;
    }*/

    private void validar(String id, Date fechaInicio, Date fechaFinal, Proveedor proveedor, Usuario usuario) throws MiException {
        if (id.isEmpty() || id == null) {
            throw new MiException("");
        }
    }
}

    

    
