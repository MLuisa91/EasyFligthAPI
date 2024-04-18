package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaExtraPK implements Serializable {

    @Column(name = "reserva_id")
    private String reservaId;
    @Column(name = "extra_id")
    private Integer extraId;

}
