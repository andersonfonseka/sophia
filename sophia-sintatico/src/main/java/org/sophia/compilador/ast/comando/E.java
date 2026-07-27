package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class E extends OperacaoBinaria {

	public E(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}

}
