package br.com.viniciustestezup.nossobancodigital.conta.transferencia.service;

import br.com.viniciustestezup.nossobancodigital.conta.transferencia.model.Transferencia;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.repository.TransferenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarTransferenciaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CadastrarTransferenciaService.class);

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private AdicionaNovoJobProcessaTransacaoService adicionaNovoJobProcessaTransacaoService;
    public CadastrarTransferenciaService() { }

    public void execute(Transferencia transferencia){
        Boolean existeTransferencia = transferenciaRepository
                                        .existsTrasferenciaByCodigoTransferencia(transferencia.getCodigoTransferencia());
        if (existeTransferencia){
            LOGGER.info(String.format("Transferencia já existe, Codigo de Transferncia:: %s", transferencia.getCodigoTransferencia()));
            return;
        }
        transferenciaRepository.save(transferencia);
        adicionaNovoJobProcessaTransacaoService.execute(transferencia.getId());
    }
}
