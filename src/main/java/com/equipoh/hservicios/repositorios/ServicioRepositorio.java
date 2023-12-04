package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {

    @Query("SELECT s FROM Servicio s WHERE s.id = :id")
    public Servicio buscarServicio(@Param("id") String id);
}
