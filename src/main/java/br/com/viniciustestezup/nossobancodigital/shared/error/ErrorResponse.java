package br.com.viniciustestezup.nossobancodigital.shared.error;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {

    private final String message;
    private final int code;
    private final String status;
    private final List<ObjetoError> errors;

    public ErrorResponse(String message, int code, String status, List<ObjetoError> errors) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errors = errors;
    }
}
