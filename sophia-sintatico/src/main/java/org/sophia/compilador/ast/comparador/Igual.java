package org.sophia.compilador.ast.comparador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Igual extends OperacaoBinaria {

    public Igual(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}
}