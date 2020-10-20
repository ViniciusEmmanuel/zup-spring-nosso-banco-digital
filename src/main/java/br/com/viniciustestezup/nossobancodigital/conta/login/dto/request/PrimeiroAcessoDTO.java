package br.com.viniciustestezup.nossobancodigital.conta.login.dto.request;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PrimeiroAcessoDTO {

    @NotBlank
    @Email
    private String Email;

    @NotBlank
    @CPF
    private String cpf;

    public String getEmail() { return Email; }

    public void setEmail(String email) { Email = email; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }
}
