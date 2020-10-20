package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import br.com.viniciustestezup.nossobancodigital.shared.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.shared.error.ObjetoError;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

public class RequestPropostaPessoaFisicaEtapa1DTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @CPF
    @Size(min = 11, max = 11)
    private String cpf;

    @Past
    @DateTimeFormat(pattern="yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public ResponseError validarRequest(PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository) {
        ResponseError responseError = new ResponseError();
        responseError.setCode(HttpStatus.BAD_REQUEST);

        if (propostaContaPessoaFisicaRepository.existsByEmail(email))
            responseError.setErros(new ObjetoError("Já existe esse e-mail cadastrado na nossa base de dados.",
                    "email", email));

        if (propostaContaPessoaFisicaRepository.existsByCpf(cpf))
            responseError.setErros(new ObjetoError("Já existe esse CPF cadastrado na nossa base de dados.",
                    "cpf", cpf));

        Period idade = Period.between(dataNascimento, LocalDate.now());
        if(idade.getYears() < 18)
            responseError.setErros(new ObjetoError("Para cadastrar a proposta, necessita ter pelo menos 18 anos.",
                    "dataNascimento", dataNascimento));

        return responseError;
    }
    public PropostaContaPessoaFisica toModel() {
        return new PropostaContaPessoaFisica(nome, sobrenome, email, cpf, dataNascimento);
    }
}
