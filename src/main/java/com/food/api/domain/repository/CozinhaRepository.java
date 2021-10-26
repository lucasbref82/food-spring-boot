package com.food.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.api.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	List<Cozinha> findByNomeContaining(String nome);
	List<Cozinha> findByNome(String nome);
}
