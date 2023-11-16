

package com.equipoh.hservicios.servicios;

import com.equipoh.hservicios.entidades.Usuario;
import com.equipoh.hservicios.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author Andres Zampar */
@Service
public class UsuarioService {

@Autowired
private UsuarioRepositorio usuarioRepositorio ;

@Transactional
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

  
}

