package utility;

public enum EnumQuery {
// Utilizzata per verificare che esista un cliente con quel nome utente e password
    ACCESSO_LOGIN("SELECT codicecliente FROM cliente WHERE nomeutente = ? AND password = ?;"),
// Seleziono tutti gli attributi del cliente che ha un certo nome utente
	GET_CLIENTE("SELECT C.codicecliente ,C.nomeutente, C.CodiceFiscale, C.Nome, C.Cognome, C.Numerotelefono, C.Numerocellulare, C.tipocliente "
			+ "FROM cliente AS C "
			+ "WHERE C.nomeutente = ?;"),
// Seleziono tutti gli attributi del cliente che non è registrato
	GET_CLIENTE_UNREGISTRED("SELECT C.codicecliente ,C.nomeutente, C.CodiceFiscale, C.Nome, C.Cognome, C.Numerotelefono, C.Numerocellulare, C.tipocliente "
			+ "FROM cliente AS C "
			+ "WHERE C.codicecliente = ?;"),
// Seleziono un immagine avente un certo nome
	GET_IMAGE("SELECT img FROM immagine WHERE imgname = ?;"),
// Verifico se esiste un cliente con un certo nome utente
    CHECK_USERNAME("SELECT 1 FROM cliente WHERE nomeutente = ?;"),
// Inserisco i dati di un cliente nel database
    INSERISCI_CLIENTE("INSERT INTO cliente(CodiceFiscale,NomeUtente,password,Nome,Cognome,CittResidenza,NumeroTelefono,NumeroCellulare,TipoCliente)"
    				+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);"),
// Inserisco i dati di un cliente occasionale nel database
    INSERISCI_CLIENTE_UNREGISTRED("INSERT INTO cliente (Nome,Cognome,CittResidenza,NumeroTelefono,NumeroCellulare,TipoCliente)"
			+"VALUES(?,?,?,?,?,?);"),
// Seleziono il valore dell'ultima transazione contenuta nella tabella vendita
    MAX_NTRANS("SELECT max(codicetransazione) from vendita;"),
// Seleziono il valore del codice cliente dell'ultimo cliente registrato
    MAX_CODICE_CLIENTE("SELECT max(codicecliente) from cliente;"),
// Ricerca per autore
    RICERCA_CD_AUTORE("SELECT cd.* "
			+ "FROM musicista as m join musicistaautore on m.codicemusicista=musicistaautore.codicemusicista "
			+ "join cd on cd.codicecd=musicistaautore.codicecd "
			+ "where m.nomearte ilike ?"),
// Ricerca per prezzo
	RICERCA_CD_PREZZO("SELECT cd.* ,m.nomearte as autore "
			+ "FROM musicista as m join musicistaautore on m.codicemusicista=musicistaautore.codicemusicista "
			+ "join cd on cd.codicecd=musicistaautore.codicecd "
			+ "WHERE prezzo = ?"),
// Ricerca per titolo
    RICERCA_CD_TITOLO("SELECT cd.*,m.nomearte as autore "
			+ "FROM musicista as m join musicistaautore on m.codicemusicista=musicistaautore.codicemusicista "
			+ "join cd on cd.codicecd=musicistaautore.codicecd "
			+ "WHERE titolocd ilike ?"),
// Ricerca per genere
    RICERCA_CD_GENERE("SELECT cd.*,m.nomearte as autore "
			+ "FROM musicista as m join musicistaautore on m.codicemusicista=musicistaautore.codicemusicista "
			+ "join cd on cd.codicecd=musicistaautore.codicecd "
			+ "WHERE generecd ilike ?"),
// Restituisce i cd consigliati ( con autore o genere simili)
    RICERCA_CD_CONSIGLIATI("SELECT cd.* FROM musicista as m join musicistaautore on m.codicemusicista=musicistaautore.codicemusicista "
    		+ "join cd on cd.codicecd=musicistaautore.codicecd "
    		+ "WHERE cd.codicecd NOT IN("
		+" select cd.codicecd from cd left outer join contenutovendita on cd.codicecd=contenutovendita.codicecd where codicecliente=?)"
		+" AND("
		+" m.nomearte IN"
		+" (select musicista.nomearte from cd left outer join contenutovendita on cd.codicecd=contenutovendita.codicecd"
		+" join musicistaautore on cd.codicecd=musicistaautore.codicecd"
		+" join musicista on musicista.codicemusicista=musicistaautore.codicemusicista"
		+" where codicecliente=?)"
		+" OR cd.generecd IN("
		+" select cd.generecd from cd left outer join contenutovendita on cd.codicecd=contenutovendita.codicecd where codicecliente=?));"),
// Restituisce tutti i cd registrati nel database
    GET_CD("SELECT CD.codicecd,CD.titolocd,CD.prezzo FROM CD"),
// Restituisce tutte le canzoni di un cd
    GET_CANZONI("SELECT titolocanzone FROM CD JOIN canzone as c ON c.codicecd=cd.codicecd WHERE cd.codicecd=?;"),
// Restituisce i musicisti che suonano in quel cd
    GET_MUSICISTI("SELECT s.NomeStrumento,m.NomeArte "
    		+"FROM strumentodamusicista as sm JOIN strumento as s on sm.IdStrumento=s.IdStrumento "
    		+"JOIN MUSICISTA as M on m.CodiceMusicista=sm.CodiceMusicista "
    		+"JOIN MUSICISTASUONAIN as MS on m.codiceMusicista=ms.codiceMusicista "
    		+"join cd on cd.codicecd=ms.codicecd "
    		+"WHERE cd.codicecd=?;"),
// Restituisce i dettagli di un cd
    GET_DETTAGLI_CD("SELECT codicecd , titolocd , prezzo , data , descrizione , generecd FROM CD WHERE codiceCD = ?"),
// Inserisco l'acquisto completo nel database
	INSERISCI_ACQUISTO("INSERT INTO vendita (codicecliente,prezzotot,pagamento,modalitconsegna) VALUES ( ?, ?, ?, ?);"),
// Inserisco l'acquisto dei singoli cd nel database
	INSERISCI_ACQUISTI_SINGOLI("INSERT INTO contenutovendita (CodiceTransazione,CodiceCD,CodiceCliente,Quantit) VALUES ( ?, ?, ?, ?);"),
// Verifico se il cliente ha già effettuato almeno 3 acquisti da 250€ durante quest'anno
	CHECK_CLIENTE_SPESO_250("SELECT 1 FROM vendita "
			+ "WHERE codicecliente=? AND prezzotot>=250 AND (EXTRACT(YEAR FROM dataacquisto) = EXTRACT(YEAR FROM current_date));");
	
    private String qVal;


    private EnumQuery(String qVal) {
        this.qVal = qVal;
    }

    public String getValore() {
        return this.qVal;
    }

}