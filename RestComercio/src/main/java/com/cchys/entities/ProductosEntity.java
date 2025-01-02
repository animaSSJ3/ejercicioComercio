package com.cchys.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductosEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotEmpty(message = "esta vacio")
	@Column(name = "codigo", length = 200)
	private String codigo;
	@NotEmpty(message = "esta vacio")
	@Column(name = "nombre", length = 100)
	private String nombre;
	@NotEmpty(message = "esta vacio")
	@Column(name = "detalle", length = 200)
	private String detalle;
	@Column(name = "precio")
	private Integer precio;
	@OneToOne
	@JoinColumn(name = "idcategoria")
	private CategoriasEntity idcategoria;
	@OneToOne
	@JoinColumn(name = "idproveedor")
	private ProveedorEntity idproveedor;
	
	
}
