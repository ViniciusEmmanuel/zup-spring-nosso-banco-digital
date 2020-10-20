package br.com.viniciustestezup.nossobancodigital.conta.login.dto.request;

import br.com.viniciustestezup.nossobancodigital.shared.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.shared.error.ObjetoError;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Cliente;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.ClienteRepository;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class NovoPasswordDTO {

    @Size(min = 8, max = 8)
    private String password;

    private Cliente cliente;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ResponseError validarRequest(ClienteRepository clienteRepository,
                                        String token,
                                        Long env_tempo_expiracao_token_primeiro_acesso) {

        ResponseError responseError = new ResponseError();

        cliente = clienteRepository.findByTokenPrimeiroAcesso(token);

        if (cliente == null){
            responseError.setCode(HttpStatus.NOT_FOUND);
            return responseError;
        }

        LocalDateTime timeToken = cliente.getCreateAtTokenPrimeiroAcesso()
                .plusSeconds(env_tempo_expiracao_token_primeiro_acesso);

        if (LocalDateTime.now().isAfter(timeToken)) {
            responseError.setCode(HttpStatus.BAD_REQUEST);
            responseError.setErros(new ObjetoError("Tempo expirado", "token", token));
        }

        if (cliente.getPrimeiroAcessoRealizado()) {
            responseError.setCode(HttpStatus.BAD_REQUEST);
            responseError.setErros(new ObjetoError("Token já utilizado", "token", token));
        }

        if (!cliente.validarSegurancaPassword(password)) {
            responseError.setCode(HttpStatus.BAD_REQUEST);
            responseError.setErros(new ObjetoError("Senha fraca", "password", password));
        }

        return  responseError;
    }

    public Cliente toModel() {
        cliente.primeiroAcesso(password);
        return cliente;
    }
}
