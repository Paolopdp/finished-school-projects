package interfaccia;
import javax.swing.*;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import utility.ButtonColumn;
import utility.Carrello;
import utility.DBManager;
import utility.EnumQuery;
import utility.Cd;
import javax.swing.JLabel;
/**
 * Classe CatalogoCd
 * Mostra il catalogo dei cd disponibili
 *
 */
public class CatalogoCd extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	private boolean isPreviewChiusa;
	private final String[] colonneTab = {"Nome", "Prezzo", "Dettagli" ,"Compra",""};
	private JButton btnSearch;
	private JButton btnAnnulla;
	private JButton btnLogin;
	private JButton btnCheckout;
	private JButton btnOfferteSpeciali;
	private Pannello framePrincipale;
	private JTable tabellacds;
	private PreviewCarrello pannelloCarrello;
	private DefaultTableModel dm;
	
	private JTextField txtRicerca;
	private JComboBox<String> comboBoxRicerca;
	private JScrollPane scrollPane;
	private JLabel apriCarrello;
	private JLabel icoCaricamento;
	private JLabel lblCaricamento;


	public CatalogoCd(Pannello framePrincipale) {
		this.framePrincipale = framePrincipale;
		this.framePrincipale.impostaTitolo("Catalogo cds");
		
		this.setPreferredSize(new Dimension(500, 430));
		setLayout(null);
		
		icoCaricamento = new JLabel();
		icoCaricamento.setBounds(22, 27, 61, 16);
		icoCaricamento.setIcon(new ImageIcon("img/caricamento.gif"));
		add(icoCaricamento);
		
		lblCaricamento = new JLabel("Caricamento in corso dei prodotti...");
		lblCaricamento.setFont(new Font("Arial", Font.ITALIC, 14));
		lblCaricamento.setBounds(67, 27, 278, 16);
		add(lblCaricamento);
		
		Thread a = new Thread(() -> { try {
			caricaPannelloThread();
		} catch (IOException e) {
			e.printStackTrace();
		} });			
		a.start();
	}
	
	private void caricaPannelloThread() throws IOException{
		scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 71, 350, 253);
		
		tabellacds = new JTable(new String[0][0],colonneTab);
		scrollPane.setViewportView(tabellacds);
		tabellacds.setShowGrid(true);
		tabellacds.setGridColor(Color.GRAY);
		tabellacds.setShowHorizontalLines(true);
		tabellacds.setShowVerticalLines(true);
		tabellacds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		riempiTabella(tabellacds);
		
		remove(icoCaricamento);
		remove(lblCaricamento);
		
		JLabel icoCarrello = new JLabel();
		icoCarrello.setIcon(new ImageIcon("img/carrelloHome.png"));
		icoCarrello.setBounds(322, 15, 45, 34);
		icoCarrello.setCursor(new Cursor(Cursor.HAND_CURSOR));
		icoCarrello.addMouseListener(this);
		add(icoCarrello);
		
		apriCarrello = new JLabel("\u25b8");
		apriCarrello.setBounds(360, 27, 17, 16);
		apriCarrello.setCursor(new Cursor(Cursor.HAND_CURSOR));
		apriCarrello.addMouseListener(this);
		add(apriCarrello);
		
		pannelloCarrello = new PreviewCarrello();
		pannelloCarrello.setBounds(112, 52, 258, 245);
		pannelloCarrello.setVisible(false);
		add(pannelloCarrello);
		
		this.add(scrollPane);
		creaPanCatalogo();
		this.repaint();
		framePrincipale.pack();
	}
	
	private void creaPanCatalogo(){
		
		this.setPreferredSize(new Dimension(388, 430));
		
		btnSearch = new JButton();
		btnSearch.setIcon(new ImageIcon("img/ricerca.png"));
		btnSearch.setBounds(275, 26, 20, 20);
		btnSearch.setBorder(null);
		btnSearch.setBackground(framePrincipale.getBackground());
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(true);
		btnSearch.addActionListener(this);
		
		txtRicerca = new JTextField();
		txtRicerca.setText("");
		txtRicerca.setBounds(18, 25, 93, 20);
		add(txtRicerca);
		txtRicerca.setColumns(10);
		add(btnSearch);
		
		final String[] tipoRicerca = {
				"Titolo","Autore","Prezzo","Genere"
		};
		
		comboBoxRicerca = new JComboBox<String>();
		comboBoxRicerca.setBounds(123, 26, 148, 20);
		comboBoxRicerca.setModel(new DefaultComboBoxModel<String>(tipoRicerca));
		add(comboBoxRicerca);
		
		if(!(framePrincipale.getUtenteGlobale()==null)){
			setBackground(new Color(240,193,75));
			tabellacds.getTableHeader().setBackground(new Color(144,115,45));
			tabellacds.getTableHeader().setForeground(Color.WHITE);
			tabellacds.setBackground(new Color(240,193,75));
			scrollPane.getViewport().setBackground(new Color(244,211,129));
			
			btnOfferteSpeciali=new JButton("Consigli per gli acquisti");
			framePrincipale.verifyButton(btnOfferteSpeciali);
			btnOfferteSpeciali.setBounds(18, 336, 350, 29);
			
			btnAnnulla = new JButton("Indietro");
			btnAnnulla.setBounds(18, 370, 350, 29);
			framePrincipale.verifyButton(btnAnnulla);

			add(btnOfferteSpeciali);
			btnOfferteSpeciali.addActionListener(this);
			add(btnAnnulla);
			btnAnnulla.addActionListener(this);
		}else{
		    if((framePrincipale.getUtenteOccasionale()==null)){
			btnLogin=new JButton();
			btnLogin.setBounds(200, 327, 40, 50);
			btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
			Border emptyBorder = BorderFactory.createEmptyBorder();
			btnLogin.setBorder(emptyBorder);
			btnLogin.setContentAreaFilled(false); 
			btnLogin.setFocusPainted(false); 
			btnLogin.setOpaque(false);
			btnLogin.addActionListener(this);
			btnLogin.setIcon(new ImageIcon("img/login.png"));
			btnLogin.setToolTipText("Login & Registrazione");
			this.setPreferredSize(new Dimension(388, 380));
			add(btnLogin);
		}	
			btnCheckout = new JButton("Checkout");
			btnCheckout.setBounds(6, 327, 150, 50);
			btnCheckout.addActionListener(this);
			add(btnCheckout);
		}
		

	}
	
	private void riempiTabella(JTable tabella) throws IOException{
		
		List<HashMap<String,Object>> risultatoQuery;

		    risultatoQuery = DBManager.selectQuery(EnumQuery.GET_CD.getValore()+";");
			dm = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) { 
			    if(column == 2 || column == 3) return true;
	            else return false;	
			}
		};
	    dm.setColumnIdentifiers(colonneTab);
	    
		Iterator<HashMap<String,Object>> itr = risultatoQuery.iterator();
	    while (itr.hasNext()) {
	    	HashMap<String,Object> riga = itr.next();
	    	dm.addRow(new Object[] { riga.get("titolocd").toString(),riga.get("prezzo").toString()+" \u20ac", "", "", riga.get("codicecd").toString()});
	    }
	    
        tabella.setModel(dm);
        tabella.removeColumn(tabella.getColumnModel().getColumn(4));	// TOGLI COLONNA ID
        tabella.getColumnModel().getColumn(2).setPreferredWidth(16);
        tabella.getColumnModel().getColumn(3).setPreferredWidth(16);
        tabella.setRowHeight(30);
    	new ButtonColumn(tabella, azioneDtg, 2);
        new ButtonColumn(tabella, azioneCrl, 3);
	}       
	
	private void riempiTabelladaRicerca(JTable tabella) throws IOException{
		String colonnaTabella1="";
		String colonnaTabella2="";
		List<HashMap<String,Object>> risultatoQuery = null;
		if(comboBoxRicerca.getSelectedItem().toString().equals("Genere")){
			risultatoQuery = DBManager.selectQuery(EnumQuery.RICERCA_CD_GENERE.getValore()+";",
						 txtRicerca.getText());
			colonnaTabella1="autore";
			colonnaTabella2="titolocd";
// GENERE:  AUTORE E TITOLO
		}else if(comboBoxRicerca.getSelectedItem().toString().equals("Prezzo")){
			risultatoQuery = DBManager.selectQuery(EnumQuery.RICERCA_CD_PREZZO.getValore()+";",
						 txtRicerca.getText());
			colonnaTabella1="autore";
			colonnaTabella2="titolocd";
//PREZZO: AUTORE E TITOLO

		}else if(comboBoxRicerca.getSelectedItem().toString().equals("Autore")){
			risultatoQuery = DBManager.selectQuery(EnumQuery.RICERCA_CD_AUTORE.getValore()+";",
					 txtRicerca.getText());
			colonnaTabella1="titolocd";
			colonnaTabella2="generecd";

//	AUTORE: TITOLO E GENERE
		}else if(comboBoxRicerca.getSelectedItem().toString().equals("Titolo")){
			risultatoQuery = DBManager.selectQuery(EnumQuery.RICERCA_CD_TITOLO.getValore()+";",
					 txtRicerca.getText());
			colonnaTabella1="autore";
			colonnaTabella2="generecd";
// TITOLO: AUTORE E GENERE
		}
		
		if(risultatoQuery.isEmpty()){
			JOptionPane.showMessageDialog(null, "Nessun cd trovato");
			riempiTabella(tabella);
		}else{
			dm = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
			    public boolean isCellEditable(int row, int column) { 
				    if(column == 2 || column == 3) return true;
		            else return false;	
				}
			};

			while (dm.getRowCount() > 0) dm.removeRow(0);
			

			String[] colonneTabRicerca = {colonnaTabella1, colonnaTabella2, "Dettagli" ,"Compra",""};
			dm.setColumnIdentifiers(colonneTabRicerca);
			
			Iterator<HashMap<String,Object>> itr = risultatoQuery.iterator();
		    while (itr.hasNext()) {
		    	HashMap<String,Object> riga = itr.next();
		    	dm.addRow(new Object[] { riga.get(colonnaTabella1).toString(),riga.get(colonnaTabella2).toString(), "", "", riga.get("codicecd").toString()});
		    }

            tabellacds.setModel(dm);	
            tabellacds.removeColumn(tabella.getColumnModel().getColumn(4));	
            tabellacds.getColumnModel().getColumn(2).setPreferredWidth(16);
            tabellacds.getColumnModel().getColumn(3).setPreferredWidth(16);
            tabellacds.setRowHeight(30);
            new ButtonColumn(tabellacds, azioneDtg, 2);
            new ButtonColumn(tabellacds, azioneCrl, 3);
         }
	}
	
	private void riempiTabellaConsigliati(JTable tabella) throws IOException{
		List<HashMap<String,Object>> risultatoQuery = null;
		risultatoQuery = DBManager.selectQuery(EnumQuery.RICERCA_CD_CONSIGLIATI.getValore()+";",
				framePrincipale.getUtenteGlobale().getCodiceCliente(),
				framePrincipale.getUtenteGlobale().getCodiceCliente(),
				framePrincipale.getUtenteGlobale().getCodiceCliente());
		
		if(risultatoQuery.isEmpty()){
			JOptionPane.showMessageDialog(null, "Nessun cd trovato");
			riempiTabella(tabella);
		}else{
			dm = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
			    public boolean isCellEditable(int row, int column) { 
				    if(column == 2 || column == 3) return true;
		            else return false;	
				}
			};

			while (dm.getRowCount() > 0) dm.removeRow(0);
			

			dm.setColumnIdentifiers(colonneTab);
			
			Iterator<HashMap<String,Object>> itr = risultatoQuery.iterator();
		    while (itr.hasNext()) {
		    	HashMap<String,Object> riga = itr.next();
		    	dm.addRow(new Object[] { riga.get("titolocd").toString(),riga.get("prezzo").toString()+" \u20ac", "", "", riga.get("codicecd").toString()});
		    }
			            
            tabellacds.setModel(dm);	
            tabella.removeColumn(tabella.getColumnModel().getColumn(4));	
            tabella.getColumnModel().getColumn(2).setPreferredWidth(16);
            tabella.getColumnModel().getColumn(3).setPreferredWidth(16);
            tabella.setRowHeight(30);

            new ButtonColumn(tabellacds, azioneDtg, 2);
            new ButtonColumn(tabellacds, azioneCrl, 3);
         }
	}

	private Cd getCdRiga() throws IOException{
	   	 String codice = tabellacds.getModel().getValueAt(tabellacds.getSelectedRow(),4).toString();
	   	 Cd cd = null;	
	   	 List<HashMap<String,Object>> risultatoQuery = DBManager.selectQuery(EnumQuery.GET_DETTAGLI_CD.getValore(), 
					codice);
	   	 
		Iterator<HashMap<String,Object>> itr = risultatoQuery.iterator();
	    while (itr.hasNext()) {
	    	HashMap<String,Object> riga = itr.next();
  			 cd = new Cd(Integer.parseInt(riga.get("codicecd").toString()),
		 				  riga.get("titolocd").toString(),
		 				  (riga.get("prezzo").toString().isEmpty()) ? 0 :Float.parseFloat(riga.get("prezzo").toString()),
		 				  riga.get("data").toString(),
		 				  riga.get("descrizione").toString(),
		 				  riga.get("generecd").toString());
	    }

	   	return cd;
	}
	
	
    Action azioneDtg = new AbstractAction(){
        private static final long serialVersionUID = 1L;        	
        public void actionPerformed(ActionEvent e){
    	 	framePrincipale.getContentPane().removeAll();
    	    try {
				framePrincipale.getContentPane().add(new DettagliCd(framePrincipale, getCdRiga()));
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
			framePrincipale.pack();
       }
    };
    
    Action azioneCrl = new AbstractAction(){
        private static final long serialVersionUID = 1L;        	
	    public void actionPerformed(ActionEvent e){

		    	try {
					Carrello.aggiungiCd(getCdRiga());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    	JOptionPane.showMessageDialog(null,"cd aggiunto al carrello!");

	    }
    };
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOfferteSpeciali ){
			try {
				riempiTabellaConsigliati(tabellacds);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == btnSearch){
			try {
				riempiTabelladaRicerca(tabellacds);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
   		}
		if(e.getSource() == btnAnnulla){
        	framePrincipale.getContentPane().removeAll();
            framePrincipale.getContentPane().add(new MenuPrincipale(framePrincipale));
            framePrincipale.pack();
		}
		if(e.getSource() == btnLogin){
			framePrincipale.getContentPane().removeAll();
            framePrincipale.getContentPane().add(new Login(framePrincipale));
            framePrincipale.pack();
   		}
		else if(e.getSource() == btnCheckout){
			framePrincipale.getContentPane().removeAll();
			try {
				framePrincipale.getContentPane().add(new Checkout(framePrincipale));
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
	        framePrincipale.pack();
		}
	}
	
	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		isPreviewChiusa = (apriCarrello.getText().indexOf("\u25b8") > -1);
		pannelloCarrello.setVisible((isPreviewChiusa) ? true : false);
		if(isPreviewChiusa){
			pannelloCarrello.aggiornaStile();
			apriCarrello.setText("\u25be");
		}else{
			apriCarrello.setText("\u25b8");
		}
	}
	
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}
}
