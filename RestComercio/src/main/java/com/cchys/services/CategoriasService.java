package com.cchys.services;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cchys.entities.CategoriasEntity;
import com.cchys.repositories.CategoriasRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class CategoriasService {
	
	private final CategoriasRepository repositorio;
	
	public List<CategoriasEntity> listar(){
		//METODO DE LA IMPLEMENTACION REPOSITORY
		//QUE PERMITE OBTENER TODOS LOS REGISTROS
		//DE LA TABLA
		return this.repositorio.findAll();
		
	}

}
