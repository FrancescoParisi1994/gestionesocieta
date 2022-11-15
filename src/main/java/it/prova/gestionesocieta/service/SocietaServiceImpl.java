package it.prova.gestionesocieta.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.exception.MyException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {

	@Autowired
	private SocietaRepository societaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void aggiungi(Societa societa) {
		societaRepository.save(societa);
		if (societa.getId()==null&&societa.getDipendenti().isEmpty()) {
			throw new RuntimeException("Inserimento societa fallito");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Societa> listAll() {
		return (List<Societa>) societaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Societa> findByExample(Societa societa){
		
		String query="select a from Societa a where a.id=a.id";
		
		if (StringUtils.isNotBlank(societa.getRagioneSociale())) {
			query+=" and a.ragioneSociale like '%"+societa.getRagioneSociale()+"%'";
		}
		if (StringUtils.isNotBlank(societa.getIndirizzo())) {
			query+=" and a.indirizzo like '%"+societa.getIndirizzo()+"%'";
		}
		if (!(societa.getDataFondazione()==null)) {
			query+=" and a.dataFondazione >= "+new java.sql.Date(societa.getDataFondazione().getTime());
		}
		
		return entityManager.createQuery(query,Societa.class).getResultList();
	}
	
	@Override
	@Transactional
	public void rimuovi(Societa societa) {
		if (!societaRepository.findEager(societa.getId()).getDipendenti().isEmpty()) {
			throw new MyException("La societa non puo essere eliminata perche ha ancora dipendenti");
		}
		societaRepository.delete(societa);
	}

	@Override
	@Transactional(readOnly = true)
	public Societa findEager(Societa societa) {
		return societaRepository.findEager(societa.getId());
	}

	@Override
	public Societa findById(Long id) {
		return societaRepository.findById(id).orElse(null);
	}

	@Override
	public List<Societa> trovaSocietaConDipendentiConRALMAggioreDi(int ral) {
		return societaRepository.findAllDistinctByDipendenti_ReditoAnnuoLordoGreaterThan(ral);
	}

}
