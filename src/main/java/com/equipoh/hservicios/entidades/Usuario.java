package com.equipoh.hservicios.entidades;

import com.equipoh.hservicios.enumeracion.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Usuario{

    @Id
    // Clave primaria de la entidad
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // Genera un id String "autogenerado".
    private String id;

    private String nombre;

    private String apellido;

    private String direccion;

    private String telefono;

    private String correo;

    @Temporal(TemporalType.DATE)
    // Con @Temporal le indico como va a trabajar los datos de tipo DATE
    // y con 'TemporalType' + 'DATE', que solo persista la fecha
    private Date fechaAlta;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    //@OneToOne
    //private Imagen imagen;

    private Boolean alta;
    
}
