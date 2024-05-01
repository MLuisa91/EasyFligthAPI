package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    @JsonbTransient
    Usuario usuario;

    @ManyToOne
    @MapsId("rolId")
    @JoinColumn(name = "rol_id")
    Rol rol;

}
