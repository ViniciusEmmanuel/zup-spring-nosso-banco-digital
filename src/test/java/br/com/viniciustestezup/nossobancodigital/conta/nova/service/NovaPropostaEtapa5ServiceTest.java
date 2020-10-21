package br.com.viniciustestezup.nossobancodigital.conta.nova.service;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.EtapaNovaConta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.StatusProposta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.StatusSistemaExterno;
import br.com.viniciustestezup.nossobancodigital.shared.interfaces.EmailService;
import br.com.viniciustestezup.nossobancodigital.shared.services.EmailServiceServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NovaPropostaEtapa5ServiceTest {

    private PropostaContaPessoaFisica propostaContaPessoaFisica;
    private UUID propostaId = UUID.randomUUID();

    private EmailService emailService = Mockito.mock(EmailServiceServiceImpl.class);
    private EntityManager entityManager = Mockito.mock(EntityManager.class);
    private BuscaSistemaExternoService buscaSistemaExternoService = Mockito.mock(BuscaSistemaExternoService.class);
    private AdicionaNovoJobPropostaEtapa5Service adicionaNovoJobPropostaEtapa5Service = Mockito.mock(AdicionaNovoJobPropostaEtapa5Service.class);

    public NovaPropostaEtapa5ServiceTest() {
        propostaContaPessoaFisica = new PropostaContaPessoaFisica("Teste","do teste", "teste@gmail.com", "81283879018", LocalDate.parse("1991-10-15"));
        propostaContaPessoaFisica.ComplementaDadosEtapa2("38123000","boa vista","proximo", "uberaba","mg");
        propostaContaPessoaFisica.ComplementaDadosEtapa3("http://driver/123.png");

        Mockito.when(entityManager.find(PropostaContaPessoaFisica.class, propostaId)).thenReturn(propostaContaPessoaFisica);
    }

    @Test
    @DisplayName("Processar uma proposta com aceite pelo sistema externo")
    void processarCadastroNovaPropostaComAceiteSistemaExteno() {
        propostaContaPessoaFisica.ComplementaDadosEtapa4(true);

        Mockito.when(buscaSistemaExternoService.validarAceitaProposta()).thenReturn(StatusSistemaExterno.VALIDADO);

        NovaPropostaEtapa5Service novaPropostaEtapa5Service= new NovaPropostaEtapa5Service(emailService,
                buscaSistemaExternoService,
                entityManager,
                adicionaNovoJobPropostaEtapa5Service);

        novaPropostaEtapa5Service.execute(propostaId);

        assertEquals(propostaContaPessoaFisica.getEtapa(), EtapaNovaConta.ETAPA_5);
        assertEquals(propostaContaPessoaFisica.getStatus(), StatusProposta.LIBERADA);
    }

    @Test
    @DisplayName("Processar uma proposta com recusa pelo sistema externo")
    void processarCadastroNovaPropostaComRecusaSistemaExteno() {
        propostaContaPessoaFisica.ComplementaDadosEtapa4(true);

        Mockito.when(buscaSistemaExternoService.validarAceitaProposta()).thenReturn(StatusSistemaExterno.NAO_VALIDO);

        NovaPropostaEtapa5Service novaPropostaEtapa5Service= new NovaPropostaEtapa5Service(emailService,
                buscaSistemaExternoService,
                entityManager,
                adicionaNovoJobPropostaEtapa5Service);

        novaPropostaEtapa5Service.execute(propostaId);

        assertEquals(propostaContaPessoaFisica.getEtapa(), EtapaNovaConta.ETAPA_5);
        assertEquals(propostaContaPessoaFisica.getStatus(), StatusProposta.NAO_LIBERADA);
    }

    @Test
    @DisplayName("Processar uma proposta com falha pelo sistema externo")
    void processarCadastroNovaPropostaComFalhaSistemaExteno() {
        propostaContaPessoaFisica.ComplementaDadosEtapa4(true);

        Mockito.when(buscaSistemaExternoService.validarAceitaProposta()).thenReturn(StatusSistemaExterno.ERRO_AO_PROCESSAR);

        NovaPropostaEtapa5Service novaPropostaEtapa5Service= new NovaPropostaEtapa5Service(emailService,
                buscaSistemaExternoService,
                entityManager,
                adicionaNovoJobPropostaEtapa5Service);

        novaPropostaEtapa5Service.execute(propostaId);
        assertEquals(propostaContaPessoaFisica.getTentativaValidarAceiteProposta(), 1);

        novaPropostaEtapa5Service.execute(propostaId);
        assertEquals(propostaContaPessoaFisica.getTentativaValidarAceiteProposta(), 2);
    }
}