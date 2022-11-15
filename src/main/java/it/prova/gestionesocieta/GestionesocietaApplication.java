package it.prova.gestionesocieta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionesocieta.service.BatteriaTest;

@SpringBootApplication
public class GestionesocietaApplication implements CommandLineRunner{

	@Autowired
	private BatteriaTest batteriaTest;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("##########Inizio Test#############");
		
		batteriaTest.testInserimentoSocieta();
		batteriaTest.testFindByExample();
		batteriaTest.testRimuoviSocieta();
		batteriaTest.testModificaDipendente();
		batteriaTest.testSocietaConDipendenteConRALMAggioreDi30000();
		batteriaTest.testDipendentePiuAnzianoDelleSocietaFondatePrimaDel1990();
		
		System.out.println("###########Fine Test#############");
	}

	public static void main(String[] args) {
		SpringApplication.run(GestionesocietaApplication.class, args);
	}
	
	

}
