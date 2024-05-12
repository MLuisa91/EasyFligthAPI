package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Viajero")
@Table(name = "viajero")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viajero implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "dni")
    private String dni;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "adulto")
    private boolean adulto;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "viajero")
    Set<ReservaViajero> reservaViajeros;

}
