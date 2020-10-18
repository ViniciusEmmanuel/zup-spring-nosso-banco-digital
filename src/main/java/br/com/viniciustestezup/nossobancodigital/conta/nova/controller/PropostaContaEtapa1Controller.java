package br.com.viniciustestezup.nossobancodigital.conta.nova.controller;

import br.com.viniciustestezup.nossobancodigital.conta.nova.compartilhado.Location;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request.RequestPropostaPessoaFisicaEtapa1DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.validadores.RequestPropostaPessoaFisicaEtapa1Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PropostaContaEtapa1Controller extends  BaseController {

    @Autowired
    private PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository;

    @InitBinder("requestPropostaPessoaFisicaEstapa1DTO")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new RequestPropostaPessoaFisicaEtapa1Validator(propostaContaPessoaFisicaRepository));
    }

    @PostMapping(value = baseURL)
    @Transactional
    public ResponseEntity NovaPropostaEtapa1(@Valid @RequestBody RequestPropostaPessoaFisicaEtapa1DTO requestProposta1) {
        PropostaContaPessoaFisica propostaContaPessoaFisica = requestProposta1.toModel();
        propostaContaPessoaFisicaRepository.save(propostaContaPessoaFisica);
        String pathUrl = baseURL+"/etapa_2/"+propostaContaPessoaFisica.getId();
        return ResponseEntity.created(Location.create(pathUrl)).build();
    }
}
