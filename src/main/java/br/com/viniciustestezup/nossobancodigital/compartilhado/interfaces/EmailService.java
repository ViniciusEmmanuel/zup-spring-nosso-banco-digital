package br.com.viniciustestezup.nossobancodigital.compartilhado.interfaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface EmailService {
    void sendEmail(@NotBlank @Email String emailTo, @NotNull String message);
}
