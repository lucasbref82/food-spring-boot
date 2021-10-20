package com.food.api.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.food.api.model.Restaurante;

@Repository
public class RestauranteRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

		/*
		 * var jpql = new StringBuilder(); jpql.append("from Restaurante where 0 = 0 ");
		 * 
		 * var parametros = new HashMap<String, Object>();
		 * 
		 * if (StringUtils.hasLength(nome)) { jpql.append("and nome like :nome ");
		 * parametros.put("nome", "%" + nome + "%"); }
		 * 
		 * if (taxaFreteInicial != null) {
		 * jpql.append("and taxaFrete >= :taxaInicial "); parametros.put("taxaInicial",
		 * taxaFreteInicial); }
		 * 
		 * if (taxaFreteFinal != null) { jpql.append("and taxaFrete <= :taxaFinal ");
		 * parametros.put("taxaFinal", taxaFreteFinal); }
		 * 
		 * // Executando a query tipada TypedQuery<Restaurante> query = manager
		 * .createQuery(jpql.toString(), Restaurante.class);
		 * 
		 * // Preenchendo os parametro com foreach parametros.forEach((chave, valor) ->
		 * query.setParameter(chave, valor));
		 * 
		 * return query.getResultList();
		 */

		/*
		 * CriteriaBuilder builder = manager.getCriteriaBuilder();
		 * CriteriaQuery<Restaurante> consulta = builder.createQuery(Restaurante.class);
		 * consulta.from(Restaurante.class); consulta.where();
		 * 
		 * TypedQuery<Restaurante> query = manager.createQuery(consulta); return
		 * query.getResultList();
		 * 
		 */

		// Construtor do criterio
		var builder = manager.getCriteriaBuilder();

		var criteria = builder.createQuery(Restaurante.class);

		// Raiz da query from Restaurante
		var root = criteria.from(Restaurante.class);
		
		// Lista de predicates
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			predicates.add(
					builder.like(
							root.get("nome"), "%" + nome + "%"));
		}
		if(taxaFreteInicial != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(
							root.get("taxaFrete"), taxaFreteInicial));
		}
		if(taxaFreteFinal != null) {
			predicates.add(
					builder.lessThanOrEqualTo(
							root.get("taxaFrete"), taxaFreteFinal));
		}
		
		// Percorrendo essa lista e adicionando na clausula WHERE
		/*
		predicates.forEach(predicate -> {
			criteria.where(predicate);
		});
		*/
		criteria.where(predicates.toArray(new Predicate[0]));
		// Todos filtros no where
		
	
		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();
	}
}
