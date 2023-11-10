/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author jorge
 */
@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor,String>{
    @Query("SELECT n FROM Proveedor n Where n.nombre = :nombre")
    public List<Proveedor> buscarProveedorPorNombre(@Param("nombre") String nombre);
}
