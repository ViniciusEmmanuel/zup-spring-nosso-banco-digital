package br.com.viniciustestezup.nossobancodigital.compartilhado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${env_from_email}")
    private String envFromEmail;

    @Async
    public void sendEmail(@NotBlank @Email String emailTo, @NotNull String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(message);
        mailMessage.setTo(emailTo);
        mailMessage.setFrom(envFromEmail);

        javaMailSender.send(mailMessage);
    }
}
