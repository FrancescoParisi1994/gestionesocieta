package it.prova.gestionesocieta.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilityForm {

	public static void intro(String titolo) {
		System.out.println(titolo + ": INIZIO");
	}

	public static void outro(String titolo) {
		System.out.println(titolo + ": SUCCESSO");
	}

	public static Date stringToDate(String data) {
		Date result = null;
		try {
			result = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
