package utility;

public class Cd {
	private int codiceCd;
	private String titoloCd;
	private float prezzo;
	private String data;
	private String descrizione;
	private String genere;
	
	public Cd(int codiceCd,String titoloCd,Float prezzo, String data, String descrizione, String genere){
		this.codiceCd = codiceCd;
		this.titoloCd = titoloCd;
		this.prezzo = prezzo;
		this.data = data;
		this.descrizione = descrizione;
		this.genere = genere;
	}	

	public int getCodiceCd(){
		return codiceCd;
	}
	
	public String getTitoloCd(){
		return titoloCd;
	}
	
	public Float getPrezzo(){
		return prezzo;
	}
	
	public String getData(){
		return data;
	}
	
	public String getDescrizione(){
		return descrizione;
	}
	
	public String getGenere(){
		return genere;
	}
	public String getImg(){
		return Integer.toString(codiceCd)+".jpg";
	}

}