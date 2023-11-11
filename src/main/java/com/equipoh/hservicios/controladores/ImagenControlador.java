
package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Martb
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
   // UsuarioServicio usuarioServicio; FALTA USUARIO SERVICIO
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]>imagenUsuario(@PathVariable String id){
        
        Usuario usuario = usuarioServicio.getOne(id); //FALTA USUARIO SERVICIO
        
       byte[] imagen = usuario.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
       
       
       return new ResponseEntity<>(imagen, headers,HttpStatus.OK);
    
    }
    
    
    
}
