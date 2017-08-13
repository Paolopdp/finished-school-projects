package interfaccia;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;

import utility.Carrello;
import utility.Cd;
/**
 * Classe PreviewCarrello
 * Visualizza gli elementi presenti nel carrello
 *
 */
public class PreviewCarrello extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel pannelloElementi;
	private JScrollPane scrollPane;
	
	public PreviewCarrello() {
		setLayout(null);
		setPreferredSize(new Dimension(258, 242));
		setOpaque(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 245, 230);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		pannelloElementi = new JPanel();
		pannelloElementi.setBorder(null);
		pannelloElementi.setLayout(null);
		pannelloElementi.setBackground(Color.WHITE); 
		scrollPane.setViewportView(pannelloElementi);
		aggiornaPreview();
	}
	
	private void aggiornaPreview(){
		int yElemento = 6;
		int cont = 0;
		Iterator<Cd> itr = Carrello.getCds().iterator();
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		boolean haElementi = itr.hasNext();
		
	    while (itr.hasNext()) {
	    	Cd tmp = itr.next();
	    	
	    	if(!list.contains(tmp.getCodiceCd())){
	    		list.add(tmp.getCodiceCd());
	    		
	        	JButton tBtn = new JButton();
	        	tBtn.setIcon(new ImageIcon("img/togliCarrello.png"));
	        	tBtn.setBounds(6, yElemento, 38, 29);
	        	tBtn.setBorder(null);
	        	tBtn.setBackground(Color.WHITE);
	        	
	    		JLabel lblTitolo = new JLabel(tmp.getTitoloCd());
	    		lblTitolo.setFont(new Font("Arial", Font.ITALIC, 11)); 
	    		lblTitolo.setBounds(56, yElemento+5, 90, 16);
	    		
	    		JLabel lblQuant = new JLabel(String.format("%d x %.2f \u20ac", Carrello.getNCds(tmp.getCodiceCd()), tmp.getPrezzo()));
	    		lblQuant.setFont(new Font("Arial", Font.BOLD, 11)); 
	    		lblQuant.setBounds(160, yElemento+5, 90, 16);
	    		
	            JSeparator separatore = new JSeparator();
	            separatore.setBounds(6, yElemento + 29, 233, 12);
	
	    		tBtn.addActionListener(this);
	    		tBtn.setName("btn" + cont);
	
	    		pannelloElementi.add(tBtn);
	    		pannelloElementi.add(lblTitolo);
	    		pannelloElementi.add(lblQuant);
	    		pannelloElementi.add(separatore);
	    		yElemento += 41;
	    	}
	    	cont++;
	    }

	    if(haElementi){    
	        JLabel lblTotale = new JLabel("Totale:");
	        lblTotale.setBounds(100, yElemento + 16, 50, 16);
	        lblTotale.setFont(new Font("Arial", Font.BOLD, 15)); 
	
	        JLabel lblTotPrezzo = new JLabel(String.format("%.2f \u20ac", Carrello.getPrezzoTotale()));
	        lblTotPrezzo.setBounds(160, yElemento + 16, 90, 16);
	        lblTotPrezzo.setFont(new Font("Arial", Font.BOLD, 15)); 
	        lblTotPrezzo.setForeground(Color.decode("#990000"));
	        
	        pannelloElementi.add(lblTotale);
	        pannelloElementi.add(lblTotPrezzo);
	        
	        scrollPane.setBounds(6, 6, 245, (yElemento + 45 <= 242) ? yElemento + 45 : 240);
	        pannelloElementi.setPreferredSize(new Dimension(240, yElemento + 50));
	        setPreferredSize(new Dimension(258, (yElemento + 50 <= 242) ? yElemento + 50 : 242));
	        scrollPane.setPreferredSize(new Dimension(245, yElemento + 50));
	    }else{
	    	JLabel lblVuoto = new JLabel("Il tuo carrello \u00E8 vuoto!");
	    	lblVuoto.setFont(new Font("Arial", Font.ITALIC, 15)); 
	    	lblVuoto.setBounds(54, 22, 150, 16);
	    	pannelloElementi.add(lblVuoto);
	    	
	    	scrollPane.setBounds(6, 6, 245, 75);
	    	pannelloElementi.setPreferredSize(new Dimension(240, 70));
	        setPreferredSize(new Dimension(258, 70));
	        scrollPane.setPreferredSize(new Dimension(245, 75));
	    }
	}
	
	protected void aggiornaStile(){
		pannelloElementi.removeAll();
		aggiornaPreview();
		pannelloElementi.revalidate();
		pannelloElementi.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		repaint();
		revalidate();
	}
		
	public void actionPerformed(ActionEvent e) {
		int indexArray = Integer.parseInt(((JButton) e.getSource()).getName().split("btn")[1]);
		Carrello.rimuoviCd(indexArray);
		aggiornaStile();
	} 
}