package com.food.api.repository;

import java.util.List;

import com.food.api.model.FormaPagamento;

public interface FormaPagamentoRepository {
	List<FormaPagamento> todas();
	FormaPagamento buscarPorId(Long id);
	FormaPagamento salvar(FormaPagamento formaPagamento);
	void deletar(FormaPagamento formaPagamento);
}
