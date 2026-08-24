package org.sophia.runtime;

public class RetornoFuncao extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Object valor;

    public RetornoFuncao(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

}