package com.donoso.easyflight.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Extra")
@Table(name = "extra")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Extra implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "coste")
    private Double coste;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "extra")
    @JsonbTransient
    Set<ReservaExtra> reservaExtras;

}
