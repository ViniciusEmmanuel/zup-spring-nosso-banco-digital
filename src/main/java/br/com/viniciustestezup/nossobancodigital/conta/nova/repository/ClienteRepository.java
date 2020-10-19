package br.com.viniciustestezup.nossobancodigital.conta.nova.repository;

import br.com.viniciustestezup.nossobancodigital.conta.nova.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, UUID> { }
