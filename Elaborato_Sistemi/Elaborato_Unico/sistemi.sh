#!/bin/bash
	
#Funzione che viene richiamata dopo che premo 1 nel menu
	function Prenota {
		echo ---------------------------------------------------------
		echo Hai selezionato la funzione Prenota
		echo ---------------------------------------------------------
#Lancio le funzioni per inserire aula,data,ora e nome.
		inserireaula
		datavalida
		oravalida
		nomeutente
#Verifico se la prenotazione può essere effettuata
		if grep -i  $aula";"$anno$mese$giorno";"$ora aula.txt; then
		echo Aula Occupata	
#Se ho un'aula libera procedo		
		else
		echo  Aula libera ed è stata prenotata
#Scrivo sul file aula.txt le informazioni della prenotazione
		echo $aula";"$anno$mese$giorno";"$ora";"$nome >> aula.txt 
	fi
	Menu	
	}
#Funzione per verificare la validità della data
	function datavalida {
		echo inserisci la data AAAAMMGG
		echo ---------------------------------------------------------
		read data;
		echo ---------------------------------------------------------
		lenght=${#data} 
#Controllo che la data inserita sia di 8 bit
		if test $lenght != 8 ; then
			echo errore! hai sbagliato ad inserire la data
#Reinsierisco la data
			datavalida
        else
#Assegno ad anno,mese e giorno i loro valori
		    anno=$((data/10000))
		    temp=$((data%10000))
		    mese=$((temp/100))
		    giorno=$((temp%100))
#Richiamo la funzione per verificare se data,mese e giorno sono #corretti	
		    datacorretta
        fi
	}

	function datacorretta {
#Verifico che l'anno sia almeno quello corrente
	if test ${#anno} -ne 4 || test $anno -lt 2016  ; then
		echo Anno non valido, inserire un anno successivo al 2015
		echo ---------------------------------------------------------
		read anno
		echo ---------------------------------------------------------
		datacorretta
    else

#Verifico che il mese esista	
	    if test ${#mese} -lt 1 || test $mese -lt 1 || test $mese -gt 12 ; then
		    echo Mese non valido, inserire un mese corretto
		    echo ---------------------------------------------------------
		    read mese
		    echo ---------------------------------------------------------
		    datacorretta
	    else
#Verifico che il giorno sia almeno 1
	        if test ${#giorno} -lt 1 || test $giorno -lt 1 ; then
		        echo ---------------------------------------------------------
		        echo Giorno non valido. Inserire giorno corretto
		        echo ---------------------------------------------------------
		        read giorno
		        datacorretta
	        else
#Verifico che i giorni siano corretti, in base al mese	
	case $mese in
#Aprile,Giugno,Settembre e Novembre hanno 30 giorni
	04|06|09|11) if test $giorno -gt 30; then
			        echo Giorno non valido! Aprile,Giugno,Settembre e Novembre hanno 30 giorni. Inserire giorno corretto
			        echo ---------------------------------------------------------
			        read giorno
			        echo ---------------------------------------------------------
			        datacorretta
                    fi;;
#Febbraio ha 28 giorni se l'anno non è bisestile, altrimenti 29. Richiamo la funzione bisestile e verifico se è un anno bisestile
	    02) bisestile
	     if test $abisestile = 0 ; then
		    if test $giorno -gt 29; then
			    echo Giorno non valido! In questo anno Febbraio può avere al massimo 29 giorni
			    echo Inserire giorno corretto
			    echo ---------------------------------------------------------
			    read giorno
			    echo ---------------------------------------------------------
			    datacorretta
		    fi
	    else 
		    if test $giorno -gt 28; then
			    echo Giorno non valido! In questo anno Febbraio può avere al massimo 28 giorni
			    echo ---------------------------------------------------------
			    read giorno
			    echo ---------------------------------------------------------
			    datacorretta		
        	    fi
                fi;;
#Gennaio,Marzo,Maggio,Luglio,Agosto,Ottobre,Dicembre hanno 31 giorni
	    *) if test $giorno -gt 31; then
		    echo Giorno non valido Gennaio,Marzo,Maggio,Luglio,Agosto,Ottobre,Dicembre hanno 
		    echo 31 giorni. Inserire giorno corretto
		    echo ---------------------------------------------------------
		    read giorno
		    echo ---------------------------------------------------------
		    datacorretta
	    fi;;
	
	esac
	fi
    fi
    fi
	 if test ${#mese} = 1 ; then
			    mese=0$mese
		    fi
		   
		    if test ${#giorno} = 1 ; then
			    giorno=0$giorno
		    fi
	}
#Controllo se l'anno è bisestile
	function bisestile {
	if test $((anno % 4)) = 0 && test $((anno % 100)) != 0 || test $((anno % 400)) = 0  ; then 
		abisestile=0
	else 
		abisestile=1
	fi
	}
#Controllo se l'ora è tra 8 e 17	
	function oravalida {
		echo Inserisci l ora 
		echo ---------------------------------------------------------
		read ora
		echo ---------------------------------------------------------
		case ${ora#[-+]} in
#Controllo che l'utente inserisca un'ora valida, che sia un numero intero tra 8 e 17
		  *[0-9]* ) if test $ora -lt 8 || test $ora -gt 17 ; then
				echo L ora è valida tra le 8 e le 17
				oravalida
				fi;;	 
	  	  * )	 echo L^ora va scritta come numero intero, tra 8 e 17
			oravalida;;
		esac	
	}
	
	function nomeutente {	
		echo Inserisci il nome utente
		echo ---------------------------------------------------------
		read nome
		echo ---------------------------------------------------------
		if test ${#nome} -lt 1;then
		echo Devi inserire il nome!
		nomeutente
		fi

	}
	
	function inserireaula {
		echo Inserisci un^aula, ricorda che le aule non possono essere
		echo rappresentate da semplici numeri interi
		echo Esempi aule: Tessari,2.1,A,B corretto! 1,2,12 non corretti!
		echo ---------------------------------------------------------		
		read aula
		echo ---------------------------------------------------------
		case ${aula#[-+]} in
		  *[!0-9]* ) echo Hai inserito l^aula ${aula^^} ;;
	  	  * ) echo Hai inserito un numero intero! ;
			echo ---------------------------------------------------------
		  	inserireaula;;
esac

	}
		
#Funzione che viene richiamata dopo che premo 2 nel menu
	function EliminaPrenotazione {
		echo ---------------------------------------------------------
		echo Hai selezionato la funzione Elimina Prenotazione
		echo ---------------------------------------------------------
#Richiedo aula,data ed ora verificando che siano corrette
		inserireaula
		datavalida
		oravalida
#Se la prenotazione è presente nel file txt, la elimino
		if grep -i $aula";"$anno$mese$giorno";"$ora aula.txt; then
			echo La prenotazione è presente
			sed -i /$aula";"$anno$mese$giorno";"$ora/Id aula.txt
			echo Prenotazione eliminata	
#Altrimenti restituisco un messaggio di errore	
		else
			echo  Errore, non esiste questa prenotazione
		fi
	Menu
	}

#Funzione che viene richiamata dopo che premo 3 nel menu
		function MostraAula {
		echo ---------------------------------------------------------
		echo Hai selezionato la funzione Mostra aula
		echo ---------------------------------------------------------
		echo Inserisci il nome dell aula di cui vuoi avere informazioni
		read aula
		echo ---------------------------------------------------------	
		if test ${#aula} -lt 1; then 
		echo Non hai inserito nulla.
		MostraAula	
		else	
#${aula^^} Permette di ottenere in output una stringa maiuscola 		
		echo PRENOTAZIONI AULA ${aula^^}
		echo Giorno Ora Utente
#Non è case sensitive, prima rimuovo dal file txt l'aula, poi sostituisco i ";" con degli spazi, infine ordino per il parametro data e ora
		if  grep -i $aula";" aula.txt | sed -e "s/$aula//i"|sed -e 's/;/ /g' | sort  -k2n,3n ;then
			echo
		else
			echo Non ci sono prenotazioni per l aula inserita
		fi
		fi
		Menu
	}

#Funzione che viene richiamata dopo che premo 4 nel menu
	function PrenotazioniPerAula {
		echo ---------------------------------------------------------
		echo Hai selezionato la funzione Prenotazioni per aula
		echo ---------------------------------------------------------
		
		
#Calcolo quante aule ci sono in totale
		numaule=$(cut -d';' -f1 aula.txt | sort -f -u | wc -l)
#Ciclo while
		i=0
		while test $i -lt $numaule; do
		i=$((i+1))
#estraggo il nome dell'aula,prendendo ciascuna riga una alla volta
		nomeaula=$(cut -d';' -f1 aula.txt | sort -f -u | sed "${i}q;d")
#Calcolo quante volte si ripetono le prenotazioni per ciascuna aula
		numprenotazioni=$(grep -i -c "$nomeaula;" aula.txt)
#File txt di appoggio per ordinare le aule		
		echo ${nomeaula^^}":" $numprenotazioni >> sort.txt	
		done
#Ordino, stampo ed elimino il file sort
		sort -t":" -k2n,2n sort.txt 
		rm sort.txt
		Menu
	}

#Funzione che viene richiamata dopo che premo 5 nel menu
	function Esci {
		echo -----------------------------------------------------------------------------
		echo Script terminato. Elenco disponibile nel file aula.txt
		echo -----------------------------------------------------------------------------
		exit
	}

#Funzione per il menu
function Menu {
	echo -----------------------------------------------------------------------------
	echo Questo è il menu. Inserisci in input il numero per effettuare una operazione
	echo -----------------------------------------------------------------------------
	echo 1. Prenota
	echo 2. Elimina prenotazione
	echo 3. Mostra aula
	echo 4. Prenotazioni per aula
	echo 5. Esci
# Leggo l'input da tastiera
	read scelta
# In base all'input invoco una funzione 
	case $scelta in
		1) Prenota;;
		2) EliminaPrenotazione;;
		3) MostraAula;;
		4) PrenotazioniPerAula;;
		5) Esci;;
#Se c'è un errore, richiamo la funzione, finchè il comando non è valido
		*) echo errore inserisci un comando valido;
		Menu;;
	esac
}
# Qui inizia il main
Menu


