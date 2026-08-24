package org.sophia.runtime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import org.sophia.compilador.ast.operador.Soma;
import org.sophia.compilador.ast.operador.Subtracao;

public class Interpretador {

	private ContextoExecucao contexto = new ContextoExecucao();

	private final EntradaSaida io;

	private Programa programa;
	
	public Interpretador(Programa programa, EntradaSaida io) {
	    this.programa = programa;
	    this.io = io;
	}

	public void executar() {
		
		this.contexto.limpar();

		for (Comando comando : programa.getComandos()) {
			executar(comando);
		}
	}
	
	public void executarREPL(Programa programa) {

		this.programa = programa;

		for (Comando comando : programa.getComandos()) {
			executar(comando);
		}
	}


	private void executar(Comando comando) {

		if (comando instanceof Leia) {
			executarLeia((Leia) comando);
			return;
		}

		if (comando instanceof Declaracao) {
			executarDeclaracao((Declaracao) comando);
			return;
		}

		if (comando instanceof Escreva) {
			executarEscreva((Escreva) comando);
			return;
		}

		if (comando instanceof Atribuicao) {
			executarAtribuicao((Atribuicao) comando);
			return;
		}

		if (comando instanceof Se se) {

			Object resultado = avaliar(se.getCondicao());

			if (!(resultado instanceof Boolean)) {
				throw new ErroExecucao("A condição do SE deve resultar em um valor lógico.", se.getLinha(), se.getColuna());
			}

			if ((Boolean) resultado) {
				executar(se.getComandosVerdadeiros());
			} else {
				executar(se.getComandosFalsos());
			}
			return;
		}

		if (comando instanceof Enquanto enquanto) {
			while ((Boolean) avaliar(enquanto.getCondicao())) {
				executar(enquanto.getComandos());
			}
			return;
		}

		if (comando instanceof Para paraComando) {

			double inicio = ((Number) avaliar(paraComando.getInicio())).doubleValue();
			double fim = ((Number) avaliar(paraComando.getFim())).doubleValue();

			contexto.declarar(paraComando.getVariavel(), new Variavel(TipoVariavel.NUMERO, inicio));

			Variavel variavel = contexto.obter(paraComando.getVariavel());

			for (double i = inicio; i <= fim; i++) {

				if (i == Math.floor(i)) {
					variavel.setValor((int) i);
				} else {
					variavel.setValor(i);
				}

				executar(paraComando.getComandos());
			}
			return;
		}

		
		if (comando instanceof ChamadaFuncao) {
		    executarChamadaFuncao((ChamadaFuncao) comando);
		    return;
		}

		if (comando instanceof Retorne retorna) {
			Object valor = avaliar(retorna.getExpressao());
			throw new RetornoFuncao(valor);
		}

		throw new ErroExecucao("Comando não implementado.", comando.getLinha(), comando.getColuna());
	}

	private void executarChamadaFuncao(ChamadaFuncao comando) {
		executarFuncao(comando.getNome(), comando.getArgumentos());
	}

	private Object executarFuncao(String nome, List<Expressao> argumentos) {

		Funcao funcao = programa.getFuncoes().get(nome);
		
		if (funcao == null) {
		    throw new RuntimeException("A função '" + nome + "' não foi declarada.");
		}

		if (argumentos.size() != funcao.getParametros().size()) {
		    throw new RuntimeException(
		        "A função '" + nome + "' espera "
		        + funcao.getParametros().size()
		        + " argumento(s), mas recebeu "
		        + argumentos.size() + "."
		    );
		}
		
		validarTipoParametro(funcao, argumentos);
		
		List<Parametro> parametros = funcao.getParametros();

		List<Object> valores = new ArrayList<>();

		for (Expressao argumento : argumentos) {
			valores.add(avaliar(argumento));
		}

		ContextoExecucao contextoAnterior = contexto;

		contexto = new ContextoExecucao(contextoAnterior);

		for (int i = 0; i < parametros.size(); i++) {

			Parametro parametro = parametros.get(i);
			Object valor = valores.get(i);

			Variavel variavel = new Variavel(TipoVariavel.valueOf(parametro.getTipo().toUpperCase()), valor);
			contexto.declarar(parametro.getNome(), variavel);
		}
		
		try {
		    
			for (Comando comandoFuncao : funcao.getComandos()) {
		        executar(comandoFuncao);
		    }

		} catch (RetornoFuncao retorno) {
			
			Object valor = retorno.getValor();
			validarTipoRetorno(funcao, valor);
		    return valor;

		} finally {
			
		    contexto = contextoAnterior;
		}
		
		if (funcao.getTipoRetorno() != null) {
		    throw new RuntimeException(
		        "A função '" + funcao.getNome()
		        + "' deve retornar um valor do tipo "
		        + funcao.getTipoRetorno() + "."
		    );
		}
		
		return null;
	}
	
	public void validarTipoParametro(Funcao funcao, List<Expressao> argumentos) {
		
	    List<Parametro> params = funcao.getParametros();
	    
	    int i = 0;
	    
	    for (Parametro p : params) {
			
	    	Expressao expr = argumentos.get(i);
			
			if (expr instanceof LiteralTexto) {
				
				LiteralTexto lt = (LiteralTexto) expr;
				
				if (!p.getTipo().equalsIgnoreCase("TEXTO")) {
					throw new ErroExecucao("O argumento " + lt.getValor() + " da função " + funcao.getNome() + " deve ser do tipo " + p.getTipo() + ".", lt.getLinha(), lt.getColuna());
				}
			}
			
			i++;
		}
	}
	
	private void validarTipoRetorno(Funcao funcao, Object valor) {
		
		TipoVariavel tipo = funcao.getTipoRetorno();

	    if (tipo == null) {
	        return;
	    }

	    boolean valido = switch (tipo) {
	        case TEXTO -> valor instanceof String;
	        case NUMERO -> valor instanceof BigDecimal;
	        case LOGICO -> valor instanceof Boolean;
	        case LISTA -> valor instanceof Object;
	    };

	    if (!valido) {
	        throw new RuntimeException(
	            "A função '" + funcao.getNome()
	            + "' deve retornar um valor do tipo "
	            + tipo + "."
	        );
	    }
	}

	private void executarLeia(Leia comando) {

		Variavel variavel = contexto.obter(comando.getIdentificador());
		String texto = io.ler();

		switch (variavel.getTipo()) {

		case NUMERO -> {
			try {
				variavel.setValor(new BigDecimal(texto));
			} catch (NumberFormatException e) {
				throw new ErroExecucao("Valor inválido para NUMERO: ", comando.getLinha(), comando.getColuna());
			}
		}

		case TEXTO -> {
			variavel.setValor(texto);
		}

		case LOGICO -> {
			if (texto.equalsIgnoreCase("verdadeiro") || texto.equalsIgnoreCase("true")) {
				variavel.setValor(true);
			} else if (texto.equalsIgnoreCase("falso") || texto.equalsIgnoreCase("false")) {
				variavel.setValor(false);
			} else {
				throw new ErroExecucao("Valor inválido para LOGICO: ", comando.getLinha(), comando.getColuna());
			}
		}
		}
	}

	private void executarDeclaracao(Declaracao declaracao) {

		Object valor = avaliar(declaracao.getValor());

		Variavel variavel = null;
		
		if (declaracao.getPosicao() != null) {
			
			Object[] valores = (Object[]) valor;
			
			
			BigDecimal pos = null;
			
			if (declaracao.getPosicao() instanceof Identificador) {
				
				if (contexto.obter(String.valueOf(declaracao.getPosicao().getValor())).getValor() instanceof BigDecimal) {
					pos = (BigDecimal) contexto.obter(String.valueOf(declaracao.getPosicao().getValor())).getValor();
				} else {
					Integer intPos = (int) contexto.obter(String.valueOf(declaracao.getPosicao().getValor())).getValor();
					pos = new BigDecimal(intPos);
				}
				
			} else {
			    pos = (BigDecimal) declaracao.getPosicao().getValor();	
			}
			
			variavel = new Variavel(declaracao.getTipo(), declaracao.getNome(), valores[pos.intValue()]);
		} else {
			variavel = new Variavel(declaracao.getTipo(), declaracao.getNome(), valor);
		}

		contexto.declarar(declaracao.getNome(), variavel);
	}

	private void executarEscreva(Escreva escreva) {

		Object valor = avaliar(escreva.getExpressao());

		if (valor instanceof BigDecimal numero) {
			io.escrever(numero.stripTrailingZeros().toPlainString());
		} else if (valor instanceof Object[]){
			Object[] valores = (Object[]) valor;
			
			BigDecimal pos = null;
			
			if (escreva.getPosicao() instanceof Identificador) {
				
				Object valorVariavel = contexto.obter(String.valueOf(escreva.getPosicao().getValor())).getValor();
				
				BigDecimal intPos = new BigDecimal(0.0);
				
				if (valorVariavel instanceof BigDecimal) {
					intPos = (BigDecimal) valorVariavel;
				} else {
					intPos = new BigDecimal(valorVariavel.toString());
				}
				
				
				pos = new BigDecimal(intPos.intValue());
			} else {
			    pos = (BigDecimal) escreva.getPosicao().getValor();	
			}

			String novoValor = String.valueOf(valores[pos.intValue()]).replace("\"", " ");
			io.escrever(texto(novoValor.trim()));
		} else {
			String novoValor = String.valueOf(valor).replace("\"", " ");
			io.escrever(texto(novoValor.trim()));
		}
	}

	private void executarAtribuicao(Atribuicao atribuicao) {

		Object valor = avaliar(atribuicao.getExpressao());

		Variavel variavel = contexto.obter(String.valueOf(atribuicao.getIdentificador().getValor()));
		
		if (atribuicao.getPosicao() != null) {
			
			if (valor instanceof Object[]) {
				Object[] obj = (Object[]) valor;
				variavel.setValor(obj[numero(atribuicao.getPosicao()).intValue()]);
			} else {
				variavel.setValor(numero(atribuicao.getPosicao()), valor);	
			}
		
		} else {
			variavel.setValor(valor);
		}
		
	}

	private void executar(List<Comando> comandos) {
		for (Comando comando : comandos) {
			executar(comando);
		}
	}

	private Object avaliar(Expressao expressao) {

		if (expressao instanceof LiteralTexto) {
			return ((LiteralTexto) expressao).getValor();
		}

		if (expressao instanceof LiteralNumero) {
			return ((LiteralNumero) expressao).getValor();
		}

		if (expressao instanceof LiteralLogico) {
			return ((LiteralLogico) expressao).getValor();
		}

		if (expressao instanceof Identificador) {
			
			try {

				String nome = String.valueOf(((Identificador) expressao).getValor());
				
				Variavel variavel = contexto.obter(nome);
				
				if (variavel.isColecaoNula()) {
					return variavel.getValor();
				} else {
					return variavel.getColecao();
				}
				
			} catch (VariavelNaoDeclaradaException e) {
				throw new ErroExecucao(e.getMessage(), expressao.getLinha(), expressao.getColuna());
			}
		}

		if (expressao instanceof Soma soma) {

			Object esquerdo = avaliar(soma.getEsquerda());
			Object direito = avaliar(soma.getDireita());

			if (esquerdo instanceof Number && direito instanceof Number) {
				return numero(esquerdo).add(numero(direito));
			}

			return texto(esquerdo) + texto(direito);
		}

		if (expressao instanceof Subtracao subtracao) {

			BigDecimal esquerdo = numero(subtracao.getEsquerda());
			BigDecimal direito = numero(subtracao.getDireita());

			return esquerdo.subtract(direito);
		}

		if (expressao instanceof Multiplicacao multiplicacao) {

			BigDecimal esquerdo = numero(multiplicacao.getEsquerda());
			BigDecimal direito = numero(multiplicacao.getDireita());

			return esquerdo.multiply(direito);
		}

		if (expressao instanceof Divisao divisao) {

			BigDecimal esquerdo = numero(divisao.getEsquerda());
			BigDecimal direito = numero(divisao.getDireita());
			
			if (direito.compareTo(BigDecimal.ZERO) == 0) {
			    throw new ErroExecucao("Divisão por zero.\n", divisao.getLinha(), divisao.getColuna());
			}

			return esquerdo.divide(direito, 10, RoundingMode.HALF_UP);
		}

		if (expressao instanceof Resto resto) {

			BigDecimal dividendo = numero(resto.getDividendo());
			BigDecimal divisor = numero(resto.getDivisor());

			return dividendo.remainder(divisor);
		}

		if (expressao instanceof Igual igual) {
			return comparar(igual.getEsquerda(), igual.getDireita()) == 0;
		}

		if (expressao instanceof Maior maior) {
			return comparar(maior.getEsquerda(), maior.getDireita()) > 0;
		}

		if (expressao instanceof MaiorOuIgual maiorOuIgual) {
			return comparar(maiorOuIgual.getEsquerda(), maiorOuIgual.getDireita()) >= 0;
		}

		if (expressao instanceof MenorOuIgual menorOuIgual) {
			return comparar(menorOuIgual.getEsquerda(), menorOuIgual.getDireita()) <= 0;
		}

		if (expressao instanceof Menor menor) {
			return comparar(menor.getEsquerda(), menor.getDireita()) < 0;
		}

		if (expressao instanceof Diferente diferente) {
			return comparar(diferente.getEsquerda(), diferente.getDireita()) != 0;
		}

		if (expressao instanceof E e) {

			Boolean esquerdo = (Boolean) avaliar(e.getEsquerda());
			if (!esquerdo) {
				return false;
			}

			Boolean direito = (Boolean) avaliar(e.getDireita());
			return direito;
		}

		if (expressao instanceof Ou ou) {

			Boolean esquerdo = (Boolean) avaliar(ou.getEsquerda());

			if (esquerdo) {
				return true;
			}

			Boolean direito = (Boolean) avaliar(ou.getDireita());
			return direito;
		}

		if (expressao instanceof Nao nao) {
			Boolean valor = (Boolean) avaliar(nao.getExpressao());
			return !valor;
		}
		
		if (expressao instanceof ChamadaFuncaoExpressao chamada) {
			
			Object resultado = executarFuncao(chamada.getNome(), chamada.getArgumentos());
		    return resultado;
		}

		throw new ErroExecucao("Expressão desconhecida.", expressao.getLinha(), expressao.getColuna());		

	}

	private int comparar(Expressao esquerda, Expressao direita) {
		
		if (esquerda instanceof Identificador && direita instanceof Identificador) {
			Variavel varEsquerda = contexto.obter(texto(esquerda.getValor()));
			Variavel varDireita = contexto.obter(texto(direita.getValor()));
			
			if (varEsquerda.getTipo() == TipoVariavel.TEXTO && varDireita.getTipo() == TipoVariavel.TEXTO) {

			    String esquerdo = String.valueOf(varEsquerda.getValor());
			    String direito = String.valueOf(varDireita.getValor());

			    return esquerdo.compareTo(direito);
			}

			if (varEsquerda.getTipo() == TipoVariavel.NUMERO && varDireita.getTipo() == TipoVariavel.NUMERO) {

			    BigDecimal esquerdo = numero(esquerda);
			    BigDecimal direito = numero(direita);

			    return esquerdo.compareTo(direito);
			}

		} else {
			
			if (esquerda instanceof LiteralNumero && direita instanceof LiteralNumero) {
				BigDecimal esquerdo = numero(esquerda);
			    BigDecimal direito = numero(direita);

			    return esquerdo.compareTo(direito);
			} 
			
			if (esquerda instanceof LiteralTexto && direita instanceof LiteralTexto) {
			    String esquerdo = texto(esquerda);
			    String direito = texto(direita);

			    return esquerdo.compareTo(direito);
			}
		} 		

		throw new RuntimeException("Não é possível comparar valores de tipos diferentes");
	}

	private boolean iguais(Object a, Object b) {

		if (a instanceof BigDecimal bd1 && b instanceof BigDecimal bd2) {
			return bd1.compareTo(bd2) == 0;
		}

		return Objects.equals(a, b);
	}

	private BigDecimal numero(Expressao expressao) {
		return numero(avaliar(expressao));
	}

	private BigDecimal numero(Object valor) {

		if (!(valor instanceof Number)) {
			throw new RuntimeException("A expressão não resulta em um número.");
		}

		if (valor instanceof BigDecimal bd) {
			return bd;
		}

		return new BigDecimal(valor.toString());
	}

	private String texto(Object valor) {

		if (valor == null) {
			return "nulo";
		}

		if (valor instanceof Boolean b) {
			return b ? "verdadeiro" : "falso";
		}

		if (valor instanceof BigDecimal bd) {
			return bd.stripTrailingZeros().toPlainString();
		}

		if (valor instanceof String s) {
			return s.replace("\"", " ");
		}

		return valor.toString();
	}

}