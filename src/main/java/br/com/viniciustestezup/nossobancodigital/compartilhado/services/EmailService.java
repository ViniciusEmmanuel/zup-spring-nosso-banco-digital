package br.com.viniciustestezup.nossobancodigital.compartilhado.services;

import br.com.viniciustestezup.nossobancodigital.compartilhado.interfaces.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class EmailService implements SendEmail {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${env_from_email}")
    private String envFromEmail;

    public void sendEmail(@NotBlank @Email String emailTo, @NotNull String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(message);
        mailMessage.setTo(emailTo);
        mailMessage.setFrom(envFromEmail);
        javaMailSender.send(mailMessage);
    }
}
