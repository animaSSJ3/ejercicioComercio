package com.cchys.services.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cchys.entities.ProveedorEntity;
import com.cchys.request.AuthRequest;
import com.cchys.response.AuthResponse;
import com.cchys.response.CategoriasEntity;
import com.cchys.response.ProductosEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class RestService {

	private final RestTemplate restTemplate;

	@Value("${base.url.api}")
	private String ruta_api;

	// SE CREA METODOS PARA AGREGAR EN LAS CABECERAS ALGUNOS
	// ELEMENTOS O DATOS DE AUTENTICACION QUE REQUIERE EL API
	private HttpHeaders setHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();

		if (token.isBlank() || token.isEmpty()) {

			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		} else {

			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", "Bearer " + token);

		}

		return headers;
	}

	public AuthResponse obtieneToken(AuthRequest datos) {

		try {

			ResponseEntity<AuthResponse> response = restTemplate.postForEntity(ruta_api + "obtieneToken", datos,
					AuthResponse.class);

			return response.getBody();

		} catch (Exception ex) {
			log.info("Token Error: " + ex.getMessage());
			return null;
		}

	}
	
	public List<CategoriasEntity> listaCategorias(String token) {

		try {

			HttpEntity<String> entity = new HttpEntity<>(this.setHeaders(token));
			ResponseEntity<List<CategoriasEntity>> response = this.restTemplate.exchange(ruta_api + "obtiene-categorias",
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<CategoriasEntity>>() {
					});
			return response.getBody();

		} catch (Exception ex) {
			log.info("Lista categorias Error: " + ex.getMessage());
			return null;
		}

	}
	
	public List<ProductosEntity> listaProductos(String token, ProveedorEntity idproveedor) {

		try {
			
			HttpEntity<ProveedorEntity> entity = new HttpEntity<>(idproveedor, this.setHeaders(token));
			ResponseEntity<List<ProductosEntity>> response = this.restTemplate.exchange(ruta_api + "obtiene-productos",
					HttpMethod.POST, entity, new ParameterizedTypeReference<List<ProductosEntity>>() {
					});
			return response.getBody();

		} catch (Exception ex) {
			log.info("Lista productos Error: " + ex.getMessage());
			return null;
		}

	}
		

}
