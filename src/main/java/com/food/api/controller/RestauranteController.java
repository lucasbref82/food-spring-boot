package com.food.api.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.exception.EntidadeNaoEncontradaException;
import com.food.api.exception.RecursoNaoEncontradoException;
import com.food.api.model.Restaurante;
import com.food.api.repository.RestauranteRepository;
import com.food.api.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	CadastroRestauranteService restauranteService;
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> todas(){
		return ResponseEntity.ok(restauranteService.todas());
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable("restauranteId") Long id){
		try {
			return ResponseEntity.ok(restauranteService.buscarPorId(id));
		}catch (RecursoNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
		Restaurante restaurante2 = restauranteService.salvar(restaurante);
		return ResponseEntity.status(HttpStatus.CREATED).body(restaurante2);
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long restauranteId, @RequestBody Restaurante restaurante ){
		try {
			Restaurante restauranteAlterado= restauranteService.alterar(restauranteId, restaurante);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(restauranteAlterado);
		}catch (RecursoNaoEncontradoException e) {
		    HashMap<String, Object> map = new HashMap<>();
		    map.put("error", true);
		    map.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}catch (EntidadeNaoEncontradaException e) {
		    HashMap<String, Object> map = new HashMap<>();
		    map.put("error", true);
		    map.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		
		return atualizar(restauranteId, restauranteAtual.get());
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		// Usado para converter um Map para um objeto do tipo Restaurante.
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}
