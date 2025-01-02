package com.cchys.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cchys.entities.ProveedorEntity;
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long>{
	 
	 Optional<ProveedorEntity> findById(Long id);

}
