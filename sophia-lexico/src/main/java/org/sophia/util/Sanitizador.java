package org.sophia.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sanitizador {
	
	public String removerEspacosDentroDasAspas(String texto) {
	    Pattern pattern = Pattern.compile("\"([^\"]*)\"");
	    Matcher matcher = pattern.matcher(texto);

	    StringBuffer resultado = new StringBuffer();

	    while (matcher.find()) {
	        String conteudo = matcher.group(1).trim();
	        matcher.appendReplacement(resultado, "\"" + conteudo + "\"");
	    }

	    matcher.appendTail(resultado);

	    return resultado.toString();
	}

}
