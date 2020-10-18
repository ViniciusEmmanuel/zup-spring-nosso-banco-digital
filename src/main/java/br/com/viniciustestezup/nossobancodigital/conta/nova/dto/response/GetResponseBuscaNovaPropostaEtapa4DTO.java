package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.response;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;

import java.time.LocalDate;

public class GetResponseBuscaNovaPropostaEtapa4DTO {

    final private String nome;
    final private String sobrenome;
    final private String email;
    final private String cpf;
    final private LocalDate dataNascimento;
    final private String cep;
    final private String bairro;
    final private String complemento;
    final private String cidade;
    final private String estado;
    final private String linkImgCpf;

    public GetResponseBuscaNovaPropostaEtapa4DTO(PropostaContaPessoaFisica propostaContaPessoaFisica) {
        this.nome = propostaContaPessoaFisica.getNome();
        this.sobrenome = propostaContaPessoaFisica.getSobrenome();
        this.email = propostaContaPessoaFisica.getEmail();
        this.cpf = propostaContaPessoaFisica.getCpf();
        this.dataNascimento = propostaContaPessoaFisica.getDataNascimento();
        this.cep = propostaContaPessoaFisica.getCep();
        this.bairro = propostaContaPessoaFisica.getBairro();
        this.complemento = propostaContaPessoaFisica.getComplemento();
        this.cidade = propostaContaPessoaFisica.getCidade();
        this.estado = propostaContaPessoaFisica.getEstado();
        this.linkImgCpf = propostaContaPessoaFisica.getLinkImgCpf();
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getLinkImgCpf() {
        return linkImgCpf;
    }
}
