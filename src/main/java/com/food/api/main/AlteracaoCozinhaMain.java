package com.food.api.main;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.api.FoodSpringBootApplication;
import com.food.api.jpa.CozinhaReposityImp;
import com.food.api.model.Cozinha;

public class AlteracaoCozinhaMain {
	public static void main(String[] args) {
		
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodSpringBootApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaReposityImp cozinhaReposityImp = applicationContext.getBean(CozinhaReposityImp.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		cozinha.setNome("Tailandesa");
		cozinhaReposityImp.salvar(cozinha);
		
		
				
	}
}
