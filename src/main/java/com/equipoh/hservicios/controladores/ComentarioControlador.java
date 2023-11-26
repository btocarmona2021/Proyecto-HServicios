package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Comentario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comentario")
public class ComentarioControlador {
    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/ingresar")
    public String ingresaComentario() {

        return "comentario";
    }

    @PostMapping("/ingresado")
    public String comIngresado(String contenido, ModelMap modelo) {
        try {
            comentarioServicio.ingresaComentario(contenido);
            return "comentario";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "comentario";
        }
    }

    @GetMapping("/actualizarcomentario/{idcomentario}")
    public String actualizarComentario(@PathVariable String idcomentario, ModelMap modelo) {
        Comentario comentario = comentarioServicio.obtieneComentario(idcomentario);
        modelo.put("comentario", comentario);
        return "comentario";
    }

    @PostMapping("/comentarioactualizado/{idcomentario}")
    public String comentarioActualizado(@PathVariable String idcomentario, String contenido) {
        try {
            Comentario comentario = comentarioServicio.actualizaComentario(idcomentario, contenido);
            return "redirect:/perfilu";
        } catch (MiException e) {
            return "comentario";
        }

    }


}
