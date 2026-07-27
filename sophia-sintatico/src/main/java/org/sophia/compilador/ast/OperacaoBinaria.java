package org.sophia.compilador.ast;

public abstract class OperacaoBinaria extends Expressao {

    private final Expressao esquerda;

    private final Expressao direita;
    
    public OperacaoBinaria(Expressao esquerda, Expressao direita) {
		super();
		this.esquerda = esquerda;
		this.direita = direita;
	}

	public Expressao getEsquerda() {
        return esquerda;
    }

    public Expressao getDireita() {
        return direita;
    }

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + esquerda + ", " + direita + ")";
	}
    
    
}
