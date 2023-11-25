/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface contratoRepositorio extends JpaRepository<Contrato, String> {
    //BUSCA CONTRATO SIN ACEPTAR
    @Query("SELECT c FROM Contrato c WHERE C.inicioT=false")
    public List<Contrato> buscaContratoSinAceptar();

    //BUSCA CONTRATO SIN ACEPTAR

    @Query("SELECT c FROM Contrato c WHERE c.inicioT=true")
    public List<Contrato> buscaContratoAceptados();



}
