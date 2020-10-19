package br.com.viniciustestezup.nossobancodigital.conta.nova.repository;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Conta;
import br.com.viniciustestezup.nossobancodigital.conta.nova.model.ContaId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends CrudRepository<Conta, ContaId> {}
