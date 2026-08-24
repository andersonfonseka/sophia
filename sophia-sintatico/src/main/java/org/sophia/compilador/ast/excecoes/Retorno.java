package org.sophia.compilador.ast.excecoes;

public final class Retorno extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Object valor;

    public Retorno(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }
}