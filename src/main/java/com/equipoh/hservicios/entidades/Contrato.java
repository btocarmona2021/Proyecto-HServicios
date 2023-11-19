/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Contrato {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
     
    private String id;
    private Boolean solicitudT;
    private Boolean inicioT;
    private Boolean finT;
    private Integer puntuacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    private Date fechaFinal;
    
    @OneToMany
    private Proveedor proveedor;
    private Usuario usuario;
    //private Comentario comentario;
    
}
