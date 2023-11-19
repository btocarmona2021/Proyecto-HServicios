

package com.equipoh.hservicios.controladores;


import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Usuario;
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


    @GetMapping("/active")
    public String active() {
        return "active.html";
    }


    @GetMapping("/buscador")
    public String buscador() {
        return "buscador.html";
    }

    @PostMapping("/buscador")
    public String buscador(String buscar, ModelMap model) {
        List<Proveedor> listado = proveedorRepositorio.buscaProveedor(buscar);
        model.addAttribute("proveedores", listado);
        return "buscador";
       /* return "listar_proveedor";*/
    }

    @GetMapping("/contrato")
    public String contrato() {
        return "contratarService.html";
    }
     @GetMapping("/nosotros")
    public String nosotros() {
        return "equipoh.html";
    }
}
