package com.cchys.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuariosEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotEmpty(message = "esta vacio")
	@Column(name = "correo", length = 100)
	private String correo;
	@NotEmpty(message = "esta vacio")
	@Column(name = "password", length = 200)
	private String password;
	@Column(name = "token", length = 200)
	private String token;
	@Column(name = "estado")
	private Integer estado;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idusuario")
	private List<RolesEntity> roles;
	@OneToOne
	@JoinColumn(name = "idproveedor")
	private ProveedorEntity idproveedor;

}
