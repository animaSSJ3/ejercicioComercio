package com.cchys.services;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cchys.entities.UsuariosEntity;
import com.cchys.repositories.UsuariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class UsuariosService {

	private final UsuariosRepository repositorio;
	
	//PERMITE RETORNAR EL OBJETO QUE FUE INSERTADO EN BASE DE DATOS
	public UsuariosEntity guardar(UsuariosEntity entity) {
		return this.repositorio.save(entity);
	}

	public UsuariosEntity buscarPorCorreo(String correo, Integer estado) {
		
		Optional<UsuariosEntity> usuarioBD = this.repositorio.findByCorreoAndEstado(correo, estado);
		
		if (usuarioBD.isPresent()) {
			return usuarioBD.get();
		}else {
			return null;
		}
		
		
	}

}
