package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;

public class Declaracao extends Comando {

	private final TipoVariavel tipo;
    private final String nome;
    private final Expressao valor;

    public Declaracao(String tipo, String nome, Expressao valor) {
        this.tipo = TipoVariavel.valueOf(tipo.toUpperCase());
        this.nome = nome;
        this.valor = valor;
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

    @Override
    public String toString() {
        return "Declaracao " + tipo + " " + nome + " = " + valor;
    }
    
    @Override
    public String toTree(String prefixo) {
        return prefixo + "+-- " + this;
    }

}
