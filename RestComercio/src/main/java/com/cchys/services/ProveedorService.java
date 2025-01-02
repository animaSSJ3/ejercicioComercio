package com.cchys.services;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cchys.entities.ProveedorEntity;
import com.cchys.repositories.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class ProveedorService {

	private final ProveedorRepository repository;

	public ProveedorEntity guardar(ProveedorEntity datosEntity) {

		return this.repository.save(datosEntity);

	}


	public ProveedorEntity buscaPorId(Long id) {

		Optional<ProveedorEntity> proveedor = this.repository.findById(id);

		if (proveedor.isPresent()) {

			return proveedor.get();

		} else {
			return null;
		}

	}

}
