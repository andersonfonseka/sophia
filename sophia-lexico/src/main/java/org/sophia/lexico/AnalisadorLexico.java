package org.sophia.lexico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.sophia.util.Sanitizador;

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

		codigoFonte = new Sanitizador().removerEspacosDentroDasAspas(codigoFonte);
		
		int linha = 1;
		int coluna = 1;

		Vocabulario vocabulario = new Vocabulario();
		String palavra = "";

		String[] linhas = codigoFonte.split("\\r?\\n");
		
		String texto = "";
		String textoAuxiliar = "";
		boolean lendoTexto = false;
		
		for (int i = 0; i < linhas.length; i++) {
			
			System.out.println(linhas[i]);
			
			if (linhas[i].trim().length() > 0) {

				String[] termos = linhas[i].split(" ");
				
				for (int j = 0; j < termos.length; j++) {
					
					if (termos[j].trim().length() > 0 && termos[j].substring(0, 1).contains("\"") && !lendoTexto) {
						lendoTexto = true;
						
						if (termos[j].lastIndexOf("\"") > 0) {
							texto = termos[j];
							textoAuxiliar = texto;	
						} else {
							texto = montarTexto(linhas[i], termos[j]);
							textoAuxiliar = texto;
						}
						
					}
					
					if (texto.trim().length() > 0 && lendoTexto) {
						adicionarSimbolo(linha, coluna, vocabulario, linhas[i], texto);	
						texto = "";
						lendoTexto = false;
					} else {
						if (!textoAuxiliar.contains(termos[j])) {
							adicionarSimbolo(linha, coluna, vocabulario, linhas[i], termos[j]);
							textoAuxiliar = "";
						}
					}
					
					coluna += termos[j].length(); 
				}
			}
			
			linha++;
			coluna = 1;
		}

		palavra = "FIM_DO_ARQUIVO";
		adicionarSimbolo(linha + 1, 1, vocabulario, "", palavra);

		simbolos.forEach(System.out::println);

		return simbolos;
	}
	
	private String montarTexto(String linha, String termo) {
		
		StringBuilder sb = new StringBuilder();
		
		String[] termos = linha.split(" "); 

		for (int i = 0; i < termos.length; i++) {
			
			if (termos[i].equals(termo)) {

				sb.append(termos[i] + " ");
				
				if (i < (termos.length-1)) {
					i++;
					
					while(i < termos.length && !termos[i].contains("\"")) {
						sb.append(termos[i] + " ");
						i++;
					}
					
					sb.append(termos[i]);
					
				}
			}
		}
		
		return sb.toString();
	}
	
	private boolean blnComentario = false;
	private StringBuilder sb = new StringBuilder();

	private void adicionarSimbolo(int linha, int coluna, Vocabulario vc, String linhaCodigo, String termo) {
				
		Simbolo simbolo = new Simbolo();

		
		if (termo.equalsIgnoreCase("COMENTARIO")) {
			blnComentario = !blnComentario;
		}
			
		if (termo.toUpperCase().contains("DIVIDIDO")) {
			simbolo.setTexto("DIVIDIDO POR");
		} else if (termo.toUpperCase().contains("RESTO")) {
			simbolo.setTexto("RESTO DE");
		} else if (termo.toUpperCase().contains("DIFERENTE")) {
			simbolo.setTexto("DIFERENTE DE");
		
		} else if (termo.toUpperCase().contains("OU")) {
			
			if (!linhaCodigo.toUpperCase().contains("OU IGUAL")) {
				simbolo.setTexto("OU");	
			}
		
		} else if (termo.toUpperCase().contains("MAIOR")) {
			
			if (linhaCodigo.toUpperCase().contains("MAIOR QUE") && (termo.length() == "MAIOR".length())) {
				simbolo.setTexto("MAIOR QUE");	
			} else if (linhaCodigo.toUpperCase().contains("MAIOR OU") && (termo.length() == "MAIOR".length())) {
				simbolo.setTexto("MAIOR OU IGUAL A");	
			} else {
				simbolo.setTexto(termo);
			}
		
		} else if (termo.toUpperCase().contains("MENOR")) {
			
			if (linhaCodigo.toUpperCase().contains("MENOR QUE") && (termo.length() == "MENOR".length())) {
				simbolo.setTexto("MENOR QUE");	
			} else if (linhaCodigo.toUpperCase().contains("MENOR OU") && (termo.length() == "MENOR".length())){
				simbolo.setTexto("MENOR OU IGUAL A");	
			} else {
				simbolo.setTexto(termo);
			}

		} else if (termo.toUpperCase().contains("IGUAL")) {
			
			if (!linhaCodigo.toUpperCase().contains("OU IGUAL A")) {
				simbolo.setTexto("IGUAL A");
			}
			
		} else {
			simbolo.setTexto(termo);
		}
		
		simbolo.setIndice(this.contador);

		simbolo.setLinha(linha);
		simbolo.setColuna(coluna);

		if (simbolo.getTexto() != null && simbolo.toString().trim().length() > 0
				&& !(simbolo.getTexto().equalsIgnoreCase("IGUAL"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("MAIOR"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("COMENTARIO"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("DIFERENTE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("RESTO"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("MENOR"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("POR"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("DE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("ATE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("QUE"))
				&& !(simbolo.getTexto().trim().equalsIgnoreCase("A"))) {
			
			
			if (!blnComentario) {
				simbolo.setCategoria(validarCategoria(vc, linhaCodigo, termo));
				this.simbolos.add(simbolo);
			}
			
			this.contador++;

		} 
		
		if (termo.trim().length() > 0) {
			this.palavras.add(termo);
		}
	}

	private CategoriaSimbolo validarCategoria(Vocabulario vc, String linhaCodigo, String termo) {

		
		CategoriaSimbolo cs = vc.categoria(termo.toUpperCase());

		if (cs == null || cs.equals(CategoriaSimbolo.IDENTIFICADOR)) {

			if (termo.toString().contains("\"")) {
				return CategoriaSimbolo.LITERAL_TEXTO;
			} else if (ehNumero(termo.toString())) {
				return CategoriaSimbolo.LITERAL_NUMERO;
			} else if (termo.toString().toUpperCase().equals("VERDADEIRO") || termo.toString().toUpperCase().equals("FALSO")) {
				return CategoriaSimbolo.LITERAL_LOGICO;
			} else if (termo.toUpperCase().contains("DIVIDIDO")) {
				cs = vc.categoria("DIVIDIDO POR");
			} else if (termo.toUpperCase().contains("RESTO")) {
				cs = vc.categoria("RESTO DE");
			} else if (termo.toUpperCase().contains("DIFERENTE")) {
				cs = vc.categoria("DIFERENTE DE");
				
			} else if (termo.toUpperCase().contains("MAIOR")) {
				
				if (linhaCodigo.toUpperCase().contains("MAIOR QUE") && (termo.length() == "MAIOR".length())) {
					cs = vc.categoria("MAIOR QUE");	
				} else if (linhaCodigo.toUpperCase().contains("MAIOR OU") && (termo.length() == "MAIOR".length())) {
					cs = vc.categoria("MAIOR OU IGUAL A");	
				} 
			
			} else if (termo.toUpperCase().contains("MENOR")) {
					
				if (linhaCodigo.toUpperCase().contains("MENOR QUE") && (termo.length() == "MENOR".length())) {
					cs = vc.categoria("MENOR QUE");
				} else if (linhaCodigo.toUpperCase().contains("MENOR OU") && (termo.length() == "MENOR".length())){
					cs = vc.categoria("MENOR OU IGUAL A");
				}

			} else if (termo.toUpperCase().contains("IGUAL")) {
				
				if (!linhaCodigo.toUpperCase().contains("OU IGUAL A")) {
					cs = vc.categoria("IGUAL A");
				}
			
			} else if (termo.toUpperCase().equals("E")) {
					cs = vc.categoria("E");
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
