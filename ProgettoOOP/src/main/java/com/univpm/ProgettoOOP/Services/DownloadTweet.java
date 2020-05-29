package com.univpm.ProgettoOOP.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import com.univpm.ProgettoOOP.Model.Tweet;
import com.univpm.ProgettoOOP.Util.ParsingJSON;

/**
 * Classe per il download dei tweet.
 * @author Ricciardi Nicola
 * @author Rendina Michele Pio
 */
public class DownloadTweet 
{
	/**
	 * Metodo statico che preleva i tweet dall'URL passato dal controller, successivamente
	 * effettua l'estrapolazione dei soli parametri che ci servono per creare il nostro oggetto
	 * Twett, e passa questi datti alla classe che si occupa della creazione di questi oggetti.
	 * @return listaDeiTweet Ritorna al controller la lista dei tweet modellati.
	 * @throws LangException 
	 */
	public static JSONArray getTweet(String url)
	{
		try 
		{
			 URLConnection openConnection = new URL(url).openConnection();
	         openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
	         InputStream in = openConnection.getInputStream();
			
			 String data = "";
			 String line = "";
			 try 
			 {
				 InputStreamReader inR = new InputStreamReader(in);
				 BufferedReader buf = new BufferedReader(inR);
			  
				 while ((line = buf.readLine()) != null) 
				 {
					 data += line;
				 }
			 } 
			 catch (IOException e) 
			 {
		    		System.out.println("ERRORE. OPERAZIONE I/O INTERROTTA.");
		    		System.out.println("MESSAGGIO: " + e.getMessage());
		    		System.out.println("CAUSA: " + e.getCause());
			 }
			 finally
			 {
				 in.close();
			 }
			
			 JSONObject obj = (JSONObject) JSONValue.parseWithException(data);
			 JSONArray objArray = (JSONArray) obj.get("statuses");
			 ArrayList<Tweet> array = new ArrayList<Tweet>();
			 
			 for(Object o: objArray)
			 {
					if (o instanceof JSONObject)
					{
				    	JSONObject o1 = (JSONObject) o; 
				    	try
				    	{
					    	String DataCreazione = (String) o1.get("created_at");
					    	String TestoTweet = (String) o1.get("text");
					    	String ID_Tweet = (String) o1.get("id_str");
					    	String LinguaTweet = (String) o1.get("lang");
					    	String PosizioneTweet = (String) o1.get("place");
					    	if(PosizioneTweet == null) PosizioneTweet = "Not Aviable";
				    		array = BuildingArrayTweet.Building(ID_Tweet,DataCreazione,
																TestoTweet,LinguaTweet,
																PosizioneTweet);
				    	}
				    	catch(Exception e)
				    	{
				    		System.out.println("ERRORE. OPERAZIONE INTERROTTA NEL PRELEVAGGIO DEI PARAMETRI.");
				    		System.out.println("MESSAGGIO: " + e.getMessage());
				    		System.out.println("CAUSA: " + e.getCause());

				    	}
				    	
				    	
				    	
				    	//String NomeUtente = (String) o1.get("lang");
				    	//String ID_Utente = (String) o1.get("name");	
				 	}
			 }
			 
			 JSONArray listaDeiTweet = new JSONArray();
			 listaDeiTweet = (JSONArray) JSONValue.parseWithException(ParsingJSON.ParsingToJSON(array));
			 /*
			  * Inserisco il metodo .clear() dell'arrayList poiché se non viene inserito 
			  * vi è presente un bug in cui sono presenti sia i tweet della ricerca nuova,
			  * sia i tweet della ricerca vecchia.
			  */
			 array.clear();
			 return listaDeiTweet;
			 
		}
		catch (IOException | ParseException e)
		{
    		System.out.println("ERRORE. OPERAZIONE I/O - PARSING INTERROTTA .");
    		System.out.println("MESSAGGIO: " + e.getMessage());
    		System.out.println("CAUSA: " + e.getCause());
		} 
		catch (Exception e) 
		{
    		System.out.println("ERRORE GENERICO.");
    		System.out.println("MESSAGGIO: " + e.getMessage());
    		System.out.println("CAUSA: " + e.getCause());
		}
		
		return null;
	}
}