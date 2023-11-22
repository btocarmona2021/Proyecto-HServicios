/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String>{
     /*@Query("SELECT c FROM Contrato c Where c.contrato = :contrato")
    public List<Contrato> buscarContrato(@Param("contrato") String id);*/

    
}
