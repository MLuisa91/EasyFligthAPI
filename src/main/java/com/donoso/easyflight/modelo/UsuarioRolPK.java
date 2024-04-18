package com.donoso.easyflight.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRolPK implements Serializable {


    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "rol_id")
    private Integer rolId;

}
