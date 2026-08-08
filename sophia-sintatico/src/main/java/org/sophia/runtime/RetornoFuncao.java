package org.sophia.runtime;

public class RetornoFuncao extends RuntimeException {

    private final Object valor;

    public RetornoFuncao(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

}