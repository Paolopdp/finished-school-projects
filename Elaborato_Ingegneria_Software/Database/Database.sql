-- DEFINIZIONE DOMINI DELLA TABELLA
CREATE DOMAIN PAGAMENTO AS VARCHAR
	CHECK ( VALUE IN ( 'PAYPAL','BONIFICO','CARTACREDITO'));

CREATE DOMAIN CONSEGNA AS VARCHAR
	CHECK ( VALUE IN ( 'POSTA','CORRIERE'));

CREATE DOMAIN TIPOLOGIACLIENTE AS VARCHAR
	CHECK ( VALUE IN ( 'REGISTRATO','OCCASIONALE'));

--DEFINIZIONE DELLE TABELLE
	CREATE TABLE CD(
		CodiceCD SERIAL PRIMARY KEY, -- Codice univoco
		TitoloCD VARCHAR(100) NOT NULL,-- Titolo del CD. I CD possono avere nomi identici
		Prezzo NUMERIC(12,2) CHECK (Prezzo>=0),-- Prezzo del cd, deve essere positivo o nullo
		Data DATE DEFAULT CURRENT_DATE,-- Data dalla quale è presente sul sito web del negozio, come default utilizza il momento dell'inserimento
		Descrizione VARCHAR(300) NOT NULL , -- Descrizione del cd
		GenereCD VARCHAR(50) NOT NULL -- Genere principale del cd
	);

	CREATE TABLE Canzone (
		CodiceCanzone SERIAL PRIMARY KEY, -- Codice della canzone
		CodiceCD INTEGER NOT NULL REFERENCES CD(codiceCD)-- Codice del cd a cui appartiene
			ON DELETE CASCADE ON UPDATE CASCADE,
		TitoloCanzone VARCHAR(100) NOT NULL, -- Titolo della canzone
		NumeroCanzone INTEGER NOT NULL -- Numero della canzone
	);	 

	CREATE TABLE INVENTARIO( -- Quantità in magazzino
		CodiceCD INTEGER NOT NULL UNIQUE REFERENCES CD(codiceCD)
			ON DELETE CASCADE ON UPDATE CASCADE,
		CopieDisponibili INTEGER NOT NULL DEFAULT 30
	);

	CREATE TABLE STRUMENTO(
		IdStrumento SERIAL PRIMARY KEY, -- Id dello strumento
		NomeStrumento  VARCHAR(50) UNIQUE NOT NULL -- Nome dello strumento
	);

	CREATE TABLE MUSICISTA(
		CodiceMusicista SERIAL PRIMARY KEY, -- Codice artista
		NomeArte VARCHAR(50) NOT NULL , -- Nome d'arte
		GenereArtista VARCHAR(50) NOT NULL , -- Genere dell'artista
		AnnoNascita INTEGER -- Anno di nascita se noto
	);

	CREATE TABLE MUSICISTAAUTORE(
		CodiceMusicista INTEGER NOT NULL REFERENCES Musicista(codiceMusicista) 
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del musicista autore del cd
		CodiceCD INTEGER NOT NULL REFERENCES CD(codiceCD) 
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del Cd di cui il musicista è l'autore
		PRIMARY KEY(CodiceMusicista,CodiceCD)
	);

	CREATE TABLE MUSICISTASUONAIN(
		CodiceMusicista INTEGER NOT NULL REFERENCES Musicista(codiceMusicista) -- Codice del Cd
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del musicista che suona nel cd
		CodiceCD INTEGER NOT NULL REFERENCES CD(codiceCD) 
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del Cd dove suona il musicista
		PRIMARY KEY(CodiceMusicista,CodiceCD)
	);

	CREATE TABLE STRUMENTODAMUSICISTA( -- Serve per la relazione molti a molti tra strumento e musicista
		ID SERIAL PRIMARY KEY, 
		CodiceMusicista INTEGER REFERENCES MUSICISTA(CodiceMusicista) 
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del musicista che suona lo strumento
		IdStrumento INTEGER REFERENCES STRUMENTO(IdStrumento)
			ON DELETE CASCADE ON UPDATE CASCADE -- Codice dello strumento suonato dal musicista
		);

	CREATE TABLE Cliente(
		CodiceCliente SERIAL PRIMARY KEY, -- Codice che identifica il cliente
		CodiceFiscale VARCHAR(16),-- Codice fiscale del cliente
		NomeUtente VARCHAR(50) UNIQUE, -- Nome utente, univoco
		password VARCHAR(50),-- Password del cliente
		Nome VARCHAR(50) NOT NULL , -- Nome del cliente
		Cognome VARCHAR(50) NOT NULL, -- Cognome del cliente
		CittResidenza VARCHAR(50) NOT NULL, -- Città di residenza del cliente
		NumeroTelefono VARCHAR(11) NOT NULL, -- numero di telefono dell'utente
		NumeroCellulare VARCHAR(11), -- numero di cellulare dell'utente (opzionale)
		TipoCliente TIPOLOGIACLIENTE -- Tipo di cliente ( occasionale o registrato)
	);

	CREATE TABLE VENDITA(
		CodiceTransazione SERIAL PRIMARY KEY, -- Codice della transazione
		CodiceCliente INTEGER NOT NULL REFERENCES CLIENTE(CodiceCliente)
			ON DELETE CASCADE ON UPDATE CASCADE, -- Cliente Che Ha Acquistato
		PrezzoTot NUMERIC(12,2) CHECK(PrezzoTot>=0), -- Prezzo totale pagato
		DataAcquisto DATE DEFAULT CURRENT_DATE, -- Data dell'acquisto, default questo istante
		OraAcquisto TIME DEFAULT current_timestamp(0), -- Ora di acquisto, default adesso
		IndirizzoIp INET DEFAULT inet_client_addr(),-- Indirizzo ip del pc dal quale acquista il cliente, default il suo pc
		Pagamento PAGAMENTO NOT NULL, -- Modalità di pagamento (paypal,bonifico,carta di credito)
		ModalitConsegna CONSEGNA NOT NULL-- Modalità di consegna (posta,corriere)
	);

	CREATE TABLE CONTENUTOVENDITA(
		CodiceTransazione INTEGER NOT NULL REFERENCES VENDITA(CodiceTransazione)
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice della transazione di cui fa parte la vendita
		CodiceCD INTEGER NOT NULL REFERENCES CD(codiceCD)
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del cd acquistato
		CodiceCliente INTEGER NOT NULL REFERENCES CLIENTE(CodiceCliente)
			ON DELETE CASCADE ON UPDATE CASCADE,-- Codice cliente Che Ha Acquistato
		Quantit INTEGER CHECK(Quantit>0) -- Quanti cd di quel tipo sono stati acquistati
	);
	
	CREATE TABLE IMMAGINE(
		CodiceCD INTEGER NOT NULL REFERENCES CD(codiceCD)
			ON DELETE CASCADE ON UPDATE CASCADE, -- Codice del cd di cui è la copertina
		ImgName TEXT PRIMARY KEY, -- Nome dell'immagine, [codiceCD].[formato]
		Img BYTEA NOT NULL-- Contenuto dell'immagine
	);
