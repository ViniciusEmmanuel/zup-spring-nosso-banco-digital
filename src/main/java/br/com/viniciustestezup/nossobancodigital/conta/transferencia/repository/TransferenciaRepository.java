package br.com.viniciustestezup.nossobancodigital.conta.transferencia.repository;

import br.com.viniciustestezup.nossobancodigital.conta.transferencia.model.Transferencia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends CrudRepository<Transferencia, Long> {
    Boolean existsTrasferenciaByCodigoTransferencia(Long codigoTransferencia);
}
