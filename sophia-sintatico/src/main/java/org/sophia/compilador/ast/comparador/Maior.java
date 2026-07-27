package org.sophia.compilador.ast.comparador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Maior extends OperacaoBinaria {

    public Maior(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}
}