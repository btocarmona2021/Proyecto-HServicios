/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoh.hservicios.entidades;

import javax.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author manie
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Proveedor extends Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(length = 1000)
    private String experiencia;
    private Double precioXHora;

    @OneToOne
    private Servicio servicio;
            
}
