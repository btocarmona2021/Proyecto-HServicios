/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

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
    @Column(length = 1000)
    private String descripcionTrabajo;
    private String presupuesto;
    private Integer horasestimadas;
    private Boolean solicitudT;
    private Boolean inicioT;
    private Boolean finT;
    private Integer puntuacion;
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;

    @ManyToOne
    private Proveedor proveedor;
    @ManyToOne
    private Usuario usuario;
    @OneToOne
    private Comentario comentario;
}
