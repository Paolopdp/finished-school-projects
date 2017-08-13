package utenti;

public class UtenteOccasionale extends UtenteDecorator{
	public UtenteOccasionale(Utente ut) {
		super(ut);
	}

	@Override
	public String getTipoCliente(){
		return "Occasionale";
	}
}