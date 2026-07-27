package org.sophia.compilador.ast.operador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Subtracao extends OperacaoBinaria {

    public Subtracao(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}

}
