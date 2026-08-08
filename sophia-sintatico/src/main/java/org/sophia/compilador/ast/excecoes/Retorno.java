package org.sophia.compilador.ast.excecoes;

public final class Retorno extends RuntimeException {

    private final Object valor;

    public Retorno(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }
}