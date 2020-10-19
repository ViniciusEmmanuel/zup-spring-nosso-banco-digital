package br.com.viniciustestezup.nossobancodigital.conta.nova.service;

import br.com.viniciustestezup.nossobancodigital.compartilhado.configuracoes.AsyncConfiguration;
import br.com.viniciustestezup.nossobancodigital.conta.nova.compartilhado.StatusSistemaExterno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BuscaSistemaExternoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuscaSistemaExternoService.class);

    public BuscaSistemaExternoService() {}

    public StatusSistemaExterno validarAceitaProposta(){
        try {
            if (new Random().nextBoolean()){
                return StatusSistemaExterno.VALIDADO;
            }else {
                return StatusSistemaExterno.NAO_VALIDO;
            }
        }catch (Exception exception){
            LOGGER.error("Falha ao validar aceite da proposta pelo sistema externo.", exception);
            return StatusSistemaExterno.ERRO_AO_PROCESSAR;
        }
    }
}
