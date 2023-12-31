
package com.equipoh.hservicios.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @author jorge
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