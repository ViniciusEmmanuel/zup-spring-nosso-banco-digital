package br.com.viniciustestezup.nossobancodigital.conta.login.service;

import br.com.viniciustestezup.nossobancodigital.shared.interfaces.EmailService;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Cliente;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.ClienteRepository;
import org.hibernate.validator.constraints.br.CPF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Service
public class PrimeiroAcessoClienteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimeiroAcessoClienteService.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Value("${env_tempoExpiracaoTokenPrimeiroAcesso}")
    private long tempo_expiracao_token_primeiro_acesso;

    public PrimeiroAcessoClienteService() { }

    @Async
    @Transactional
    public void execute(@Email String email, @CPF String cpf)  {
        Cliente cliente = clienteRepository.findByCpfEmail(cpf, email);

        if (cliente == null) {
            LOGGER.info("Cliente não identificado");
            return;
        }

        if (cliente.getPrimeiroAcessoRealizado()) {
            LOGGER.info("Cliente já realizou o primeiro acesso");
            return;
        }

        LocalDateTime timeToken = cliente.getCreateAtTokenPrimeiroAcesso()
                                            .plusSeconds(tempo_expiracao_token_primeiro_acesso);

        if (LocalDateTime.now().isBefore(timeToken)) {
            LOGGER.info("Token com prazo válido.");
            return;
        }

        try {
            cliente.gerarTokenPrimeiroAcesso();
            clienteRepository.save(cliente);

            String mensagem = String.format("Por favor acesse o link para digitar sua senha. \n" +
                    "http://localhost:8080/api/nova_senha/%s", cliente.getTokenPrimeiroAcesso());
            emailService.sendEmail(cliente.getEmail(), mensagem);
        }catch (Exception ex) {
            LOGGER.info("Erro ao executar o processo de gerar um token de acesso para o primeiro login");
        }
    }
}
