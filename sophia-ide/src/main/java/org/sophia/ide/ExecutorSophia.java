package org.sophia.ide;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import javax.swing.SwingUtilities;

import org.sophia.compilador.ast.Programa;
import org.sophia.lexico.AnalisadorLexico;
import org.sophia.runtime.ErroExecucao;
import org.sophia.runtime.Interpretador;
import org.sophia.sintatico.AnalisadorSintatico;

public class ExecutorSophia {
	
	private final EntradaSaidaIDE entradaSaidaIDE = new EntradaSaidaIDE();

    public Resultado executar(String codigo) {

        ByteArrayOutputStream buffer =
                new ByteArrayOutputStream();

        PrintStream saidaOriginal = System.out;

        try {

            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));

            AnalisadorLexico analisadorLexico = new AnalisadorLexico();
            AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(analisadorLexico.analisar(codigo));
            Programa programa = analisadorSintatico.analisar();
            
            Interpretador interpretador = new Interpretador(programa, entradaSaidaIDE);
            interpretador.executar();
  
            return Resultado.sucesso(buffer.toString(StandardCharsets.UTF_8));

        } catch (ErroExecucao e) {
        	return Resultado.erro(
        	        e.getMessage(),
        	        e.getLinha(),
        	        e.getColuna()
        	    );
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        } finally {
            System.setOut(saidaOriginal);
        }
    }

	public String getSaida() {
		return entradaSaidaIDE.getSaida().trim();
	}
	
	public void limpar() {
		entradaSaidaIDE.limpar();
	}
       
}