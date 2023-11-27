package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Pcensurada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcensuradaRepositorio extends JpaRepository<Pcensurada, Integer> {
    @Query("SELECT p FROM Pcensurada p")
    public List<Pcensurada> listaPalabrasCensuradas();
}
