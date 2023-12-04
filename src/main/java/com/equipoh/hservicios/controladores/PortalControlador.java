package com.equipoh.hservicios.controladores;


import com.equipoh.hservicios.entidades.Contrato;
import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Servicio;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.repositorios.ContratoRepositorio;
import com.equipoh.hservicios.repositorios.ProveedorRepositorio;
import com.equipoh.hservicios.servicios.ProveedorServicio;
import com.equipoh.hservicios.servicios.ServicioServicio;
import com.equipoh.hservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/")
public class PortalControlador {


    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ContratoRepositorio contratoRepositorio;
    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuario();
        modelo.addAttribute("usuarios", usuarios);

        return "index.html";
    }


    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }
        return "login.html";
    }


    @GetMapping("/panel")
    public String panel(ModelMap modelo) {
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
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

    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        List<Servicio> servicios = servicioServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "panel.html";
    }


    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        List<Contrato> contratos = contratoRepositorio.buscaContratoSinAceptar(usuario.getId());
        modelo.put("usuario", usuario);
        modelo.addAttribute("contratos", contratos);
        List<Servicio> servicio = servicioServicio.listarServicios();
        modelo.addAttribute("servicio", servicio);
        return "perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfiles")
    public String perfiles(HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        List<Contrato> contratos = contratoRepositorio.buscaContratoSinAceptar(usuario.getId());
        modelo.put("usuario", usuario);
        modelo.addAttribute("contratos", contratos);
        if (usuario.getRol().toString().equals("PROVEEDOR")) {
            Double promedioValoracion = (double) Math.round(contratoRepositorio.buscapromedio(usuario.getId()));
            modelo.put("promedio", promedioValoracion);
            return "trabajosproveedor";
        } else {
            return "redirect:/perfilusuario";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfilusuario")
    public String perfilusuario(HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        List<Contrato> contratos = contratoRepositorio.buscaContratoSinAceptar(usuario.getId());
        modelo.put("usuario", usuario);
        modelo.addAttribute("contratos", contratos);
        return "solicitudestrabajo";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfil/complete/{id}")
    public String perfil_proveedor(HttpSession session, @PathVariable(required = false) String id, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        List<Contrato> contratos = contratoRepositorio.buscacomentario(id);
        modelo.put("usuario", usuario);
        modelo.addAttribute("contratos", contratos);
        modelo.put("proveedores", proveedorServicio.getOne(id));

        return "perfil_proveedor.html";
    }
}
