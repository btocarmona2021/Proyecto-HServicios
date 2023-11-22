package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Pcensurada;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.PcensuradaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PcensuradaServicio {
    @Autowired
    private PcensuradaRepositorio pcensuradaRepositorio;

    //INGRESAR PALABRA CENSURADA
    @Transactional
    public void ingresarPalabra(String palabra) throws MiException {
        if (palabra == null || palabra.isEmpty()) {
            throw new MiException("La palabra no puede ser nula o vacía");
        }
        Pcensurada pcensurada = new Pcensurada();
        pcensurada.setPalabra(palabra);
        pcensuradaRepositorio.save(pcensurada);
    }

    //MODIFICAR PALABRA CENSURADA
    @Transactional
    public void modificarPalabra(Integer idPalabra, String palabra) throws MiException {
        if (palabra == null || palabra.isEmpty()) {
            throw new MiException("La palabra no puede ser nula o vacía");
        }
        Optional<Pcensurada> respuesta = pcensuradaRepositorio.findById(idPalabra);
        Pcensurada pcensurada = new Pcensurada();
        if (respuesta.isPresent()) {
            pcensurada = respuesta.get();
        }
        pcensurada.setPalabra(palabra);
        pcensuradaRepositorio.save(pcensurada);
    }

    //ELIMINAR DE LA BASE DE DATOS PALABRA CENSURADA
    @Transactional
    public void eliminarPalabra(Integer idPalabra) {
        Pcensurada pcensurada = pcensuradaRepositorio.getOne(idPalabra);
        pcensuradaRepositorio.delete(pcensurada);
    }

    //OBTIENE UNA LISTA DE PALABRAS CENSURADAS
    public List<Pcensurada> listaPalabrasCensuradas() {
        List<Pcensurada> listadoPalabrasCensuradas = pcensuradaRepositorio.listaPalabrasCensuradas();
        return listadoPalabrasCensuradas;
    }

}
