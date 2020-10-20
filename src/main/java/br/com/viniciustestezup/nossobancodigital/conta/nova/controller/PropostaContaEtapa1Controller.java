package br.com.viniciustestezup.nossobancodigital.conta.nova.controller;

import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.Location;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request.RequestPropostaPessoaFisicaEtapa1DTO;
import br.com.viniciustestezup.nossobancodigital.shared.dto.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PropostaContaEtapa1Controller extends  BaseController {

    @Autowired
    private PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository;

    @PostMapping(value = baseURL)
    @Transactional
    public ResponseEntity NovaPropostaEtapa1(@Valid @RequestBody RequestPropostaPessoaFisicaEtapa1DTO requestProposta1) {
        ResponseError responseError = requestProposta1.validarRequest(propostaContaPessoaFisicaRepository);
        if (responseError.hasError())
            return ResponseEntity.status(responseError.getCode()).body(responseError);

        PropostaContaPessoaFisica propostaContaPessoaFisica = requestProposta1.toModel();
        propostaContaPessoaFisicaRepository.save(propostaContaPessoaFisica);
        String pathUrl = baseURL+"/etapa_2/"+propostaContaPessoaFisica.getId();
        return ResponseEntity.created(Location.create(pathUrl)).build();
    }
}
