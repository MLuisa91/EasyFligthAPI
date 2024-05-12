package com.donoso.easyflight.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Usuario")
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "dni")
    private String dni;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "user")
    private String user;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "telefono")
    private String telefono;
    @OneToOne
    @JoinColumn(name = "pais")
    private Pais pais;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    Set<UsuarioRol> usuarioRol;
}
