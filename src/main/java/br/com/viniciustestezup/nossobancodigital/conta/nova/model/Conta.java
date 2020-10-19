package br.com.viniciustestezup.nossobancodigital.conta.nova.model;

import br.com.viniciustestezup.nossobancodigital.conta.transferencia.model.Transferencia;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.service.ProcessaTransacaoService;
import jdk.jfr.Unsigned;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Entity
@IdClass(ContaId.class)
public class Conta {

    private static final Logger LOGGER = LoggerFactory.getLogger(Conta.class);

    @Id
    @NotNull
    @Unsigned
    private Integer conta;

    @Id
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

//    @OneToMany(targetEntity = Transferencia.class)
//    @JoinColumns({
//            @JoinColumn(name = "contaDestino"),
//            @JoinColumn(name = "agenciaDestino")
//    })
//    private List<Transferencia> transferencias;

    public Conta() { }

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

    public void adicionaTransferencia(Transferencia transferencia) {
        LOGGER.info(String.format("Adicionado saldo da transferencia no valor: %s",transferencia.getValor()));
        this.saldo = this.saldo.add(transferencia.getValor());
    }
}