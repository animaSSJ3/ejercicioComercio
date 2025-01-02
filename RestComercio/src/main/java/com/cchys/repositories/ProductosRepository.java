package com.cchys.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cchys.entities.ProductosEntity;
import com.cchys.entities.ProveedorEntity;




public interface ProductosRepository extends JpaRepository<ProductosEntity, Long>{
	
	Optional<ProductosEntity> findById(Long id);
	
	@Query("SELECT MAX(pe.codigo) FROM ProductosEntity pe")
	Optional<String> findMaxCodigo();
	
	List<ProductosEntity> findByIdproveedor(ProveedorEntity idproveedor);
	
	@Modifying
	@Query("DELETE FROM ProductosEntity pe WHERE pe.id = :id")
	void deleteById (@Param("id") Long id);
	
}
