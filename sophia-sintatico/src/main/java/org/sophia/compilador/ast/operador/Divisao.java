package org.sophia.compilador.ast.operador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Divisao extends OperacaoBinaria {

    public Divisao(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}

}
