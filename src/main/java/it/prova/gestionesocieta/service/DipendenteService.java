package it.prova.gestionesocieta.service;

import java.util.Date;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {

	public void aggiungi(Dipendente dipendente);
	
	public void rimuovi(Dipendente dipendente);
	
	public void aggiorna(Dipendente dipendente);
	
	public Dipendente findInId(Long id);
	
	public Dipendente dipendentePiuAnzianoDelleSocietaFondatePrimaDi(Date date);
}
