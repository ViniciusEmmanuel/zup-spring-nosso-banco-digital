package br.com.viniciustestezup.nossobancodigital.conta.login.controller;

import br.com.viniciustestezup.nossobancodigital.compartilhado.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.compartilhado.interfaces.EmailService;
import br.com.viniciustestezup.nossobancodigital.conta.login.dto.request.NovoPasswordDTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Cliente;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NovoPasswordController {

    private final String _URL= "/api/nova_senha";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    @Value("${env_tempoExpiracaoTokenPrimeiroAcesso}")
    private long env_tempo_expiracao_token_primeiro_acesso;

    @PostMapping(value = _URL+"/{token}")
    @Transactional
    public ResponseEntity NovaSenha(@PathVariable String token, @Valid @RequestBody NovoPasswordDTO novoPasswordDTO) {

        ResponseError responseError = novoPasswordDTO.validarRequest(clienteRepository, token, env_tempo_expiracao_token_primeiro_acesso);
        if (responseError.hasError())
            return ResponseEntity.status(responseError.getCode()).body(responseError.getErros());

        Cliente cliente = novoPasswordDTO.toModel();
        clienteRepository.save(cliente);
        emailService.sendEmail(cliente.getEmail(), "Sua senha foi alterarada.");
        return ResponseEntity.ok().build();
    }
}
