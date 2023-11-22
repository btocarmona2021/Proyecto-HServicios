package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Comentario;
import com.equipoh.hservicios.entidades.Pcensurada;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ComentarioServicio {
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PcensuradaServicio pcensuradaServicio;

    @Transactional
    public Comentario ingresaComentario(String contenido) throws MiException {
        Comentario comentario = new Comentario();
        List<Pcensurada> pcensuradas = pcensuradaServicio.listaPalabrasCensuradas();
        if (contenido == null || contenido.isEmpty()) {
            contenido = "Dejá tu apreciación hacia el proveedor, ayudará a otros usuarios a elegir, Gracias por usar nuestro servicio";
        }
        comentario.setContenido(contenido);
        comentario.setEstado(validarComentario(contenido, pcensuradas));
        comentarioRepositorio.save(comentario);
        return comentario;
    }

    @Transactional
    public Comentario actualizaComentario(String idComentario, String contenido) throws MiException {
        Comentario comentario = new Comentario();
        List<Pcensurada> pcensuradas = pcensuradaServicio.listaPalabrasCensuradas();
        if (contenido == null || contenido.isEmpty()) {
            throw new MiException("El comentario no puede estar vacio,deja tu apreciación");
        }
        Optional<Comentario> respuesta = comentarioRepositorio.findById(idComentario);
        if (respuesta.isPresent()) {
            comentario = respuesta.get();
        }
        comentario.setContenido(contenido);
        comentario.setEstado(validarComentario(contenido, pcensuradas));
        comentarioRepositorio.save(comentario);
        return comentario;
    }

    public Boolean validarComentario(String contenido, List<Pcensurada> pCensuradas) {

        for (Pcensurada pcensurada : pCensuradas) {
            String regex = "\\b" + Pattern.quote(pcensurada.getPalabra()) + "\\b";
            if (Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(contenido).find()) {
                return false;
            }
        }
        return true;
    }

    public void estadoComemtario(String idComentario) {
        Comentario comentario = comentarioRepositorio.getOne(idComentario);
        comentario.setEstado(!comentario.getEstado());
        comentarioRepositorio.save(comentario);
    }

    public Comentario obtieneComentario(String idComentario) {
        Comentario comentario = comentarioRepositorio.getOne(idComentario);
        return comentario;
    }
}
