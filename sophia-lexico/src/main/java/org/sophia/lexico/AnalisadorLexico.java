package org.sophia.lexico;

import java.util.ArrayList;
import java.util.List;

public final class AnalisadorLexico {

    private final Vocabulario vocabulario = new Vocabulario();

    private final List<Simbolo> simbolos = new ArrayList<>();

    private String codigo;

    private int indice;

    private int linha;

    private int coluna;
    
    private int backupIndice;

    private int backupLinha;
    
    private int backupColuna;
    
    
    public List<Simbolo> analisar(String codigoFonte) {

        this.codigo = codigoFonte;
        this.indice = 0;
        this.linha = 1;
        this.coluna = 1;

        simbolos.clear();

        while (!fim()) {

            ignorarEspacos();

            if (fim()) {
                break;
            }

            char c = atual();

            if (c == '"') {
                simbolos.add(lerTexto());
            } else if (Character.isDigit(c)) {
                simbolos.add(lerNumero());
            } else if (Character.isLetter(c) || c == '_') {
                simbolos.add(lerPalavra());
            } else {
            	 throw erroLexico("Caractere inválido: '" + c + "'");
            }
        }

        simbolos.add(
                novoToken(
                        "FIM_DO_ARQUIVO",
                        CategoriaSimbolo.FIM_DO_ARQUIVO));

        for (Simbolo simbolo : simbolos) {
			System.out.println(simbolo);
		}
        
        return simbolos;
    }
    
    private RuntimeException erroLexico(String mensagem) {

        return new RuntimeException(
            "Erro léxico na linha "
            + linha
            + ", coluna "
            + coluna
            + ": "
            + mensagem);
    }
    
	private char atual() {
        return codigo.charAt(indice);
    }
    
    private boolean fim() {
        return indice >= codigo.length();
    }

    private void avancar() {

        if (atual() == '\n') {
            linha++;
            coluna = 1;
       } else {
            coluna++;
        }

        indice++;
    }
    
    private void ignorarEspacos() {

        while (!fim()) {

            char c = atual();

            if (c == ' '
                    || c == '\t'
                    || c == '\r'
                    || c == '\n') {

                avancar();

            } else {

                return;
            }
        }
    }
    
    private Simbolo novoToken(
            String texto,
            CategoriaSimbolo categoria) {

        Simbolo simbolo = new Simbolo();

        simbolo.setTexto(texto);
        simbolo.setCategoria(categoria);
        simbolo.setLinha(linha);
        simbolo.setColuna(coluna);

        return simbolo;
    }
    
    private Simbolo lerTexto() {

        int colunaInicial = coluna;

        avancar();

        StringBuilder sb = new StringBuilder();

        while (!fim() && atual() != '"') {

            sb.append(atual());

            avancar();
        }

        if (fim()) {

            throw new RuntimeException(
                    "Texto não finalizado.");
        }

        avancar();

        Simbolo simbolo = new Simbolo();

        simbolo.setTexto("\"" + sb + "\"");
        simbolo.setCategoria(CategoriaSimbolo.LITERAL_TEXTO);
        simbolo.setLinha(linha);
        simbolo.setColuna(colunaInicial);

        return simbolo;
    }
    
    
    private Simbolo lerPalavra() {

        int colunaInicial = coluna;

        StringBuilder sb = new StringBuilder();

        while (!fim()) {

            char c = atual();

            if (Character.isLetterOrDigit(c) || c == '_') {

                sb.append(c);
                avancar();

            } else {

                break;
            }
        }

        String palavra = sb.toString();

        return resolverPalavra(palavra, colunaInicial);
    }
    
    private Simbolo resolverPalavra(
            String palavra,
            int colunaInicial) {

        String texto = palavra;

        CategoriaSimbolo categoria;

        // Literais lógicos
        if (palavra.equalsIgnoreCase("verdadeiro") || palavra.equalsIgnoreCase("falso")) {
            categoria = CategoriaSimbolo.LITERAL_LOGICO;
        } else {

            String operador = tentarOperadorComposto(palavra);

            if (operador != null) {
                texto = operador;
                categoria = vocabulario.categoria(operador);

            } else {
                categoria = vocabulario.categoria(palavra.toUpperCase());

                if (categoria == null) {
                    categoria = CategoriaSimbolo.IDENTIFICADOR;
                }
            }
        }

        Simbolo simbolo = new Simbolo();

        simbolo.setTexto(texto);
        simbolo.setCategoria(categoria);
        simbolo.setLinha(linha);
        simbolo.setColuna(colunaInicial);

        return simbolo;
    }
    
    private String tentarOperadorComposto(String primeiraPalavra) {

        switch (primeiraPalavra.toUpperCase()) {

        	case "IGUAL":
        		return lerIgual();

        	case "MAIOR":
                return lerMaior();

            case "MENOR":
                return lerMenor();

            case "DIVIDIDO":
                return lerDivisao();

            case "RESTO":
                return lerResto();

            case "DIFERENTE":
                return lerDiferente();

            default:
                return null;
        }
    }
    
    private String lerIgual() {

    	salvarPosicao();

		ignorarEspacos();

        String palavra = proximaPalavra();

        if ("A".equalsIgnoreCase(palavra)) {

            return "IGUAL A";
        }

        restaurar();

        return null;
    
    }

	private String lerDiferente() {
    	
    	salvarPosicao();

		ignorarEspacos();

        String palavra = proximaPalavra();

        if ("DE".equalsIgnoreCase(palavra)) {

            return "DIFERENTE DE";
        }

        restaurar();

        return null;

	}

	private String lerResto() {
		
		salvarPosicao();
        
		ignorarEspacos();

        String palavra = proximaPalavra();

        if ("DE".equalsIgnoreCase(palavra)) {

            return "RESTO DE";
        }

        restaurar();

        return null;
	}

	private String lerDivisao() {
		
		salvarPosicao();

        ignorarEspacos();

        String palavra = proximaPalavra();

        if ("POR".equalsIgnoreCase(palavra)) {

            return "DIVIDIDO POR";
        }

        restaurar();

        return null;
    }

	private String lerMenor() {

		salvarPosicao();
		
        ignorarEspacos();

        String palavra = proximaPalavra();

        if ("QUE".equalsIgnoreCase(palavra)) {
            return "MENOR QUE";
        }

        if ("OU".equalsIgnoreCase(palavra)) {

            ignorarEspacos();

            palavra = proximaPalavra();

            if ("IGUAL".equalsIgnoreCase(palavra)) {

                ignorarEspacos();

                palavra = proximaPalavra();

                if ("A".equalsIgnoreCase(palavra)) {

                    return "MENOR OU IGUAL A";
                }
            }
        }

        restaurar();
        
        return null;

    
    }

	private String lerMaior() {

		salvarPosicao();
		
        ignorarEspacos();

        String palavra = proximaPalavra();

        if ("QUE".equalsIgnoreCase(palavra)) {
            return "MAIOR QUE";
        }

        if ("OU".equalsIgnoreCase(palavra)) {

            ignorarEspacos();

            palavra = proximaPalavra();

            if ("IGUAL".equalsIgnoreCase(palavra)) {

                ignorarEspacos();

                palavra = proximaPalavra();

                if ("A".equalsIgnoreCase(palavra)) {

                    return "MAIOR OU IGUAL A";
                }
            }
        }

        restaurar();
        
        return null;
    }
    
    private String proximaPalavra() {

        ignorarEspacos();

        StringBuilder sb = new StringBuilder();

        while (!fim()) {

            char c = atual();

            if (!Character.isLetter(c)) {
                break;
            }

            sb.append(c);

            avancar();
        }

        return sb.toString();
    }
    
    private Simbolo lerNumero() {

        int colunaInicial = coluna;

        StringBuilder sb = new StringBuilder();

        while (!fim()) {

            char c = atual();

            if (Character.isDigit(c) || c == '.') {

                sb.append(c);

                avancar();

            } else {

                break;
            }
        }

        Simbolo simbolo = new Simbolo();

        simbolo.setIndice(indice);
        simbolo.setTexto(sb.toString());
        simbolo.setCategoria(CategoriaSimbolo.LITERAL_NUMERO);
        simbolo.setLinha(linha);
        simbolo.setColuna(colunaInicial);

        return simbolo;
    }
    
    private void salvarPosicao() {

        backupIndice = indice;
        backupLinha = linha;
        backupColuna = coluna;
    }
    
    private void restaurar() {

        indice = backupIndice;
        linha = backupLinha;
        coluna = backupColuna;
    }
}