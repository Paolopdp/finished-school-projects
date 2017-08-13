package interfaccia;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
/**
 * Classe DescrizioneCd
 * Mostra i dettagli della descrizione del cd
 *
 */
public class DescrizioneCd extends JFrame{
	private static final long serialVersionUID = 1L;
		
	public DescrizioneCd(){
		super();
	}

	public DescrizioneCd(String descrizione) throws SQLException, IOException{
		JPanel pannello = new JPanel();
		this.setPreferredSize(new Dimension(1200, 100));				
		JLabel lblDescrizioneCd = new JLabel(descrizione);
		lblDescrizioneCd.setBounds(10, 10, 10, 10);
		pannello.add(lblDescrizioneCd);
        this.getContentPane().add(pannello);
		this.setTitle("Descrizione");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		this.setLocation ( ( screenSize.width / 2 )-400, (
		screenSize.height / 2 )-200);
		this.pack();
	}
}