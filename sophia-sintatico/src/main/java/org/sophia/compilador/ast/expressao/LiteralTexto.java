package org.sophia.compilador.ast.expressao;

import org.sophia.compilador.ast.Expressao;

public class LiteralTexto extends Expressao {

    private final String valor;

    public LiteralTexto(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

	@Override
	public String toString() {
		return "LiteralTexto (" + valor + ")";
	}
    
    

}
