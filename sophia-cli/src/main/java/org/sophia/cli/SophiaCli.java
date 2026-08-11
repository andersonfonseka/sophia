package org.sophia.cli;

public class SophiaCli {

    private static final String VERSAO = "1.0.0";
    
    private static boolean verbose = false;

    public static void main(String[] args) {

        if (args.length == 0) {
            ajuda();
            return;
        }

        String comando = args[0].toLowerCase();

        switch (comando) {

            case "versao":
                version();
                break;

            case "executar":
                executar(args);
                break;

            case "verificar":
                verificar(args);
                break;
                
            case "repl":
                iniciarRepl();
                break;     

            case "ajuda":
                ajuda();
                break;

            default:
                System.err.println("Comando desconhecido: " + args[0]);
                System.err.println();
                ajuda();
                System.exit(1);
        }
    }

    private static void version() {
        System.out.println("Sophia " + VERSAO);
    }

    private static void executar(String[] args) {

        if (args.length < 2) {
            erro("Informe o arquivo .sph.");
        }
        
        if (args.length > 2) {
        	if (args[2].toLowerCase().equals("--verbose")) {
        		verbose = true;
        	}
        }

        ExecutorSophia executor = new ExecutorSophia();

        try {
            executor.executar(args[1], verbose);
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    private static void verificar(String[] args) {

        if (args.length < 2) {
            erro("Informe o arquivo .sph.");
        }

        ExecutorSophia executor = new ExecutorSophia();

        try {
            executor.verificar(args[1], verbose);
            System.out.println("Programa válido.");
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }
    
    private static void iniciarRepl() {
        try {
            new ReplSophia().iniciar();
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    private static void ajuda() {

        System.out.println("Sophia " + VERSAO);
        System.out.println();
        System.out.println("Uso:");
        System.out.println("  sophia versao");
        System.out.println("  sophia executar <arquivo.sph> [--verbose]");
        System.out.println("  sophia verificar <arquivo.sph> [--verbose]");
        System.out.println("  sophia repl");
        System.out.println("  sophia ajuda");
    }

    private static void erro(String mensagem) {
        System.err.println("Erro: " + mensagem);
        System.exit(1);
    }
}