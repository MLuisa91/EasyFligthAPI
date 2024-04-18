package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "UsuarioRol")
@Table(name = "usuario_rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRol {

    @EmbeddedId
    private UsuarioRolPK id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    Usuario usuario;

    @ManyToOne
    @MapsId("rolId")
    @JoinColumn(name = "rol_id")
    Rol rol;
    /*@ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;
    @OneToMany
    @JoinColumn(name = "rol")
    private List<Rol> rol;*/

}
