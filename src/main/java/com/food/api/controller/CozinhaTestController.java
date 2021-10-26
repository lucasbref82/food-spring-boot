package com.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.domain.model.Cozinha;
import com.food.api.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste-cozinha")
public class CozinhaTestController {
	
	@Autowired
	CozinhaRepository repository;
	
	@GetMapping("/contais")
	public List<Cozinha> getPorNomeContais(String nome) {
		return repository.findByNomeContaining(nome);
	}
	
	
	@GetMapping
	public List<Cozinha> getPorNome(String nome) {
		return repository.findByNome(nome);
	}
}
