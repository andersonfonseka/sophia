package org.sophia.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

import org.sophia.compilador.ast.Programa;
import org.sophia.lexico.AnalisadorLexico;
import org.sophia.runtime.EntradaSaidaConsole;
import org.sophia.runtime.Interpretador;
import org.sophia.sintatico.AnalisadorSintatico;

public class ReplSophia {

	private Interpretador interpretador;
	
	private final StringBuilder blocoAtual;
	
	private boolean modoMultilinha;
	
	private Programa programaREPL;
	
	private final StringBuilder funcoesREPL;
	
	private final StringBuilder codigoREPL;
	
	private final Deque<String> estruturas;
	
	private boolean modoFuncao;
	
	public ReplSophia() {
	    
	    this.blocoAtual = new StringBuilder();
	    this.modoMultilinha = false;
	
	    funcoesREPL = new StringBuilder();
	    
	    this.codigoREPL = new StringBuilder();
	    codigoREPL.append("programa \"REPL\"\n");
	    codigoREPL.append("inicio\n");
	    
	    this.estruturas = new ArrayDeque<>();
	    
	}
	
    public void iniciar() throws Exception {

        BufferedReader leitor =  new BufferedReader(new InputStreamReader(System.in));

        System.out.println();
        System.out.println("Sophia 1.0.0");
        System.out.println("REPL");
        System.out.println();
        System.out.println("Digite 'ajuda' para obter ajuda.");
        System.out.println("Digite 'sair' para encerrar.");
        System.out.println();

        while (true) {

        	if (modoMultilinha) {
        	    System.out.print("... ");
        	} else {
        	    System.out.print("sophia> ");
        	}
        	
            String linha = leitor.readLine();

            if (linha == null) {
                break;
            }

            linha = linha.trim();

            if (linha.isEmpty()) {
                continue;
            }

            if (linha.equalsIgnoreCase("sair")) {
                break;
            }

            if (linha.equalsIgnoreCase("ajuda")) {
                ajuda();
                continue;
            }

            if (linha.equalsIgnoreCase("limpar")) {
                limpar();
                continue;
            }
            
            if (modoMultilinha) {
                adicionarLinhaBloco(linha);
                continue;
            }

            if (iniciaBloco(linha)) {
                iniciarBloco(linha);
                continue;
            } else {
                codigoREPL.append(linha);
                executar();
            }

            //executar();
        }
    }
    
    private boolean iniciaEstrutura(String linha) {

        String comando = linha.trim().toLowerCase();

        return comando.equals("se")
                || comando.startsWith("se ")

                || comando.equals("enquanto")
                || comando.startsWith("enquanto ")

                || comando.equals("para")
                || comando.startsWith("para ")

                || comando.equals("funcao")
                || comando.startsWith("funcao ");
    }
    
    private String estruturaDaLinha(String linha) {

        String comando = linha.trim().toLowerCase();

        if (comando.equals("se") || comando.startsWith("se ")) {
            return "se";
        }

        if (comando.equals("enquanto") || comando.startsWith("enquanto ")) {
            return "enquanto";
        }

        if (comando.equals("para") || comando.startsWith("para ")) {
            return "para";
        }

        if (comando.equals("funcao") || comando.startsWith("funcao ")) {
            return "funcao";
        }

        return null;
    }

    private boolean iniciaBloco(String linha) {

        String comando = linha.trim().toLowerCase();

        return comando.startsWith("se ")
            || comando.equals("se")
            
            || comando.startsWith("enquanto ")
            || comando.equals("enquanto")
            
            || comando.startsWith("para ")
            || comando.equals("para")
            
            || comando.startsWith("funcao ")
            || comando.equals("funcao");
    }
    
    private void iniciarBloco(String linha) {

        blocoAtual.setLength(0);

        blocoAtual.append(linha).append("\n");

        estruturas.clear();
        estruturas.push(estruturaDaLinha(linha));

        modoMultilinha = true;

        modoFuncao = linha.trim()
                .toLowerCase()
                .startsWith("funcao ");
    }
    
    private void adicionarLinhaBloco(String linha) {

        blocoAtual.append(linha).append("\n");

        String comando = linha.trim();

        if (iniciaEstrutura(comando)) {
            estruturas.push(estruturaDaLinha(comando));
            return;
        }

        if (comando.equalsIgnoreCase("fim")) {

            if (estruturas.isEmpty()) {
                System.err.println("Erro: 'fim' sem estrutura correspondente.");
                return;
            }

            estruturas.pop();

            if (estruturas.isEmpty()) {

                if (modoFuncao) {
                    funcoesREPL.append(blocoAtual);
                    modoFuncao = false;
                } else {
                    codigoREPL.append(blocoAtual);
                    executar();
                }

                blocoAtual.setLength(0);
                modoMultilinha = false;
            }
        }
    }    
    
	private void executar() {
		
        String codigoCompleto =
                funcoesREPL.toString()
                + codigoREPL.toString()
                + System.lineSeparator()
                + "fim";
        
        try {

            AnalisadorLexico analisadorLexico = new AnalisadorLexico();
            AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(analisadorLexico.analisar(codigoCompleto));
            this.programaREPL = analisadorSintatico.analisar();

            this.interpretador = new Interpretador(programaREPL, new EntradaSaidaConsole());
            interpretador.executarREPL(this.programaREPL);

        } catch (Exception e) {

            System.err.println("Erro: " + e.getMessage());
        }
    }
    
    private void ajuda() {

        System.out.println();
        System.out.println("Comandos:");
        System.out.println("  ajuda   mostra esta ajuda");
        System.out.println("  limpar  limpa a tela");
        System.out.println("  sair    encerra o REPL");
        System.out.println();
    }

    private void limpar() {

        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}