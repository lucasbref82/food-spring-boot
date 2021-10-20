package com.food.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.api.exception.EntidadeNaoEncontradaException;
import com.food.api.exception.RecursoNaoEncontradoException;
import com.food.api.model.Cidade;
import com.food.api.model.Estado;
import com.food.api.repository.CidadeRepository;
import com.food.api.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadoRepository;

	public List<Cidade> todas() {
		return cidadeRepository.findAll();
	}

	
	public Cidade buscarPorId(Long id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		if(cidade.isEmpty()) {
			throw new RecursoNaoEncontradoException(String.format("Restaurente de id %d não econtrado", id));
		}
		return cidade.get();
	}
	
	public Cidade salvar(Cidade cidade) {
		
		Long idEstado = cidade.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(idEstado);
		
		if(estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado de id %d não existe.", idEstado));
		}
		return cidadeRepository.save(cidade);
	}
	
	public Cidade alterar(Long id, Cidade cidadeBody) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		if(cidade.isEmpty()) {
			throw new RecursoNaoEncontradoException(String.format("Restaurante de ID %d, não encontrado", id));
		}
		Long estadoId = cidadeBody.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		if(estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha de ID %d não existe", estadoId));
		}
		cidadeBody.setId(id);
		return cidadeRepository.save(cidadeBody);
	}
}
