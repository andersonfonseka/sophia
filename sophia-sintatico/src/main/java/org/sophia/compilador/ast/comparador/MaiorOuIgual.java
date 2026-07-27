package org.sophia.compilador.ast.comparador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class MaiorOuIgual extends OperacaoBinaria {

    public MaiorOuIgual(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}
}