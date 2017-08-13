package interfaccia;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import utenti.Utente;
import utenti.UtenteOccasionale;
import utenti.UtenteSemplice;
import utility.Carrello;
import utility.DBManager;
import utility.EnumQuery;
import utility.Funzioni;
import utility.Cd;
import javax.swing.JRadioButton;
/**
 * Classe Checkout
 * Mostra i dettagli per l'acquisto e per la spedizione dell'ordine
 *
 */
public class Checkout extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private String strIndirizzo;
	private String strCivico;
	private String strCitta;
	private String strCap;
	private String tipoCorriere;
	private String tipoPagamento;
	private Boolean utenteSpeciale;
	private HashMap<Integer,JTextField> mapTextfield;
	private Pannello framePrincipale;
	private JPanel pannelloCheckout;
	private JPanel pannelloSpedizione;
	private JPanel pannelloInserimento;
	private JPanel pannelloPagamento;
	
	private JLabel lblPesoTot;
	private JScrollPane scrollPane;
	private JLabel lblTotale;
	private JLabel lblTotPrezzo;
	private JTextField txtIndirizzo;
	private JTextField txtNIndirizzo;
	private JTextField txtCitta;
	private JTextField txtCap;
	private JButton btnVaiPagamento;
	private JLabel icoIndirizzo;
	private JLabel icoCitta;
	private JLabel icoCap;
	private JButton btnAnnulla;
	private JButton btnVaiSpedizione;
	private JButton btnVaiCarrello;
	private JRadioButton radioPosta;
	private JRadioButton radioCorriere;
	private float speseSpedizione;
	private JLabel lblPrezzoSpedizione;
	private JLabel lblPrezzoTot;
	private JTextField txtCartaUno;
	private JTextField txtCartaDue;
	private JTextField txtCartaTre;
	private JTextField txtCartaQuattro;
	private JTextField txtIntestario;
	private JTextField txtCVV;
	private JLabel icoVIntestario;
	private JLabel icoVCarta;
	private JLabel icoVCVV;
	private JButton btnConfermaPagamento;
	private JButton btnTornaSpedizione;
	private JRadioButton radioPayPal;
	private JRadioButton radioCartaCredito;
	private JRadioButton radioBonifico;
	private JTextField txtRicevutaBonifico;
	private JLabel icoRicevutaBonifico;
	private JLabel icoVerificaPagamento;
	private JLabel lblVerificaPagamento;
	private JLabel lblCodice;
	private JLabel lblIntestario;
	private JLabel lblCVV;
	private JLabel icoCVV;
	private JLabel lblIstruzioneUno;
	private JLabel lblTotaleBonifico;
	private JLabel lblIstruzioneUnoA;
	private JLabel lblIstruzioneUnoB;
	private JLabel lblIstruzioneDue;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JLabel lblPassword;
	private JLabel lblEMail;
	private JTextField txtCittà;
	private JTextField txtNome;
	private JTextField txtTelefono;
	private JButton btnConferma;
	private JButton btnCancel;
	private JTextField txtCognome;
	private JTextField txtCellulare;
	private JLabel icoCittà;
	private JLabel icoNome;
	private JLabel icoCognome;
	private JLabel icoTelefono;
	private JLabel icoCellulare;

	private JLabel icoEmail;
	private int yElemento;
	private JLabel icoPassword;

	public Checkout(Pannello framePrincipale) throws SQLException, IOException {
		this.framePrincipale = framePrincipale;
		this.framePrincipale.impostaTitolo("Checkout");
		this.framePrincipale.setBackground(new Color(240,193,75));
		if(!(framePrincipale.getUtenteGlobale()==null)){
			setBackground(new Color(240,193,75));
		}
		setLayout(null);
		setPreferredSize(new Dimension(799, 448));
		
		strIndirizzo = "";
		strCivico = "";
		strCitta = "";
		strCap = "";

		creaHeader();
		creaPanCheckout();
		aggiornaCheckout();
	}
	
	private void creaPanCheckout(){
		mapTextfield = new HashMap<Integer,JTextField>();
		
		lblPesoTot = new JLabel("CARRELLO DELLA SPESA");
		lblPesoTot.setFont(new Font("Arial", Font.BOLD, 15)); 
		lblPesoTot.setBounds(25, 19, 439, 16);
		add(lblPesoTot);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 102, 610, 221);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);
		
		pannelloCheckout = new JPanel();
		scrollPane.setViewportView(pannelloCheckout);
		pannelloCheckout.setLayout(null);
		if(!(framePrincipale.getUtenteGlobale()==null))
			pannelloCheckout.setBackground(new Color(244,211,129));
		else
			pannelloCheckout.setBackground(Color.WHITE); 
	}
	
	private void aggiornaCheckout() throws SQLException, IOException{
		yElemento = 10;
		
		Iterator<Cd> itr = Carrello.getCds().iterator();
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean haElementi = itr.hasNext();
		
		int cont = 0;
		
	    while (itr.hasNext()) {
	    	Cd tmp = itr.next();
	    	if(!list.contains(tmp.getCodiceCd())){
	    		int pQuant = Carrello.getNCds(tmp.getCodiceCd());
	    		
	    		list.add(tmp.getCodiceCd());
	    		JLabel lblImg = new JLabel();

	    			lblImg.setIcon(Funzioni.ridimensionaImg(Funzioni.leggereImmagine(tmp.getImg()),88, 68));

				lblImg.setBounds(10, yElemento, 88, 68);
				pannelloCheckout.add(lblImg);
				
				JLabel lblNome = new JLabel(diviviStr(tmp.getTitoloCd()));
				lblNome.setBounds(129, yElemento, 153, 68);
				pannelloCheckout.add(lblNome);
				
				JTextField txtQuant = new JTextField(String.format("%d", pQuant));
				txtQuant.setBounds(318, yElemento+8, 40, 32);
				pannelloCheckout.add(txtQuant);
				txtQuant.setColumns(10);
				mapTextfield.put(cont, txtQuant);
				
				JButton aBtn = new JButton();
				aBtn.setName("Abtn" + cont);
				aBtn.setBorderPainted(false);
				aBtn.setIcon(new ImageIcon("img/aggiornaCarrello.png"));
				aBtn.setBounds(360, yElemento+8, 40, 34);
				aBtn.addActionListener(this);
				aBtn.setBorder(null);
				aBtn.setBackground(Color.WHITE);
				pannelloCheckout.add(aBtn);
			
				JButton rBtn = new JButton();
				rBtn.setName("Rbtn" + cont);
				rBtn.setBorderPainted(false);
				rBtn.setIcon(new ImageIcon("img/togliCarrello.png"));
				rBtn.setBounds(390, yElemento+8, 40, 34);
				rBtn.addActionListener(this);
				rBtn.setBorder(null);
				rBtn.setBackground(Color.WHITE);
				pannelloCheckout.add(rBtn);
								
				JLabel lblTot = new JLabel(String.format("%.2f \u20ac", pQuant*tmp.getPrezzo()));
				lblTot.setBounds(493, yElemento+18, 85, 16);
				pannelloCheckout.add(lblTot);
				
				JSeparator separator = new JSeparator();
				separator.setBounds(0, yElemento+75, 610, 16);
				pannelloCheckout.add(separator);
				
				yElemento += 99;
	    	}
	    	cont++;
	    }
	    
        lblTotale = new JLabel("Totale:");
        lblTotale.setFont(new Font("Arial", Font.BOLD, 15)); 

        lblTotPrezzo = new JLabel(String.format("%.2f \u20ac", 0.00));
        lblTotPrezzo.setFont(new Font("Arial", Font.BOLD, 15)); 
        lblTotPrezzo.setForeground(Color.decode("#990000"));
        
        btnAnnulla = new JButton("Continua con gli acquisti");
        btnAnnulla.addActionListener(this);
        
        if(haElementi){
        	lblTotale.setBounds(520, (yElemento + 100 <= 340) ? yElemento + 100 : 340, 61, 16);
        	lblTotPrezzo.setBounds(570, (yElemento + 100 <= 340) ? yElemento + 100 : 340, 120, 16);
        	lblTotPrezzo.setText(String.format("%.2f \u20ac", Carrello.getPrezzoTotale()));
        	
        	btnAnnulla.setBounds(22, (yElemento + 95 <= 335) ? yElemento + 95 : 335, 195, 29);
            btnVaiSpedizione = new JButton("Conferma l'ordine");
            btnVaiSpedizione.setBounds(229, (yElemento + 95 <= 335) ? yElemento + 95 : 335, 195, 29);
            btnVaiSpedizione.addActionListener(this);
            if(!(framePrincipale.getUtenteGlobale()==null))
            	framePrincipale.verifyButton(btnVaiSpedizione);
            add(btnVaiSpedizione);
        	
            scrollPane.setBounds(25, 102, 610, (yElemento - 10 <= 223) ? yElemento - 10 : 231);
            pannelloCheckout.setPreferredSize(new Dimension(650, yElemento - 20));
            setPreferredSize(new Dimension(660, (yElemento + 130 <= 370) ? yElemento + 130 : 370));
            scrollPane.setPreferredSize(new Dimension(645, yElemento + 50));
        }else{
        	JLabel lblVuoto = new JLabel("Il tuo carrello \u00E8 vuoto!");
        	lblVuoto.setFont(new Font("Arial", Font.ITALIC, 17)); 
        	lblVuoto.setBounds(200, 6, 344, 49);
        	pannelloCheckout.add(lblVuoto);
        	 	
        	lblTotale.setBounds(520, yElemento + 180, 61, 16);
        	lblTotPrezzo.setBounds(570, yElemento + 180, 120, 16);
        	
        	btnAnnulla.setBounds(22, yElemento + 173, 195, 29);
        	
            scrollPane.setBounds(25, 102, 610, 75);
            pannelloCheckout.setPreferredSize(new Dimension(650, 70));
            setPreferredSize(new Dimension(660, 222));
            scrollPane.setPreferredSize(new Dimension(645, 200));
        }
        if(!(framePrincipale.getUtenteGlobale()==null))
        	framePrincipale.verifyButton(btnAnnulla);
        add(btnAnnulla);
        add(lblTotale);
        add(lblTotPrezzo);
	}
	
	private void creaPanPagamento(){	
        pannelloPagamento = new JPanel();
        pannelloPagamento.setBounds(25, 15, 459, 263);
        if(!(framePrincipale.getUtenteGlobale()==null)){
			pannelloPagamento.setBackground(new Color(240,193,75));
		}
        add(pannelloPagamento);
        pannelloPagamento.setLayout(null);
		
        JSeparator separatoreUno = new JSeparator();
        separatoreUno.setBounds(6, 10, 9, 12);
        pannelloPagamento.add(separatoreUno);
        
        JLabel lblSeparatoreConsegna = new JLabel("Dettagli pagamento");
        lblSeparatoreConsegna.setBounds(21, 6, 93, 16);
        pannelloPagamento.add(lblSeparatoreConsegna);
        lblSeparatoreConsegna.setFont(new Font("Arial", Font.PLAIN, 10));
        
        JSeparator separatoreDue = new JSeparator();
        separatoreDue.setBounds(121, 10, 482, 12);
        pannelloPagamento.add(separatoreDue);
        
        JLabel lblMetodoPagamento = new JLabel("Seleziona il metodo di pagamento:");
        lblMetodoPagamento.setBounds(16, 34, 225, 16);
        lblMetodoPagamento.setBackground(Color.WHITE);
        pannelloPagamento.add(lblMetodoPagamento);
        
        ButtonGroup bTipoPagamento = new ButtonGroup();
        
        radioPayPal = new JRadioButton();
        radioPayPal.setBounds(43, 62, 54, 23);
        pannelloPagamento.add(radioPayPal);
        bTipoPagamento.add(radioPayPal);
        radioPayPal.addActionListener(this);
        
        radioCartaCredito = new JRadioButton();
        radioCartaCredito.setBounds(206, 62, 54, 23);
        pannelloPagamento.add(radioCartaCredito);
        bTipoPagamento.add(radioCartaCredito);
        radioCartaCredito.addActionListener(this);
        
        radioBonifico = new JRadioButton();
        radioBonifico.setBounds(369, 62, 46, 23);
        pannelloPagamento.add(radioBonifico);
        bTipoPagamento.add(radioBonifico);
        radioBonifico.addActionListener(this);
        
        JLabel icoPaypal = new JLabel();
        icoPaypal.setBounds(21, 85, 128, 68);
        icoPaypal.setIcon(new ImageIcon("img/pagamento_paypal.png"));
        pannelloPagamento.add(icoPaypal);
        
        JLabel icoCartaCredito = new JLabel();
        icoCartaCredito.setBounds(184, 85, 128, 68);
        icoCartaCredito.setIcon(new ImageIcon("img/pagamento_cartacredito.png"));
        pannelloPagamento.add(icoCartaCredito);
        
        JLabel icoBonifico = new JLabel();
        icoBonifico.setBounds(347, 85, 128, 68);
        icoBonifico.setIcon(new ImageIcon("img/pagamento_bonifico.png"));
        pannelloPagamento.add(icoBonifico);
        
        btnTornaSpedizione = new JButton("Torna ai dettagli spedizione");
        btnTornaSpedizione.setBounds(6, 210, 205, 29);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	framePrincipale.verifyButton(btnTornaSpedizione);
        pannelloPagamento.add(btnTornaSpedizione);
        btnTornaSpedizione.addActionListener(this);
        
        btnConfermaPagamento = new JButton("Conferma il pagamento");
        btnConfermaPagamento.setBounds(223, 210, 217, 29);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	framePrincipale.verifyButton(btnConfermaPagamento);
        pannelloPagamento.add(btnConfermaPagamento);
        btnConfermaPagamento.addActionListener(this);
        
        icoVerificaPagamento = new JLabel();
        icoVerificaPagamento.setBounds(21, 182, 20, 16);
        icoVerificaPagamento.setIcon(new ImageIcon("img/caricamento.gif"));
        pannelloPagamento.add(icoVerificaPagamento);
        
        lblVerificaPagamento = new JLabel("Verifica in corso del pagamento");
        lblVerificaPagamento.setBounds(53, 182, 158, 16);
        lblVerificaPagamento.setFont(new Font("Arial", Font.ITALIC, 10));
        pannelloPagamento.add(lblVerificaPagamento);
       
        icoVerificaPagamento.setVisible(false);
        lblVerificaPagamento.setVisible(false);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(6, 161, 451, 15);
        pannelloPagamento.add(separator);
        tipoPagamento = "";
        setBounds(100, 100, 450, 300);

		
		
		
		pannelloPagamento.setBounds(25, 15, 459, 355);
		btnTornaSpedizione.setBounds(6, 307, 205, 29);
		btnConfermaPagamento.setBounds(223, 307, 217, 29);
		icoVerificaPagamento.setBounds(21, 279, 20, 16);
		lblVerificaPagamento.setBounds(53, 279, 158, 16);

		JPanel 	panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 11, 460, 151);
		pannelloPagamento.add(panel);

	framePrincipale.pack();
	pannelloPagamento.repaint();
	pannelloPagamento.revalidate();

	}
	
	private void impostaPagamento(int tipo){
		// TIPO 1 = PAYPAL
		// TIPO 2 = CARTA
		// TIPO 3 = BONIFICO
		if(tipoPagamento.equals("PAYPAL")){
			pannelloPagamento.remove(lblEMail);
			pannelloPagamento.remove(txtEmail);
			pannelloPagamento.remove(txtPassword);
			pannelloPagamento.remove(lblPassword);}
		else if(tipoPagamento.equals("CARTACREDITO")){
			pannelloPagamento.remove(lblCodice);
			pannelloPagamento.remove(txtCartaUno);
			pannelloPagamento.remove(txtCartaDue);
			pannelloPagamento.remove(txtCartaTre);
			pannelloPagamento.remove(txtCartaQuattro);
			pannelloPagamento.remove(lblIntestario);
			pannelloPagamento.remove(txtIntestario);
			pannelloPagamento.remove(lblCVV);
			pannelloPagamento.remove(txtCVV);
			pannelloPagamento.remove(icoCVV);
			pannelloPagamento.remove(icoVIntestario);
			pannelloPagamento.remove(icoVCarta);
			pannelloPagamento.remove(icoVCVV);
		}else if(tipoPagamento.equals("BONIFICO")){
			pannelloPagamento.remove(lblIstruzioneUno);
			pannelloPagamento.remove(lblTotaleBonifico);
			pannelloPagamento.remove(lblIstruzioneUnoA);
			pannelloPagamento.remove(lblIstruzioneUnoB);
			pannelloPagamento.remove(lblIstruzioneDue);
			pannelloPagamento.remove(txtRicevutaBonifico);
			pannelloPagamento.remove(icoRicevutaBonifico);
		}
				
		if(tipo == 1) {
			
			lblEMail = new JLabel("E - Mail :");
			lblEMail.setBounds(21, 213, 93, 16);
			pannelloPagamento.add(lblEMail);
			
			txtEmail = new JTextField();
			txtEmail.setBounds(124, 208, 280, 26);
			pannelloPagamento.add(txtEmail);
			txtEmail.setColumns(10);
			
			icoEmail = new JLabel();
			icoEmail.setBounds(430, 208, 85, 23);
			pannelloPagamento.add(icoEmail);
			
			lblPassword = new JLabel("Password :");
			lblPassword.setBounds(21, 241, 280, 26);
			pannelloPagamento.add(lblPassword);
			
			txtPassword = new JPasswordField();
			txtPassword.setBounds(124, 241, 280, 26);
			pannelloPagamento.add(txtPassword);
			txtPassword.setColumns(10);
			
			icoPassword = new JLabel();
			icoPassword.setBounds(430, 241, 85, 23);
			pannelloPagamento.add(icoPassword);
			
			setPreferredSize(new Dimension(513, 285));
	        pannelloPagamento.setBounds(25, 15, 459, 263);
			btnTornaSpedizione.setBounds(6, 210, 205, 29);
			btnConfermaPagamento.setBounds(223, 210, 217, 29);
			icoVerificaPagamento.setBounds(21, 182, 20, 16);
			lblVerificaPagamento.setBounds(53, 182, 158, 16);
			
		}if(tipo == 2){
	        lblCodice = new JLabel("Codice carta:");
	        lblCodice.setBounds(21, 213, 93, 16);
	        pannelloPagamento.add(lblCodice);
	        
	        txtCartaUno = new JTextField();
	        txtCartaUno.setBounds(197, 208, 61, 26);
	        pannelloPagamento.add(txtCartaUno);
	        txtCartaUno.setDocument(new JTextFieldLimit(4));
	        txtCartaUno.setColumns(10);
	        
	        txtCartaDue = new JTextField();
	        txtCartaDue.setBounds(270, 208, 61, 26);
	        pannelloPagamento.add(txtCartaDue);
	        txtCartaDue.setDocument(new JTextFieldLimit(4));
	        txtCartaDue.setColumns(10);
	        
	        txtCartaTre = new JTextField();
	        txtCartaTre.setColumns(10);
	        txtCartaTre.setBounds(124, 208, 61, 26);
	        txtCartaTre.setDocument(new JTextFieldLimit(4));
	        pannelloPagamento.add(txtCartaTre);
	        
	        txtCartaQuattro = new JTextField();
	        txtCartaQuattro.setColumns(10);
	        txtCartaQuattro.setBounds(343, 208, 61, 26);
	        txtCartaQuattro.setDocument(new JTextFieldLimit(4));
	        pannelloPagamento.add(txtCartaQuattro);
	        
	        lblIntestario = new JLabel("Intestatario:");
	        lblIntestario.setBounds(21, 179, 100, 16);
	        pannelloPagamento.add(lblIntestario);
	        
	        txtIntestario = new JTextField();
	        txtIntestario.setBounds(124, 174, 280, 26);
	        pannelloPagamento.add(txtIntestario);
	        txtIntestario.setColumns(10);
	        
	        lblCVV = new JLabel("CVV:");
	        lblCVV.setBounds(21, 246, 61, 16);
	        pannelloPagamento.add(lblCVV);
	        
	        txtCVV = new JTextField();
	        txtCVV.setBounds(124, 241, 61, 26);
	        pannelloPagamento.add(txtCVV);
	        txtCVV.setDocument(new JTextFieldLimit(3));
	        txtCVV.setColumns(10);
	        
	        icoCVV = new JLabel();
	        icoCVV.setBounds(199, 230, 61, 37);
	        icoCVV.setIcon(Funzioni.ridimensionaImg(new ImageIcon("img/pagamento_cvv.png"), 55, 35));
	        pannelloPagamento.add(icoCVV);
	        
	        icoVIntestario = new JLabel();
	        icoVIntestario.setBounds(416, 179, 61, 16);
	        pannelloPagamento.add(icoVIntestario);
	        
	        icoVCarta = new JLabel();
	        icoVCarta.setBounds(416, 213, 61, 16);
	        pannelloPagamento.add(icoVCarta);
	        
	        icoVCVV = new JLabel();
	        icoVCVV.setBounds(280, 244, 85, 23);
	        pannelloPagamento.add(icoVCVV);
		}else if(tipo == 3){
	        lblIstruzioneUno = new JLabel("1. Effettua un bonifico di ");
	        lblIstruzioneUno.setBounds(21, 193, 161, 16);
	        pannelloPagamento.add(lblIstruzioneUno);
	        
	        lblTotaleBonifico = new JLabel(String.format("%.2f \u20ac", Carrello.getPrezzoTotale() + speseSpedizione));
	        lblTotaleBonifico.setBounds(184, 194, 61, 16);
	        lblTotaleBonifico.setFont(new Font("Arial", Font.BOLD, 11)); 
	        lblTotaleBonifico.setForeground(Color.decode("#990000"));
	        pannelloPagamento.add(lblTotaleBonifico);

	        lblIstruzioneUnoA = new JLabel("al conto");
	        lblIstruzioneUnoA.setBounds(234, 193, 61, 16);
	        pannelloPagamento.add(lblIstruzioneUnoA);
	        
	        lblIstruzioneUnoB = new JLabel("IT11X0326810001100000000000");
	        lblIstruzioneUnoB.setBounds(291, 194, 185, 16);
	        lblIstruzioneUnoB.setFont(new Font("Arial", Font.BOLD, 10));
	        pannelloPagamento.add(lblIstruzioneUnoB);
	        
	        lblIstruzioneDue = new JLabel("2. Inserisci il codice di ricevuta:");
	        lblIstruzioneDue.setBounds(21, 221, 205, 16);
	        pannelloPagamento.add(lblIstruzioneDue);
	        
	        txtRicevutaBonifico = new JTextField();
	        txtRicevutaBonifico.setBounds(223, 216, 192, 26);
	        pannelloPagamento.add(txtRicevutaBonifico);
	        txtRicevutaBonifico.setColumns(10);
	        
	        icoRicevutaBonifico = new JLabel();
	        icoRicevutaBonifico.setBounds(427, 221, 61, 16);
	        pannelloPagamento.add(icoRicevutaBonifico);
		}

			setPreferredSize(new Dimension(513, 376));
			pannelloPagamento.setBounds(25, 15, 459, 355);
			btnTornaSpedizione.setBounds(6, 307, 205, 29);
			btnConfermaPagamento.setBounds(223, 307, 217, 29);
			icoVerificaPagamento.setBounds(21, 279, 20, 16);
			lblVerificaPagamento.setBounds(53, 279, 158, 16);

		
		framePrincipale.pack();
		pannelloPagamento.repaint();
		pannelloPagamento.revalidate();
	}
	
	private void creaPanSpedizione(){
		if((framePrincipale.getUtenteGlobale()==null && framePrincipale.getUtenteOccasionale()==null)){
	       	try {
					switchPannello(4);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
	        	
		}else{ 
        pannelloSpedizione = new JPanel();
        pannelloSpedizione.setBounds(25, 15, 610, 289);
        if(!(framePrincipale.getUtenteGlobale()==null))
			pannelloSpedizione.setBackground(new Color(240,193,75));
        add(pannelloSpedizione);
        pannelloSpedizione.setLayout(null);
        
        JSeparator separatoreUno = new JSeparator();
        separatoreUno.setBounds(6, 10, 9, 12);
        pannelloSpedizione.add(separatoreUno);
        
        JLabel lblSeparatoreConsegna = new JLabel("Dettagli consegna");
        lblSeparatoreConsegna.setBounds(21, 6, 93, 16);
        pannelloSpedizione.add(lblSeparatoreConsegna);
        lblSeparatoreConsegna.setFont(new Font("Arial", Font.PLAIN, 10));
        
        JSeparator separatoreDue = new JSeparator();
        separatoreDue.setBounds(113, 10, 490, 12);
        pannelloSpedizione.add(separatoreDue);
        
        JLabel lblIndirizzo = new JLabel("Indirizzo:");
        lblIndirizzo.setBounds(6, 39, 61, 16);
        pannelloSpedizione.add(lblIndirizzo);
        
        txtIndirizzo = new JTextField(strIndirizzo);
        txtIndirizzo.setBounds(70, 34, 147, 26);
        txtIndirizzo.setDocument(new JTextFieldLimit(50));
        pannelloSpedizione.add(txtIndirizzo);
        txtIndirizzo.setColumns(10);
        
        JLabel lblNIndirizzo = new JLabel("N°:");
        lblNIndirizzo.setBounds(222, 39, 28, 16);
        pannelloSpedizione.add(lblNIndirizzo);
        
        txtNIndirizzo = new JTextField(strCivico);
        txtNIndirizzo.setBounds(250, 34, 41, 26);
        pannelloSpedizione.add(txtNIndirizzo);
        txtNIndirizzo.setColumns(10);
        
        JLabel lblCitta = new JLabel("Città :");
        lblCitta.setBounds(6, 72, 61, 16);
        pannelloSpedizione.add(lblCitta);
        
        txtCitta = new JTextField(strCitta);
        txtCitta.setColumns(10);
        txtCitta.setBounds(70, 67, 221, 26);
        txtCitta.setDocument(new JTextFieldLimit(50));
        pannelloSpedizione.add(txtCitta);
        
        JLabel lblCap = new JLabel("CAP:");
        lblCap.setBounds(6, 105, 61, 16);
        pannelloSpedizione.add(lblCap);
        
        txtCap = new JTextField();
        txtCap.setColumns(10);
        txtCap.setBounds(70, 100, 74, 26);
        txtCap.setDocument(new JTextFieldLimit(5));
        txtCap.setText(strCap);
        pannelloSpedizione.add(txtCap);
        
        icoIndirizzo = new JLabel("");
        icoIndirizzo.setBounds(303, 39, 61, 16);
        pannelloSpedizione.add(icoIndirizzo);
        
        icoCitta = new JLabel("");
        icoCitta.setBounds(303, 72, 61, 16);
        pannelloSpedizione.add(icoCitta);
        
        icoCap = new JLabel("");
        icoCap.setBounds(303, 105, 61, 16);
        pannelloSpedizione.add(icoCap);
        
        JLabel lblTipoCorriere = new JLabel("Tipo consegna:");
        lblTipoCorriere.setBounds(457, 39, 109, 16);
        pannelloSpedizione.add(lblTipoCorriere);
        
        ButtonGroup bTipoCorriere = new ButtonGroup();
        
        speseSpedizione = 8;
        radioPosta = new JRadioButton("POSTA");
        radioPosta.setBounds(450, 68, 141, 23);
        radioPosta.setSelected(true);
        pannelloSpedizione.add(radioPosta);
        bTipoCorriere.add(radioPosta);
        
        radioCorriere = new JRadioButton("CORRIERE");
        radioCorriere.setBounds(450, 101, 153, 23);
        pannelloSpedizione.add(radioCorriere);
        bTipoCorriere.add(radioCorriere);
        tipoCorriere = "POSTA";

        
        radioPosta.addActionListener(this);
        radioCorriere.addActionListener(this);
       
        JLabel lblSubTotale = new JLabel("Subtotale:");
        lblSubTotale.setBounds(365, 196, 90, 16);
        pannelloSpedizione.add(lblSubTotale);
        
        JLabel lblPrezzoSub = new JLabel(String.format("%.2f \u20ac", Carrello.getPrezzoTotale()));
        lblPrezzoSub.setBounds(530, 196, 73, 16);
        lblPrezzoSub.setFont(new Font("Arial", Font.BOLD, 15)); 
        lblPrezzoSub.setForeground(Color.decode("#990000"));
        pannelloSpedizione.add(lblPrezzoSub);
        
        JLabel lblSpedizioneTratt = new JLabel("Spedizione e trattamento:");
        lblSpedizioneTratt.setBounds(365, 224, 166, 16);
        pannelloSpedizione.add(lblSpedizioneTratt);
        
        if(!(framePrincipale.getUtenteGlobale()==null)){
        if(utenteSpeciale == null){
    		List<HashMap<String,Object>> checkUtenteTremila = DBManager.selectQuery(EnumQuery.CHECK_CLIENTE_SPESO_250.getValore(),
    				framePrincipale.getUtenteGlobale().getCodiceCliente());
    		utenteSpeciale = (checkUtenteTremila.size() >= 3) ? true : false;
        }
        }
        else utenteSpeciale=false;
        
        lblPrezzoSpedizione = new JLabel((utenteSpeciale) ? String.format("<html><strike>%.2f \u20ac</strike></html>", speseSpedizione) :String.format("%.2f \u20ac", speseSpedizione));
        lblPrezzoSpedizione.setBounds(530, 224, 73, 16);
        lblPrezzoSpedizione.setFont(new Font("Arial", Font.BOLD, 15)); 
        lblPrezzoSpedizione.setForeground(Color.decode("#990000"));
        pannelloSpedizione.add(lblPrezzoSpedizione);
        
        JLabel lblTotaleCompl = new JLabel("Totale complessivo:");
        lblTotaleCompl.setBounds(365, 252, 141, 16);
        pannelloSpedizione.add(lblTotaleCompl);
        
        lblPrezzoTot = new JLabel(String.format("%.2f \u20ac", (utenteSpeciale) ? Carrello.getPrezzoTotale() : Carrello.getPrezzoTotale() + speseSpedizione));
        lblPrezzoTot.setBounds(530, 252, 73, 16);
        lblPrezzoTot.setFont(new Font("Arial", Font.BOLD, 15)); 
        lblPrezzoTot.setForeground(Color.decode("#990000"));
        pannelloSpedizione.add(lblPrezzoTot);
                
        pannelloSpedizione.add(lblTotaleCompl);
       
        btnVaiPagamento = new JButton("Vai al pagamento");
        btnVaiPagamento.setBounds(21, 237, 270, 31);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	framePrincipale.verifyButton(btnVaiPagamento);
        pannelloSpedizione.add(btnVaiPagamento);
        btnVaiPagamento.addActionListener(this);
        
        btnVaiCarrello = new JButton("Torna al carrello");
        btnVaiCarrello.setBounds(21, 196, 270, 29);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	framePrincipale.verifyButton(btnVaiCarrello);
        pannelloSpedizione.add(btnVaiCarrello);
        btnVaiCarrello.addActionListener(this);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(6, 172, 597, 12);
        pannelloSpedizione.add(separator);
        
        JSeparator separator_1 = new JSeparator(SwingConstants.VERTICAL);
        separator_1.setBounds(323, 177, 41, 112);
        pannelloSpedizione.add(separator_1);
        if (utenteSpeciale==true)
        	JOptionPane.showMessageDialog(null, "Dato che hai effettuato almeno 3 acquisti del valore di 250€ l'uno, non avrai spese di spedizione");
        btnVaiPagamento.addActionListener(this);
		}
	}
	
	private String diviviStr(String str){
		int lunghezza = str.length();
		String fin = str;
		if(lunghezza > 25) fin = str.substring(0, 25) + "<br />" + str.substring(15, str.length());
		if(lunghezza > 45) fin = fin.substring(0, 45) + "<br />" + fin.substring(45, fin.length());
		return ("<html>" + fin + "</html>");
	}
	
	private void creaHeader(){
        JPanel pannelloHeader = new JPanel();
        if(!(framePrincipale.getUtenteGlobale()==null)){
        	pannelloHeader.setBackground(new Color(144,115,45));
			pannelloHeader.setForeground(Color.WHITE);
		}
        pannelloHeader.setBorder(new LineBorder(new Color(0, 0, 0)));
        pannelloHeader.setLayout(null);
        pannelloHeader.setBounds(25, 47, 610, 55);
        add(pannelloHeader);
        
        JLabel imgH = new JLabel("Immagine");
        imgH.setBounds(16, 19, 86, 16);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	imgH.setForeground(Color.WHITE);
        pannelloHeader.add(imgH);
        
        JLabel nomeH = new JLabel("Nome Prodotto");
        nomeH.setBounds(135, 19, 123, 16);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	nomeH.setForeground(Color.WHITE);
        pannelloHeader.add(nomeH);
        
        JLabel quantH = new JLabel("Quantità");
        quantH.setBounds(320, 19, 61, 16);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	quantH.setForeground(Color.WHITE);
        pannelloHeader.add(quantH);
        
        JLabel totH = new JLabel("Totale");
        totH.setBounds(493, 19, 61, 16);
        if(!(framePrincipale.getUtenteGlobale()==null))
        	totH.setForeground(Color.WHITE);
        pannelloHeader.add(totH);
	}
	
	private void aggiornaStile() throws SQLException, IOException{
		mapTextfield.clear();
		remove(lblTotale);
		remove(lblTotPrezzo);
		remove(btnAnnulla);
		remove(btnVaiSpedizione);
		pannelloCheckout.removeAll();
		aggiornaCheckout();
		framePrincipale.pack();
		pannelloCheckout.revalidate();
		pannelloCheckout.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		repaint();
		revalidate();
	}
	
	private void switchPannello(int tipo) throws SQLException, IOException{
		// TIPO 1 = pannelloCheckout
		// TIPO 2 = pannelloSpedizione
		// TIPO 3 = pannelloPagamento
		// TIPO 4 = pannelloDatiCliente
		removeAll();
		if(tipo == 1){
			creaHeader();
			creaPanCheckout();
			aggiornaCheckout();
		}else if(tipo == 2){
			creaPanSpedizione();
			setPreferredSize(new Dimension(660, 305));
		}else if(tipo == 3){
			creaPanPagamento();
			setPreferredSize(new Dimension(513, 376));
		}else if (tipo == 4){
			inserimentoDatiCliente();
			setPreferredSize(new Dimension(513, 376));
		}
		framePrincipale.pack();
		repaint();
		revalidate();
	}
	
	private boolean checkCampi(int tipo){
		// TIPO 1 = SPEDIZIONE
		// TIPO 2 = PAGAMENTO CARTA
		// TIPO 3 = PAGAMENTO BONIFICO
		// TIPO 4 = PAYPAL
		if(tipo == 1){
			boolean indirizzoV = txtIndirizzo.getText().isEmpty();
			boolean civicoV = !Funzioni.isInteger(txtNIndirizzo.getText()) || txtNIndirizzo.getText().isEmpty();
			boolean cittaV = txtCitta.getText().isEmpty();
			boolean capV = !Funzioni.isInteger(txtCap.getText()) || txtCap.getText().isEmpty() || txtCap.getText().length() < 5;
	
			icoIndirizzo.setIcon(indirizzoV || civicoV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			icoCitta.setIcon(cittaV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			icoCap.setIcon(capV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			
			return !(indirizzoV || civicoV || cittaV || capV);
		}else if(tipo == 2){
			boolean intestarioV = txtIntestario.getText().isEmpty();
			boolean cartaUnoV = !Funzioni.isInteger(txtCartaUno.getText()) || txtCartaUno.getText().length() < 4;
			boolean cartaDueV = !Funzioni.isInteger(txtCartaDue.getText()) || txtCartaDue.getText().length() < 4;
			boolean cartaTreV = !Funzioni.isInteger(txtCartaTre.getText()) || txtCartaUno.getText().length() < 4;
			boolean cartaQuattroV = !Funzioni.isInteger(txtCartaQuattro.getText()) || txtCartaQuattro.getText().length() < 4;
			boolean cvvV = !Funzioni.isInteger(txtCVV.getText()) || txtCVV.getText().length() < 3;
	
			icoVIntestario.setIcon(intestarioV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			icoVCarta.setIcon(cartaUnoV || cartaDueV || cartaTreV || cartaQuattroV  ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			icoVCVV.setIcon(cvvV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			
			return !(intestarioV || cartaUnoV || cartaDueV || cartaTreV || cartaQuattroV || cvvV);
		}else if(tipo == 3){
			boolean ricevutaV = txtRicevutaBonifico.getText().isEmpty();
			icoRicevutaBonifico.setIcon(ricevutaV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			
			return !ricevutaV;
		} else if(tipo == 4){
			boolean emailV = !(Funzioni.isEmailCorretta(txtEmail.getText()))|| txtEmail.getText().isEmpty();
			boolean passwordV = String.valueOf(txtPassword.getPassword()).length() < 8;
	
			icoEmail.setIcon(emailV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			icoPassword.setIcon(passwordV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
			
			return !(emailV||passwordV);
		}
		return true;
	}
	

	private void inserimentoDatiCliente(){
		pannelloInserimento = new JPanel();
		pannelloInserimento.setBounds(25, 15, 459, 263);
		add(pannelloInserimento);
		pannelloInserimento.setLayout(null);
		this.setPreferredSize(new Dimension(388, 392));
		
		JLabel lblCittà = new JLabel("Città:");
		lblCittà.setBounds(16, 26, 86, 16);
		pannelloInserimento.add(lblCittà);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(16, 54, 99, 16);
		pannelloInserimento.add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome:");
		lblCognome.setBounds(16, 82, 137, 16);
		pannelloInserimento.add(lblCognome);
		
		JLabel lblTelefono = new JLabel("Numero di telefono:");
		lblTelefono.setBounds(16, 110, 158, 16);
		pannelloInserimento.add(lblTelefono);
		
		JLabel lblCellulare = new JLabel("Numero di cellulare:");
		lblCellulare.setBounds(16, 138, 137, 16);
		pannelloInserimento.add(lblCellulare);
		
		txtCittà = new JTextField();
		txtCittà.setBounds(181, 26, 158, 26);
		txtCittà.setDocument(new JTextFieldLimit(50));
		pannelloInserimento.add(txtCittà);
		txtCittà.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setBounds(181, 54, 158, 26);
		txtNome.setDocument(new JTextFieldLimit(50));
		pannelloInserimento.add(txtNome);
		txtNome.setColumns(10);
		
		txtCognome = new JTextField();
		txtCognome.setBounds(181, 82, 158, 26);
		txtCognome.setDocument(new JTextFieldLimit(50));
		pannelloInserimento.add(txtCognome);
		txtCognome.setColumns(10);
				
		txtTelefono = new JTextField();
		txtTelefono.setBounds(181, 110, 158, 26);
		txtTelefono.setDocument(new JTextFieldLimit(10));
		pannelloInserimento.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtCellulare = new JTextField();
		txtCellulare.setBounds(181, 138, 158, 26);
		txtCellulare.setDocument(new JTextFieldLimit(10));
		pannelloInserimento.add(txtCellulare);
		txtCellulare.setColumns(10);
		
		btnConferma = new JButton("Conferma");
		btnConferma.setBounds(10, 177, 117, 29);
		pannelloInserimento.add(btnConferma);
		btnConferma.addActionListener(this);
		btnCancel = new JButton("Annulla");
		btnCancel.setBounds(181, 177, 117, 29);
		pannelloInserimento.add(btnCancel);
	
	    btnCancel.addActionListener(this);	

		icoCittà = new JLabel();
		icoCittà.setBounds(351, 28, 61, 16);
		pannelloInserimento.add(icoCittà);
		
		icoNome = new JLabel();
		icoNome.setBounds(351, 54, 61, 16);
		pannelloInserimento.add(icoNome);
		
		icoCognome = new JLabel();
		icoCognome.setBounds(351, 82, 61, 16);
		pannelloInserimento.add(icoCognome);
		
		icoTelefono = new JLabel();
		icoTelefono.setBounds(351, 110, 61, 16);
		pannelloInserimento.add(icoTelefono);
		
		icoCellulare = new JLabel();
		icoCellulare.setBounds(351, 138, 61, 16);
		pannelloInserimento.add(icoCellulare);
		
	pannelloInserimento.setVisible(true);
	pannelloInserimento.repaint();
	pannelloInserimento.revalidate();

	framePrincipale.pack();
	pannelloInserimento.repaint();
	pannelloInserimento.revalidate();

	}
	private void venditaTerminata() throws IOException{
		Iterator<Cd> itr = Carrello.getCds().iterator();
		ArrayList<Integer> list = new ArrayList<Integer>();

		//transazione attuale
		String transAct=Funzioni.getTransactionNumb();
		String codiceCliente="";
		Float prezzoTot;
		prezzoTot=((utenteSpeciale) ? Carrello.getPrezzoTotale() : Carrello.getPrezzoTotale() + speseSpedizione);
		if (!(framePrincipale.getUtenteGlobale()==null))
				codiceCliente=framePrincipale.getUtenteGlobale().getCodiceCliente();
		if  (!(framePrincipale.getUtenteOccasionale()==null))
				codiceCliente=framePrincipale.getUtenteOccasionale().getCodiceCliente();
		// AGGIUNTA DELL'ACQUISTO NEL DB
        	int inserisciDB = DBManager.updateQuery(EnumQuery.INSERISCI_ACQUISTO.getValore(), false,
        				codiceCliente,
        				prezzoTot,
        				tipoPagamento,
        				tipoCorriere
        				);
        		if(inserisciDB <= 0){
        			JOptionPane.showMessageDialog(null, "Errore nell'inserimento dell'acquisto nel DB!");
        		}
	    	
	    while (itr.hasNext()) {
	    	Cd tmp = itr.next();
	    	if(!list.contains(tmp.getCodiceCd())){
	    		list.add(tmp.getCodiceCd());
	    		int quantita = Carrello.getNCds(tmp.getCodiceCd());
        		int inserisciDB2 = DBManager.updateQuery(EnumQuery.INSERISCI_ACQUISTI_SINGOLI.getValore(), false,
        				transAct,
        				tmp.getCodiceCd(),
        				codiceCliente,
        				quantita
        				);
        		if(inserisciDB2 <= 0){
        			JOptionPane.showMessageDialog(null, "Errore nell'inserimento dell'acquisto nel DB!");
        		}
	    	}
	    }
	  
	    Carrello.svuotaCarrello();
	    

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "Il tuo pagamento \u00E8 stato verificato\nGrazie per aver acquistato presso il nostro negozio!");
		    	framePrincipale.getContentPane().removeAll();
		        framePrincipale.getContentPane().add(new MenuPrincipale(framePrincipale));
		        framePrincipale.pack();
		        framePrincipale.setUtenteOccasionale(null);
			}
		}, 2500); //2.5 sec
	}
	
	private boolean checkCampi(){

		boolean CittàV = txtCittà.getText().isEmpty();
		boolean nomeV = txtNome.getText().isEmpty();
		boolean cognomeV = txtCognome.getText().isEmpty();
		boolean telefonoV = !Funzioni.isInteger(txtTelefono.getText()) || txtTelefono.getText().isEmpty();
		boolean cellulareV = !Funzioni.isInteger(txtCellulare.getText()) && !txtCellulare.getText().isEmpty();
		icoCittà.setIcon(CittàV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoNome.setIcon(nomeV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoCognome.setIcon(cognomeV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoTelefono.setIcon(telefonoV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		icoCellulare.setIcon(cellulareV ? new ImageIcon("img/errore.png") : new ImageIcon("img/ok.png"));
		
		return !( CittàV || nomeV || cognomeV || telefonoV || cellulareV);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			String nomeBtn = ((JButton) e.getSource()).getName();
			if(nomeBtn != null && nomeBtn.indexOf("btn") > -1){
				int indexArray = Integer.parseInt(nomeBtn.split("btn")[1]);
				if(nomeBtn.charAt(0) == 'R'){
					Carrello.rimuoviCd(indexArray);
				}else{
					if(!mapTextfield.get(indexArray).getText().isEmpty() && Funzioni.isInteger(mapTextfield.get(indexArray).getText())){
						Carrello.aggiornaQuantita(Integer.parseInt(mapTextfield.get(indexArray).getText()), indexArray);
					}
				}
				try {
					aggiornaStile();
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == btnVaiPagamento){
				if(checkCampi(1)){
					strIndirizzo = txtIndirizzo.getText();
					strCivico = txtNIndirizzo.getText();
					strCitta = txtCitta.getText();
					strCap = txtCap.getText();
					try {
						switchPannello(3);
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}else if(e.getSource() == btnAnnulla){
	        	framePrincipale.getContentPane().removeAll();
	            framePrincipale.getContentPane().add(new MenuPrincipale(framePrincipale));
	            framePrincipale.pack();
			}else if(e.getSource() == btnConferma){
				if(checkCampi()){
	        		int inserisciDB = DBManager.updateQuery(EnumQuery.INSERISCI_CLIENTE_UNREGISTRED.getValore(), true,
	        				txtNome.getText(),
	        				txtCognome.getText(),
	        				txtCittà.getText(),
	        				txtTelefono.getText(),
	        				txtCellulare.getText(),
	        				"OCCASIONALE"
	        				);
	        		if(inserisciDB > 0){
	        			JOptionPane.showMessageDialog(null, "Dati registrati \nE' ora possibile procedere con il pagamento");
	        			Utente ut = null;
	        			String codiceUnregistred=Integer.toString(inserisciDB);
	             		List<HashMap<String,Object>> getInfoCliente = DBManager.selectQuery(
	             				EnumQuery.GET_CLIENTE_UNREGISTRED.getValore(), codiceUnregistred);
	             		
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
	             	    	if(riga.get("tipocliente").toString().equals("OCCASIONALE")){
	             	    		ut = new UtenteOccasionale(new UtenteSemplice(codiceCliente,nomeUtente, cf, nome, cognome,ntelefono, ncellulare));
	             	    	}
	             	    	 framePrincipale.setUtenteOccasionale(ut);
	             		}
	        		}else{
	        			JOptionPane.showMessageDialog(null, "Errore nella registrazione del cliente!");
	        		}
	                 	

						try {
							switchPannello(2);
						} catch (SQLException | IOException e1) {
							e1.printStackTrace();
						}

				}
			} else if(e.getSource() == btnVaiSpedizione || e.getSource() == btnTornaSpedizione ){
				try {
					switchPannello(2);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == btnVaiCarrello || e.getSource() == btnCancel ){
				try {
					switchPannello(1);
				} catch (SQLException | IOException e1) {

					e1.printStackTrace();
				}
			}else if(e.getSource() == btnConfermaPagamento){
				
				boolean campiOk = true;
				if(radioCartaCredito.isSelected()){
					campiOk = checkCampi(2);
					if(campiOk){
						txtCartaUno.setEnabled(false);
						txtCartaDue.setEnabled(false);
						txtCartaTre.setEnabled(false);
						txtCartaQuattro.setEnabled(false);
						txtIntestario.setEnabled(false);
						txtCVV.setEnabled(false);
					}
				
				}else if(radioBonifico.isSelected()){
					campiOk = checkCampi(3);
					if(campiOk)	txtRicevutaBonifico.setEnabled(false);
				}else if(radioPayPal.isSelected()){
					campiOk = checkCampi(4);
					if (campiOk){
						txtEmail.setEnabled(false);
						txtPassword.setEnabled(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleziona il metodo di pagamento");
					campiOk=false;
				}
				if(campiOk){
			        icoVerificaPagamento.setVisible(true);
			        lblVerificaPagamento.setVisible(true);
			        btnConfermaPagamento.setEnabled(false);
			        btnTornaSpedizione.setEnabled(false);
			        radioPayPal.setEnabled(false);
			        radioCartaCredito.setEnabled(false);
			        radioBonifico.setEnabled(false);
			       
					Thread a = new Thread(() -> { try {
						venditaTerminata();
					} catch (IOException e1) {
						e1.printStackTrace();
					} });			
					a.start();
				}
				
			}
		}else if(e.getSource() == radioPosta || e.getSource() == radioCorriere){
			tipoCorriere = ((JRadioButton)e.getSource()).getText();			
		}else if(e.getSource() == radioPayPal){
			impostaPagamento(1);
			tipoPagamento = "PAYPAL";
		}else if(e.getSource() == radioCartaCredito){
			impostaPagamento(2);
			tipoPagamento = "CARTACREDITO";
		}else if(e.getSource() == radioBonifico){
			impostaPagamento(3);
			tipoPagamento = "BONIFICO";
		}
	} 
}