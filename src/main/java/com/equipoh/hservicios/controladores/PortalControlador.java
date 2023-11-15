

package com.equipoh.hservicios.controladores;


import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import java.util.List;
import com.equipoh.hservicios.repositorios.ProveedorRepositorio;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/")
public class PortalControlador {

   
    @Autowired
    private UsuarioServicio usuarioServicio;
   
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuario();
        modelo.addAttribute("usuarios", usuarios);
        return "index.html";
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
        List<Usuario> usuarios = usuarioServicio.listarUsuario();
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
        List<Usuario> usuarios = usuarioServicio.listarUsuario();
        modelo.addAttribute("usuarios", usuarios);
        return "buscador.html";
    }

    @PostMapping("/encontrados")
    public String buscador(String buscar, ModelMap model) {
        List<Proveedor> listado = proveedorRepositorio.buscaProveedor(buscar);
        model.addAttribute("proveedores", listado);
        return "listar_proveedor";
    }

    @GetMapping("/contrato")
    public String contrato() {
        return "contratarService.html";
    }
}
