package org.sophia.compilador.ast.operador;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoBinaria;

public class Soma extends OperacaoBinaria {

    public Soma(Expressao esquerda, Expressao direita) {
		super(esquerda, direita);
	}

}
