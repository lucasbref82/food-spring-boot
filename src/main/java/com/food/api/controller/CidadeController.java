package com.food.api.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
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
import com.food.api.model.Cidade;
import com.food.api.model.Restaurante;
import com.food.api.repository.CidadeRepository;
import com.food.api.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

		@Autowired
		CadastroCidadeService cadastroCidadeService;
		
		@Autowired
		CidadeRepository cidadeRepository;
		
		
		
		@GetMapping
		public ResponseEntity<List<Cidade>> todas(){
			return ResponseEntity.ok(cadastroCidadeService.todas());
		}
		
		@GetMapping("/{cidadeId}")
		public ResponseEntity<Cidade> buscarPorId(@PathVariable("cidadeId") Long id){
			try {
				return ResponseEntity.ok(cadastroCidadeService.buscarPorId(id));
			}catch (RecursoNaoEncontradoException e) {
				return ResponseEntity.notFound().build();
			}
		}
		
		@PostMapping
		public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
			try {
			Cidade cidade2 = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade2);
			}catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}
		
		@PutMapping("/{cidadeId}")
		public ResponseEntity<?> atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade){
			try {
				Cidade cidadeAlterada = cadastroCidadeService.alterar(cidadeId, cidade);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(cidadeAlterada);
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
		
		@PatchMapping("/{cidadeId}")
		public ResponseEntity<?> atualizarParcial(@PathVariable Long cidadeId,
				@RequestBody Map<String, Object> campos) {
			Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);
			
			if (cidadeAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			merge(campos, cidadeAtual.get());
			
			return atualizar(cidadeId, cidadeAtual.get());
		}

		private void merge(Map<String, Object> dadosOrigem, Cidade cidadeDestino) {
			// Usado para converter um Map para um objeto do tipo Restaurante.
			ObjectMapper objectMapper = new ObjectMapper();
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				ReflectionUtils.setField(field, cidadeDestino, novoValor);
			});
		}
	}
