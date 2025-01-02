package com.cchys.utilities;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utilidades {
	
	public static final String ROLE_ADMIN = "ADMIN";
	
	public static final Integer CANTIDAD_POR_PAGINA = 4;
	
	//PERMITE CREAR UNA RESPUESTA PERSONALIZADA USANDO LOS ESTATUS HTTP
	public static ResponseEntity<Object> generateResponse(HttpStatus status, String message) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("fecha", new Date());
			map.put("status", status.value());
			map.put("mensaje", message);

			return new ResponseEntity<Object>(map, status);
		} catch (Exception e) {
			map.clear();
			map.put("fecha", new Date());
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("mensaje", e.getMessage());
			return new ResponseEntity<Object>(map, status);
		}
	}
	

}
