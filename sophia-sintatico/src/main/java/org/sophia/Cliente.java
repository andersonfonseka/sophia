package org.sophia;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.sophia.compilador.ast.Programa;
import org.sophia.lexico.AnalisadorLexico;
import org.sophia.runtime.Interpretador;
import org.sophia.sintatico.AnalisadorSintatico;

public class Cliente {
	
	
	public static void main(String[] args) throws Exception {

		System.out.println(System.getProperty("file.encoding"));
		System.out.println(System.getProperty("native.encoding"));
		System.out.println(java.nio.charset.Charset.defaultCharset());

//		String[] testes = new String[] {"operadores.sph", "ola.sph", "variavel.sph", "comparadores.sph", "maioridade.sph", "ou.sph", "nao.sph", "enquanto.sph","para.sph", "comentarios.sph", "leia.sph"}; 
		String[] testes = new String[] {"notas.sph"}; 
		
		for (int i = 0; i < testes.length; i++) {

			Path path = Paths.get(AnalisadorLexico.class.getResource("/exemplos/" + testes[i]).toURI());
			String codigo = Files.readString(path, StandardCharsets.UTF_8);
			
			AnalisadorLexico analexico = new AnalisadorLexico();
			AnalisadorSintatico as = new AnalisadorSintatico(analexico.analisar(codigo));
			Programa prg = as.analisar();
			System.out.println(prg);
			
			Interpretador interpretador = new Interpretador();
			interpretador.executar(prg);
		}
		
	}

}
