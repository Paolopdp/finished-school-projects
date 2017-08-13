package interfaccia;

import javax.swing.JPanel;
import javax.swing.JTextField;
import utenti.Utente;
import utenti.UtenteRegistrato;
import utenti.UtenteSemplice;
import utility.DBManager;
import utility.EnumQuery;
import utility.Funzioni;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPasswordField;
/**
 * Classe Login
 * Implementa l'interfaccia per il pannello login, dove l'utente può procedere con il login
 *
 */
public class Login extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Pannello framePrincipale;
    
    private JTextField nomeUtente;
    private JButton btnLogin;	
    private JPasswordField passwordField;
    private JButton btnRegistra;
    private JButton btnIndietro;

    public Login(Pannello framePrincipale) {
        this.framePrincipale = framePrincipale;
		this.framePrincipale.impostaTitolo("Login");
        this.setLayout(null);
        this.setPreferredSize(new Dimension(319, 170));

        nomeUtente = new JTextField();
        nomeUtente.setBounds(135, 20, 169, 26);
        add(nomeUtente);
        nomeUtente.setColumns(10);
        nomeUtente.setDocument(new JTextFieldLimit(16));

        JLabel lblUser = new JLabel("Nome utente:");
        lblUser.setBounds(21, 25, 111, 16);
        add(lblUser);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(21, 92, 283, 29);
        add(btnLogin);

        btnLogin.addActionListener(this);

        JLabel lblPassw = new JLabel("Password:");
        lblPassw.setBounds(21, 58, 88, 16);
        add(lblPassw);

        passwordField = new JPasswordField();
        passwordField.setBounds(135, 53, 169, 26);
        add(passwordField);

        JLabel lblRegistra = new JLabel("Nuovo utente?");
        lblRegistra.setBounds(21, 135, 132, 16);
        add(lblRegistra);

        btnRegistra = new JButton("Clicca qui");
        btnRegistra.setBounds(110, 130, 104, 26);
        add(btnRegistra);
        
        btnRegistra.addActionListener(this);
      
        btnIndietro = new JButton("Indietro");
        btnIndietro.setBounds(216, 130, 88, 26);
        add(btnIndietro);
        
        btnIndietro.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            if (DBManager.CheckEsiste(
            		EnumQuery.ACCESSO_LOGIN.getValore(),
            		nomeUtente.getText(),
            		Funzioni.strToUnixCrypt(String.valueOf(passwordField.getPassword()))
            	)){
            	Utente ut = null;
        		List<HashMap<String,Object>> getInfoCliente = DBManager.selectQuery(
        				EnumQuery.GET_CLIENTE.getValore(), nomeUtente.getText());
        		
        		Iterator<HashMap<String,Object>> itr = getInfoCliente.iterator();

        	    while (itr.hasNext()) {
        	    	HashMap<String,Object> riga = itr.next();
        	    	String codiceCliente = riga.get("codicecliente").toString();
        	    	String nomeUtente = riga.get("nomeutente").toString();
        	    	String cf = riga.get("codicefiscale").toString();
        	    	String nome = riga.get("nome").toString();
        	    	String cognome = riga.get("cognome").toString();
        	    	String ntelefono = riga.get("numerotelefono").toString();
        	    	String ncellulare = riga.get("numerocellulare").toString();
        	    	if(riga.get("tipocliente").toString().equals("REGISTRATO")){
        	    		ut = new UtenteRegistrato(new UtenteSemplice(codiceCliente,nomeUtente, cf, nome, cognome,ntelefono, ncellulare));
        	    	}
        		}
        	                    
                framePrincipale.setUtenteGlobale(ut);
                framePrincipale.getContentPane().removeAll();
                framePrincipale.getContentPane().add(new MenuPrincipale(framePrincipale));
                framePrincipale.pack();
            } else {
                JOptionPane.showMessageDialog(null, "Nome Utente o Password errato!");
            }
        } else if (e.getSource() == btnRegistra) {
            framePrincipale.getContentPane().removeAll();
            framePrincipale.getContentPane().add(new InserimentoPersona(framePrincipale));
            framePrincipale.pack();
        } else if (e.getSource() == btnIndietro) { // AGGIUNTO PULSANTE INDIETRO
        framePrincipale.getContentPane().removeAll();
        framePrincipale.getContentPane().add(new CatalogoCd(framePrincipale));
        framePrincipale.pack();
        }
    }
}