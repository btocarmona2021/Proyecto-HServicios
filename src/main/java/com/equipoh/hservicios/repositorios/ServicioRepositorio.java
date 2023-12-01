/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {
    @Query("SELECT r FROM Servicio r Where r.rubro = :rubro")
    public List<Servicio> buscarPorRubro(@Param("rubro") String rubro);

@Query("SELECT s FROM Servicio s WHERE s.id = :id")
    public Servicio buscarServicio(@Param("id") String id);
    }