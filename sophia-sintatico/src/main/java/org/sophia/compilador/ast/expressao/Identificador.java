package org.sophia.compilador.ast.expressao;

import org.sophia.compilador.ast.Expressao;

public class Identificador extends Expressao {

    private final String valor;

    public Identificador(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

	@Override
	public String toString() {
		return "Identificador (" + valor + ")";
	}
    
    

}
