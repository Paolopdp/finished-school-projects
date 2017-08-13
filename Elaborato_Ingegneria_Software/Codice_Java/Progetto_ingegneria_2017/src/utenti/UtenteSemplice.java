package utenti;

public class UtenteSemplice implements Utente{
	private String nomeUtente;
	private String cf;
	private String nome;
	private String cognome;
	private String ntelefono;
	private String ncellulare;
	private String codiceCliente;

	
	public UtenteSemplice(String codiceCliente,String nomeUtente,String cf,String nome, String cognome, String ntelefono, String ncellulare){
		this.codiceCliente = codiceCliente;
		this.nomeUtente = nomeUtente;
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.ntelefono = ntelefono;
		this.ncellulare = ncellulare;

	}
	@Override
	public String getCodiceCliente(){
		return codiceCliente;
	}
	
	@Override
	public String getUsername(){
		return nomeUtente;
	}
	
	@Override
	public String getCf(){
		return cf;
	}
	
	@Override
	public String getNome(){
		return nome;
	}
	
	@Override
	public String getCognome(){
		return cognome;
	}
	
	@Override
	public String getNomeCognome(){
		return nome + " " + cognome;
	}
	
	@Override
	public String getTipoCliente(){
		return "Utente semplice";
	}
	
	@Override
	public String getTelefono(){
		return ntelefono;
	}
	
	@Override
	public String getCellulare(){
		return ncellulare;
	}
}
