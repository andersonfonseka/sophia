package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;

public class Escreva extends Comando {

    private Expressao expressao;
    
	private Expressao posicao;
	
    public Escreva() {}

    public Escreva(Expressao expressao) {
        this.expressao = expressao;
    }

    public Expressao getExpressao() {
        return expressao;
    }
    
    public Expressao getPosicao() {
		return posicao;
	}

	public void setExpressao(Expressao expressao) {
        this.expressao = expressao;
    }
	
    public void setPosicao(Expressao posicao) {
		this.posicao = posicao;
	}

	@Override
    public String toString() {
        return "Escreva " + expressao + "";
    }

    @Override
    public String toTree(String prefixo) {
        return prefixo + "+-- " + this;
    }
    
}