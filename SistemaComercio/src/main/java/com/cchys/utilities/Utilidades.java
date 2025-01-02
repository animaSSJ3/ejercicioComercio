package com.cchys.utilities;

import java.text.DecimalFormat;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Utilidades {
	
	public static final String ROLE_ADMIN = "ADMIN";
	
	public static final Integer CANTIDAD_POR_PAGINA = 4;
	
	public String genFolio(String folio) {
		
		if (folio.isBlank() || folio.isEmpty()) {
			
			return "PROD-0000000001";
			
		}else {
			
	        String [] parteFolio = folio.split("-");
	        String consecutivo = convertirNumero(parteFolio[1]);
			return "PROD-" + consecutivo;
		}
		
	}

	
	public WebContext crearContexto(HttpServletRequest request, HttpServletResponse response) {
		
		return createContext(request,response);
	}
	
	
	private WebContext createContext(HttpServletRequest request, HttpServletResponse response) {
		JakartaServletWebApplication application = JakartaServletWebApplication
				.buildApplication(request.getServletContext());
		IWebExchange exchange = application.buildExchange(request, response);
		return new WebContext(exchange);
	}
	
	
	//Se recibe como parámetro el número en forma de String que se trae de la consulta.
    private String convertirNumero(String numero) {
        //Se hace el formato del String.
        DecimalFormat format = new DecimalFormat("0000000000");
        //Se realiza la convesión del String recibido como parámetro y se le suma 1.
        return format.format(Long.parseLong(numero) + 1);
    }

	

}
