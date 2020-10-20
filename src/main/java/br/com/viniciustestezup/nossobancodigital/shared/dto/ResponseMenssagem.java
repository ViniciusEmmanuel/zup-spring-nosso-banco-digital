package br.com.viniciustestezup.nossobancodigital.shared.dto;

public class ResponseMenssagem {

    private String mensagem;

    public ResponseMenssagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
