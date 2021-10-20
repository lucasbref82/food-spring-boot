package com.food.api.repository;

import java.util.List;

import com.food.api.model.Permissao;

public interface PermissaoRepository {
	List<Permissao>  todas();
	Permissao buscarPorId(Long id);
	Permissao salvar(Permissao permissao);
	Permissao deletar(Permissao permissao);
}
