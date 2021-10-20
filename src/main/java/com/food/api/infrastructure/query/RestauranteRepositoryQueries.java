package com.food.api.infrastructure.query;

import java.math.BigDecimal;
import java.util.List;

import com.food.api.model.Restaurante;

public interface RestauranteRepositoryQueries {
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
