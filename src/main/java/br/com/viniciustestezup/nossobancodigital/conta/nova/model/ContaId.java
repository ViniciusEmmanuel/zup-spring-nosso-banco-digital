package br.com.viniciustestezup.nossobancodigital.conta.nova.model;

import java.io.Serializable;

public class ContaId implements Serializable {
    private Integer conta;
    private Integer agencia;

    @Deprecated
    public ContaId() { }

    public ContaId(Integer conta, Integer agencia) {
        this.conta = conta;
        this.agencia = agencia;
    }
}
