package org.sophia.cli;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.sophia.compilador.ast.Programa;
import org.sophia.lexico.AnalisadorLexico;
import org.sophia.lexico.Simbolo;
import org.sophia.runtime.Interpretador;
import org.sophia.sintatico.AnalisadorSintatico;

public class ExecutorSophia {

    public void executar(String arquivo, boolean verbose) throws Exception {

        Path caminho = validarArquivo(arquivo);

        String codigo = Files.readString(caminho, StandardCharsets.UTF_8);

        AnalisadorLexico analisadorLexico = new AnalisadorLexico();

        AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(analisadorLexico.analisar(codigo));

        Programa programa = analisadorSintatico.analisar();

        if (verbose) {
            
        	System.out.println(arquivo);
        	
        	System.out.println("--------------------------------------------------------------------------");
        	
        	System.out.println(codigo);

        	System.out.println("--------------------------------------------------------------------------");
        	
        	for (Simbolo simbolo : analisadorLexico.getSimbolos()) {
    			System.out.println(simbolo);
    		}
        	
        	System.out.println("--------------------------------------------------------------------------");

            System.out.println(programa);
            
            System.out.println("--------------------------------------------------------------------------");
        }
        
        Interpretador interpretador = new Interpretador();

        interpretador.executar(programa);
    }

    public void verificar(String arquivo, boolean verbose) throws Exception {

        Path caminho = validarArquivo(arquivo);

        String codigo = Files.readString(caminho, StandardCharsets.UTF_8);

        AnalisadorLexico analisadorLexico = new AnalisadorLexico();

        AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(analisadorLexico.analisar(codigo));

        Programa programa = analisadorSintatico.analisar();
        
        if (verbose) {
            
        	System.out.println(arquivo);
        	
        	System.out.println("--------------------------------------------------------------------------");
        	
        	System.out.println(codigo);

        	System.out.println("--------------------------------------------------------------------------");
        	
        	for (Simbolo simbolo : analisadorLexico.getSimbolos()) {
    			System.out.println(simbolo);
    		}
        	
        	System.out.println("--------------------------------------------------------------------------");

            System.out.println(programa);
            
            System.out.println("--------------------------------------------------------------------------");
        }
    }

    private Path validarArquivo(String arquivo) {

        Path caminho = Path.of(arquivo);

        if (!Files.exists(caminho)) {
            throw new IllegalArgumentException(
                    "Arquivo não encontrado: " + arquivo
            );
        }

        if (!Files.isRegularFile(caminho)) {
            throw new IllegalArgumentException(
                    "O caminho informado não é um arquivo: " + arquivo
            );
        }

        if (!arquivo.toLowerCase().endsWith(".sph")) {
            throw new IllegalArgumentException(
                    "O arquivo deve possuir a extensão .sph."
            );
        }

        return caminho;
    }
}