package br.com.viniciustestezup.nossobancodigital.conta.nova.repository;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.PropostaContaPessoaFisica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropostaContaPessoaFisicaRepository extends CrudRepository<PropostaContaPessoaFisica, UUID> {
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
}
