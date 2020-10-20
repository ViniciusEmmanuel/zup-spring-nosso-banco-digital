package br.com.viniciustestezup.nossobancodigital.conta.nova.repository;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Cliente;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, UUID> {
    @Query("SELECT c FROM Cliente c WHERE cpf = ?1 AND email = ?2")
    Cliente findByCpfEmail(String cpf, String email);

    @Query("SELECT c FROM Cliente c WHERE token_primeiro_acesso = ?1")
    Cliente findByTokenPrimeiroAcesso(String token);
}
