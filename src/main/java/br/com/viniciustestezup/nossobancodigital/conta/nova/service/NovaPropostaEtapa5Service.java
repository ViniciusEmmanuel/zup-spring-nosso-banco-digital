package br.com.viniciustestezup.nossobancodigital.conta.nova.service;

import br.com.viniciustestezup.nossobancodigital.shared.interfaces.EmailService;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.StatusProposta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.StatusSistemaExterno;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Cliente;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Conta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import java.util.UUID;


@Service
public class NovaPropostaEtapa5Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(NovaPropostaEtapa5Service.class);

    private EmailService emailService;

    private BuscaSistemaExternoService buscaSistemaExternoService;

    private EntityManager entityManager;

    private AdicionaNovoJobPropostaEtapa5Service adicionaNovoJobPropostaEtapa5Service;

    private PropostaContaPessoaFisica propostaContaPessoaFisica;

    @Autowired
    public NovaPropostaEtapa5Service(EmailService emailService,
                                     BuscaSistemaExternoService buscaSistemaExternoService,
                                     EntityManager entityManager,
                                     AdicionaNovoJobPropostaEtapa5Service adicionaNovoJobPropostaEtapa5Service) {
        this.emailService = emailService;
        this.buscaSistemaExternoService = buscaSistemaExternoService;
        this.entityManager = entityManager;
        this.adicionaNovoJobPropostaEtapa5Service = adicionaNovoJobPropostaEtapa5Service;
    }

    @Transactional
    public void execute(UUID propostaId) {
        propostaContaPessoaFisica = entityManager.find(PropostaContaPessoaFisica.class, propostaId);

        if (propostaContaPessoaFisica.getAceitaProposta() == false) {
            emailService.sendEmail(propostaContaPessoaFisica.getEmail(), "Venha fazer parte do Nosso Banco Digital, queremos você como nosso cliente.");
            return;
        }

        StatusSistemaExterno statusSistemaExterno  = buscaSistemaExternoService.validarAceitaProposta();
        LOGGER.info(statusSistemaExterno.toString());

        switch (statusSistemaExterno){
            case VALIDADO:
                atualizarStatusPropostaAposAceiteValidadoPorSistemaExterno();
                Cliente cliente = criarClienteApartirAceiteProposta();
                Conta conta = criarConta(cliente);
                enviarClienteContaEmail(conta);
                return;

            case NAO_VALIDO:
                atualizarStatusPrpostaAposRecusarAceitoPorSistemaExteno();
                return;

            case ERRO_AO_PROCESSAR:
                int tentativas = propostaContaPessoaFisica.getTentativaValidarAceiteProposta();
                propostaContaPessoaFisica.setTentativaValidarAceiteProposta(++tentativas);
                entityManager.persist(propostaContaPessoaFisica);

                if(propostaContaPessoaFisica.getTentativaValidarAceiteProposta() < 2)
                    adicionaNovoJobPropostaEtapa5Service.execute(propostaId);
                return;

            default:
                return;
        }
    }

    private void atualizarStatusPropostaAposAceiteValidadoPorSistemaExterno() {
        propostaContaPessoaFisica.ComplementaDadosEtapa5(StatusProposta.LIBERADA);
        entityManager.persist(propostaContaPessoaFisica);
    }

    private void atualizarStatusPrpostaAposRecusarAceitoPorSistemaExteno() {
        propostaContaPessoaFisica.ComplementaDadosEtapa5(StatusProposta.NAO_LIBERADA);
        entityManager.persist(propostaContaPessoaFisica);
    }


    private Cliente criarClienteApartirAceiteProposta() {
        Cliente cliente = new Cliente(propostaContaPessoaFisica);
        entityManager.persist(cliente);
        return cliente;
    }

    private Conta criarConta(Cliente cliente){
        Conta conta = new Conta(cliente);
        entityManager.persist(conta);
        return conta;
    }

    private void enviarClienteContaEmail(Conta conta) {
        String message = String.format("A sua conta foi criada \n" +
                "Os dados da sua conta são: \n" +
                "Agencia: %s \n" +
                "Conta: %s \n" +
                "Obrigado por contar com Nosso Banco Digital.", conta.getAgencia(), conta.getConta());

        emailService.sendEmail(propostaContaPessoaFisica.getEmail(), message);
    }
}
