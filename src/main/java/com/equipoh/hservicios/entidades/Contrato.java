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
    private Boolean solicitudT;
    private Boolean inicioT;
    private Boolean finT;
    private Integer puntuacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP) //AGREGUE EL TIMESTAMP QUE FALTABA
    private Date fechaFinal;

    @ManyToOne //CAMBIEN A MANY TO ONE
    private Proveedor proveedor;
    @ManyToOne  //AGREGUE LA RELACION
    private Usuario usuario;
    //AGREGUE RELACION Y PROPIEDAD CON COMENTARIO
    @OneToOne
    private Comentario comentario;
}
