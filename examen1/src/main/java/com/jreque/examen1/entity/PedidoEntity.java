package com.jreque.examen1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "pedido")
@Getter
@Setter
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private Long cantidad;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "created_date", nullable = false)
    private Timestamp created_date;

    @Column(name = "update_by")
    private String update_by;

    @Column(name = "update_date")
    private Timestamp update_date;

    @Column(name = "delete_by")
    private String delete_by;

    @Column(name = "delete_date")
    private Timestamp delete_date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id", nullable = false)
    private PersonaEntity persona;

}
