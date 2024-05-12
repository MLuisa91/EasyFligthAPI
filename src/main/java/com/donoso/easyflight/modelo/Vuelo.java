package com.donoso.easyflight.modelo;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "Vuelo")
@Table(name = "vuelo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vuelo implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @OneToOne
    @JoinColumn(name = "origen")
    private Aeropuerto origen;
    @OneToOne
    @JoinColumn(name = "destino")
    private Aeropuerto destino;
    @Column(name = "fechaSalida")
    private LocalDate fechaSalida;
    @Column(name = "horaSalida")
    private LocalTime horaSalida;
    @Column(name = "horaLlegada")
    private LocalTime horaLlegada;
    @Column(name = "precio")
    private Double precio;

    @OneToOne
    @JoinColumn(name = "avion")
    private Avion avion;

}
