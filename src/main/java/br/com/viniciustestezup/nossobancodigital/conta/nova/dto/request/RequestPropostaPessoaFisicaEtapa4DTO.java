package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request;

import br.com.viniciustestezup.nossobancodigital.compartilhado.error.ResponseError;
import br.com.viniciustestezup.nossobancodigital.conta.nova.compartilhado.EtapaNovaConta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.response.GetResponseBuscaNovaPropostaEtapa4DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.response.PostResponseAceitaNovaPropostaEtapa4DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class RequestPropostaPessoaFisicaEtapa4DTO {

    @NotNull
    private Boolean aceitaProposta;

    private PropostaContaPessoaFisica propostaContaPessoaFisica;

    public Boolean getAceitaProposta() { return aceitaProposta; }
    public void setAceitaProposta(boolean aceitaProposta) {
        this.aceitaProposta = aceitaProposta;
    }

    public ResponseError validarRequest(PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository,
                                        UUID propostaId) {
        Optional<PropostaContaPessoaFisica> existPropostaContaPessoaFisica = propostaContaPessoaFisicaRepository
                .findById(propostaId);

        ResponseError responseError = new ResponseError();

        if (!existPropostaContaPessoaFisica.isPresent())
        {
            responseError.setCode(HttpStatus.NOT_FOUND);
            responseError.setMessages(new Error("Proposta não encontrada"));
            return responseError;
        }

        propostaContaPessoaFisica = existPropostaContaPessoaFisica.get();
        if (propostaContaPessoaFisica.getEtapa() != EtapaNovaConta.ETAPA_3)
        {
            responseError.setCode(HttpStatus.UNPROCESSABLE_ENTITY);
            responseError.setMessages(new Error("Proposta precisa ter finalizada a primeira a segunda e a terceira etapa."));
            return responseError;
        }
        return responseError;
    }

    public PropostaContaPessoaFisica toModel() {
        propostaContaPessoaFisica.ComplementaDadosEtapa4(aceitaProposta);
        return  propostaContaPessoaFisica;
    }

    public GetResponseBuscaNovaPropostaEtapa4DTO toGetResponseBuscaNovaPropostaEtapa4DTO() {
        return new GetResponseBuscaNovaPropostaEtapa4DTO(propostaContaPessoaFisica);
    }

    public PostResponseAceitaNovaPropostaEtapa4DTO  toPostResponseAceitaNovaPropostaEtapa4DTO() {
        if (aceitaProposta == true)
            return new PostResponseAceitaNovaPropostaEtapa4DTO("Sua conta será criada e um e-mail contendo as informações necessarias será enviado.");

        return new PostResponseAceitaNovaPropostaEtapa4DTO("Proposta recusada.");
    }
}
