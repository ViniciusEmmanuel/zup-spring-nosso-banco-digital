package br.com.viniciustestezup.nossobancodigital.conta.nova.repository;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PropostaContaPessoaFisicaRepository extends CrudRepository<PropostaContaPessoaFisica, UUID> {
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
}
