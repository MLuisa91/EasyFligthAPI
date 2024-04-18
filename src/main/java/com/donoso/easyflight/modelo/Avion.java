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

@Entity(name = "Avion")
@Table(name = "avion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Avion implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "pasajeros")
    private Integer pasajeros;

}
