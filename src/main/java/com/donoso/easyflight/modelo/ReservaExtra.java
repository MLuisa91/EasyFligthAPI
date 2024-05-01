package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity(name = "ReservaExtra")
@Table(name = "reserva_extra")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaExtra {

    @EmbeddedId
    private ReservaExtraPK id;

    @ManyToOne
    @MapsId("reservaId")
    @JoinColumn(name = "reserva_id")
    @JsonbTransient
    Reserva reserva;

    @ManyToOne
    @MapsId("extraId")
    @JoinColumn(name = "extra_id")
    Extra extra;
}
