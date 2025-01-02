package com.cchys.entities.dto.login;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistroUserDTO {
	
	/*
	 * CLASE QUE PERMITE LA ABSTRACCION DE DATOS 
	 * PARA EL REGISTRO DEL USUARIO
	 */
	
	@NotEmpty(message = "esta vacio")
	private String nombre;
	@NotEmpty(message = "esta vacio")
	private String primerApellido;
	@NotEmpty(message = "esta vacio")
	private String segundoApellido;
	@NotNull(message = "es requerido")
	private LocalDate fechaNacimiento;
	@NotNull(message = "es requerido")
	private Integer edad;
	@NotEmpty(message = "esta vacio")
	@Size(max = 10, message = "debe ser de 10 digitos")
	private String telefono;
	@NotEmpty(message = "esta vacio")
	private String correo;
	@NotEmpty(message = "esta vacio")
	private String password;
	@NotEmpty(message = "esta vacio")
	private String rfc;
	@NotEmpty(message = "esta vacio")
	private String confirmePassword;

	
	

}
