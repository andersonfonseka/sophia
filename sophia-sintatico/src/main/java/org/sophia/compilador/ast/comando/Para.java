package org.sophia.compilador.ast.comando;

import java.util.List;

import org.sophia.compilador.ast.Expressao;

public class Para extends Comando {

    private final String variavel;
    private final Expressao inicio;
    private final Expressao fim;
    private final List<Comando> comandos;
    
	public Para(String variavel, Expressao inicio, Expressao fim, List<Comando> comandos) {
		super();
		this.variavel = variavel;
		this.inicio = inicio;
		this.fim = fim;
		this.comandos = comandos;
	}

	public String getVariavel() {
		return variavel;
	}

	public Expressao getInicio() {
		return inicio;
	}

	public Expressao getFim() {
		return fim;
	}

	public List<Comando> getComandos() {
		return comandos;
	}

	@Override
	public String toTree(String prefixo) {

	    StringBuilder sb = new StringBuilder();

	    sb.append(prefixo).append("+-- Para\n");

	    sb.append(prefixo).append("|  +-- Variavel\n");
	    sb.append(prefixo).append("|  |   +-- ").append(variavel).append("\n");

	    sb.append(prefixo).append("|  +-- De\n");
	    sb.append(prefixo).append("|  |   +-- ").append(inicio).append("\n");

	    sb.append(prefixo).append("|  +-- Ate\n");
	    sb.append(prefixo).append("|  |   +-- ").append(fim).append("\n");

	    sb.append(prefixo).append("|  +-- Faca\n");

	    for (Comando comando : comandos) {
	        sb.append(comando.toTree(prefixo + "|  |  "));
	    }

	    return sb.toString();
	}

}