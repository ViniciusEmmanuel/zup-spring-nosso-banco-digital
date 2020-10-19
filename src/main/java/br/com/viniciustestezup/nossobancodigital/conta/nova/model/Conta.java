package br.com.viniciustestezup.nossobancodigital.conta.nova.model;

import jdk.jfr.Unsigned;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;

@Entity
public class Conta {

    @Id
    @NotNull
    @Unsigned
    private Integer conta;

    @NotNull
    @Unsigned
    private Integer agencia;

    @NotNull
    @Unsigned
    private Integer banco;

    private BigDecimal saldo;

    @NotNull
    @OneToOne(targetEntity = Cliente.class)
    private Cliente cliente;

    public Conta(@NotNull Cliente cliente) {
        this.conta = gerarNumeroConta();
        this.agencia = gerarNumeroAgencia();
        this.banco = 341;
        this.saldo = BigDecimal.valueOf(0);
        this.cliente = cliente;
    }

    private Integer gerarNumeroConta() {
        Random random = new Random();
        int numeroConta = random.nextInt(1000) + 10000000;
        return numeroConta;
    }

    private Integer gerarNumeroAgencia() {
        Random random = new Random();
        int numeroAgencia = random.nextInt(100) + 1000;
        return numeroAgencia;
    }

    public Integer getConta() {
        return conta;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public Integer getBanco() {
        return banco;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
