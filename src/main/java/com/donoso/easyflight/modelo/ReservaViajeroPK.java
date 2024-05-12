package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaViajeroPK implements Serializable {

    @Column(name = "reserva_id")
    private String reservaId;
    @Column(name = "viajero_id")
    private Integer viajeroId;

}
