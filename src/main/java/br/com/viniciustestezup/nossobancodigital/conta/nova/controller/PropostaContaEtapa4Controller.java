package br.com.viniciustestezup.nossobancodigital.conta.nova.controller;

import br.com.viniciustestezup.nossobancodigital.compartilhado.error.ResponseError;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request.RequestPropostaPessoaFisicaEtapa4DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.response.GetResponseBuscaNovaPropostaEtapa4DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.response.PostResponseAceitaNovaPropostaEtapa4DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.UUID;

@RestController
public class PropostaContaEtapa4Controller extends BaseController{

    private final String _URL = baseURL+"/etapa_4/{propostaId}";

    @Autowired
    PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository;

    @GetMapping(value = _URL, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GetResponseBuscaNovaPropostaEtapa4DTO> BuscaNovaPropostaEtapa4(@PathVariable UUID propostaId) {

        RequestPropostaPessoaFisicaEtapa4DTO requestPropostaPessoaFisicaEtapa4DTO
                                                = new RequestPropostaPessoaFisicaEtapa4DTO();
        ResponseError error = requestPropostaPessoaFisicaEtapa4DTO
                                .validarRequest(propostaContaPessoaFisicaRepository, propostaId);

        if (error.hasError())
            return (ResponseEntity) ResponseEntity.status(error.getCode()).body(error.getMessages());

        return ResponseEntity.status(HttpStatus.OK).body(requestPropostaPessoaFisicaEtapa4DTO.toGetResponseBuscaNovaPropostaEtapa4DTO());
    }

    @PostMapping(value = _URL, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<PostResponseAceitaNovaPropostaEtapa4DTO> AceitaNovaPropostaEtapa4(@Valid @RequestBody RequestPropostaPessoaFisicaEtapa4DTO requestProposta4,
                                                                                            @PathVariable UUID propostaId) {
        ResponseError error = requestProposta4.validarRequest(propostaContaPessoaFisicaRepository, propostaId);
        if (error.hasError())
            return (ResponseEntity) ResponseEntity.status(error.getCode()).body(error.getMessages());

        PropostaContaPessoaFisica propostaContaPessoaFisica = requestProposta4.toModel();
        propostaContaPessoaFisicaRepository.save(propostaContaPessoaFisica);
        return ResponseEntity.status(HttpStatus.OK).body(requestProposta4.toPostResponseAceitaNovaPropostaEtapa4DTO());
    }
 }
