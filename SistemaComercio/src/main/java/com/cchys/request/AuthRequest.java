package com.cchys.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {

	@NotEmpty(message = "esta vacio")
	private String correo;
	@NotEmpty(message = "esta vacio")
	private String password;


}
