package com.cchys.response;

import com.cchys.entities.ProveedorEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductosEntity {

	
	private Long id;
	private String codigo;
	private String nombre;
	private String detalle;
	private Integer precio;
	private CategoriasEntity idcategoria;
	private ProveedorEntity idproveedor;
	
	
}
