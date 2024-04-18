package com.donoso.easyflight.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Set;

@Entity(name = "Reserva")
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserva {

    @Id
    @Column(name = "id")
    private String id;
    @OneToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "vuelo")
    private Vuelo vuelo;
    @OneToOne
    @JoinColumn(name = "oferta")
    private Oferta oferta;
    @Column(name = "numPasajeros")
    private Integer numPasajeros;
    @Column(name = "total")
    private Double total;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reserva")
    @JsonbTransient
    Set<ReservaExtra> reservaExtras;

}
