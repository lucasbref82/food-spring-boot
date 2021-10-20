package com.food.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.food.api.exception.EntidadeEmUsoException;
import com.food.api.exception.RecursoNaoEncontradoException;
import com.food.api.model.Estado;
import com.food.api.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	EstadoRepository estadoRepository;

	public List<Estado> todas() {
		return estadoRepository.findAll();
	}

	public Estado buscarPorId(Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		if (estado.isEmpty()) {
			throw new RecursoNaoEncontradoException(String.format("Estado de ID %d não existe", id));
		}
		return estado.get();
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado alterar(Long id, Estado estadoNovo) {
		Optional<Estado> estadoAtual = estadoRepository.findById(id);
		if (estadoAtual.isEmpty()) {
			throw new RecursoNaoEncontradoException(String.format("Estado de ID %d não existe", id));
		}
		estadoNovo.setId(id);
		return estadoRepository.save(estadoNovo);
	}

	public void excluir(Long estadoId) {
		try {
			Estado estado = buscarPorId(estadoId);
			if (estado == null) {
				throw new RecursoNaoEncontradoException(
						String.format("Não existe um cadastro de estado com código %d", estadoId));
			}
			estadoRepository.delete(estado);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
		}
	}
}
