package com.equipoh.hservicios.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pcensurada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPalabra;
    private String palabra;
}
