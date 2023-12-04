package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    public Usuario buscarPorCorreo(@Param("correo") String correo);


    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    public Usuario buscarUsuario(@Param("id") String id);

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.alta = TRUE")
    public List<Usuario> buscarCorreoUsuarioActivo(@Param("correo") String correo);

    //------QUERYS AGREGADAS------
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.alta = FALSE")
    public List<Usuario> buscarCorreoUsuarioInactivo(@Param("correo") String correo);

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.alta = FALSE")
    public Usuario buscarCorreoInactivo(@Param("correo") String correo);


    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.alta = TRUE")
    public Usuario buscarCorreoActivo(@Param("correo") String correo);

    // QUERY para buscar un dato especifico en la tabla
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:dato% OR u.apellido LIKE %:dato% OR u.direccion LIKE %:dato% OR u.correo LIKE %:dato% ")
    public List<Usuario> buscarDato(@Param("dato") String dato);

    //PARA LA CREACION DE ADMIN
    @Query("SELECT u FROM Usuario u WHERE u.rol= 'ADMIN'")
    public List<Usuario> buscaAdmin();

}