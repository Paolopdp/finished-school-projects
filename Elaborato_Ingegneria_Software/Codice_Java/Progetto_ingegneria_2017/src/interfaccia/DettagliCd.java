package interfaccia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;import javax.swing.JLabel;
import javax.swing.JPanel;
import utility.Funzioni;
import utility.Cd;
import utility.DBManager;
import utility.EnumQuery;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;

import javax.swing.JScrollPane;
/**
 * Classe DettagliCd
 * Mostra i dettagli specifici del cd selezionato
 *
 */
public class DettagliCd extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private Pannello framePrincipale;
	private JButton btnBack;
	private DefaultTableModel dm;
	private final String[] colonneTab = {"Traccia"};
	private final String[] colonneTabMus={"NomeMus","Strumento"};
	private JButton btnImg1;
	private Cd cd;
	private JTable tabellaCanzoni;
	private JTable tabellaMusicisti;
	private JButton btnDescr;
	
	public DettagliCd(Pannello framePrincipale, Cd newCd) throws SQLException, IOException{
		this.framePrincipale = framePrincipale;
		this.framePrincipale.impostaTitolo("Dettagli Cd");
		this.cd = newCd;
		setLayout(null);
		this.setPreferredSize(new Dimension(269, 610));
		JLabel lblGenere = new JLabel("Genere:");
		lblGenere.setBounds(43, 38, 81, 14);
		add(lblGenere);

		JLabel lblGenereCd= new JLabel(cd.getGenere());
		lblGenereCd.setBounds(121, 38, 138, 14);
		add(lblGenereCd);
		
		JLabel lblTitolo = new JLabel("Titolo:");
		lblTitolo.setBounds(43, 18, 81, 14);
		add(lblTitolo);
		
		JLabel lblTitoloCd = new JLabel(cd.getTitoloCd());
		lblTitoloCd.setBounds(121, 18, 138, 14);
		add(lblTitoloCd);
		
		JLabel lblPrezzo = new JLabel("Prezzo:");
		lblPrezzo.setBounds(43, 59, 81, 14);
		add(lblPrezzo);
		
		JLabel lblPrezzoCd = new JLabel(""+cd.getPrezzo()+" \u20ac");
		lblPrezzoCd.setBounds(121, 59, 129, 14);
		add(lblPrezzoCd);

		
		JLabel lblData = new JLabel("Data sul sito:");
		lblData.setBounds(43, 79, 116, 14);
		add(lblData);
		
		JLabel lblDataCd = new JLabel(Funzioni.formattaData(cd.getData()));
		lblDataCd.setBounds(121, 79, 138, 14);
		add(lblDataCd);

		btnDescr = new JButton("Descrizione");
		btnDescr.setBounds(43, 104, 170, 20);
		btnDescr.addActionListener(this);
		if(!(framePrincipale.getUtenteGlobale()==null))
			framePrincipale.verifyButton(btnDescr);
		add(btnDescr);

		JLabel lblImgTitolo = new JLabel("Immagini");
		lblImgTitolo.setBounds(43, 400, 70, 14);
		add(lblImgTitolo);
		
		btnImg1 = new JButton();
		btnImg1.setBounds(43, 430, 189, 139);
		btnImg1.addActionListener(this);
		btnImg1.setVisible(false);
		add(btnImg1);
		
		btnImg1.setToolTipText("Clicca per ingrandire");
		
		btnBack = new JButton("Indietro");
		btnBack.setBounds(90, 580, 89, 23);
		btnBack.addActionListener(this);
		if(!(framePrincipale.getUtenteGlobale()==null))
			framePrincipale.verifyButton(btnBack);
		add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 129, 189, 109);
		add(scrollPane);
		
		tabellaCanzoni = new JTable(new String[0][0],colonneTab);
		scrollPane.setViewportView(tabellaCanzoni);
		tabellaCanzoni.setBackground(SystemColor.scrollbar);
		tabellaCanzoni.setShowGrid(true);
		tabellaCanzoni.setGridColor(Color.GRAY);
		tabellaCanzoni.setShowHorizontalLines(true);
		tabellaCanzoni.setShowVerticalLines(true);
		tabellaCanzoni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if(!(framePrincipale.getUtenteGlobale()==null)){
		setBackground(new Color(240,193,75));
		tabellaCanzoni.getTableHeader().setBackground(new Color(144,115,45));
		tabellaCanzoni.getTableHeader().setForeground(Color.WHITE);
		tabellaCanzoni.setBackground(new Color(240,193,75));
		scrollPane.getViewport().setBackground(new Color(244,211,129));
		}
		riempiTabella(tabellaCanzoni,EnumQuery.GET_CANZONI.getValore(),Integer.toString(cd.getCodiceCd()),colonneTab,1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(43, 270, 189, 109);
		add(scrollPane_1);
	
		tabellaMusicisti = new JTable(new String[0][0],colonneTabMus);
		scrollPane_1.setViewportView(tabellaMusicisti);
		tabellaMusicisti.setBackground(SystemColor.scrollbar);
		tabellaMusicisti.setShowGrid(true);
		tabellaMusicisti.setGridColor(Color.GRAY);
		tabellaMusicisti.setShowHorizontalLines(true);
		tabellaMusicisti.setShowVerticalLines(true);
		tabellaMusicisti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if(!(framePrincipale.getUtenteGlobale()==null)){
		setBackground(new Color(240,193,75));
		tabellaMusicisti.getTableHeader().setBackground(new Color(144,115,45));
		tabellaMusicisti.getTableHeader().setForeground(Color.WHITE);
		tabellaMusicisti.setBackground(new Color(240,193,75));
		scrollPane_1.getViewport().setBackground(new Color(244,211,129));
		}
		riempiTabella(tabellaMusicisti,EnumQuery.GET_MUSICISTI.getValore(),Integer.toString(cd.getCodiceCd()),colonneTabMus,2);

	
		caricaImmCd();
	}
	private void riempiTabella(JTable tabella,String query,String param,String [] colonneTabella,int val){
		List<HashMap<String,Object>> risultatoQuery;
		    risultatoQuery = DBManager.selectQuery(query,param);
			dm = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) { 
			    if(column == 2 || column == 3) return true;
	            else return false;	
			}
		};
	    dm.setColumnIdentifiers(colonneTabella);
		Iterator<HashMap<String,Object>> itr = risultatoQuery.iterator();
	    while (itr.hasNext()) {
	    	HashMap<String,Object> riga = itr.next();
	    	if (val==1)
	    	dm.addRow(new Object[] { riga.get("titolocanzone").toString()});
	    	if (val==2)
	    	dm.addRow(new Object[] { riga.get("nomearte").toString(),riga.get("nomestrumento").toString()});
	    }
	    
        tabella.setModel(dm);


        tabella.setRowHeight(30);
	}       	
	public String diviviStr(String str, String insert, int period){
	    StringBuilder builder = new StringBuilder(
	    		str.length() + insert.length() * (str.length()/period)+1);

	    int index = 0;
	    String prefix = "";
	    while (index < str.length())
	    {
	        builder.append(prefix);
	        prefix = insert;
	        builder.append(str.substring(index, 
	            Math.min(index + period, str.length())));
	        index += period;
	    }
	    return builder.toString();
	}
	
	
	public void caricaImmCd() throws SQLException, IOException {		
		ImageIcon img=(Funzioni.leggereImmagine(cd.getImg()));
		btnImg1.setIcon(Funzioni.ridimensionaImg(img, 189, 139));
		btnImg1.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnBack){
			framePrincipale.getContentPane().removeAll();
			framePrincipale.getContentPane().add(new CatalogoCd(framePrincipale));
    		framePrincipale.pack();
		} else if(e.getSource() == btnImg1) {
			try {
				new Immagini(cd.getImg());
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == btnDescr){
			try {
				new DescrizioneCd(cd.getDescrizione());
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		}
}
}