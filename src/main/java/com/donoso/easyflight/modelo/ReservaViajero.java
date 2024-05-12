package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity(name = "ReservaViajero")
@Table(name = "reserva_viajero")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaViajero {

    @EmbeddedId
    private ReservaViajeroPK id;

    @ManyToOne
    @MapsId("reservaId")
    @JoinColumn(name = "reserva_id")
    @JsonbTransient
    Reserva reserva;

    @ManyToOne
    @MapsId("viajeroId")
    @JoinColumn(name = "viajero_id")
    Viajero viajero;
}
