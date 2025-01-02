package com.cchys.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cchys.entities.UsuariosEntity;

public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long>{
	
	Optional<UsuariosEntity> findByCorreoAndEstado(String correo, Integer estado);

}
