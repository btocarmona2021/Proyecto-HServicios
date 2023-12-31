package com.equipoh.hservicios.controladores;

import com.equipoh.hservicios.entidades.Contrato;
import com.equipoh.hservicios.entidades.Proveedor;
import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.excepciones.MiException;
import com.equipoh.hservicios.servicios.ContratoServicio;
import com.equipoh.hservicios.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/enviocontrato/{id}")
    public String nuevoConotrato(@PathVariable String id, HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Proveedor proveedor = proveedorServicio.getOne(id);
        modelo.put("usuario", usuario);
        modelo.put("proveedor", proveedor);
        return "registrar_contrato";
    }

    @PostMapping("/solicitudTrabajo")
    public String registroContrato(String idProveedor, HttpSession session, String descTrabajo, String contenido, ModelMap modelo) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        try {
            contratoServicio.registrarContrato(idProveedor, usuario.getId(), descTrabajo, contenido);
            modelo.put("exito", "La solicitud fue enviada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrar_contrato";
        }

        return "panel";
    }

    @PostMapping("/enviapresupuesto/{id}")
    public String enviapresupuesto(HttpSession session, @PathVariable String id, String presupuesto, Integer horasestimadas, ModelMap modelo) {
        Proveedor proveedor = (Proveedor) session.getAttribute("session.usuario");
        try {
            contratoServicio.enviapresuspuesto(id, presupuesto, horasestimadas);
            modelo.put("exito", "El presuspuesto se ha enviado correctamente");
            modelo.put("usuario", proveedor);
            return "redirect:/perfil";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/perfil";
        }
    }

    @GetMapping("/aceptacontrato")
    public String aceptacontrato() {
        return "aceptacontrato";
    }

    @GetMapping("/contratoaceptado/{id}")
    public String contratoAceptado(HttpSession session, @PathVariable String id, ModelMap modelo) {
        Proveedor proveedor = (Proveedor) session.getAttribute("session.usuario");
        try {
            contratoServicio.aceptacionContrato(id);
            modelo.put("exito", "El trabajo ha sido aceptado");
            modelo.put("usuario", proveedor);
            return "redirect:/perfil";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/perfil";
        }
    }

    @GetMapping("/cancelacontrato/{id}")
    public String cancelacontrato(HttpSession session, @PathVariable String id, ModelMap modelo) {
        Proveedor proveedor = (Proveedor) session.getAttribute("session.usuario");
        try {
            contratoServicio.cancelaContrato(id);
            modelo.put("exito", "El trabajo ha sido rechazado");
            modelo.put("usuario", proveedor);
            return "redirect:/perfil";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/perfil";
        }
    }

    @GetMapping("/finalizatrabajo/{id}")
    public String finalizaTrabajo(HttpSession session, @PathVariable String id, ModelMap modelo) {
        Proveedor proveedor = (Proveedor) session.getAttribute("session.usuario");
        try {
            contratoServicio.finalizaTrabajo(id);
            modelo.put("exito", "El trabajo ha sido finalizado");
            modelo.put("usuario", proveedor);
            return "redirect:/perfil";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/perfil";
        }
    }

    @PostMapping("/valorar/{id}")
    public String valorar(@PathVariable String id, Integer puntuacion) {
        System.out.println(puntuacion + "puntuacion quellega");
        Contrato contrato = contratoServicio.obtenerContrato(id);
        contratoServicio.valoracionProveedor(id, puntuacion);
        return "redirect:/perfilusuario";
    }
}
