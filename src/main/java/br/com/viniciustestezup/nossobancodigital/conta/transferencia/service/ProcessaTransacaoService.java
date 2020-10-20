package br.com.viniciustestezup.nossobancodigital.conta.transferencia.service;

import br.com.viniciustestezup.nossobancodigital.compartilhado.interfaces.EmailService;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Conta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.ContaId;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.compartilhado.StatusTransferencia;
import br.com.viniciustestezup.nossobancodigital.conta.transferencia.model.Transferencia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class ProcessaTransacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessaTransacaoService.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmailService emailService;

    public ProcessaTransacaoService() { }

    @Transactional
    public void execute(Long id) {

        Transferencia transferencia = entityManager.find(Transferencia.class, id);

        if (transferencia.getStatusTransferencia() == StatusTransferencia.TRANSFERENCIA_REALIZADA) {
            LOGGER.info("Transferencia já realizada");
            return;
        }

        ContaId contaId = new ContaId(transferencia.getContaDestino(), transferencia.getAgenciaDestino());
        Conta conta = entityManager.find(Conta.class, contaId);
        conta.adicionaTransferencia(transferencia);

        entityManager.persist(conta);

        transferencia.transferenciaRealizada();
        entityManager.persist(transferencia);

        String message = String.format("Você recebeu uma transferencia no valor de %s \n" +
                "Obrigado por contar com Nosso Banco Digital.", transferencia.getValor());
        emailService.sendEmail(conta.getCliente().getEmail(), message);
    }
}
