package progetto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Connessione {
	Connection conn;
	public Connessione(Connection conn){
		this.conn=conn;
	}
	public Connection getConnessione() {
		String url = "jdbc:postgresql://localhost/"; // URL del database, questo è quello base che installa postgres
	    String dbName = "postgres"; // Nome standard del DB
	    String userName = "postgres"; // Utente standard del DB
	    String password = ""; // Password del DB
		try {
			if(conn == null) conn = DriverManager.getConnection(url + dbName , userName,password); // Connessione
			//conn = DriverManager.getConnection(url + dbName);
		} catch (SQLException e) {
			System.out.println("Errore nella connessione al DB");
			System.out.println("Errore = " + e.getMessage());
		}
	    return conn;
	}

	public void InserireImmagine(File file) throws SQLException, IOException{

			FileInputStream fis = new FileInputStream(file);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO immagine VALUES (?, ? , ?)");
			ps.setInt(1, 1); // Set primo parametro (Identificatore CD)
			ps.setString(2, file.getName()); // Set secondo parametro (Nome CD preso da file)
			ps.setBinaryStream(3, fis, file.length()); // Set terzo parametro(BYTEA)
			ps.executeUpdate();
			ps.close();
			fis.close();

	}
	public void LeggereImmagine(String nomefile) throws SQLException, IOException {
	PreparedStatement pss = conn.prepareStatement("SELECT img FROM immagine WHERE imgname = ?");
	pss.setString(1, nomefile);
	ResultSet rs = pss.executeQuery();
	while (rs.next()) {
	    byte[] imgBytes = rs.getBytes(1);
	   
	    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));
	    JFrame frame = new JFrame();
	    frame.getContentPane().setLayout(new FlowLayout());
	    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
	    frame.pack();
	    frame.setVisible(true);
	}
	rs.close();
	pss.close();
	}
	
	public void Chiudi() throws SQLException{
		conn.close();
	}
}