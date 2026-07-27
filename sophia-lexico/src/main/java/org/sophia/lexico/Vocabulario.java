package org.sophia.lexico;

import java.util.HashMap;
import java.util.Map;

public class Vocabulario {

	private final Map<String, CategoriaSimbolo> palavras = new HashMap<>();

	public Vocabulario() {

		palavras.put("PROGRAMA", CategoriaSimbolo.ESTRUTURA);
		palavras.put("INICIO", CategoriaSimbolo.ESTRUTURA);
		palavras.put("FIM", CategoriaSimbolo.ESTRUTURA);

		palavras.put("NUMERO", CategoriaSimbolo.TIPO);
		palavras.put("TEXTO", CategoriaSimbolo.TIPO);
		palavras.put("LOGICO", CategoriaSimbolo.TIPO);
		palavras.put("DATA", CategoriaSimbolo.TIPO);
		palavras.put("HORA", CategoriaSimbolo.TIPO);
		palavras.put("LISTA", CategoriaSimbolo.TIPO);

		palavras.put("ESCREVA", CategoriaSimbolo.COMANDO);
		palavras.put("LEIA", CategoriaSimbolo.COMANDO);

		palavras.put("RECEBE", CategoriaSimbolo.COMANDO);
		
		palavras.put("IGUAL A", CategoriaSimbolo.COMPARACAO);
		palavras.put("DIFERENTE DE", CategoriaSimbolo.COMPARACAO);
		palavras.put("MAIOR QUE", CategoriaSimbolo.COMPARACAO);
		palavras.put("MAIOR OU IGUAL A", CategoriaSimbolo.COMPARACAO);
		palavras.put("MENOR QUE", CategoriaSimbolo.COMPARACAO);
		palavras.put("MENOR OU IGUAL A", CategoriaSimbolo.COMPARACAO);
		
		palavras.put("MAIS", CategoriaSimbolo.OPERADOR);
		palavras.put("MENOS", CategoriaSimbolo.OPERADOR);
		palavras.put("VEZES", CategoriaSimbolo.OPERADOR);
		palavras.put("VEZ", CategoriaSimbolo.OPERADOR);
		palavras.put("DIVIDIDO POR", CategoriaSimbolo.OPERADOR);
		palavras.put("RESTO DE", CategoriaSimbolo.OPERADOR);

		palavras.put("E", CategoriaSimbolo.OPERADOR);
		palavras.put("OU", CategoriaSimbolo.OPERADOR);
		palavras.put("NAO", CategoriaSimbolo.OPERADOR);

		palavras.put("SE", CategoriaSimbolo.COMANDO);
		palavras.put("ENTAO", CategoriaSimbolo.COMANDO);
		palavras.put("SENAO", CategoriaSimbolo.COMANDO);
		palavras.put("PARA", CategoriaSimbolo.COMANDO);
		palavras.put("ENQUANTO", CategoriaSimbolo.COMANDO);
		palavras.put("REPITA", CategoriaSimbolo.COMANDO);

		palavras.put("FUNCAO", CategoriaSimbolo.COMANDO);
		palavras.put("RETORNE", CategoriaSimbolo.COMANDO);
		
		palavras.put("FIM_DO_ARQUIVO", CategoriaSimbolo.FIM_DO_ARQUIVO);

	}

	public boolean existe(String palavra) {
		return palavras.containsKey(palavra.toUpperCase());
	}

	public CategoriaSimbolo categoria(String palavra) {
		if (palavras.containsKey(palavra.toUpperCase())) {
			return palavras.get(palavra.toUpperCase());
		} else {
			return CategoriaSimbolo.IDENTIFICADOR;
		}
	}

}
