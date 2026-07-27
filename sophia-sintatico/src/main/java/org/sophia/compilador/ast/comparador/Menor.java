package org.sophia.compilador.ast.comparador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Menor extends OperacaoBinaria {

    public Menor(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}
}