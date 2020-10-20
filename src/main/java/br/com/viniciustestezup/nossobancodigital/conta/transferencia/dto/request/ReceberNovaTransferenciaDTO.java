package br.com.viniciustestezup.nossobancodigital.conta.transferencia.dto.request;

import br.com.viniciustestezup.nossobancodigital.shared.error.ObjetoError;
import br.com.viniciustestezup.nossobancodigital.shared.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Conta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.ContaId;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.ContaRepository;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.model.Transferencia;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class ReceberNovaTransferenciaDTO {

    @NotNull
    private Long codigoUnicoTransferencia;

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
    @Positive
    private BigDecimal valor;

    @NotNull
    @PastOrPresent
    @DateTimeFormat(pattern="yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataRealizacao;

    public Long getCodigoUnicoTransferencia() {
        return codigoUnicoTransferencia;
    }

    public void setCodigoUnicoTransferencia(Long codigoUnicoTransferencia) {
        this.codigoUnicoTransferencia = codigoUnicoTransferencia;
    }

    public Integer getBancoOrigem() {
        return bancoOrigem;
    }

    public void setBancoOrigem(Integer bancoOrigem) {
        this.bancoOrigem = bancoOrigem;
    }

    public Integer getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Integer contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Integer getAgenciaOrigem() {
        return agenciaOrigem;
    }

    public void setAgenciaOrigem(Integer agenciaOrigem) {
        this.agenciaOrigem = agenciaOrigem;
    }

    public Integer getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Integer contaDestino) {
        this.contaDestino = contaDestino;
    }

    public Integer getAgenciaDestino() {
        return agenciaDestino;
    }

    public void setAgenciaDestino(Integer agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public ResponseError validarRequest(ContaRepository contaRepository) {

        Optional<Conta> existeConta = contaRepository.findById(new ContaId(contaDestino, agenciaDestino));

        ResponseError responseError = new ResponseError();
        if (!existeConta.isPresent()){
            responseError.setCode(HttpStatus.BAD_REQUEST);
            responseError.setErros(new ObjetoError("Conta não identificada.", "contaDestino", contaDestino.toString()));
            responseError.setErros(new ObjetoError("Conta não identificada.", "agenciaDestino", agenciaDestino.toString()));
            return responseError;
        }

        return responseError;
    }

    public Transferencia toModel() {
        return new Transferencia(codigoUnicoTransferencia, bancoOrigem, contaOrigem, agenciaOrigem, contaDestino, agenciaDestino, valor, dataRealizacao);
    }
}
