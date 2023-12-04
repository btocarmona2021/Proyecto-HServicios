package com.equipoh.hservicios.entidades;

import com.equipoh.hservicios.enumeracion.Rol;
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
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")

    private String id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;

    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne
    private Imagen imagen;

    private Boolean alta;
}