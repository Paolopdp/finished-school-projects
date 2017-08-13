package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DBManager {
	private static Connection conn = null;

// Implementazione del gestore connessioni con il pattern singleton
    public static Connection getConnessione() {
       	Properties prop = new Properties();
    	InputStream input = null;
    	try {
			input = new FileInputStream("config.properties");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    	try {
			prop.load(input);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	String url = prop.getProperty("jdbc.url");
    	String dbName = prop.getProperty("jdbc.dbName");
    	String userName = prop.getProperty("jdbc.userName");
    	String password = prop.getProperty("jdbc.password");
		try {
			if(conn == null) conn = DriverManager.getConnection(url + dbName , userName,password); // Connessione
//conn = DriverManager.getConnection(url + dbName);
		} catch (SQLException e) {
			System.out.println("Errore nella connessione al DB");
			System.out.println("Errore = " + e.getMessage());
		}
	    return conn;
    }
    public static void chiudiConnessione(Connection conn) {
        try { 
        	conn.close(); 
        	} 
        catch (SQLException e) {
        	System.out.println("Errore nella chiusura del database");
			System.out.println("Errore = " + e.getMessage());
        }
    }
// Ritorna come risultato una lista con le tuple risultanti dalla query e richiede (NomeQuery,Parametri)
    public static List<HashMap<String,Object>> selectQuery(String query, String...parametri) {
    	List<HashMap<String,Object>> listaRisultati = new ArrayList<HashMap<String,Object>>();
    	Connection conn = getConnessione();
    	ResultSet rs = null;
    	ResultSetMetaData metaData = null;
    	
    	try (PreparedStatement pst = conn.prepareStatement(query)) {
			// Inserimento parametri
			// In base al tipo di parametro letto, viene trasformato nel tipo corrispondente per evitare sql injection
	    	for(int i = 0; i < parametri.length; i++){
	    		if(Funzioni.isTimestampCorretto(parametri[i].toString())){
	    			pst.setTimestamp(i+1, Timestamp.valueOf(parametri[i].toString()));
	    		}else if(Funzioni.isBoolean(parametri[i].toString())){
	    			pst.setBoolean(i+1, Boolean.getBoolean(parametri[i].toString()));
	    		}else if(Funzioni.isDouble(parametri[i].toString())){
	    			pst.setDouble(i+1, Double.parseDouble(parametri[i].toString()));
	    	    }else if(Funzioni.isInteger(parametri[i].toString())){
	    			pst.setInt(i+1, Integer.parseInt(parametri[i].toString()));
	    		}else{
	    			pst.setString(i+1, parametri[i].toString());
	    		}
	    	}
	    	// Eseguo la query, quindi vengono messi i risultati nell'insieme rs, i domini dei risultati in metadata e viene salvato il numero di colonne
        	rs = pst.executeQuery();
        	metaData = rs.getMetaData();
        	int colonne = metaData.getColumnCount();
        	// Scorro l'insieme dei risultati
            while (rs.next()) {
            	HashMap<String,Object> row = new HashMap<String, Object>(colonne); // Creo un HashMap di dimensioni pari a quello delle colonne
                for (int i = 1; i <= colonne; i++) { 
                	String isNull = (rs.getObject(i) == null ? "" : rs.getObject(i).toString()); // Se è null, scrivo null nella colonna, altrimenti scrivo la tupla
                	row.put(metaData.getColumnName(i),isNull); // mette nell' Hashmap Row : Nomecolonna,valore. Esempio: Codice=10
                }
                listaRisultati.add(row); // metto nella lista dei risultati la mia HashMap. Qui ho il risultato della mia query
            }
            
        }catch (SQLException e) {
            System.out.println("Errore nell'esecuzione della query");
            System.out.println("Errore = " + e.getMessage());
        }
    	
    	return listaRisultati; // ritorno il risultato della query
    }
    
    public static int updateQuery(String query, Boolean ritornaIdGenerato, Object...parametri) {
    	Connection conn = getConnessione();
    	try (PreparedStatement pst = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
    		// Inserimento parametri
    		// In base al tipo di parametro letto, viene trasformato nel tipo corrispondente per evitare sql injection
    		// le variabili di tipo time,boolean,double,integer hanno i loro metodi per essere convertite in stringhe
	    	for(int i = 0; i < parametri.length; i++){
	    		if(Funzioni.isTimestampCorretto(parametri[i].toString())){
	    			pst.setTimestamp(i+1, Timestamp.valueOf(parametri[i].toString()));
	    		}else if(Funzioni.isBoolean(parametri[i].toString())){
	    			pst.setBoolean(i+1, Boolean.getBoolean(parametri[i].toString()));
	    		}else if(Funzioni.isDouble(parametri[i].toString())){
	    			pst.setDouble(i+1, Double.parseDouble(parametri[i].toString()));
	    	    }else if(Funzioni.isInteger(parametri[i].toString())){
	    			pst.setInt(i+1, Integer.parseInt(parametri[i].toString()));
	    		}else{
	    			pst.setString(i+1, parametri[i].toString());
	    		}
	    	}
	    	int ris = pst.executeUpdate();
	    	// Se voglio che ritorni l'id generato, il campo deve essere true
	    	if(ritornaIdGenerato) {
			   ResultSet rs = pst.getGeneratedKeys();
			   rs.next();
			   return (rs.getInt(1));
	    	}else{
	    		return ris;
	    	}
        }catch (SQLException e) {
            System.out.println("Errore nell'esecuzione della query");
            System.out.println("Errore = " + e.getMessage());
        }
    	return -1;
    }
    // Controllo che la query ritorni qualcosa
    public static boolean CheckEsiste(String query, String...parametri){
		try {
			return !(selectQuery(query, parametri).isEmpty());
		} catch (Exception e) {
			return false;
		}
    }
}