package it.prova.gestionesocieta.service;

import java.util.List;
import java.util.Optional;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaService {

	public void aggiungi(Societa societa);
	
	public List<Societa> listAll();
	
	public List<Societa> findByExample(Societa societa);
	
	public void rimuovi(Societa societa);
	
	public Societa findEager(Societa societa);
	
	public Societa findById(Long id);
	
	public List<Societa> trovaSocietaConDipendentiConRALMAggioreDi(int ral);
}
