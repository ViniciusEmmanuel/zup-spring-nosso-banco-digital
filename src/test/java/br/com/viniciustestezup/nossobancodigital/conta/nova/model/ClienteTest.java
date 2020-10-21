package br.com.viniciustestezup.nossobancodigital.conta.nova.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {


    private PropostaContaPessoaFisica propostaContaPessoaFisica;

    public ClienteTest() {
        propostaContaPessoaFisica = new PropostaContaPessoaFisica("Teste","do teste", "teste@gmail.com", "81283879018", LocalDate.parse("1991-10-15"));
    }

    @Test
    @DisplayName("Assert equals com um token contendo 6 digitos")
    void gerarTokenPrimeiroAcessoContendo6Digitos() {
        Cliente cliente = new Cliente(propostaContaPessoaFisica);
        cliente.gerarTokenPrimeiroAcesso();
        assertEquals(6, cliente.getTokenPrimeiroAcesso().length());
    }

    @Test
    @DisplayName("Assert throw ao gerar um token com o primeiro acesso realizado")
    void gerarTokenPrimeiroAcessoComPrimeiroAcessoRealizado() {
        Cliente cliente = new Cliente(propostaContaPessoaFisica);
        cliente.primeiroAcesso("Va123#@!");
        assertThrows(RuntimeException.class, cliente::gerarTokenPrimeiroAcesso);
    }

    @Test
    @DisplayName("Assert false ao validar password com parametros que não atendem os requisitos minimos")
    void validarPasswordComSenhasFracas() {
        Cliente cliente = new Cliente(propostaContaPessoaFisica);
        assertFalse(cliente.validarPassword("123#45"));
        assertFalse(cliente.validarPassword("12345678"));
        assertFalse(cliente.validarPassword("cliente1234"));
        assertFalse(cliente.validarPassword("Vi151091"));
    }

    @Test
    @DisplayName("Assert true ao validar password com parametros que não atendem os requisitos minimos")
    void validarPasswordComSenhaForte8Digitos() {
        Cliente cliente = new Cliente(propostaContaPessoaFisica);
        assertTrue(cliente.validarPassword("Va123#@!"));
        assertTrue(cliente.validarPassword("Vi_15@91"));
    }

    @Test
    @DisplayName("Assert throw ao tentar realizar o primeiro acesso novamente")
    void primeiroAcesso() {
        Cliente cliente = new Cliente(propostaContaPessoaFisica);
        cliente.primeiroAcesso("Va123#@!");
        assertThrows(RuntimeException.class, ()->cliente.primeiroAcesso("Va123#@!"));
    }
}