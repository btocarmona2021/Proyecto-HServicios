package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jorge
 */
@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    @Query("SELECT p FROM Proveedor p WHERE p.rol = 'PROVEEDOR'")
    public List<Proveedor> buscarProveedores();

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.rubro like %:buscar% "
            + "or p.nombre like %:buscar% "
            + "or p.apellido like %:buscar% order by p.precioXHora asc")
    public List<Proveedor> buscaProveedor(@Param("buscar") String buscar);

    @Query("SELECT p FROM Proveedor p WHERE p.correo = :correo AND p.alta = TRUE")
    public List<Proveedor> buscarCorreoProveedorActivo(@Param("correo") String correo);

    //------QUERYS AGREGADAS------
    @Query("SELECT p FROM Proveedor p WHERE p.correo = :correo AND p.alta = FALSE")
    public List<Proveedor> buscarCorreoProveedorInactivo(@Param("correo") String correo);
    
    @Query("SELECT p FROM Proveedor p WHERE p.correo = :correo AND p.alta = FALSE")
    public Proveedor buscarCorreoInactivo(@Param("correo") String correo);
    //----------------------------
}
