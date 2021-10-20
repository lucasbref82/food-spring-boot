package com.food.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.exception.EntidadeEmUsoException;
import com.food.api.exception.EntidadeNaoEncontradaException;
import com.food.api.model.Cozinha;
import com.food.api.repository.CozinhaRepository;
import com.food.api.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaRepository cozinhaReposity;
	
	@GetMapping
	// Caso a Collection vazia retornar 
	public List<Cozinha> buscar(){
		List<Cozinha> cozinhas = cozinhaReposity.findAll();
		return cozinhas;
	}
	
	@GetMapping("/{cozinhaId}")
	// Retorna um ResponseEntity do tipo cozinha
	public ResponseEntity<Cozinha> buscarPorid(@PathVariable("cozinhaId") Long id) {
		Optional<Cozinha> cozinha = cozinhaReposity.findById(id);
		if(cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		return ResponseEntity.notFound().build();
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId,
							@RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaBuscarId = cozinhaReposity.findById(cozinhaId);
		if(!cozinhaBuscarId.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		cozinha.setId(cozinhaId);
		return ResponseEntity.ok(cozinhaReposity.save(cozinha));
	}
	
	
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinhaService.excluir(cozinhaId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
	
}
