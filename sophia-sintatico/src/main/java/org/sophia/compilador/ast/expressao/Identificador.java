package org.sophia.compilador.ast.expressao;

import org.sophia.compilador.ast.Expressao;

public class Identificador extends Expressao {


	public Identificador(String valor) {
		super(valor);
	}

	@Override
	public String toString() {
		return "Identificador (" + getValor() + ")";
	}
    
    

}
