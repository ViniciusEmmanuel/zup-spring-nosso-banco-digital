package br.com.viniciustestezup.nossobancodigital.compartilhado.error;

import org.springframework.http.HttpStatus;
import java.util.ArrayList;

public class ResponseError {

    private HttpStatus code;
    private ArrayList<Error> messages;

    public ResponseError() {
        this.code = HttpStatus.BAD_REQUEST;
        this.messages = new ArrayList<>();
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public ArrayList<Error> getMessages() {
        return messages;
    }

    public void setMessages(Error messages) {
        this.messages.add(messages);
    }

    public boolean hasError(){
        return !messages.isEmpty();
    }
}
