package utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.codec.digest.UnixCrypt;

public class Funzioni {


/**
 * Verifica che la data abbia un formato corretto
 * @param data passata come stringa
 * @return true se il formato è corretto (dd-MM-yyyy) , altrimenti false.
 */

	
/**
* Verifica che il timestamp abbia un formato corretto
* @param timestamp passato come stringa
* @return true se il formato è corretto (yyyy-MM-dd HH:mm:ss.SSSSSS), altrimenti false.
*/

	public static boolean isTimestampCorretto(String str)
	{ 
	    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    try{
	       format.parse(str);
	       return true;
	    } catch(ParseException e) {
	        return false;
	    }
	}
	
/**
 * Verifica se la stringa passata è un integer
 * @param stringa da verificare
 * @return true se rappresenta un intero, altrimenti false
 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("\\d+");
		return (pattern.matcher(str).matches());
	}
	
/**
 * Verifica se la stringa passata è un boolean
 * @param stringa da verificare
 * @return true se rappresenta un boolean, altrimenti false
 */
	public static boolean isBoolean(String str) {
		return (str.toLowerCase().equals("false") || str.toLowerCase().equals("true"));
	}

/**
 * Verifica se la stringa passata è un double
 * @param stringa da verificare
 * @return true se rappresenta un double, altrimenti false
 */
	public static boolean isDouble(String str) {
		try {  
			Double.parseDouble(str);
		    return (str.indexOf(".") > -1);
		} catch (NumberFormatException e) {  
			return false;  
		} 
	}
	
/**
 * Trasforma un timestamp, nel formato dd-mm-yyyy
 * @param stringa da trasformare
 * @return dd-mm-yyyy
 */
	public static Timestamp toTimestamp(String str) {    
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(str);
            Timestamp tstamp = new Timestamp(date.getTime());
            return tstamp;
        } 
        catch (ParseException e) {
            return null;
        }
	}
	
/**
 * Formatta la data
 * @param stringa da trasformare
 * @return data formattata
 */
	public static String formattaData(String ts)  {   
		return (ts.isEmpty() ? "" : ts.split("-")[2] + "-" + ts.split("-")[1] + "-" +  ts.split("-")[0]);
	}
	
/**
 * Formatta la data
 * @param timestamp da trasformare
 * @return data formattata
 */
	public static String formattaData(Timestamp ts) {    
		String dataStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(ts.getTime()));
		return dataStr;
	}

/**
 * Verifica se la stringa passata rappresenta un indirizzo email corretto
 * @param è la stringa che rappresente l'email
 * @return true se corretto, false altrimenti
 */
	public static boolean isEmailCorretta(String email){
       String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
       java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
       java.util.regex.Matcher m = p.matcher(email);
       return m.matches();
	}

/**
 * Trasforma una stringa in una password attraverso il metodo UnixCrypt
 * @param stringa da trasformare attraverso la funzione hash
 * @return password criptata
 */
	public static String strToUnixCrypt(String pass){
		String password=UnixCrypt.crypt(pass,"italia1");
		return password;
	}

/**
 * Ridimensiona l'immagine secondo i parametri specificati
 * @param img immagine passata
 * @param width larghezza desiderata
 * @param heigth altezza desiderata
 * @return immagine ridimensionata
 */
	public static ImageIcon ridimensionaImg(ImageIcon img, int width, int heigth){
		Image ridimensionata=null;
		 try {
			 ridimensionata = img.getImage().getScaledInstance(width, heigth, Image.SCALE_SMOOTH);;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return (new ImageIcon(ridimensionata));
	}
	
/**
 * Prende l'ultima transazione dalla tabella vendite e ne aumenta il valore di 1
 * @return il valore della transazione attuale
 * @throws IOException 
 */
public static String getTransactionNumb() throws IOException{
		int transMax=0;
		String trans="";
		List<HashMap<String,Object>> getTransNumb = DBManager.selectQuery(
				EnumQuery.MAX_NTRANS.getValore());
		
		Iterator<HashMap<String,Object>> itr = getTransNumb.iterator();

	    while (itr.hasNext()) {
	    	HashMap<String,Object> riga = itr.next();
	    	trans=riga.get("max").toString();
	    	transMax=Integer.valueOf(trans);
	    	transMax=transMax+1;
	    	trans=Integer.toString(transMax);

	    	}
	    return trans;
	    	
	}

/**
 * Ritorna l'id assegnato al cliente non registrato
 * @return id
 * @throws IOException 
 */
public static String getIdClienteNotReg() throws IOException{
	int codice=0;
	String codiceAsStr="";
	List<HashMap<String,Object>> getId = DBManager.selectQuery(
			EnumQuery.MAX_CODICE_CLIENTE.getValore());
	
	Iterator<HashMap<String,Object>> itr = getId.iterator();

    while (itr.hasNext()) {
    	HashMap<String,Object> riga = itr.next();
    	codiceAsStr=riga.get("max").toString();
    	codice=Integer.valueOf(codiceAsStr);
    	codice=codice+1;
    	codiceAsStr=Integer.toString(codice);

    	}
    return codiceAsStr;
    	
}
/**
 * Ritorna l'immagine dato un nome
 * @param nome dell'immagine da mostrare
 * @return restituisce l'immagine da mostrare
 */
	public static ImageIcon leggereImmagine(String nomefile) throws SQLException, IOException {
		Connection conn = DBManager.getConnessione();
		PreparedStatement pss = conn.prepareStatement("SELECT img FROM immagine WHERE imgname = ?");
		pss.setString(1, nomefile);
		ImageIcon immagine=new ImageIcon();
		ResultSet rs = pss.executeQuery();
		while (rs.next()) {
		byte[] imgBytes = rs.getBytes(1);
		    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));
		    immagine=new ImageIcon(img);
		}
		rs.close();
		pss.close();
		return immagine;
		}

}