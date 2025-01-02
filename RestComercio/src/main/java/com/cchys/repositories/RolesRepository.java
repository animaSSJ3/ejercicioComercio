package com.cchys.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cchys.entities.RolesEntity;

public interface RolesRepository extends JpaRepository<RolesEntity, Long>{

	Optional<RolesEntity> findByNombre(String nombre);
	
}
