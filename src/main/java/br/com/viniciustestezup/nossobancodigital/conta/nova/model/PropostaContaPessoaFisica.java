package br.com.viniciustestezup.nossobancodigital.conta.nova.model;

import br.com.viniciustestezup.nossobancodigital.conta.nova.compartilhado.EtapaNovaConta;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class PropostaContaPessoaFisica {

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
    @Enumerated(EnumType.STRING)
    private EtapaNovaConta etapa;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();;

    @PastOrPresent
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Size(min = 8, max = 8)
    private String cep;

    private String bairro;

    private String complemento;

    @Size(max = 100)
    private String cidade;

    @Size(max = 80)
    private String estado;


    @Deprecated
    PropostaContaPessoaFisica() {}

    public PropostaContaPessoaFisica(@NotBlank @NotNull String nome, @NotBlank @NotNull String sobrenome, @NotBlank @Email @NotNull String email, @NotBlank @CPF @NotNull String cpf, @NotBlank @Past LocalDate dataNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.etapa = EtapaNovaConta.ETAPA_1;
    }

    public void ComplementaDadosEtapa2 (@NotBlank @Size(min = 8, max = 8) String cep, @NotBlank String bairro, @NotBlank String complemento, @NotBlank @Size(max = 100) String cidade, @Size(max = 50) String estado) {
        this.cep = cep;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.etapa = EtapaNovaConta.ETAPA_2;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

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

    public EtapaNovaConta getEtapa() {
        return etapa;
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
}
