package com.food.api.main;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.api.FoodSpringBootApplication;
import com.food.api.jpa.CozinhaReposityImp;
import com.food.api.model.Cozinha;

public class ConsultaCozinhaMain {
	public static void main(String[] args) {
		
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodSpringBootApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaReposityImp cozinhaReposityImp = applicationContext.getBean(CozinhaReposityImp.class);
		
		List<Cozinha> cozinhas = cozinhaReposityImp.todas();
		
		for (Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.getNome());
		}
				
	}
}
