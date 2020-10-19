package br.com.viniciustestezup.nossobancodigital.conta.transferencia.model;

import br.com.viniciustestezup.nossobancodigital.conta.transferencia.compartilhado.StatusTransferencia;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Long codigoTransferencia;

    @NotNull
    private Integer bancoOrigem;

    @NotNull
    private Integer contaOrigem;

    @NotNull
    private Integer agenciaOrigem;

    @NotNull
    private Integer contaDestino;

    @NotNull
    private Integer agenciaDestino;

    @NotNull
    private BigDecimal valor;

    @NotNull
    @PastOrPresent
    @DateTimeFormat(pattern="yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataRealizacao;

    @Enumerated(value = EnumType.STRING)
    private StatusTransferencia statusTransferencia;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();;

    @PastOrPresent
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Deprecated
    public Transferencia() {}

    public Transferencia(@NotNull Long codigoTransferencia, @NotNull Integer bancoOrigem, @NotNull Integer contaOrigem, @NotNull Integer agenciaOrigem, @NotNull Integer contaDestino, @NotNull Integer agenciaDestino, @NotNull BigDecimal valor, @NotNull @PastOrPresent LocalDate dataRealizacao) {
        this.codigoTransferencia = codigoTransferencia;
        this.bancoOrigem = bancoOrigem;
        this.contaOrigem = contaOrigem;
        this.agenciaOrigem = agenciaOrigem;
        this.contaDestino = contaDestino;
        this.agenciaDestino = agenciaDestino;
        this.valor = valor;
        this.dataRealizacao = dataRealizacao;
        this.statusTransferencia = StatusTransferencia.TRANSFERENCIA_A_PROCESSAR;
    }


    public Long getId() { return id; }

    public Long getCodigoTransferencia() {
        return codigoTransferencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public Integer getBancoOrigem() {
        return bancoOrigem;
    }

    public Integer getContaOrigem() {
        return contaOrigem;
    }

    public Integer getAgenciaOrigem() {
        return agenciaOrigem;
    }

    public Integer getContaDestino() { return contaDestino; }

    public Integer getAgenciaDestino() {
        return agenciaDestino;
    }

    public StatusTransferencia getStatusTransferencia() { return statusTransferencia; }

    public void transferenciaRealizada() {
        this.statusTransferencia = StatusTransferencia.TRANSFERENCIA_REALIZADA;
        this.updatedAt = LocalDateTime.now();
    }
}
