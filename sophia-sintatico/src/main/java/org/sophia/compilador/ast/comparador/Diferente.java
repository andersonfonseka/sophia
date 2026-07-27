package org.sophia.compilador.ast.comparador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Diferente extends OperacaoBinaria {

    public Diferente(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}
}