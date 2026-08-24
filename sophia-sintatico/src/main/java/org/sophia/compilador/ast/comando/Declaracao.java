package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;

public class Declaracao extends Comando {

	private final TipoVariavel tipo;
    private final String nome;
    private final Expressao valor;
    private Expressao posicao;
    
    public Declaracao(String tipo, String nome, Expressao valor) {
        this.tipo = TipoVariavel.valueOf(tipo.toUpperCase());
        this.nome = nome;
        this.valor = valor;
    }
    
    public Declaracao(String tipo, String nome, Expressao valor, Expressao posicao) {
		super();
		this.tipo = TipoVariavel.valueOf(tipo.toUpperCase());
		this.nome = nome;
		this.valor = valor;
		this.posicao = posicao;
	}

	public TipoVariavel getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public Expressao getValor() {
        return valor;
    }
    
    public Expressao getPosicao() {
		return posicao;
	}

	@Override
    public String toString() {
        return "Declaracao " + tipo + " " + nome + " = " + valor;
    }
    
    @Override
    public String toTree(String prefixo) {
        return prefixo + "+-- " + this;
    }

}
