package com.ifma.apifrete.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "O nome deve ser preenchido")
    @NotEmpty @Length(min = 3, max = 200)
    private String nome;

    @NotNull
    private String endereco;

    @NotEmpty
    @Length(min = 8, message = "Deve possuir {min} dígitos no mínimo")
    private String telefone;
}
