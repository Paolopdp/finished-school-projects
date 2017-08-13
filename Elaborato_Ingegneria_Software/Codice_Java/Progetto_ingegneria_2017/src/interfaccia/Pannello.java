package interfaccia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import utility.DBManager;
import utenti.Utente;
/**
 * Classe Pannello
 * Estensione di JFrame per creare Frame di default
 *
 */
public class Pannello extends JFrame {
	private static final long serialVersionUID = 1L;
	private Utente utenteGlobale;
	private Utente utenteOccasionale;
	public Pannello(){
		super();
	}
	
	public void avvia(){
		this.setTitle("Inserimento cliente");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		this.setLocation ( ( screenSize.width / 2 )-200, (
		screenSize.height / 2 )-350);
		this.add(new CatalogoCd(this));
		this.pack();
	}
	
	public void impostaTitolo(String titolo){
		this.setTitle(titolo);
	}
	
/** 
 * Utilizzato quando ho settato l'utente globale per ritornare l'utente, si usa per costruire correttamente i vari frame, con le
 * eccezioni legate alla presenza o meno dell'utente registrato
*/
	public Utente getUtenteGlobale(){
		return utenteGlobale;
	}
	
	public Utente getUtenteOccasionale(){
		return utenteOccasionale;
	}
/**
 * Setta utente globale	
 * @param ut, l'utente
 */
	public void setUtenteGlobale(Utente ut){
		utenteGlobale = ut;
	}
/**
 * Setta utente occasionale
 * @param ut, l'utente
 */
	public void setUtenteOccasionale(Utente ut){
		utenteOccasionale = ut;
	}
	
/**
* metodo invocato quando ut è settato per cambiare lo sfondo e il testo di un bottone
* @param bottone è il bottone da modificare
* @return il bottone modificato
*/
	public void verifyButton(JButton bottone){
		bottone.setBackground(new Color(144,115,45));
		bottone.setForeground(Color.WHITE);
	}
/**
 *  Chiudi la connessione al Database
 */
	public void chiudiConnessione(){
		DBManager.chiudiConnessione(DBManager.getConnessione());
	}
}