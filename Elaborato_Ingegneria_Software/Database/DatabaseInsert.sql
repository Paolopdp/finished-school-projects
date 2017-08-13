-- INSERIMENTO NUOVO CD
INSERT INTO CD(TitoloCD ,Prezzo,Descrizione,GenereCD)
VALUES('Hardwired... To Self-Destruct',
	30.70,
	'Hardwired... To Self-Destruct è il decimo album in studio del gruppo musicale statunitense Metallica, pubblicato il 18 novembre 2016 dalla Blackened Recordings',
	'Heavy Metal'),
	
	('Blackstar',
	45.00,
	'Blackstar è il venticinquesimo e ultimo album in studio del cantautore britannico David Bowie, pubblicato l 8 gennaio 2016 dalla Radio Corporation of America.',
	'Art Rock'),

	('Purple Rain',
	37.50,
	'Purple Rain è il primo album da studio del gruppo statunitense Prince and The Revolution, pubblicato nel 1984 dalla Warner Bros. Records.',
	'Contemporary R&B'),

	('Death of a Bachelor',
	31.50,
	'Death of a Bachelor è il quinto album in studio del gruppo rock statunitense Panic! at the Disco, pubblicato nel gennaio 2016.',
	'Pop rock'),

	('Blurryface',
	25.38,
	'Blurryface è il quarto album in studio del duo musicale statunitense Twenty One Pilots, pubblicato nel maggio 2015 dalla Fueled by Ramen',
	'Indie Pop'),

	('Dangerous Woman',
	27.14,
	'Dangerous Woman è il terzo album in studio della cantante statunitense Ariana Grande, pubblicato il 20 maggio 2016.',
	'Pop'),

	('Vessel',
	27.14,
	'Vessel è il terzo album in studio del duo musicale Twenty One Pilots, pubblicato l''8 gennaio 2013 dalla FBR',
	'Indie Pop'),

	('Ceremonials',
	31.50,
	'Ceremonials è il secondo album in studio del gruppo musicale britannico Florence and the Machine, pubblicato il 28 ottobre 2011 dalla Island Records.',
	'Art Rock'),

	('Overkill',
	50.50,
	'Overkill è il secondo album dei Motörhead pubblicato nel 1979 da Bronze Records in formato LP e MC.',
	'Heavy Metal'),

	('Hammered',
	40.50,
	'Hammered è il sedicesimo album dei Motörhead, uscito nel 2002 per la Sanctuary Records.',
	'Heavy Metal'),

	('Radici',
	20.00,
	'Radici è il quarto album di Francesco Guccini, che è autore di tutti i testi e le musiche.',
	'Musica d''autore'),

	('Tutti morimmo a stento',
	24.11,
	'Tutti morimmo a stento è il secondo album registrato in studio di Fabrizio De André per la Bluebell Records pubblicato nel 1968.',
	'Musica d''autore'),

	('Rimini',
	24.22,
	'Rimini (1978) è il nono album registrato in studio di Fabrizio De André.',
	'Musica d''autore');


INSERT INTO Canzone (CodiceCD,TitoloCanzone,NumeroCanzone)
VALUES
--Inserimento canzoni in Hardwired... To Self-Destruct
(1,'Hardwired',1), 
(1,'Atlas, Rise!',2),
(1,'Now That We are Dead',3), 
(1,'Moth into Flame',4),
(1,'Dream No More',5),
(1,'Halo on Fire',6),
(1,'Confusion',7),
(1,'ManUNkind',8),
(1,'Here Comes Revenge',9),
(1,'Am I Savage?',10),
(1,'Murder One',11),
(1,'Spit Out the Bone',12),
-- Inserimento canzoni in Blackstar
(2,'Blackstar',1),
(2,'Tis a Pity She Was a Whore',2),
(2,'Lazarus',3),
(2,'Sue (Or in a Season of Crime)',4),
(2,'Girl Loves Me',5),
(2,'Dollar Days',6),
(2,'I Cant Give Everything Away',7),
-- Inserimento canzoni in Purple Rain
(3,'Lets Go Crazy',1),
(3,'Take Me with U',2),
(3,'The Beautiful Ones',3),
(3,'Computer Blue',4),
(3,'Darling Nikki',5),
(3,'When Doves Cry',6),
(3,'I Would Die 4 U',7),
(3,'Baby I am a Star',8),
(3,'Purple Rain',9),
-- Inserimento canzoni in Death of a Bachelor
(4,'Victorious',1),
(4,'Don''t Threaten Me with a Good Time',2),
(4,'Hallelujah',3),
(4,'Emperor''s New Clothes',4),
(4,'Death of a Bachelor',5),
(4,'Crazy=Genius',6),
(4,'LA Devotee',7),
(4,'Golden Days',8),
(4,'The Good, the Bad and the Dirty',9),
(4,'House of Memories',10),
(4,'Impossible Year',11),
-- Inserimento canzoni in Blurryface
(5,'Heavydirtysoul',1),
(5,'Stressed Out',2),
(5,'Ride',3),
(5,'Fairly Local',4),
(5,'Tear in My Heart',5),
(5,'Lane Boy',6),
(5,'The Judge',7),
(5,'Doubt',8),
(5,'Polarize',9),
(5,'We Dont Believe What is on TV',10),
(5,'Message Man',11),
(5,'Hometown',12),
(5,'Not Today',13),
(5,'Goner',14),
-- Inserimento canzoni in Dangerous Woman
(6,'Moonlight',1),
(6,'Dangerous Woman',2),
(6,'Be Alright',3),
(6,'Into You',4),
(6,'Side to Side (feat. Nicki Minaj)',5),
(6,'Greedy',6),
(6,'Leave Me Lonely',7),
(6,'Everyday',8),
(6,'Bad Decisions',9),
(6,'Thinking Bout You',10),
-- Inserimento canzoni in Vessel
(7,'Ode to Sleep',1),
(7,'Holding on to You',2),
(7,'Migraine',3),
(7,'House of Gold',4),
(7,'Car Radio',5),
(7,'Semi-Automatic',6),
(7,'Leave Me Lonely',7),
(7,'Everyday',8),
(7,'Screen',9),
(7,'Fake You Out',10),
-- Inserimento canzoni in Ceremonials
(8,'Only If for a Night',1),
(8,'Shake It Out',2),
(8,'What the Water Gave Me',3),
(8,'Never Let Me Go',4),
(8,'Breaking Down',5),
(8,'Lover to Lover',6),
(8,'No Light, No Light',7),
(8,'Heartlines',8),
(8,'Spectrum',9),
(8,'All This and Heaven Too',10),
(8,'Leave My Body',11),
-- Inserimento canzoni in Overkill
(9,'Overkill',1),
(9,'Stay Clean',2),
(9,'(I Won''t) Pay Your Price',3),
(9,'I''ll Be Your Sister',4),
(9,'Capricorn',5),
(9,'No Class',6),
(9,'Damage Case',7),
(9,'Tear Ya Down',8),
(9,'Metropolis',9),
(9,'Limb from Limb',10),
-- Inserimento canzoni in Hammered
(10,'Walk A Crooked Mile',1),
(10,'Down The Line',2),
(10,'Brave New World',3),
(10,'Voices From the War',4),
(10,'Mine All Mine',5),
(10,'Shut Your Mouth',6),
(10,'Kill the World',7),
(10,'Dr. Love',8),
(10,'No Remorse',9),
(10,'Red Raw',10),
(10,'Serial Killer (Kilmister)',11),
-- Inserimento canzoni in Radici
(11,'Radici',1),
(11,'La locomotiva',2),
(11,'Piccola città',3),
(11,'Incontro',4),
(11,'Canzone dei dodici mesi',5),
(11,'Canzone della bambina portoghese',6),
(11,'Il vecchio e il bambino',7),
-- Inserimento canzoni in Tutti morimmo a stento
(12,'Cantico dei drogati',1),
(12,'Primo intermezzo',2),
(12,'Leggenda di Natale',3),
(12,'Secondo intermezzo',4),
(12,'Ballata degli impiccati',5),
(12,'Inverno',6),
(12,'Girotondo',7),
(12,'Terzo intermezzo',8),
(12,'Recitativo',9),
(12,'Corale',10),
-- Inserimento canzoni in Rimini 
(13,'Rimini',1),
(13,'Volta la carta',2),
(13,'Coda di lupo',3),
(13,'Andrea',4),
(13,'Tema di Rimini',5),
(13,'Avventura a Durango',6),
(13,'Sally',7),
(13,'Zirichiltaggia',8),
(13,'Parlando del naufragio della London Valour',9),
(13,'Folaghe',10);
INSERT INTO MUSICISTA(NomeArte,GenereArtista,AnnoNascita)
VALUES
-- Metallica
('Metallica','Heavy Metal',1981),
('James Hetfield' ,'Heavy Metal',1963),
('Kirk Hammett','Heavy Metal',1962),
('Robert Trujillo','Heavy Metal',1964),
('Lars Ulrich','Heavy Metal',1963),
-- David Bowie
('David Bowie','Art Pop',1947),
('Ben Monder','Art Pop',1961),
('Tim Lefebvre','Art Pop',1962),
-- Prince
('Prince','Contemporary R&B',1958),
-- Panic! at the Disco
('Panic! at the Disco','Pop rock',2005),
('Brendon Urie','Pop rock',1987),
-- Twenty One Pilots
('Twenty One Pilots','Indie Pop',2009),
('Tyler Joseph','Indie Pop',1988), 
('Josh Dun','Indie Pop',1988),
-- Ariana Grande
('Ariana Grande','Pop',1993),--15
('Nicki Minaj','Pop',1982),
-- Florence and the Machine
('Florence and the Machine','Art Rock', 2008), --17
('Florence Welch','Art Rock', 1986),
('Robert Ackroyd','Art Rock', 1986),
-- Motorhead
('Motorhead','Heavy Metal', 1975),-- 20
('Lemmy Kilmister','Heavy Metal', 1945),
('Eddie Clarke','Heavy Metal', 1950),
('Phil Taylor','Heavy Metal', 1954),
-- Guccini
('Francesco Guccini','Musica d''autore',1940),--24
('Deborah Kooperman','Musica d''autore',1944),
-- De Andrè
('Fabrizio De André','Musica d''autore',1940);--26

INSERT INTO MUSICISTAAUTORE (CodiceMusicista,CodiceCD)
VALUES
(1,1),
(6,2),
(9,3),
(10,4),
(12,5),
(15,6),
(12,7),
(17,8),
(20,9),
(20,10),
(24,11),
(26,12),
(26,13);
INSERT INTO MUSICISTASUONAIN (CodiceMusicista,CodiceCD)
VALUES
-- Metallica
(2,1),
(3,1),
(4,1),
(5,1),
-- David Bowie
(6,2),
(7,2),
(8,2),
-- Prince
(9,3),
-- Panic! at the Disco
(11,4),
-- Twenty One Pilots
(13,5),
(14,5),
(13,7),
(14,7),
-- Ariana Grande
(15,6),
(16,6),	
--Florence and the Machine
(18,8),
(19,8),
-- Motorhead
(21,9),
(22,9),
(23,9),
(21,10),
(22,10),
(23,10),
-- Guccini
(24,11),
(25,11),
-- De Andrè
(26,12),
(26,13);
INSERT INTO STRUMENTO(NomeStrumento)
VALUES
('Voce'),
('Chitarra'),
('Basso'),
('Batteria');

INSERT INTO STRUMENTODAMUSICISTA(CodiceMusicista,IdStrumento)
VALUES
-- Metallica
(2,1),
(3,2),
(4,3),
(5,4),
-- Bowie
(6,1),
(7,2),
(8,3),
-- Prince
(9,1),
-- Panic! at the Disco
(11,1),
-- Twenty One Pilots
(13,1),
(14,4),
-- Ariana Grande
(15,1),
(16,1),
-- Florence and the Machine
(18,1),
(19,4),
-- Motorhead
(21,1),
(22,2),
(23,4),
-- Guccini
(24,1),
(25,2),
-- De Andrè
(26,1);
INSERT INTO Cliente(CodiceFiscale,NomeUtente,password,Nome,Cognome,CittResidenza,NumeroTelefono,NumeroCellulare,TipoCliente)
			VALUES('DLLPLAX423123231','Paolopdp','itbg3hZDAibXk','Paolo','Dalla Piazza','Verona','0456300220','3454096770','REGISTRATO'),
			('DGPDFG567HGF23DF','Diecorra','itbg3hZDAibXk','Diego','Corradi','Verona','0456303250','3452036776','REGISTRATO');

INSERT INTO Vendita(codiceCliente,prezzotot,pagamento,modalitconsegna)
VALUES(1,30.70,'PAYPAL','POSTA');

INSERT INTO ContenutoVendita(codicetransazione,codicecd,codiceCliente,quantit)
VALUES (1,1,1,1);
