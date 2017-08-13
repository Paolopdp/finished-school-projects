package interfaccia;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Pannello pan = new Pannello();
				pan.avvia();
				pan.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				pan.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				    	pan.chiudiConnessione();
				    	System.exit(0);
				    }
				});
			}
		});
	}
}