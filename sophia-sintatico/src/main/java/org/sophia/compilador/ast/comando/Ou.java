package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Ou extends OperacaoBinaria {

	public Ou(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}

}
