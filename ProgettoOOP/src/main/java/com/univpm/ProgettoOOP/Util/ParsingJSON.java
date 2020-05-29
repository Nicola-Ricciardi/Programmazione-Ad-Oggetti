package com.univpm.ProgettoOOP.Util;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.univpm.ProgettoOOP.Model.Tweet;

/**
 * Classe per il parsing dell'arraList di tweet nel formato JSON.
 * @author Ricciardi Nicola
 * @author Rendina Michele Pio
 */
public class ParsingJSON 
{
	/**
	 * Metodo statico che passato l'arrayList dei tweet modellati, lo converta nel formato JSON.
	 * @param array Lista di array contenente i tweet modellati.
	 * @return outFinal Contiene il nostro arrayList convertito nel formato JSON, in modo che il web server possa conprenderlo.
	 */
	public static String ParsingToJSON(ArrayList<Tweet> array)
	{
		Gson out = new GsonBuilder().setPrettyPrinting().create();
		String outFinal = out.toJson(array);
		return outFinal;
	}
}
