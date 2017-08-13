package interfaccia;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import utility.DBManager;
import utility.EnumQuery;
import utility.Funzioni;
/**
 * Classe InserimentoPersona
 * Implementa l'interfaccia per la registrazione di un nuovo utente
 *
 */
public class InserimentoPersona extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L; 																																								
	private Pannello framePrincipale;	
	private JButton btnConferma;
	private JButton btnAnnulla;

	private JPasswordField txtPass1;
	private JPasswordField txtPass2;
	private JTextField txtUsername;
	private JTextField txtCittà;
	private JTextField txtNome;
	private JTextField txtTelefono;
	private JTextField txtCf;
	private JTextField txtCognome;
	private JTextField txtCellulare;
	private JLabel icoUsername;
	private JLabel icoPass1;
	private JLabel icoPass2;
	private JLabel icoCittà;
	private JLabel icoNome;
	private JLabel icoCognome;
	private JLabel icoCf;
	private JLabel icoTelefono;
	private JLabel icoCellulare;
	
	public InserimentoPersona(Pannello framePrincipale) {
		
		this.framePrincipale = framePrincipale;
		this.setPreferredSize(new Dimension(388, 392));
		setLayout(null);
		
		JLabel lblUsername = new JLabel("Nome utente:");
		lblUsername.setBounds(16, 26, 137, 16);
		add(lblUsername);
		
		JLabel lblPass1 = new JLabel("Password:");
		lblPass1.setBounds(16, 55, 86, 16);
		add(lblPass1);
		
		JLabel lblSuggPassw = new JLabel("* La password deve essere composta da almeno 8 caratteri");
		lblSuggPassw.setFont(new Font("Arial", Font.ITALIC, 10)); 
		lblSuggPassw.setBounds(16, 78, 320, 16);
		add(lblSuggPassw);
		
		JLabel lblPass2 = new JLabel("Reinserisci la password:");
		lblPass2.setBounds(16, 106, 158, 16);
		add(lblPass2);
		
		JLabel lblCittà = new JLabel("Città:");
		lblCittà.setBounds(16, 134, 86, 16);
		add(lblCittà);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(16, 162, 99, 16);
		add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome:");
		lblCognome.setBounds(16, 190, 137, 16);
		add(lblCognome);
		
		JLabel lblCf = new JLabel("Codice fiscale:");
		lblCf.setBounds(16, 218, 118, 16);
		add(lblCf);
		
		JLabel lblTelefono = new JLabel("Numero di telefono:");
		lblTelefono.setBounds(16, 246, 158, 16);
		add(lblTelefono);
		
		JLabel lblCellulare = new JLabel("Numero di cellulare:");
		lblCellulare.setBounds(16, 276, 137, 16);
		add(lblCellulare);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(181, 21, 158, 26);
		add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPass1 = new JPasswordField();
		txtPass1.setBounds(181, 50, 158, 26);
		add(txtPass1);
		txtPass1.setColumns(10);
		
		txtPass2 = new JPasswordField();
		txtPass2.setBounds(181, 101, 158, 26);
		add(txtPass2);
		txtPass2.setColumns(10);
		
		txtCittà = new JTextField();
		txtCittà.setBounds(181, 129, 158, 26);
		txtCittà.setDocument(new JTextFieldLimit(50));
		add(txtCittà);
		txtCittà.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setBounds(181, 157, 158, 26);
		txtNome.setDocument(new JTextFieldLimit(50));
		add(txtNome);
		txtNome.setColumns(10);
		
		txtCognome = new JTextField();
		txtCognome.setBounds(181, 185, 158, 26);
		txtCognome.setDocument(new JTextFieldLimit(50));
		add(txtCognome);
		txtCognome.setColumns(10);
		
		txtCf = new JTextField();
		txtCf.setBounds(181, 213, 158, 26);
		txtCf.setColumns(10);
		// Utilizzo la classe JTextFieldLimit e imposto il limite di questo campo a 16 caratteri
		txtCf.setDocument(new JTextFieldLimit(16));
		add(txtCf);
				
		txtTelefono = new JTextField();
		txtTelefono.setBounds(181, 241, 158, 26);
		txtTelefono.setDocument(new JTextFieldLimit(11));
		add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtCellulare = new JTextField();
		txtCellulare.setBounds(181, 271, 158, 26);
		txtCellulare.setDocument(new JTextFieldLimit(11));
		add(txtCellulare);
		txtCellulare.setColumns(10);
		
		btnConferma = new JButton("Conferma");
		btnConferma.setBounds(16, 348, 117, 29);
		add(btnConferma);
		
		btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(219, 348, 117, 29);
		add(btnAnnulla);


		btnConferma.addActionListener(this);
		btnAnnulla.addActionListener(this);	
		
		icoUsername = new JLabel();
		icoUsername.setBounds(351, 26, 61, 16);
		add(icoUsername);
		
		icoPass1 = new JLabel();
		icoPass1.setBounds(351, 55, 61, 16);
		add(icoPass1);
		
		icoPass2 = new JLabel();
		icoPass2.setBounds(351, 106, 61, 16);
		add(icoPass2);
		
		icoCittà = new JLabel();
		icoCittà.setBounds(351, 134, 61, 16);
		add(icoCittà);
		
		icoNome = new JLabel();
		icoNome.setBounds(351, 162, 61, 16);
		add(icoNome);
		
		icoCognome = new JLabel();
		icoCognome.setBounds(351, 190, 61, 16);
		add(icoCognome);
		
		icoCf = new JLabel();
		icoCf.setBounds(351, 218, 61, 16);
		add(icoCf);
		
		icoTelefono = new JLabel();
		icoTelefono.setBounds(351, 246, 61, 16);
		add(icoTelefono);
		
		icoCellulare = new JLabel();
		icoCellulare.setBounds(351, 274, 61, 16);
		add(icoCellulare);
	}
	
	private boolean checkCampi(){
		boolean userV = txtUsername.getText().isEmpty();
		boolean pass1V = String.valueOf(txtPass1.getPassword()).length() < 8;
		boolean pass2V = !(String.valueOf(txtPass1.getPassword()).equals(String.valueOf(txtPass2.getPassword())));
		boolean CittàV = txtCittà.getText().isEmpty();
		boolean nomeV = txtNome.getText().isEmpty();
		boolean cognomeV = txtCognome.getText().isEmpty();
		boolean cfV = txtCf.getText().length() < 16;
		boolean telefonoV = !Funzioni.isInteger(txtTelefono.getText()) || txtTelefono.getText().isEmpty();
		boolean cellulareV = !Funzioni.isInteger(txtCellulare.getText()) && !txtCellulare.getText().isEmpty();
		icoUsername.setIcon(userV ? new ImageIcon("img/errore.png") : new ImageIcon("img/caricamento.gif"));
		icoPass1.setIcon(pass1V ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoPass2.setIcon(pass2V ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoCittà.setIcon(CittàV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoNome.setIcon(nomeV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoCognome.setIcon(cognomeV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoCf.setIcon(cfV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoTelefono.setIcon(telefonoV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoCellulare.setIcon(cellulareV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		
		if(!userV){
			userV = DBManager.CheckEsiste(EnumQuery.CHECK_USERNAME.getValore(), txtUsername.getText());
			icoUsername.setIcon(userV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		}
		
		return !(userV || pass1V || pass2V || CittàV || nomeV || cognomeV || cfV || telefonoV || cellulareV);
	}
		
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnConferma){
        	if(checkCampi()){
        		int inserisciDB = DBManager.updateQuery(EnumQuery.INSERISCI_CLIENTE.getValore(), false,
        				txtCf.getText().toUpperCase(),
        				txtUsername.getText(),
        				Funzioni.strToUnixCrypt(String.valueOf(txtPass1.getPassword())),
        				txtNome.getText(),
        				txtCognome.getText(),
        				txtCittà.getText(),
        				txtTelefono.getText(),
        				txtCellulare.getText(),
        				"REGISTRATO"
        				);
        		if(inserisciDB > 0){
        			JOptionPane.showMessageDialog(null, "Cliente registrato correttamente!\nE' ora possibile effettuare il login");
                	framePrincipale.getContentPane().removeAll();
                    framePrincipale.getContentPane().add(new Login(framePrincipale));
                    framePrincipale.pack();
        		}else{
        			JOptionPane.showMessageDialog(null, "Errore nella registrazione del cliente!");
        		}
        	}
        }else if(e.getSource() == btnAnnulla){
        	framePrincipale.getContentPane().removeAll();
            framePrincipale.getContentPane().add(new Login(framePrincipale));
            framePrincipale.pack();
        }
	 } 
}
