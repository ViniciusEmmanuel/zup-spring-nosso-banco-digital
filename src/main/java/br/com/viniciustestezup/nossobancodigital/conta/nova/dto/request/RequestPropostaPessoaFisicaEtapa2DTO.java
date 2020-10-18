package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request;

import br.com.viniciustestezup.nossobancodigital.conta.nova.compartilhado.EtapaNovaConta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class RequestPropostaPessoaFisicaEtapa2DTO {

    @NotBlank
    @Size(min = 8, max = 8,message = "O cep deve conter somente seus 8 digitos.")
    @Pattern(regexp = "[0-9]+$", message = "O cep é aceito somente no formato String com seus respectivos números.")
    private String cep;
    @NotBlank
    private String bairro;
    @NotBlank
    private String complemento;
    @NotBlank
    @Size(max = 100)
    private String cidade;
    @NotBlank
    @Size(max = 50)
    private String estado;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    private PropostaContaPessoaFisica propostaContaPessoaFisica;

    public ArrayList<Error> validarRequest(PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository,
                                           UUID propostaId) {
        Optional<PropostaContaPessoaFisica> existPropostaContaPessoaFisica = propostaContaPessoaFisicaRepository
                .findById(propostaId);

        ArrayList<Error> errors = new ArrayList<Error>();

        if (!existPropostaContaPessoaFisica.isPresent())
            errors.add(new Error("Proposta não encontrada"));

        propostaContaPessoaFisica = existPropostaContaPessoaFisica.get();

        if (propostaContaPessoaFisica.getEtapa() != EtapaNovaConta.ETAPA_1)
            errors.add(new Error("Proposta precisa ter completado a primeira etapa."));

        return errors;
    }

    public PropostaContaPessoaFisica toModel() {
        propostaContaPessoaFisica.ComplementaDadosEtapa2(cep, bairro, complemento, cidade, estado);
        return propostaContaPessoaFisica;
    }
}
