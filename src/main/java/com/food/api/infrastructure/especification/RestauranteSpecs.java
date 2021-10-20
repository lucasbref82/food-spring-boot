package com.food.api.infrastructure.especification;

import org.springframework.data.jpa.domain.Specification;

import com.food.api.model.Restaurante;

public class RestauranteSpecs {
	public static Specification<Restaurante> comFreteGratis(){
		return new RestauranteComFreteGratisSpec();
	}
	
	public static Specification<Restaurante> comNomeSemelhante(String nome){
		return new RestauranteComNomeContendoSpec(nome);
	}
}
