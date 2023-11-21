/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    @Query("SELECT p FROM Proveedor p WHERE p.servicio.rubro like %:buscar% or p.nombre like %:buscar% or p.apellido like %:buscar%")
    public List<Proveedor> buscaProveedor(@Param("buscar") String buscar);
    
     @Query("SELECT r FROM Proveedor r WHERE r.servicio.rubro = :rubro")
     public List<Proveedor> buscarPorRubro(@Param("rubro") String rubro);
}
