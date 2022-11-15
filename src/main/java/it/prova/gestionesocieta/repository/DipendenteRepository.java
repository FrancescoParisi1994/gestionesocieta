package it.prova.gestionesocieta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>,QueryByExampleExecutor<Dipendente> {

	public Dipendente findFirst1BySocieta_DataFondazioneLessThanOrderByDataAssunzioneAsc(Date date);
}
