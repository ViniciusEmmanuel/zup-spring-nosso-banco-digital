package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request;

import br.com.viniciustestezup.nossobancodigital.shared.error.ObjetoError;
import br.com.viniciustestezup.nossobancodigital.shared.dto.ResponseError;
import br.com.viniciustestezup.nossobancodigital.shared.interfaces.FilesUploader;
import br.com.viniciustestezup.nossobancodigital.conta.nova.shared.EtapaNovaConta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
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
            responseError.setErros(new ObjetoError("Proposta não encontrada","",propostaId.toString()));
            return responseError;
        }

        propostaContaPessoaFisica = existPropostaContaPessoaFisica.get();
        if (propostaContaPessoaFisica.getEtapa() != EtapaNovaConta.ETAPA_2)
        {
            responseError.setCode(HttpStatus.UNPROCESSABLE_ENTITY);
            responseError.setErros(new ObjetoError("Proposta precisa ter finalizada a primeira e a segunda etapa.", "",""));
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
