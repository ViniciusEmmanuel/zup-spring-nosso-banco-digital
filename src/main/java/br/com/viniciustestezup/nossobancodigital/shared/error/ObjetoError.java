package br.com.viniciustestezup.nossobancodigital.shared.error;

public class ObjetoError {

    private final String mensagem;
    private final String campo;
    private final Object parametro;


    public ObjetoError(String mensagem, String campo, Object parametro) {
        this.mensagem = mensagem;
        this.campo = campo;
        this.parametro = parametro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public Object getParametro() {
        return parametro;
    }
}