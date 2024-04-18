package com.donoso.easyflight.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Aeropuerto")
@Table(name = "aeropuerto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aeropuerto implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
}
