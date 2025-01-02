package com.cchys.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cchys.utilities.Utilidades;

@RestController
@RequestMapping("/api/v1")
public class ResponseEntityController {
	
	/* ResponseEntity representa la respuesta HTTP completa: código de estado, encabezados y cuerpo . 
	 * Como resultado, podemos usarlo para configurar completamente la respuesta HTTP. 
	 * Si queremos usarlo, tenemos que devolverlo desde el endpoint; Spring se encarga del resto. 
	 * ResponseEntity es un tipo genérico. Por lo tanto, podemos utilizar cualquier tipo como cuerpo de la respuesta:*/
	
	@GetMapping("/response-entity")
	public ResponseEntity<String> metodo_get() {
		return ResponseEntity.ok("método GET desde ResponseEntity");
	}

	@GetMapping("/response-entity-personalizado")
	public ResponseEntity<Object> metodo_get_personalizado() {
		return Utilidades.generateResponse(HttpStatus.OK, "ResponseEntity personalizado");
	}

	@GetMapping("/response-entity/{id}")
	public ResponseEntity<String> metodo_get_parametro(@PathVariable("id") Integer id) {
		return ResponseEntity.ok("método GET desde ResponseEntity  | parámetro=" + id);
	}

	@PostMapping("/response-entity")
	public ResponseEntity<String> metodo_post() {
		return ResponseEntity.ok("método POST desde ResponseEntity");
	}


	@PutMapping("/response-entity")
	public ResponseEntity<String> metodo_put() {
		return ResponseEntity.ok("método PUT desde ResponseEntity");
	}

	@DeleteMapping("/response-entity")
	public ResponseEntity<String> metodo_delete() {
		return ResponseEntity.ok("método DELETE desde ResponseEntity");
	}

}
