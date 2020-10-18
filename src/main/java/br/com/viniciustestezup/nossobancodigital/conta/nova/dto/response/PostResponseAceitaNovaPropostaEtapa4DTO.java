package br.com.viniciustestezup.nossobancodigital.conta.nova.dto.response;

import com.fasterxml.jackson.databind.util.JSONPObject;

public class PostResponseAceitaNovaPropostaEtapa4DTO {

    final private String message;

    public PostResponseAceitaNovaPropostaEtapa4DTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
