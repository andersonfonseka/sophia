package org.sophia.lexico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

	private String codigoFonte;

	private int contador = 0;

	private List<Simbolo> simbolos;
	
	private List<String> palavras;

	private int posicao;

	private int linha;

	private int coluna;

	public AnalisadorLexico() {

		this.simbolos = new ArrayList<>();
		
		this.palavras = new ArrayList<>();

		this.posicao = 0;

		this.linha = 1;

		this.coluna = 1;

	}

	public List<Simbolo> analisar(String codigoFonte) {

		int linha = 1;
		int coluna = 1;
		int colunaInicio = 1;

		boolean lendoTexto = false;

		Vocabulario vocabulario = new Vocabulario();

		StringBuilder palavra = new StringBuilder();

		for (int i = 0; i < codigoFonte.length(); i++) {

			char caractere = codigoFonte.charAt(i);

			/*
			 * =========================== ESTADO: LENDO TEXTO ===========================
			 */
			if (lendoTexto) {

				palavra.append(caractere);

				if (caractere == '"') {

					adicionarSimbolo(linha, colunaInicio, vocabulario, palavra);

					palavra.setLength(0);

					lendoTexto = false;
				}

			}
			/*
			 * =========================== INÍCIO DE UM TEXTO ===========================
			 */
			else if (caractere == '"') {

				if (!palavra.isEmpty()) {
					adicionarSimbolo(linha, colunaInicio, vocabulario, palavra);
					palavra.setLength(0);
				}

				lendoTexto = true;
				colunaInicio = coluna;

				palavra.append(caractere); // guarda a aspas inicial

			}
			/*
			 * =========================== ESPAÇOS ===========================
			 */
			else if (Character.isWhitespace(caractere)) {

				if (!palavra.isEmpty()) {

					adicionarSimbolo(linha, colunaInicio, vocabulario, palavra);

					palavra.setLength(0);
				}

			}
			/*
			 * =========================== PALAVRA NORMAL ===========================
			 */
			else {

				if (palavra.isEmpty()) {
					colunaInicio = coluna;
				}

				palavra.append(caractere);

			}

			/*
			 * Atualiza posição
			 */
			if (caractere == '\n') {

				linha++;
				coluna = 1;

			} else {

				coluna++;

			}
		}

		/*
		 * Último token
		 */
		if (!palavra.isEmpty()) {
			adicionarSimbolo(linha, colunaInicio, vocabulario, palavra);
		}

		/*
		 * Fim do arquivo
		 */
		palavra.setLength(0);
		palavra.append("FIM_DO_ARQUIVO");
		adicionarSimbolo(linha + 1, 1, vocabulario, palavra);

		simbolos.forEach(System.out::println);

		return simbolos;
	}

	private void adicionarSimbolo(int linha, int coluna, Vocabulario vc, StringBuilder palavra) {

		Simbolo simbolo = new Simbolo();

		simbolo.setIndice(this.contador);
		simbolo.setCategoria(validarCategoria(vc, palavra.toString()));

		String smb0 = null;
		String smb = null;
		
		if (palavras.size() > 1) {
			smb0 = palavras.get(palavras.size()-2);
		}
		
		if (palavras.size() > 0) {
			smb = palavras.get(palavras.size()-1);
		}
		
		if (palavra.toString().trim().toUpperCase().equals("DIVIDIDO")) {
			simbolo.setTexto("DIVIDIDO POR");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("DE") && smb.equalsIgnoreCase("RESTO")) {
			simbolo.setTexto("RESTO DE");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("DE") && smb.equalsIgnoreCase("DIFERENTE")) {
			simbolo.setTexto("DIFERENTE DE");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("A") && !smb0.equalsIgnoreCase("OU") && smb.equalsIgnoreCase("IGUAL")) {
			simbolo.setTexto("IGUAL A");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("QUE") && smb.equalsIgnoreCase("MAIOR")) {
			simbolo.setTexto("MAIOR QUE");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("QUE") && smb.equalsIgnoreCase("MENOR")) {
			simbolo.setTexto("MENOR QUE");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("OU") && smb.equalsIgnoreCase("MAIOR")) {
			simbolo.setTexto("MAIOR OU IGUAL A");
		} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("OU") && smb.equalsIgnoreCase("MENOR")) {
			simbolo.setTexto("MENOR OU IGUAL A");
		} else {
			simbolo.setTexto(palavra.toString());
		}

		simbolo.setLinha(linha);
		simbolo.setColuna(coluna);

		if (palavra.toString().trim().length() > 0
				&& !(simbolo.getTexto().equalsIgnoreCase("IGUAL"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("MAIOR"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("DIFERENTE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("RESTO"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("MENOR"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("POR"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("DE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("ATE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("A"))) {
			
			this.simbolos.add(simbolo);
			this.contador++;
		}
		
		if (palavra.toString().trim().length() > 0) {
			this.palavras.add(palavra.toString());
		}
	}

	private CategoriaSimbolo validarCategoria(Vocabulario vc, String palavra) {

		String smb0 = null;
		String smb = null;
		
		if (palavras.size() > 1) {
			smb0 = palavras.get(palavras.size()-2);
		}
		
		if (palavras.size() > 0) {
			smb = palavras.get(palavras.size()-1);
		}
		
		CategoriaSimbolo cs = vc.categoria(palavra.toUpperCase());

		if (cs == null || cs.equals(CategoriaSimbolo.IDENTIFICADOR)) {

			if (palavra.toString().contains("\"")) {
				return CategoriaSimbolo.LITERAL_TEXTO;
			} else if (ehNumero(palavra.toString())) {
				return CategoriaSimbolo.LITERAL_NUMERO;
			} else if (palavra.toString().toUpperCase().equals("VERDADEIRO")
					|| palavra.toString().toUpperCase().equals("FALSO")) {
				return CategoriaSimbolo.LITERAL_LOGICO;
			} else if (palavra.toString().toUpperCase().equals("DIVIDIDO")) {
				cs = vc.categoria("DIVIDIDO POR");
			} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("DE") && smb.equalsIgnoreCase("RESTO")) {
				cs = vc.categoria("RESTO DE");
			} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("DE") && smb.equalsIgnoreCase("DIFERENTE")) {
				cs = vc.categoria("DIFERENTE DE");
			} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("A") && !smb0.equalsIgnoreCase("OU") && smb.equalsIgnoreCase("IGUAL")) {
				cs = vc.categoria("IGUAL A");
			} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("QUE") && smb.equalsIgnoreCase("MAIOR")) {
				cs = vc.categoria("MAIOR QUE");
			} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("QUE") && smb.equalsIgnoreCase("MENOR")) {
				cs = vc.categoria("MENOR QUE");
			}else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("OU") && smb.equalsIgnoreCase("MAIOR")) {
				cs = vc.categoria("MAIOR OU IGUAL A");
			} else if (palavra.toString().trim().toUpperCase().equalsIgnoreCase("OU") && smb.equalsIgnoreCase("MENOR")) {
				cs = vc.categoria("MENOR OU IGUAL A");
			}
		}

		return cs;
	}

	private boolean ehNumero(String texto) {

		try {
			new BigDecimal(texto);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}
}
