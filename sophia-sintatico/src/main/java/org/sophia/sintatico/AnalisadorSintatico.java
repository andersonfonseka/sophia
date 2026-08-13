package org.sophia.sintatico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.Programa;
import org.sophia.compilador.ast.comando.Atribuicao;
import org.sophia.compilador.ast.comando.Comando;
import org.sophia.compilador.ast.comando.Declaracao;
import org.sophia.compilador.ast.comando.E;
import org.sophia.compilador.ast.comando.Enquanto;
import org.sophia.compilador.ast.comando.Escreva;
import org.sophia.compilador.ast.comando.Leia;
import org.sophia.compilador.ast.comando.Nao;
import org.sophia.compilador.ast.comando.Ou;
import org.sophia.compilador.ast.comando.Para;
import org.sophia.compilador.ast.comando.Retorne;
import org.sophia.compilador.ast.comando.Se;
import org.sophia.compilador.ast.comando.TipoVariavel;
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
import org.sophia.compilador.ast.funcao.ChamadaFuncao;
import org.sophia.compilador.ast.funcao.ChamadaFuncaoExpressao;
import org.sophia.compilador.ast.funcao.Funcao;
import org.sophia.compilador.ast.funcao.Parametro;
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
	
	private Programa programa;

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

		if (this.programa == null) {
		    this.programa = new Programa();
		}
		
		while (proximoEh("FUNCAO")) {
		    programa.adicionarFuncao(funcao());
		}

		consumir("programa");

		String titulo = atual().getTexto();

		consumir(CategoriaSimbolo.LITERAL_TEXTO);

		consumir("inicio");

		programa.setTitulo(titulo);

		while (!textoAtual().equals("fim")) {
			programa.adicionarComando(comando());
		}

		consumir("fim");

		return programa;

	}

	private Funcao funcao() {

		consumir("FUNCAO");

	    String nome = atual().getTexto();
	    consumir(CategoriaSimbolo.IDENTIFICADOR);

	    Funcao funcao = new Funcao(nome);

	    while (proximoEh("PARAMETRO")) {

	        Parametro parametro = parametro();

	        funcao.adicionarParametro(parametro);
	    }
	    
	    if (proximoEh("RETORNO")) {

	        consumir("RETORNO");

	        String tipo = atual().getTexto();
	        consumir(CategoriaSimbolo.TIPO);

	        funcao.setTipoRetorno(TipoVariavel.valueOf(tipo.toUpperCase()));
	    }
	    

	    consumir("INICIO");

	    while (!proximoEh("FIM")) {
	        funcao.adicionarComando(comando());
	    }

	    consumir("FIM");
	    
	    boolean existeRetorno = false;
	    
	    if (funcao.getTipoRetorno() != null) {
	    	
	    	for (Comando comando : funcao.getComandos()) {
			
	    		existeRetorno = verificaRetornoFuncao(funcao, existeRetorno, comando);
				
				if (comando instanceof Se) {
					Se comandoSe = (Se) comando;
					
					for (Comando com : comandoSe.getComandosFalsos()) {
						existeRetorno = verificaRetornoFuncao(funcao, existeRetorno, com);
					}
					
					for (Comando com : comandoSe.getComandosVerdadeiros()) {
						existeRetorno = verificaRetornoFuncao(funcao, existeRetorno, com);
					}
				}
			}
	    	
	    	if (!existeRetorno) {
	    		throw new RuntimeException("A função " + funcao.getNome() + " deve retornar um valor do tipo " + funcao.getTipoRetorno() + ".");
	    	}
	    }
	    

	    return funcao;
	    
	}

	private boolean verificaRetornoFuncao(Funcao funcao, boolean existeRetorno, Comando comando) {
		if (comando instanceof Retorne) {
			Retorne ret = (Retorne) comando;
			
			existeRetorno = true;
			
			if (ret.getExpressao() instanceof LiteralTexto && 
					!funcao.getTipoRetorno().toString().equals("TEXTO")) {
				throw new RuntimeException("A função " + funcao.getNome() + " deve retornar um valor do tipo " + funcao.getTipoRetorno() + ".");
			}
			
			if (ret.getExpressao() instanceof LiteralNumero && 
					!funcao.getTipoRetorno().toString().equals("NUMERO")) {
				throw new RuntimeException("A função " + funcao.getNome() + " deve retornar um valor do tipo " + funcao.getTipoRetorno() + ".");
			}
			
			if (ret.getExpressao() instanceof LiteralLogico && 
					!funcao.getTipoRetorno().toString().equals("LOGICO")) {
				throw new RuntimeException("A função " + funcao.getNome() + " deve retornar um valor do tipo " + funcao.getTipoRetorno() + ".");
			}
		}
		return existeRetorno;
	}
	
	private Parametro parametro() {

	    consumir("PARAMETRO");

	    String tipo = atual().getTexto();
	    consumir(CategoriaSimbolo.TIPO);

	    String nome = atual().getTexto();
	    consumir(CategoriaSimbolo.IDENTIFICADOR);

	    return new Parametro(tipo, nome);
	}

	private Comando comando() {
	
		while ((categoriaAtual() == CategoriaSimbolo.COMENTARIO)) {
			avancar();
		}
		
		if (proximoEh("ENQUANTO")) {
		    return enquanto();
		}
		
		if (proximoEh("PARA")) {
		    return para();
		}
		
		if (textoAtual().equalsIgnoreCase("se")) {
		    return se();
		}

		if (textoAtual().equals("escreva")) {
			return escreva();
		}
		
		if (textoAtual().equalsIgnoreCase("retorne")) {
		    return retorne();
		}

		if (categoriaAtual() == CategoriaSimbolo.TIPO) {
			return declaracao();
		}

		if (categoriaAtual() == CategoriaSimbolo.IDENTIFICADOR) {
			
			if (proximo().getCategoria().equals(CategoriaSimbolo.COMANDO) && 
					proximo().getTexto().equalsIgnoreCase("RECEBE")){
		        return atribuicao();
			}
		        
	    	return chamadaFuncao();
		}
		
		 if (proximoEh("LEIA")) {
		    return leia();
		 }
	
		throw erro("Comando desconhecido.");
	}
	
	private Retorne retorne() {

	    consumir("RETORNE");
	    Expressao expressao = expressao();
	    return new Retorne(expressao);
	}
	
	private ChamadaFuncao chamadaFuncao() {

	    String nome = atual().getTexto();

	    consumir(CategoriaSimbolo.IDENTIFICADOR);

	    Funcao funcao = this.programa.getFuncoes().get(nome);

	    if (funcao == null) {
	        throw new RuntimeException("A função" +nome  + " não foi declarada.");
	    }

	    ChamadaFuncao chamada = new ChamadaFuncao(nome);

	    for (Parametro parametro : funcao.getParametros()) {
	        Expressao argumento = expressao();
	        chamada.adicionarArgumento(argumento);
	    }

	    return chamada;
	}
	
	private Leia leia() {

		consumir("LEIA");
	    String identificador = consumirIdentificador();

	    return new Leia(identificador);
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
	
	private Enquanto enquanto() {

	    consumir("ENQUANTO");

	    Expressao condicao = expressao();

	    List<Comando> comandos = new ArrayList<>();

	    while (!proximoEh("FIM")) {
	        comandos.add(comando());
	    }

	    consumir("FIM");

	    return new Enquanto(condicao, comandos);
	}
	
	private Para para() {

	    consumir("PARA");

	    String variavel = consumirIdentificador();

	    consumir("DE");

	    Expressao inicio = expressao();

	    consumir("ATE");

	    Expressao fim = expressao();

	    List<Comando> comandos = new ArrayList<>();

	    while (!proximoEh("FIM")) {
	        comandos.add(comando());
	    }

	    consumir("FIM");
	    return new Para(variavel, inicio, fim, comandos);
	}

	private String consumirIdentificador() {
		
		Simbolo simbolo = atual();

	    if (simbolo.getCategoria() != CategoriaSimbolo.IDENTIFICADOR) {
	        erro("Esperado um identificador.");
	    }

	    avancar();

	    return simbolo.getTexto();
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

		while (proximoEh("VEZES")
			    || proximoEh("DIVIDIDO POR")
			    || proximoEh("RESTO DE")) {

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
		    
			if (pareceChamadaFuncao()) {
		        return chamadaFuncaoExpressao();
		    }

		    avancar();
		    return new Identificador(simbolo.getTexto());

		case ESTRUTURA:
			avancar();
			return null;
			
		default:

			throw erro("Expressão inválida.");

		}

	}

	private ChamadaFuncaoExpressao chamadaFuncaoExpressao() {

	    String nome = atual().getTexto();

	    consumir(CategoriaSimbolo.IDENTIFICADOR);

	    Funcao funcao = this.programa.getFuncoes().get(nome);
	    
	    if (funcao == null) {
	        throw new RuntimeException("A função " + nome  + " não foi declarada.");
	    }

	    ChamadaFuncaoExpressao chamada = new ChamadaFuncaoExpressao(nome);

	    for (Parametro parametro : funcao.getParametros()) {
	        Expressao argumento = expressao();
	        
	        if (argumento != null) {
	        	chamada.adicionarArgumento(argumento);	
	        }
	    }
	    
	    if (funcao.getParametros().size() != chamada.getArgumentos().size()) {
	    	throw new RuntimeException("A função " + funcao.getNome() + " espera " + funcao.getParametros().size() +  " argumentos, mas recebeu "  + chamada.getArgumentos().size() + ".");
	    }

	    return chamada;
	}

	private boolean pareceChamadaFuncao() {

	    if (categoriaAtual() != CategoriaSimbolo.IDENTIFICADOR) {
	        return false;
	    }

	    if (estaNoFim()) {
	        return false;
	    }

	    Simbolo atual = atual();
	    Simbolo proximo = olhar(1);

	    if (proximo == null) {
	        return false;
	    }

	    // Atribuição nunca é chamada de função.
	    if (proximo.getTexto().equalsIgnoreCase("recebe")) {
	        return false;
	    }

	    // Um argumento de chamada deve estar na mesma linha.
	    if (atual.getLinha() != proximo.getLinha()) {
	        return false;
	    }

	    return switch (proximo.getCategoria()) {

	        case IDENTIFICADOR,
	             LITERAL_NUMERO,
	             LITERAL_TEXTO,
	             LITERAL_LOGICO -> true;

	        default -> false;
	    };
	}
	
	private Simbolo olhar(int deslocamento) {
	    int indice = this.indice + deslocamento;

	    if (indice >= simbolos.size()) {
	        return null;
	    }

	    return simbolos.get(indice);
	}

	private boolean estaNoFim() {
	    return categoriaAtual() == CategoriaSimbolo.FIM_DO_ARQUIVO;
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
			
		case RESTO_DE:
			return new Resto(esquerda, direita);

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
	
	private Simbolo proximo() {
		return simbolos.get(indice+1);
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