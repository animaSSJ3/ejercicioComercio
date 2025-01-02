package com.cchys.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cchys.entities.RolesEntity;
import com.cchys.repositories.RolesRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class RolesService {

	private final RolesRepository repositorio;

	public void guardar(RolesEntity rol) {
		this.repositorio.save(rol);
	}

}
