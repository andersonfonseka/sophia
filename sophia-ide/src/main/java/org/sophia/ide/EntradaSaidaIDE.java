package org.sophia.ide;

import javax.swing.JOptionPane;
import org.sophia.runtime.EntradaSaida;

public class EntradaSaidaIDE implements EntradaSaida {

    private final StringBuilder saida = new StringBuilder();
    
    @Override
    public void escrever(String texto) {
        saida.append(texto + "\n");
    }

    @Override
    public String ler() {
    	return JOptionPane.showInputDialog(null, "Digite um valor:", "Sophia", JOptionPane.QUESTION_MESSAGE);
    }

    public String getSaida() {
        return saida.toString();
    }

    public void limpar() {
        saida.setLength(0);
    }
}