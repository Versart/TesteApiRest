package com.ifma.apifrete.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Frete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "A descrição deve ser preenchida")
    private String descricao;
    @NotNull(message = "O peso deve ser preenchido")
    private Float peso;
    @NotNull(message = "O valor deve ser preenchido")
    private BigDecimal valor;

    @ManyToOne
    @NotNull(message = "O cliente deve ser preenchido")
    private Cliente cliente;

    @ManyToOne
    @NotNull(message = "A cidade deve ser preenchida")
    private Cidade cidade;


    public BigDecimal calcularFrete() {
        // R$10,00 é o valor fixo para o cálculo
        this.valor = new BigDecimal(this.peso * 10).add(this.cidade.getTaxa() );
        return this.getValor();

    }
}
