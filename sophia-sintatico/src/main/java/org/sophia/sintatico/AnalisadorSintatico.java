package org.sophia.sintatico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.Programa;
import org.sophia.compilador.ast.comando.Atribuicao;
import org.sophia.compilador.ast.comando.Comando;
import org.sophia.compilador.ast.comando.Declaracao;
import org.sophia.compilador.ast.comando.E;
import org.sophia.compilador.ast.comando.Escreva;
import org.sophia.compilador.ast.comando.Nao;
import org.sophia.compilador.ast.comando.Ou;
import org.sophia.compilador.ast.comando.Se;
import org.sophia.compilador.ast.comparador.Diferente;
import org.sophia.compilador.ast.comparador.Igual;
import org.sophia.compilador.ast.comparador.Maior;
import org.sophia.compilador.ast.comparador.MaiorOuIgual;
import org.sophia.compilador.ast.comparador.Menor;
import org.sophia.compilador.ast.comparador.MenorOuIgual;
import org.sophia.compilador.ast.expressao.Identificador;
import org.sophia.compilador.ast.expressao.LiteralLogico;
import org.sophia.compilador.ast.expressao.LiteralNumero;
import org.sophia.compilador.ast.expressao.LiteralTexto;
import org.sophia.compilador.ast.expressao.Resto;
import org.sophia.compilador.ast.operador.Divisao;
import org.sophia.compilador.ast.operador.Multiplicacao;
import org.sophia.compilador.ast.operador.Operador;
import org.sophia.compilador.ast.operador.Soma;
import org.sophia.compilador.ast.operador.Subtracao;
import org.sophia.lexico.CategoriaSimbolo;
import org.sophia.lexico.Simbolo;

public class AnalisadorSintatico {

	private final List<Simbolo> simbolos;

	private int indice;

	public AnalisadorSintatico(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
		this.indice = 0;
	}

	public Programa analisar() {

		Programa programa = programa();
		consumir(CategoriaSimbolo.FIM_DO_ARQUIVO);
		return programa;
	}

	private Programa programa() {

		consumir("programa");

		String titulo = atual().getTexto();

		consumir(CategoriaSimbolo.LITERAL_TEXTO);

		consumir("inicio");

		Programa programa = new Programa();

		programa.setTitulo(titulo);

		while (!textoAtual().equals("fim")) {
			programa.adicionarComando(comando());
		}

		consumir("fim");

		return programa;

	}

	private Comando comando() {
		
		if (textoAtual().equalsIgnoreCase("se")) {
		    return se();
		}

		if (textoAtual().equals("escreva")) {
			return escreva();
		}

		if (categoriaAtual() == CategoriaSimbolo.TIPO) {
			return declaracao();
		}

		if (categoriaAtual() == CategoriaSimbolo.IDENTIFICADOR) {
			return atribuicao();
		}

		throw erro("Comando desconhecido.");
	}

	private Declaracao declaracao() {

		String tipo = atual().getTexto();

		avancar();

		String nome = atual().getTexto();

		consumir(CategoriaSimbolo.IDENTIFICADOR);

		consumir("recebe");

		Expressao valor = expressao();

		return new Declaracao(tipo, nome, valor);

	}

	private Escreva escreva() {

		consumir("escreva");
		Expressao expressao = expressao();
		return new Escreva(expressao);

	}

	private Atribuicao atribuicao() {

		Identificador identificador = new Identificador(atual().getTexto());

		consumir(CategoriaSimbolo.IDENTIFICADOR);

		consumir("recebe");

		Expressao expressao = expressao();

		return new Atribuicao(identificador, expressao);
	}
	
	private Se se() {

	    consumir("SE");

	    Expressao condicao = expressao();

	    List<Comando> comandosVerdadeiros = new ArrayList<>();

	    while (!proximoEh("SENAO") && !proximoEh("FIM")) {
	        comandosVerdadeiros.add(comando());
	    }

	    List<Comando> comandosFalsos = new ArrayList<>();

	    if (proximoEh("SENAO")) {
	        avancar();
	        while (!proximoEh("FIM")) {
	            comandosFalsos.add(comando());
	        }
	    }

	    consumir("FIM");
	    return new Se(condicao, comandosVerdadeiros, comandosFalsos);
	}
	
	
	private Expressao e() {

	    Expressao esquerda = nao();

	    while (textoAtualEh("E")) {
	        consumir("E");
	        Expressao direita = nao();
	        esquerda = new E(esquerda, direita);
	    }

	    return esquerda;
	}
	
	private Expressao ou() {

	    Expressao esquerda = e();

	    while (textoAtualEh("OU")) {

	        consumir("OU");

	        Expressao direita = e();

	        esquerda = new Ou(esquerda, direita);
	    }

	    return esquerda;
	}
	
	private Expressao nao() {

	    if (textoAtualEh("NAO")) {
	        consumir("NAO");
	        return new Nao(nao());
	    }

	    return comparacao();
	}

	private Expressao expressao() {
		return ou();
	}

	private Expressao comparacao() {

		Expressao esquerda = adicao();

		while (textoAtualEh("IGUAL A")) {

			avancar();
			Expressao direita = adicao();
			esquerda = new Igual(esquerda, direita);
		}
		
		while (textoAtualEh("DIFERENTE DE")) {

			avancar();
			Expressao direita = adicao();
			esquerda = new Diferente(esquerda, direita);
		}
		
		while (textoAtualEh("MAIOR QUE")) {

			avancar();
			Expressao direita = adicao();
			esquerda = new Maior(esquerda, direita);
		}
		
		while (textoAtualEh("MAIOR OU IGUAL A")) {

			avancar();
			Expressao direita = adicao();
			esquerda = new MaiorOuIgual(esquerda, direita);
		}

		
		while (textoAtualEh("MENOR QUE")) {

			avancar();
			Expressao direita = adicao();
			esquerda = new Menor(esquerda, direita);
		}
		
		while (textoAtualEh("MENOR OU IGUAL A")) {

			avancar();
			Expressao direita = adicao();
			esquerda = new MenorOuIgual(esquerda, direita);
		}


		return esquerda;
	}

	private Expressao adicao() {

		Expressao esquerda = multiplicacao();

		while (proximoEh("mais") || proximoEh("menos")) {

			String operador = atual().getTexto();

			avancar();

			Expressao direita = multiplicacao();

			esquerda = criarOperacao(operador, esquerda, direita);

		}

		return esquerda;

	}

	private Expressao multiplicacao() {

		Expressao esquerda = primario();

		while (proximoEh("vez") || proximoEh("vezes") || proximoEh("DIVIDIDO POR")) {

			String operador = atual().getTexto();

			avancar();

			Expressao direita = primario();

			esquerda = criarOperacao(operador, esquerda, direita);
		}

		return esquerda;

	}

	private Expressao primario() {

		Simbolo simbolo = atual();

		if (proximoEh("RESTO DE")) {
			return resto();
		}

		switch (categoriaAtual()) {

		case LITERAL_NUMERO:

			avancar();

			return new LiteralNumero(new BigDecimal(simbolo.getTexto()));

		case LITERAL_TEXTO:

			avancar();

			return new LiteralTexto(simbolo.getTexto());

		case LITERAL_LOGICO:
			avancar();
			return new LiteralLogico(simbolo.getTexto().equalsIgnoreCase("verdadeiro"));

		case IDENTIFICADOR:
			avancar();
			return new Identificador(simbolo.getTexto());

		default:

			throw erro("Expressão inválida.");

		}

	}

	private Expressao resto() {

		consumir("RESTO DE");

		Expressao dividendo = primario();

		consumir("DIVIDIDO POR");

		Expressao divisor = primario();

		return new Resto(dividendo, divisor);

	}

	private Expressao criarOperacao(String operador, Expressao esquerda, Expressao direita) {

		Operador op = Operador.fromTexto(operador);

		switch (op) {

		case MAIS:
			return new Soma(esquerda, direita);

		case MENOS:
			return new Subtracao(esquerda, direita);

		case VEZ:
			return new Multiplicacao(esquerda, direita);

		case VEZES:
			return new Multiplicacao(esquerda, direita);

		case DIVIDIDO_POR:
			return new Divisao(esquerda, direita);

		default:
			throw erro("Operador '" + operador + "' não implementado.");
		}
	}

	private boolean textoAtualEh(String texto) {
	    return textoAtual().equalsIgnoreCase(texto);
	}
	
	private boolean proximoEh(String texto) {
		return atual().getTexto().equalsIgnoreCase(texto);
	}
	
	private boolean proximoEh(CategoriaSimbolo categoria) {
		return atual().getCategoria() == categoria;
	}

	private void consumir(String textoEsperado) {
		if (!textoAtual().equalsIgnoreCase(textoEsperado)) {
			throw erro("Esperava '" + textoEsperado + "'.");
		}
		avancar();
	}

	private void consumir(CategoriaSimbolo categoriaEsperada) {
		if (categoriaAtual() != categoriaEsperada) {
			throw erro("Esperava " + categoriaEsperada + ".");
		}
		avancar();
	}

	private void avancar() {
		indice++;
	}

	private Simbolo atual() {
		return simbolos.get(indice);
	}

	private String textoAtual() {
		return atual().getTexto();
	}

	private CategoriaSimbolo categoriaAtual() {
		return atual().getCategoria();
	}

	private RuntimeException erro(String mensagem) {
		Simbolo simbolo = atual();

		return new RuntimeException(
				"Erro sintático na linha " + simbolo.getLinha() + ", coluna " + simbolo.getColuna() + ": " + mensagem);
	}

}