package com.donoso.easyflight.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "Reserva")
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserva {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "code")
    private String code;
    @OneToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "vueloIda")
    private Vuelo vueloIda;
    @OneToOne
    @JoinColumn(name = "vueloVuelta")
    private Vuelo vueloVuelta;
    @OneToOne
    @JoinColumn(name = "oferta")
    private Oferta oferta;
    @Column(name = "numPasajeros")
    private Integer numPasajeros;
    @Column(name = "total")
    private Double total;
    @Column(name = "fechaReserva")
    private LocalDate fechaReserva;

    //Fetch: Eager te carga toda la información de las columnas y sus relaciones con base de datos
    //Fetch: Lazy, carga la información una vez que se solicita
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reserva")
    Set<ReservaExtra> reservaExtras;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reserva")
    Set<ReservaViajero> reservaViajeros;

}
