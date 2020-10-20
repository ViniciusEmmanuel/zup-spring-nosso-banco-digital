package br.com.viniciustestezup.nossobancodigital.compartilhado.dto;

import br.com.viniciustestezup.nossobancodigital.compartilhado.error.ErrorObject;
import br.com.viniciustestezup.nossobancodigital.compartilhado.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;

public class ResponseError {

    private HttpStatus code;
    private ArrayList<ErrorObject> erros = new ArrayList<>();

    private final String message = "Falha na Requisição";

    public ResponseError() {
        this.code = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getCode() { return code; }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public ErrorResponse getErros()
    {
        return new ErrorResponse(message, code.value(), code.getReasonPhrase(), erros);
    }

    public void setErros(ErrorObject erros) { this.erros.add(erros); }

    public boolean hasError(){ return !erros.isEmpty(); }
}
