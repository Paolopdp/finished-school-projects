package interfaccia;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

import utility.Funzioni;
/**
 * Classe Immagini
 * Mostra una versione ingradita dell'immagine di copertina se clicco sopra l'immagine
 *
 */
public class Immagini extends JFrame{
	private static final long serialVersionUID = 1L;
		
	public Immagini(){
		super();
	}
	
	public Immagini(String img) throws SQLException, IOException{
		JPanel pannello = new JPanel();
		this.setPreferredSize(new Dimension(450, 450));				
		JLabel imgLbl = new JLabel();
		imgLbl.setIcon(Funzioni.ridimensionaImg(Funzioni.leggereImmagine(img),350,350));
		imgLbl.setBounds(50,50, 400,400);
		pannello.add(imgLbl);
        this.getContentPane().add(pannello);
		avvia();
	}
	
	public void avvia(){
		this.setTitle("Immagini");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		this.setLocation ( ( screenSize.width / 2 )-270, (
		screenSize.height / 2 )-200);
		this.pack();
	}
}