package com.pioriko.ms_restaurante.entities;

import com.pioriko.ms_restaurante.entities.enu.EstadoMesa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesas")
@Entity
public class MesasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa", nullable = false)
    private Integer idMesa;
    @Column(nullable = false, unique = true)
    private String numeroMesa;
    private int capacidad;
    @Enumerated(EnumType.STRING)
    private EstadoMesa estado;
}
