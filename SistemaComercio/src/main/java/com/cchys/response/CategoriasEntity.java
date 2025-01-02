package com.cchys.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoriasEntity {


	private Long id;
	private String nombre;
	private Integer estado;
	
}
