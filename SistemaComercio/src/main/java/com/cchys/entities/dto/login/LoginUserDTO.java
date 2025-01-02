package com.cchys.entities.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserDTO {
	
	/*
	 * CLASE QUE PERMITE LA ABSTRACCION DE DATOS 
	 * PARA CUANDO EL USUARIO ESTE LOGUEADO
	 */
	
	@NotEmpty(message = "esta vacio")
	private String nombre;
	@NotEmpty(message = "esta vacio")
	private String primerApellido;
	@NotEmpty(message = "esta vacio")
	private String segundoApellido;
	
	
	

}
