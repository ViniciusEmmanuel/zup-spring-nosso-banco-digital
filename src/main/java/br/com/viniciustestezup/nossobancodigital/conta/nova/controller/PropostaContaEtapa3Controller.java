package br.com.viniciustestezup.nossobancodigital.conta.nova.controller;

import br.com.viniciustestezup.nossobancodigital.shared.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.shared.interfaces.FilesUploader;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.Location;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request.RequestPropostaPessoaFisicaEtapa3DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class PropostaContaEtapa3Controller extends BaseController {

    @Autowired
    private PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository;

    @Autowired
    private FilesUploader filesUploader;

    @PostMapping(value = baseURL+"/etapa_3/{propostaId}", consumes="multipart/form-data")
    @Transactional
    public ResponseEntity NovaPropostaEtapa3(@RequestPart(value="file", required = true) final MultipartFile imgCpf,
                                             @PathVariable UUID propostaId) {
        RequestPropostaPessoaFisicaEtapa3DTO requestProposta3 = new RequestPropostaPessoaFisicaEtapa3DTO(imgCpf);

        ResponseError error = requestProposta3.validarRequest(propostaContaPessoaFisicaRepository, propostaId);
        if (error.hasError())
            return (ResponseEntity) ResponseEntity.status(error.getCode()).body(error.getErros());

        PropostaContaPessoaFisica propostaContaPessoaFisica = requestProposta3.toModel(filesUploader);
        propostaContaPessoaFisicaRepository.save(propostaContaPessoaFisica);
        String pathUrl = baseURL+"/etapa_4/"+propostaContaPessoaFisica.getId();
        return ResponseEntity.created(Location.create(pathUrl)).build();
    }
}
