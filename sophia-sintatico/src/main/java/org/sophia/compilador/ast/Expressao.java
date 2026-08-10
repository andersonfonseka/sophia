package org.sophia.compilador.ast;

public abstract class Expressao {
	
    private String valor;
    
    public Expressao() {}
    
	public Expressao(String valor) {
		super();
		this.valor = valor;
	}

	public Object getValor() {
        return valor;
    }

}
