package it.prova.gestionesocieta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.utility.UtilityForm;

@Service
public class BatteriaTest {

	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private SocietaService societaService;
	
	//  Inserimento Società.
	public void testInserimentoSocieta() {
		String titolo="testInserimentoSocieta";
		UtilityForm.intro(titolo);
		Societa nuovaSocieta=new Societa("Iliad", "Via Sua", UtilityForm.stringToDate("05/04/2003"));
		societaService.aggiungi(nuovaSocieta);
		if (nuovaSocieta.getId()==null) {
			throw new RuntimeException(titolo+" inserimento fallito");
		}
		if (societaService.listAll().isEmpty()) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		UtilityForm.outro(titolo);
	}
	
	//findByExample di Società.
	public void testFindByExample() {
		String titolo="testFindByExample";
		UtilityForm.intro(titolo);
		Societa nuovaSocieta=new Societa("Unipol", "Indirizzo Azienda", UtilityForm.stringToDate("12/12/2012"));
		societaService.aggiungi(nuovaSocieta);
		if (nuovaSocieta.getId()==null) {
			throw new RuntimeException(titolo+" inserimento fallito");
		}
		if (societaService.findByExample(new Societa()).isEmpty()) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		if (societaService.findByExample(new Societa("U", "I", UtilityForm.stringToDate("11/12/2012"))).isEmpty()) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		UtilityForm.outro(titolo);
	}
	
	//Rimozione Società (se ha dipendenti lanciare eccezione).
	public void testRimuoviSocieta() {
		String titolo="testRimuoviSocieta";
		UtilityForm.intro(titolo);
		
		Societa nuovaSocieta=new Societa("Unipol", "Indirizzo Azienda", UtilityForm.stringToDate("12/12/2012"));
		Dipendente nuovoDipendente=new Dipendente("Mario", "Rossi", UtilityForm.stringToDate("13/12/2012"), 15000, nuovaSocieta);

		societaService.aggiungi(nuovaSocieta);
		if (nuovaSocieta.getId()==null&&nuovaSocieta.getDipendenti().isEmpty()) {
			throw new RuntimeException(titolo+" inserimento societa fallito");
		}
		
		dipendenteService.aggiungi(nuovoDipendente);
		if (nuovoDipendente.getId()==null) {
			throw new RuntimeException(titolo+" inserimento dipendente fallito");
		}
		
		//provo la costum exception
		try {
			societaService.rimuovi(nuovaSocieta);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if (nuovaSocieta.getId()==null) {
			throw new RuntimeException(titolo+" costum exception fallita");
		}
		
		//adesso elimino il dipendente/i dipendenti
		dipendenteService.rimuovi(nuovoDipendente);
		if (!societaService.findEager(nuovaSocieta).getDipendenti().isEmpty()) {
			throw new RuntimeException(titolo+" eliminazione dipendenti fallita");
		}
		
		societaService.rimuovi(nuovaSocieta);
		if (!(societaService.findById(nuovaSocieta.getId())==null)) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		
		UtilityForm.outro(titolo);
	}
	
	//Modifica dipendente 
	public void testModificaDipendente() {
		String titolo="testModificaDipendente";
		UtilityForm.intro(titolo);

		Societa nuovaSocieta=new Societa("Unipol", "Indirizzo Azienda", UtilityForm.stringToDate("12/12/2012"));
		Dipendente nuovoDipendente=new Dipendente("Mario", "Rossi", UtilityForm.stringToDate("13/12/2012"), 15000, nuovaSocieta);

		try {
			societaService.aggiungi(nuovaSocieta);
			dipendenteService.aggiungi(nuovoDipendente);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception
		}
		
		long idConfronto=nuovoDipendente.getId();

		nuovoDipendente.setNome("Luigi");
		dipendenteService.aggiorna(nuovoDipendente);
		
		Dipendente dipendenteDiConfronto=dipendenteService.findInId(idConfronto);
		if (dipendenteDiConfronto==null||!(dipendenteDiConfronto.getNome().equals("Luigi"))) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		UtilityForm.outro(titolo);
	}
	
	//La lista distinta delle società in cui lavora almeno un dipendente con una RAL a partire da 30000 euro
	public void testSocietaConDipendenteConRALMAggioreDi30000() {
		String titolo="testSocietaConDipendenteConRALMAggioreDi30000";
		UtilityForm.intro(titolo);

		Societa nuovaSocieta=new Societa("Unipol", "Indirizzo Azienda", UtilityForm.stringToDate("12/12/2012"));
		Dipendente nuovoDipendente=new Dipendente("Mario", "Rossi", UtilityForm.stringToDate("13/12/2012"), 30001, nuovaSocieta);

		try {
			societaService.aggiungi(nuovaSocieta);
			dipendenteService.aggiungi(nuovoDipendente);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception
		}
		
		if (societaService.trovaSocietaConDipendentiConRALMAggioreDi(30000).isEmpty()) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		UtilityForm.outro(titolo);
	}
	
	//dipendente più anziano  - lavorativamente parlando – delle società fondate prima del 1990
	public void testDipendentePiuAnzianoDelleSocietaFondatePrimaDel1990() {
		String titolo="testDipendentePiuAnzianoDelleSocietaFondatePrimaDel1990";
		UtilityForm.intro(titolo);
		
		Societa nuovaSocieta=new Societa("Unipol", "Indirizzo Azienda", UtilityForm.stringToDate("12/12/1890"));
		Dipendente nuovoDipendente=new Dipendente("Mario", "Rossi", UtilityForm.stringToDate("13/12/2012"), 30001, nuovaSocieta);

		try {
			societaService.aggiungi(nuovaSocieta);
			dipendenteService.aggiungi(nuovoDipendente);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception
		}
		
		if (dipendenteService.dipendentePiuAnzianoDelleSocietaFondatePrimaDi(UtilityForm.stringToDate("01/01/1990"))==null) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		UtilityForm.outro(titolo);
	}
}
