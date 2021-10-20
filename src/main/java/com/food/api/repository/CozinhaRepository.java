package com.food.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.api.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	List<Cozinha> findByNomeContaining(String nome);
	List<Cozinha> findByNome(String nome);
} 
