package utility;

import java.util.ArrayList;
import java.util.Iterator;

public class Carrello {

	private static Carrello carrello = null;
	private ArrayList<Cd> cds;
	

	protected Carrello(){
		cds = new ArrayList<Cd>();
	}

	public static Carrello getCarrello() {
		if(carrello == null) {
			carrello = new Carrello();
		}
		return carrello; 	
	}
	
	public static void setCds(ArrayList<Cd> arr){
		getCarrello().cds = arr;
	}

	public static void aggiungiCd(Cd str){
		getCarrello().cds.add(str);
	}
	
	public static void rimuoviCd(int indice){
		getCarrello().cds.remove(indice);
	}
	
	public static ArrayList<Cd> getCds(){
		return getCarrello().cds;
	}
	
	public static float getPrezzoTotale(){
		float prezzoFinale = 0;
		Iterator<Cd> itr = getCarrello().cds.iterator();
	    while (itr.hasNext()) {
	    	prezzoFinale += itr.next().getPrezzo();
	    }
	    return prezzoFinale;
	}

	public static int getTotCds(){
		return getCarrello().cds.size();
	}
	
	public static Cd getCdByIndex(int indice){
		return getCarrello().cds.get(indice);
	}
	
	public static Cd getCdByCodice(int codiceCd){
		Cd tmp = null;
		Iterator<Cd> itr = getCarrello().cds.iterator();
	    while (itr.hasNext() && tmp == null) {
	    	Cd curr = itr.next();
	    	if(curr.getCodiceCd() == codiceCd) tmp = curr;
	    }
	    return tmp;
	}
		
	public static int getNCds(int codice){
		int cont = 0;
		Iterator<Cd> itr = getCarrello().cds.iterator();
	    while (itr.hasNext()) {
	    	if(itr.next().getCodiceCd() == codice) cont++;
	    }
	    return cont;
	}
	
	public static void aggiornaQuantita(int quant, int indice){
		Cd str = getCarrello().cds.get(indice);
		Iterator<Cd> itr = getCarrello().cds.iterator();
		int cont = 0;
		int codiceCd = str.getCodiceCd();
	    while (itr.hasNext()) {
	    	if(itr.next().getCodiceCd() == codiceCd){
	    		cont++;
	    		if(cont > quant) itr.remove();
	    	}
	    }

	    int daAgg = (quant - cont);
	    if(daAgg > 0) for(int i = 0; i < daAgg; i++) Carrello.aggiungiCd(str);
	}
	
	public static void svuotaCarrello(){
		getCarrello().cds.clear();
	}
}