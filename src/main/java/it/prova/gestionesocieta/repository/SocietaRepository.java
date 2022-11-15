package it.prova.gestionesocieta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaRepository extends CrudRepository<Societa, Long>,QueryByExampleExecutor<Societa>{

	@Query("from Societa s left join fetch s.dipendenti d where s.id=?1")
	public Societa findEager(Long id);
	
	@EntityGraph(attributePaths = "dipendenti")
	public List<Societa> findAllDistinctByDipendenti_ReditoAnnuoLordoGreaterThan(int ral);
}
