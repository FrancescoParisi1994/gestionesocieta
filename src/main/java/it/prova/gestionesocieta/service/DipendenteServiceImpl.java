package it.prova.gestionesocieta.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService{

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Override
	@Transactional
	public void aggiungi(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
		if (dipendente.getId()==null) {
			throw new RuntimeException("Inserimento dipendente fallito");
		}
	}
	
	@Transactional
	public Dipendente findInId(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}



	@Override
	@Transactional
	public void rimuovi(Dipendente dipendente) {
		dipendenteRepository.delete(dipendente);
	}

	@Override
	@Transactional
	public Dipendente dipendentePiuAnzianoDelleSocietaFondatePrimaDi(Date date) {
		return dipendenteRepository.findFirst1BySocieta_DataFondazioneLessThanOrderByDataAssunzioneAsc(date);
	}
	
	
	
}
