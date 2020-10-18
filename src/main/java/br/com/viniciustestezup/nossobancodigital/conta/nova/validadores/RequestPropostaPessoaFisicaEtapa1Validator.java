package br.com.viniciustestezup.nossobancodigital.conta.nova.validadores;

import br.com.viniciustestezup.nossobancodigital.conta.nova.dto.request.RequestPropostaPessoaFisicaEtapa1DTO;
import br.com.viniciustestezup.nossobancodigital.conta.nova.repository.PropostaContaPessoaFisicaRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class RequestPropostaPessoaFisicaEtapa1Validator implements Validator {

    private PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository;

    public RequestPropostaPessoaFisicaEtapa1Validator(PropostaContaPessoaFisicaRepository propostaContaPessoaFisicaRepository) {
        this.propostaContaPessoaFisicaRepository = propostaContaPessoaFisicaRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RequestPropostaPessoaFisicaEtapa1DTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestPropostaPessoaFisicaEtapa1DTO requestPropostaPessoaFisicaEtapa1DTO
                = (RequestPropostaPessoaFisicaEtapa1DTO) target;

        if (propostaContaPessoaFisicaRepository.existsByEmail(requestPropostaPessoaFisicaEtapa1DTO.getEmail()))
            errors.rejectValue("email",null,"Já existe esse e-mail cadastrado na nossa base de dados.");

        if (propostaContaPessoaFisicaRepository.existsByCpf(requestPropostaPessoaFisicaEtapa1DTO.getCpf()))
            errors.rejectValue("cpf",null,"Já existe esse CPF cadastrado na nossa base de dados.");

        Period idade = Period.between(requestPropostaPessoaFisicaEtapa1DTO.getDataNascimento(), LocalDate.now());
        if(idade.getYears() < 18)
            errors.rejectValue("dataNascimento",null,"Para cadastrar a proposta, necessita ter pelo menos 18 anos.");
    }
}


