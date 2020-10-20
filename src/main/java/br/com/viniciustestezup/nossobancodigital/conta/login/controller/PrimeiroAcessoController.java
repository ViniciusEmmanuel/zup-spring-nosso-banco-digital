package br.com.viniciustestezup.nossobancodigital.conta.login.controller;

import br.com.viniciustestezup.nossobancodigital.compartilhado.dto.ResponseMenssagem;
import br.com.viniciustestezup.nossobancodigital.conta.login.dto.request.PrimeiroAcessoDTO;
import br.com.viniciustestezup.nossobancodigital.conta.login.service.PrimeiroAcessoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PrimeiroAcessoController {

    private final String _URL= "/api/primeiro_acesso";

    @Autowired
    private PrimeiroAcessoClienteService primeiroAcessoClienteService;

    @PostMapping(value = _URL)
    public ResponseEntity<ResponseMenssagem> PrimeiroAcesso(@Valid @RequestBody PrimeiroAcessoDTO primeiroAcessoDTO) {
        primeiroAcessoClienteService.execute(primeiroAcessoDTO.getEmail(), primeiroAcessoDTO.getCpf());

        return ResponseEntity.ok().body(new ResponseMenssagem(
                "Um email foi enviado com o link para o registro de uma nova senha."));
    }
}
