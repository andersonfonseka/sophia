package org.sophia.runtime;

import java.util.Scanner;

public class EntradaSaidaConsole implements EntradaSaida {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void escrever(String texto) {
        System.out.println(texto);
    }

    @Override
    public String ler() {
        return scanner.nextLine();
    }
}