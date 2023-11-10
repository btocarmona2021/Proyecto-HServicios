

package com.equipoh.hservicios.controladores;


import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.servicios.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

   
 @Autowired
    private UsuarioService usuarioService;
   
    @GetMapping("/")
    public String index(ModelMap modelo) {
         List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

     return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
    @GetMapping("/panel")
    public String panel() {
        return "panel.html";
    }
    @GetMapping("/list")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "listar_usuario.html";
    }
    @GetMapping("/active")
    public String active() {
        return "active.html";
    }
    @GetMapping("/modificar")
    public String modificar() {
        return "modificar_usuario.html";
    }
     @GetMapping("/buscador")
    public String buscador(ModelMap modelo) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "buscador.html";
    }
     @GetMapping("/contrato")
    public String contrato() {
        return "contratarService.html";
    }
}
