package org.sophia.compilador.ast.comparador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class MenorOuIgual extends OperacaoBinaria {

    public MenorOuIgual(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}
}