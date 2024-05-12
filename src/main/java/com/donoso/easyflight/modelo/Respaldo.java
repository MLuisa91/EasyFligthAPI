package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity(name = "Respaldo")
@Table(name = "respaldo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Respaldo {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fechaGenerada")
    private LocalDate fechaGenerada;
    @Column(name = "fechaRestaurada")
    private LocalDate fechaRestaurada;
}
