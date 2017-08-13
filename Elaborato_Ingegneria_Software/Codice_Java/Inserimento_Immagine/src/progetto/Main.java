package progetto;
import java.io.*;
import java.sql.*;
public class Main {
	public static void main(String [] args) throws SQLException, IOException{
	
	for(int i=1;i<14;i++){
	Connection conn=null;
	File file = new File("img/"+i+".jpg");
	Connessione connect=new Connessione(conn);
	conn=connect.getConnessione();
	connect.InserireImmagine(file);
	connect.LeggereImmagine("13.jpg");
	connect.Chiudi();
	}
	}
}
