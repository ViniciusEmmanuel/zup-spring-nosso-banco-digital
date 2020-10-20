package br.com.viniciustestezup.nossobancodigital.conta.transferencia.controller;

import br.com.viniciustestezup.nossobancodigital.compartilhado.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.ContaRepository;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.dto.request.ReceberNovaTransferenciaDTO;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.model.Transferencia;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.service.CadastrarTransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ReceberTransferenciaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CadastrarTransferenciaService cadastrarTransferenciaService;

    @PostMapping(value = "/api/transferencias")
    @Transactional
    public  ResponseEntity ReceberNovaTransferencia(@Valid @RequestBody ReceberNovaTransferenciaDTO receberNovaTransferenciaDTO) {
        ResponseError error = receberNovaTransferenciaDTO.validarRequest(contaRepository);

        if (error.hasError())
            return ResponseEntity.badRequest().body(error);

        Transferencia transferencia = receberNovaTransferenciaDTO.toModel();
        cadastrarTransferenciaService.execute(transferencia);
        return ResponseEntity.ok().build();
    }
}
