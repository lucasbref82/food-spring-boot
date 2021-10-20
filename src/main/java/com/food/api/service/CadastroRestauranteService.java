package com.food.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.api.exception.EntidadeNaoEncontradaException;
import com.food.api.exception.RecursoNaoEncontradoException;
import com.food.api.model.Cozinha;
import com.food.api.model.Restaurante;
import com.food.api.repository.CozinhaRepository;
import com.food.api.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaRepository cozinhaReposityImp;

	public List<Restaurante> todas() {
		return restauranteRepository.findAll();
	}

	
	public Restaurante buscarPorId(Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		if(restaurante.isEmpty()) {
			throw new RecursoNaoEncontradoException(String.format("Restaurente de id %d não econtrado", id));
		}
		return restaurante.get();
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long idCozinha = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaReposityImp.findById(idCozinha);
		
		if(!cozinha.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha de id %d não existe.", idCozinha));
		}
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante alterar(Long id, Restaurante restauranteBody) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		if(restaurante.isEmpty()) {
			throw new RecursoNaoEncontradoException(String.format("Restaurante de ID %d, não encontrado", id));
		}
		Long cozinhaId = restauranteBody.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaReposityImp.findById(cozinhaId);
		if(!cozinha.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha de ID %d não existe", cozinhaId));
		}
		// Copia as informações do restaurante atual para o restaurante novo.
		BeanUtils.copyProperties(restaurante, restauranteBody,
				"id", "formasPagamento", "endereco");
		
		return restauranteRepository.save(restauranteBody);
	}
}
