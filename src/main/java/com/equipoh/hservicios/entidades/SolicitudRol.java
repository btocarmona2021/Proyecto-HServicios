package com.equipoh.hservicios.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author jorge
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SolicitudRol {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(length = 1000)
    private String experiencia;
    private Double precioXHora;
    private Boolean estado;
    @OneToOne
    private Servicio servicio;
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @OneToOne
    private Usuario usuario;
    @OneToOne
    private Proveedor proveedor;
}
