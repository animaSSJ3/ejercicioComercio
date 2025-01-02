package com.cchys.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cchys.entities.CategoriasEntity;
import com.cchys.entities.ProductosEntity;
import com.cchys.entities.ProveedorEntity;
import com.cchys.entities.UsuariosEntity;
import com.cchys.jwt.AuthRequest;
import com.cchys.jwt.AuthResponse;
import com.cchys.jwt.JwtTokenUtil;
import com.cchys.services.CategoriasService;
import com.cchys.services.ProductosService;
import com.cchys.services.UsuariosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccesoController {

	private final AuthenticationManager authManager;

	private final JwtTokenUtil jwtUtil;

	private final UsuariosService usuarioService;

	private final CategoriasService catService;

	private final ProductosService prodService;

	/* {"correo":"info@tamila.cl", "password":"123456"} */
	@PostMapping("/obtieneToken")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			// HACE LA IMPLEMENTACION DE LA AUTENTICACION DEL USUARIO
			Authentication authentication = this.authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
			System.out.println(authentication);

			UsuariosEntity user = this.usuarioService.buscarPorCorreo(request.getCorreo(), 1);
			String accessToken = this.jwtUtil.generarToken(user);

			AuthResponse response = new AuthResponse(request.getCorreo(), accessToken);

			return ResponseEntity.ok(response);

		} catch (BadCredentialsException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/obtiene-categorias")
	public List<CategoriasEntity> obtieneCategorias() {

		return this.catService.listar();

	}
	
	@GetMapping("/obtiene-codigo")
	public String obtieneCodigo() {

		return this.prodService.buscaCodigo();

	}

	@PostMapping("/obtiene-productos")
	public ResponseEntity<?>  obtieneProductos(ProveedorEntity idproveedor) {
		
		System.out.println(idproveedor);
		
		try {

			return ResponseEntity.ok(this.prodService.listaProductos(idproveedor));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}


	}

	@PostMapping("/obtiene-producto")
	public ResponseEntity<?> obtieneProductos(Long id) {
		
		
		try {

			return ResponseEntity.ok(this.prodService.buscaProducto(id));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}

	}

	@PostMapping("/guardar-producto")
	public ResponseEntity<?> guardaProducto(@RequestBody ProductosEntity producto) {

		try {

			return ResponseEntity.ok(this.prodService.guardar(producto));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}

	}

	@PostMapping("/elimina-producto")
	public void eliminaProducto(@RequestBody Long id) {

		this.prodService.eliminarProducto(id);

	}

}
