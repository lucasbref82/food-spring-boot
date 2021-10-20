package com.food.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.api.infrastructure.query.RestauranteRepositoryQueries;
import com.food.api.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries{

	//@Query("from Restaurante where nome like %:nome%")
	List<Restaurante> consultarPorNome(String nome);
}
