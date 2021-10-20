package com.food.api.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.exception.EntidadeEmUsoException;
import com.food.api.exception.RecursoNaoEncontradoException;
import com.food.api.model.Estado;
import com.food.api.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {
	
	
	@Autowired
	CadastroEstadoService estadoService;
	
	@GetMapping()
	public ResponseEntity<List<Estado>> buscar(){
		List<Estado> estados = estadoService.todas();
		return ResponseEntity.ok(estados);
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<?>buscarPorId(@PathVariable("estadoId") Long id) {
		try {
			Estado estado = estadoService.buscarPorId(id);
			return ResponseEntity.ok(estado);
		}catch (RecursoNaoEncontradoException e) {
			HashMap<String, Object> estadoMap = new HashMap<>();
			estadoMap.put("error", true);
			estadoMap.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(estadoMap);
		}
	}
	
	@PostMapping
	public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
		Estado estadoSave = estadoService.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estadoSave);
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<?> alterar(@PathVariable("estadoId") Long id, @RequestBody Estado estado){
		try {
			Estado estado2 = estadoService.alterar(id, estado);
			return ResponseEntity.accepted().body(estado2);
		}catch (RecursoNaoEncontradoException e) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("error", true);
			map.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> deletar(@PathVariable("estadoId") Long id){
		try {
			estadoService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch (EntidadeEmUsoException e) {
			HashMap<String, Object> body = new HashMap<String, Object>();
			body.put("error", true);
			body.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}catch (RecursoNaoEncontradoException e) {
			HashMap<String, Object> body = new HashMap<String, Object>();
			body.put("error", true);
			body.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		}
	}
}
