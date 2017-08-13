package utenti;

public class UtenteRegistrato extends UtenteDecorator{
	public UtenteRegistrato(Utente ut) {
		super(ut);
	}

	@Override
	public String getTipoCliente(){
		return "Registrato";
	}
}