package org.sophia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sophia.compilador.ast.Programa;
import org.sophia.lexico.AnalisadorLexico;
import org.sophia.runtime.Interpretador;
import org.sophia.sintatico.AnalisadorSintatico;

public class Cliente {
	
	
	public static void main(String[] args) throws Exception {

		System.out.println(System.getProperty("file.encoding"));
		System.out.println(System.getProperty("native.encoding"));
		System.out.println(java.nio.charset.Charset.defaultCharset());

		Path pathBase = Paths.get(Cliente.class.getResource("/erros").getFile().replaceFirst("/", ""));
		
		try (Stream<Path> stream = Files.list(pathBase)) {
			
			List<Path> files = stream
		        .filter(Files::isRegularFile)
		        .collect(Collectors.toList());

			if (files.isEmpty()) {
		        System.out.println("Nenhum arquivo encontrado.");
		        return;
		    }

		    AnalisadorLexico analisadorLexico = new AnalisadorLexico();
		    Interpretador interpretador = new Interpretador();

		    for (Path arquivo : files) {
		        try {
		            System.out.println("Processando: " + arquivo.getFileName());

		            String codigo = Files.readString(arquivo, StandardCharsets.UTF_8);

		            AnalisadorSintatico analisadorSintatico =
		                    new AnalisadorSintatico(analisadorLexico.analisar(codigo));

		            Programa programa = analisadorSintatico.analisar();

		            System.out.println(programa);

		            interpretador.executar(programa);

		        } catch (Exception e) {
		            System.err.println("Erro ao processar o arquivo: " + arquivo);
		            e.printStackTrace();
		        }
		    }
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
				
	}

}
