
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Imagen;
import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.repositorios.ImagenRepositorio;
import com.equipoh.hservicios.servicios.ImagenServicio;
import com.equipoh.hservicios.servicios.ServicioServicio;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Martb
 */
@Controller
@RequestMapping("/imagen")

public class ImagenControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    @Autowired
    private ServicioServicio servicioServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

        Usuario usuario = usuarioServicio.obtenerUsuario(id); //FALTA USUARIO SERVICIO
        Servicio servicio = servicioServicio.obtenerServicio(id);

        byte[] imagen = usuario.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @PostMapping("/cargado")
    public String cargado(MultipartFile archivo, ModelMap modelo) {
        if (imagenRepositorio.imagenXDefecto() != null) {
            modelo.put("existe", "La imagen por defecto ya existe");
            return "redirect:/admin/imagendefault";
        }
        imagenServicio.guardarImagenxdefecto(archivo);
        modelo.put("exito", "Se ha cargado la imagen correctamente");
        return "redirect:/admin/imagendefault";
    }

    @PostMapping("/editado")
    public String editado(MultipartFile archivo, ModelMap modelo) {
        Imagen imagen = imagenRepositorio.imagenXDefecto();
        try {
            imagenServicio.actualizarImagenXdefecto(archivo, imagen.getId());
            modelo.put("exito", "Se ha actualizado la imagen correctamente");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
        }
        modelo.put("exito", "Se ha cargado la imagen correctamente");
        return "redirect:/admin/imagendefault";
    }
@GetMapping("/service/{id}")
    public ResponseEntity<byte[]>imagenServicio(@PathVariable String id){

       
        Servicio servicio = servicioServicio.obtenerServicio(id);

       byte[] imagen = servicio.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);


        return new ResponseEntity<>(imagen, headers,HttpStatus.OK);

    } 

       
//       
    @GetMapping("/lista")
    public String listarServicios (ModelMap modelo) {
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "listar_servicio.html";
    }
}
