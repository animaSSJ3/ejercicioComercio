package com.cchys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


import com.cchys.entities.ProductosEntity;
import com.cchys.entities.ProveedorEntity;
import com.cchys.repositories.ProductosRepository;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class ProductosService {

	
	private final ProductosRepository repositorio;
	
	
	public ProductosEntity guardar(ProductosEntity datosProducto) {
		return this.repositorio.save(datosProducto);
		
	}
	
	public List<ProductosEntity> listaProductos(ProveedorEntity idproveedor){
		
		List<ProductosEntity> listaProductos = this.repositorio.findByIdproveedor(idproveedor);
		
		return listaProductos.isEmpty() ? Collections.emptyList() : listaProductos;
		
	}
	
	public ProductosEntity buscaProducto(Long id) {
		
		Optional<ProductosEntity> obtieneProducto = this.repositorio.findById(id);
		
		return obtieneProducto.isPresent() ? obtieneProducto.get() : null;
		
		
		
	}
	
	public String buscaCodigo() {
		
		Optional<String> encuentraCodigo = this.repositorio.findMaxCodigo();
		
		return encuentraCodigo.isPresent() ? encuentraCodigo.get() : null;
		
	}
	
	
	public void eliminarProducto(Long id) {
		
		this.repositorio.deleteById(id);
		
	}
	
	
}
