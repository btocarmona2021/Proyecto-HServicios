package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    // query de prueba
    // COMO NO PEDIMOS NOMBRE DE USUARIO SEGURAMENTE VAMOS A USAR LOGUEO POR MAIL
    //HABRIA QUE ADAPTAR LA QUERY
    /*
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public Usuario buscarPorNombre(@Param("nombre") String nombre);
    */

    // Busca por correo 
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    public Usuario buscarPorCorreo(@Param("correo") String correo);

    /**************************
     * Cuando quiera buscar por mail para LOGUEARME LUEGO DE EFECTUAR EL CAMBIO DE 
     * TIPO DE USUARIO), tengo que, además de buscar el mail, buscar si esta dado 
     * de ALTA (Alta = TRUE). Esto se haría para poder evitar que la @QUERY arroje 
     * más de un resultado.
     **************************/

}
