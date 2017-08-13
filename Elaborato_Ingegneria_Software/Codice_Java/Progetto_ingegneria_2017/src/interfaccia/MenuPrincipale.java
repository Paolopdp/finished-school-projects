package interfaccia;

import javax.swing.JPanel;

import utility.Carrello;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * Classe MenuPrincipale
 * Mostra il menu principale, da dove è possibile proseguire con l'acquisto o tornare al catalogo
 *
 */
public class MenuPrincipale extends JPanel implements ActionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	private PreviewCarrello pannelloCarrello;
	private Pannello framePrincipale;
	private boolean isPreviewChiusa = false;
	
	private JLabel lblTotArticoli;
	private JButton btnVisualizzaCatalogo;
	private JButton btnCheckout;

	
	public MenuPrincipale(Pannello framePrincipale) {

		this.framePrincipale = framePrincipale;
		this.framePrincipale.impostaTitolo("Menu principale");
		this.setLayout(null);
		this.setPreferredSize(new Dimension(506, 303));
		
		pannelloCarrello = new PreviewCarrello();
		pannelloCarrello.setBounds(242, 43, 258, 245);
		pannelloCarrello.setVisible(false);
		add(pannelloCarrello);
		JLabel txtNomeCognome = new JLabel("Benvenuto, ");
		txtNomeCognome.setFont(new Font("Arial", Font.BOLD, 12));
		txtNomeCognome.setBounds(20, 9, 97, 16);
		add(txtNomeCognome);

		if(!(framePrincipale.getUtenteGlobale()==null)){
			setBackground(new Color(240,193,75));
			
			JLabel lblNomeCognome = new JLabel(framePrincipale.getUtenteGlobale().getNomeCognome());
			lblNomeCognome.setFont(new Font("Arial", Font.ITALIC, 11)); 
			lblNomeCognome.setBounds(87, 10, 145, 16);
			add(lblNomeCognome);
			
			JLabel lblCf = new JLabel(framePrincipale.getUtenteGlobale().getCf());
			lblCf.setFont(new Font("Arial", Font.ITALIC, 11)); 
			lblCf.setBounds(112, 37, 145, 16);
			add(lblCf);
			
			
			JLabel txtCf = new JLabel("Codice fiscale:");
			txtCf.setFont(new Font("Arial", Font.BOLD, 12));
			txtCf.setBounds(20, 37, 97, 16);
			add(txtCf);
			
			JLabel txtTipoCliente = new JLabel("Sei loggato come :");
			txtTipoCliente.setFont(new Font("Arial", Font.BOLD, 12)); 
			txtTipoCliente.setBounds(20, 65, 134, 16);
			add(txtTipoCliente);
			
			JLabel lbluser = new JLabel(framePrincipale.getUtenteGlobale().getUsername());
			lbluser.setFont(new Font("Arial", Font.ITALIC, 11));
			lbluser.setBounds(134, 64, 100, 16);
			add(lbluser);
			
		}  

		btnVisualizzaCatalogo = new JButton("Visualizza catalogo");
		if(!(framePrincipale.getUtenteGlobale()==null))
			framePrincipale.verifyButton(btnVisualizzaCatalogo);
		btnVisualizzaCatalogo.setBounds(20, 93, 188, 29);
		add(btnVisualizzaCatalogo);
		btnVisualizzaCatalogo.addActionListener(this);
		
		btnCheckout = new JButton("Checkout");
		if(!(framePrincipale.getUtenteGlobale()==null))
			framePrincipale.verifyButton(btnCheckout);
		btnCheckout.setBounds(20, 248, 188, 40);
		add(btnCheckout);
		btnCheckout.addActionListener(this);
				
		JLabel icoCarrello = new JLabel();
		icoCarrello.setIcon(new ImageIcon("img/carrelloHome.png"));
		icoCarrello.setBounds(321, 9, 45, 34);
		add(icoCarrello);
		
		JLabel lblCarrelloSpesa = new JLabel("CARRELLO SPESA");
		lblCarrelloSpesa.setFont(new Font("Arial", Font.BOLD, 10)); 
		lblCarrelloSpesa.setBounds(370, 9, 100, 16);
		add(lblCarrelloSpesa);

		lblTotArticoli = new JLabel(String.format("<html><span style='font-size:7px'>%d Prodotto(i) - %.2f \u20ac \u25b8</span></html>", Carrello.getTotCds(), Carrello.getPrezzoTotale()));
		  lblTotArticoli.setForeground(Color.decode("#990000"));
		  lblTotArticoli.setCursor(new Cursor(Cursor.HAND_CURSOR));
		  lblTotArticoli.setBounds(370, 26, 133, 16);
		  add(lblTotArticoli);
		  lblTotArticoli.addMouseListener(this);
		  
		  
		
	}
	
	public void actionPerformed(ActionEvent e) {
		framePrincipale.getContentPane().removeAll();
		if(e.getSource() == btnVisualizzaCatalogo){
			framePrincipale.getContentPane().add(new CatalogoCd(framePrincipale));
		}else if(e.getSource() == btnCheckout){
			try {
				framePrincipale.getContentPane().add(new Checkout(framePrincipale));
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		}
		framePrincipale.pack();
	}
	

	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == lblTotArticoli){
			isPreviewChiusa = (lblTotArticoli.getText().indexOf("\u25b8") > -1);
			pannelloCarrello.setVisible((isPreviewChiusa) ? true : false);
			if(isPreviewChiusa){
				lblTotArticoli.setText(String.format("<html><span style='font-size:7px'>%d Prodotto(i) - %.2f \u20ac \u25be</span></html>", Carrello.getTotCds(), Carrello.getPrezzoTotale()));
			}else{
				lblTotArticoli.setText(String.format("<html><span style='font-size:7px'>%d Prodotto(i) - %.2f \u20ac \u25b8</span></html>", Carrello.getTotCds(), Carrello.getPrezzoTotale()));
			}
		}
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}
}
