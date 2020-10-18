package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request;

import br.com.viniciustestezup.nossobancodigital.compartilhado.error.ResponseError;
import br.com.viniciustestezup.nossobancodigital.compartilhado.interfaces.FilesUploader;
import br.com.viniciustestezup.nossobancodigital.conta.nova.compartilhado.EtapaNovaConta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class RequestPropostaPessoaFisicaEtapa3DTO {

    @NotNull
    private MultipartFile imgCpf;

    private PropostaContaPessoaFisica propostaContaPessoaFisica;

    public RequestPropostaPessoaFisicaEtapa3DTO(@NotNull MultipartFile imgCpf) {
        this.imgCpf = imgCpf;
    }

    public MultipartFile getImgCpf() {
        return imgCpf;
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
        if (propostaContaPessoaFisica.getEtapa() != EtapaNovaConta.ETAPA_2)
        {
            responseError.setCode(HttpStatus.UNPROCESSABLE_ENTITY);
            responseError.setMessages(new Error("Proposta precisa ter finalizada a primeira e a segunda etapa."));
            return responseError;
        }
        return responseError;
    }

    public PropostaContaPessoaFisica toModel(FilesUploader filesUploader) {
        String linkImgCpf = filesUploader.upload(imgCpf, propostaContaPessoaFisica.getId().toString());
        propostaContaPessoaFisica.ComplementaDadosEtapa3(linkImgCpf);
        return propostaContaPessoaFisica;
    }
}
