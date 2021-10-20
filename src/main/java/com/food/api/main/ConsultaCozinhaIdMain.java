package com.food.api.main;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.api.FoodSpringBootApplication;
import com.food.api.jpa.CozinhaReposityImp;

public class ConsultaCozinhaIdMain {
	public static void main(String[] args) {
		
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodSpringBootApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaReposityImp cozinhaReposityImp = applicationContext.getBean(CozinhaReposityImp.class);
		System.out.println(cozinhaReposityImp.buscaPorId(1L).getNome());
				
	}
}
