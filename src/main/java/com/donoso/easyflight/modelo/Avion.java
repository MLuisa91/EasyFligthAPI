package com.donoso.easyflight.modelo;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
