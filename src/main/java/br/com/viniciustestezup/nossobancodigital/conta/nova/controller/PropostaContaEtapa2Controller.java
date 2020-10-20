package br.com.viniciustestezup.nossobancodigital.conta.nova.controller;

import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.Location;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request.RequestPropostaPessoaFisicaEtapa2DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class PropostaContaEtapa2Controller extends BaseController{

    @Autowired
    private PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository;

    @PostMapping(value = baseURL+"/etapa_2/{propostaId}")
    @Transactional
    public ResponseEntity NovaPropostaEtapa2(@Valid @RequestBody RequestPropostaPessoaFisicaEtapa2DTO requestProposta2,
                                          @PathVariable UUID propostaId) {
        List<Error> error = requestProposta2.validarRequest(propostaContaPessoaFisicaRepository, propostaId);
        if (!error.isEmpty())
          return (ResponseEntity) ResponseEntity.badRequest().body(error);

        PropostaContaPessoaFisica propostaContaPessoaFisica = requestProposta2.toModel();
        propostaContaPessoaFisicaRepository.save(propostaContaPessoaFisica);
        String pathUrl = baseURL+"/etapa_3/"+propostaContaPessoaFisica.getId();
        return ResponseEntity.created(Location.create(pathUrl)).build();
    }
}
