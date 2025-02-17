package com.jreque.examen1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Anotaciones
@Entity
@Table(name = "direccion")    // Nombre en Tabla
@Getter
@Setter

public class DireccionEntity {

    // Identificador de entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Declarar variables de entidad
    private Long id;
    private String avenida;
    private String numero;
    private String distrito;
    private String provincia;
    private String departamento;

}
