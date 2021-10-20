package com.food.api.main;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.api.FoodSpringBootApplication;
import com.food.api.jpa.CozinhaReposityImp;
import com.food.api.model.Cozinha;

public class InclusaoCozinhaMain {
	public static void main(String[] args) {
		
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodSpringBootApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaReposityImp cozinhaReposityImp = applicationContext.getBean(CozinhaReposityImp.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Chinesa");
		
		cozinhaReposityImp.salvar(cozinha1);
		cozinhaReposityImp.salvar(cozinha2);
		
		
		
		}
				
	}
