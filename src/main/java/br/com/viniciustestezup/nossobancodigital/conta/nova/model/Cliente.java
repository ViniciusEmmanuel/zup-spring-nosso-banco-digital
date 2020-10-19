package br.com.viniciustestezup.nossobancodigital.conta.nova.model;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @NotNull
    private String nome;

    @NotBlank
    @NotNull
    private String sobrenome;

    @NotBlank
    @Email
    @NotNull
    @Column(unique = true, length = 100)
    private String email;

    @NotBlank
    @CPF
    @NotNull
    @Column(unique = true, length = 11)
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotNull
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    private String bairro;

    @NotNull
    private String complemento;

    @NotNull
    @Size(max = 100)
    private String cidade;

    @NotNull
    @Size(max = 80)
    private String estado;

    @NotBlank
    @URL
    @NotNull
    private String linkImgCpf;

    @NotNull
    @OneToOne
    private PropostaContaPessoaFisica proposta;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();;

    @PastOrPresent
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Deprecated
    Cliente() {}

    public Cliente(PropostaContaPessoaFisica propostaContaPessoaFisica) {
        this.nome = propostaContaPessoaFisica.getNome();
        this.sobrenome = propostaContaPessoaFisica.getSobrenome() ;
        this.email = propostaContaPessoaFisica.getEmail();
        this.cpf = propostaContaPessoaFisica.getCpf();
        this.dataNascimento = propostaContaPessoaFisica.getDataNascimento();
        this.cep = propostaContaPessoaFisica.getCep();
        this.bairro = propostaContaPessoaFisica.getBairro();
        this.complemento = propostaContaPessoaFisica.getComplemento();
        this.cidade = propostaContaPessoaFisica.getCidade();
        this.estado = propostaContaPessoaFisica.getEstado();
        this.linkImgCpf = propostaContaPessoaFisica.getLinkImgCpf();
        this.proposta = propostaContaPessoaFisica;
    }

    public UUID getId() {
        return id;
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

    public PropostaContaPessoaFisica getProposta() { return proposta; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
