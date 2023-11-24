package com.equipoh.hservicios.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comentario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idcomentario;
    @Column(length = 2000) //GENERA EN BASE DE DATOS UN VARCHAR DE 1000 CARACTERES.
    private String contenido;
    private Boolean estado; //ADMIN DECIDIRÁ SI EL COMENTARIO ES OFENSIVO PUDIENDO DESHABILITARLO
}
