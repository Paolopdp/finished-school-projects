#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/ipc.h> 

#define SIZE 512 	// Dimensione del buffer di lettura

/** op_to_int: converte il carattere del segno (char) in numero (int) compreso tra 1 e 4
    @param c Il carattere corrispondente al segno di operazione
    @return Il numero assegnato al segno di operazione
*/
int op_to_int(char c){
  switch(c){
    case '+':return 1;
    case '-':return 2;
    case '*':return 3;
    case '/':return 4;
  }
}

/** int_to_op: metodo che converte il numero del segno (int) nel carattere del segno (char)
    @param a numero corrispondente al segno di operazione
    @return il carattere del segno identificato dal numero
*/

char int_to_op(int c){
  switch(c){
    case 1:return '+';
    case 2:return '-';
    case 3:return '*';
    case 4:return '/';
  }
}

/** calcola_riga: esegue il calcolo su una riga e lo salva in memoria condivisa
    @param k numero del processo che svolgerà l'operazione
    @param numero_righe numero totale delle righe di operazioni
    @param num_proc numero totale dei processi
    @param memoria puntatore a float della porzione di memoria che contiene i dati da elaborare
*/
void calcola_riga(int k,int numero_righe, int num_proc, float *memoria){

  int numeroRiga;
  float risultato;
  int noperazione=memoria[4*numero_righe+k-1];

  switch((int)memoria[noperazione*4+1]){                       
    case 1: 
        risultato=(float)memoria[noperazione*4+2]+memoria[noperazione*4+3];
        break;
    case 2: 
        risultato=(float)memoria[noperazione*4+2]-memoria[noperazione*4+3];
        break;
    case 3: 
        risultato=(float)memoria[noperazione*4+2]*memoria[noperazione*4+3];
        break;
    case 4: 
        risultato=(float)memoria[noperazione*4+2]/memoria[noperazione*4+3];
        break;
  }
  numeroRiga=memoria[4*numero_righe+k-1];
  memoria[4*numero_righe+num_proc+numeroRiga]=risultato;
}

/** stampa_mem: stampa a video i valori all'interno della memoria condivisa
    @param numero_righe numero totale delle righe di operazioni
    @param memoria puntatore a float all'inizio della memoria condivisa
*/
void stampa_mem(int numero_righe, float *memoria){

  char *print_mem=malloc(sizeof(char)*50);
  write(1,"-----------------------",sizeof(char)*23);
  write(1,"\nMemoria Condivisa\n\n",sizeof(char)*20);
  int i;
  for(i=0;i<4*numero_righe;i++){                                    // Scorro la memoria           
    sprintf(print_mem,"%.0f ", *memoria);
    write(1,print_mem,sizeof(char)*strlen(print_mem));              // E la stampo a video
    memoria++;
    if((i+1)%4==0 && i!=0)                                          // Ogni 4 passi (ha finito di scorrere una riga), vai a capo
      write(1,"\n",1);
  }
  write(1,"-----------------------\n\n",sizeof(char)*25);
  free(print_mem);
}

/** main: legge da file le operazioni, le esegue e salva i risultati ottenuti in un file.
    In questo elaborato si utilizzano processi figli, memoria condivisa e semafori.
    @param argc numero di argomenti
    @param argv array di stringhe passate da linea di comando
*/

int main(int argc, char **argv){

  struct sembuf oper;                                               // Per eseguire operazioni sui semafori    
  int ret;	
  int file_desc;
  int k,i=0;
  char buf[SIZE]="";
  int nproc=0;
  char c;
  int ncifre=0;	
  long where;
  int nread; 
  int riga=0;
  int righeOper = 0;
  int m0=0; 
  int x=0;
  int flag=0; 
  char tmp0[SIZE]="";                                               // Array temporanei per inserimento dati in memoria                    
  char tmp1[SIZE]="";
  char tmp2[SIZE]="";
  int f=0,g=0;
  int temp;
  int file_stampa;
  int b=0;		
  char *buffer="";
  char *stampa="";
  char *tempo="";
  float *mem;
  float *start;
  float *media;
  pid_t pid_father;

  if (argc != 2) {
    write(2,"File di configurazione non specificato\n",sizeof(char)*38);
    exit(1);
  }

  if ((file_desc = open(argv[1],O_RDONLY)) == -1) {                 // Apro il file da leggere, se < 0 c'è stato un errore
    write(2,"OPEN: -1, apertura fallita",sizeof(char)*26);
    exit(1);
  }
	
  while ((nread = read(file_desc,&c,1))==1){                        // Conto il numero di righe 
    if(c=='\n')
      riga++;
  }

  float risultati[riga-1];                                          // Creo l'array che deve contenere i risultati 

  if ((where = lseek(file_desc,0,SEEK_SET))==-1){                   // Mi posiziono davanti al primo carattere
    write(2,"Posizionamento fallito",sizeof(char)*23);
    exit(1);
  }

  while ((nread = read(file_desc,&c,1))==1){                        // Conto numero di cifre da leggere (numero dei processi)
	ncifre++;                                                       // Mi fermo al primo \n per capire la dimensione 
  	if(c=='\n')
		break;
  }
  
  char array_proc[ncifre];                                          // Creo array per conoscere numero di cifre del n° di proc da creare
                                                
	
	if ((where = lseek(file_desc,0,SEEK_SET))==-1){                 // Si riposiziona davanti al primo carattere
		write(2,"Posizionamento fallito",sizeof(char)*23);             
  	 	exit(1); 
	}
	if ((nread = read(file_desc,&array_proc,ncifre-1)) != -1){      // Leggo dal file il numero di cifre necessario
		nproc = atoi (array_proc);                                  // Trasformo e metto in nproc il numero di processi da creare
	} else {
 	    write(2,"READ: -1",sizeof(char)*11);
  	    exit(1);
 	}

  pid_t statusarr[nproc+1];                                         // Creo array per contenere Process ID (+1 per il padre)

  /*************************************************** MEMORIA CONDIVISA *********************************************/
  
  int size_mem=sizeof(float)*(riga-1)*4+nproc+(riga-1)+1;           // Spazio per allocare la memoria:
                                                                    // Uno slot per float
                                                                    // (riga-1)*4 -> Servono 4 slot per ogni operazione (che sono riga-1):
                                                                    // 1 -> Numero del processo
                                                                    // 2 -> Operazione (+,-...) 
                                                                    // 3 & 4 -> Operatore 1 e operatore 2   
                                                                    // + nproc -> Spazio per allocare num. proc. che ha svolto l'operazione
                                                                    // + righeOper -> Spazio per allocare risultati
                                                                    // + 1 -> Per inserire 'K' 
  
  key_t key_mem=ftok("/conf.txt", nproc);                           // Creo chiave per la shmget
	
  int shmid = shmget(key_mem,size_mem,IPC_CREAT | 0664);            // Crea la memoria condivisa, con permessi 0664
  if(shmid==-1){
    write(2,"SHMGET: -1",sizeof(char)*10);
    exit(1);
  }
  mem = (float *)(shmat(shmid,0,0));                                // Primo zero: il sistema operativo gestisce gli indirizzi
                                                                    // Secondo zero: la memoria non è solo Read only 
  if(mem==(void*)-1){
    write(2,"SHMAT: -1",sizeof(char)*9);
    exit(1);
  }
  
  start=mem;                                                        // Segna il punto di inizio per la memoria
  media=mem;

  /********************************************* INIZIO PROCEDURA ******************************************************/

  // PASSO NUMERO 1: lettura del file di configurazione 

 if ((where = lseek(file_desc,ncifre,SEEK_SET))==-1){               // Mi posiziono davanti alla seconda riga
    write(2,"LSEEK: -1",sizeof(char)*9);
    exit(1);
  }

  while ((nread = read(file_desc,&c,1))==1){                        // Leggi un carattere alla volta
    buf[i++] = c;                                                   // e inseriscilo in un array che uso come buffer
    if(c=='\n'){                                                    // Quando arriva alla fine della riga
      while(x<SIZE){
	    if(x==0){                                                   // Prima volta che entro nel buffer
		    while(buf[x]!=' '){                                     // Scorro fino al primo spazio e inserisco valori che trovo in array
		        tmp0[m0++]=buf[x];                                  // Ottengo qual è il processo che deve eseguire l'operazione
			    x++;
		    }
	    *mem=atoi(tmp0);                                            // Trasformo in un intero e lo inserisco in memoria
	    mem++;                                                      
	    } else {  	                                                // ------ Dopo il primo spazio -------
	        if(buf[x]=='+' || buf[x]=='-' || buf[x]=='*' || buf[x]=='/'){
	            *mem=op_to_int(buf[x]);                             // Se trovi un segno chiama la funzione che lo trasforma in numero
		        mem++;                                              // poi mettilo in memoria                
	            flag=1;                                             // E alza la flag che indica che ora leggeremo il secondo operatore   
	        } else {   
	            if( buf[x]>='0' && buf[x]<='9' ){                   
	                if(flag==0){                                    // Primo operatore
		                tmp1[f++]=buf[x];	
	                } else {             
		                tmp2[g++]=buf[x];                           // Secondo operatore
	                } 
	            }
	        }
	    }
	   x++;                                                         // Passa al carattere dopo
      }

      *mem=atoi(tmp1);                                              // Inserisci in memoria il primo operatore
      mem++;
      *mem=atoi(tmp2);                                              // Inserisci in memoria il secondo operatore            
      mem++;
      
      x=0;                                                          // Azzera variabili e flag
      flag=0; 
	  m0=0;
      f=0;
      g=0; 
      i = 0;	
      righeOper++;
      
      for(k=0;k<SIZE;k++){                                          // Azzera array e buffer                          
	    buf[k]=' '; 
	    tmp0[k]=' ';
	    tmp1[k]=' '; 
	    tmp2[k]=' ';
      }
    }
  }
  
  /******************************************* SEMAFORI ****************************************************************/

  key_t key_sem =ftok("/conf.txt", nproc+1);                        // Genero chiave per i semafori
  
  int idsem = semget(key_sem, nproc+1, IPC_CREAT|IPC_EXCL|0666);	// Creo spazio per i semafori usando la chiave generata
  if(idsem == -1) {                                                 // Grazie a IPC_CREAT e IPC_EXCL ritorna errore se zona già occupata
    write(2,"SEMGET: -1",sizeof(char)*10);
    exit(1);
  } 

  for(k=0;k<=nproc;k++){                                            
    if(k==0)
      ret = semctl(idsem, 0, SETVAL, righeOper);	                // Assegno al semaforo padre il valore n° di operazioni da svolgere
    else
      ret = semctl(idsem, k, SETVAL, 1);		                    // Assegno ai semafori figli il valore 1 (partono bloccati)

    if(ret == -1) {
      write(2,"SEMCTL: -1",sizeof(char)*10);
      exit(1);
    }
  }
  
  /*****************************************************************************************************************/
 
  stampa=malloc(sizeof(char)*50);

  write(1,"\n//// ELABORATO2 ////\n",sizeof(char)*22);                  
  sprintf(stampa,"\nNumero Processi:%d\nNumero Operazioni:%d\n\n", nproc, righeOper);
  write(1,stampa,sizeof(char)*strlen(stampa));                       

  stampa_mem(righeOper,start);                                      // Stampa a video il contenuto della memoria condivisa
  pid_father=getpid();                                              // Prende il pid del processo padre prima di creare i figli

  sprintf(stampa,"Processo Padre, pid=%d\n\n", getpid());           // Stampa pid processo padre
  write(1,stampa,sizeof(char)*strlen(stampa));

  for(k=1;k<=nproc;k++){    
    
    statusarr[k]=fork();                                            // Creo copia del processo padre
    if(statusarr[k]==0){                                            // Se vero, sono un processo figlio
      statusarr[k]=getpid();                                        // Prendo dunque il mio PID 
	
      sprintf(stampa,"%d° Figlio, pid=%d, pidd=%d\n",k,getpid(),getppid());
      write(1,stampa,sizeof(char)*strlen(stampa));                      

      while(1){
        mem=start;		                                            // Punta all'inizio della memoria condivisa 
	    oper.sem_num = k;                                           // Semaforo su cui sem_op viene applicata
	    oper.sem_op = 0;	                                        // Attende che il valore del semaforo diventi 0 per proseguire
	    oper.sem_flg = 0; 	                                        
	    ret = semop(idsem, &oper, 1);                               // Applica l’insieme (array) di operazioni (in numero pari a nsops) 
                                                                    // all’insieme di semafori idsem
	
	    // ---------- Si ferma qui ---------- //
	
	    if(*(mem+4*righeOper+nproc+righeOper)=='K') {               // Il padre mette a K quella zona quando tutti i figli terminano
	        exit(0);                                                // quindi se l'if ritorna true il figlio termina              
        }
        
	    sprintf(stampa,"Sbloccato %d° figlio, pid=%d, calcolo risultato della %.0f° riga\n",k,getpid(),mem[4*righeOper+k-1]+1);
	    write(1,stampa,sizeof(char)*strlen(stampa));

	    calcola_riga(k,righeOper,nproc,start);                      // Calcola il risultato dell'operazione sulla riga 
	
        oper.sem_num = k;
	    oper.sem_op = +1; 
	    oper.sem_flg = 0;
	    semop(idsem, &oper, 1);		                                // Aumenta di uno il suo valore per bloccarsi

	    oper.sem_num = 0;
	    oper.sem_op = -1;
	    oper.sem_flg = 0;
	    semop(idsem, &oper, 1);		                                // Decrementa il semaforo del padre
      }
    }
  }

  if(getpid()==pid_father){                                         // Se invece sono il padre

    // PASSO NUMERO 2: ciclo che esegue le operazioni

    mem=start;                                                      
    for(i=0;i<righeOper;i++){
      if(mem[i*4]==0){   					                        // Se il processo che deve eseguire è 0 (primo processo libero)
	    do{
	        b++;						                            
	        if(b>nproc){                                            // Se arriva in fondo e non trova proc liberi ricomincia a cercare
	            b=1;
	        }
	        media=start+b-1+4*righeOper;                            // In memoria condivisa, salta le operazioni e scorri i numeri dei
	                                                                // processi per cercarne uno libero
	    } while((ret = semctl(idsem, b, GETVAL))==0);               // Se il suo semaforo è 0 continua a cercare
	    *media=i;	                                                // Quando trova un processo libero (sem=1) lo inserisce in memoria   
	    oper.sem_num = b;                                           // Si segna a quale semaforo mandare il segnale     
      } else {							                            // Se processo che deve eseguire è già specificato
  	    temp=mem[i*4]-1;                                            
	    media=start+4*righeOper+temp;                                    
	    while((ret = semctl(idsem, mem[i*4], GETVAL))==0); 	        // Aspetta che quel processo si liberi

	    *media=i;                                                       
	    oper.sem_num = mem[i*4];                
      }
      oper.sem_op = -1;                                             // Libera il processo che deve eseguire l'operazione              
      oper.sem_flg = 0;
      ret = semop( idsem, &oper, 1);
      
      if (ret == -1) {
        write(2,"SEMCTL: -1",sizeof(char)*10);
        exit(1);
      }
    } 		

    // PASSO NUMERO 3: il padre attende che i figli abbiano eseguito tutti i calcoli

    oper.sem_num = 0;                                               
    oper.sem_op = 0;
    oper.sem_flg = 0;
    ret = semop( idsem, &oper, 1);                                  // Il padre si blocca da solo ed attende di essere sbloccato dai figli
    
    if (ret == -1) {
      write(2,"SEMCTL: -1",sizeof(char)*10);
      exit(1);
    }
    // PASSO NUMERO 4: il padre uccide i figli e salva i risultati nell'array

    *(mem+4*righeOper+nproc+righeOper)='K';                         // Metti quella zona a K; quando i figli lo trovano terminano

    for(i=1;i<=nproc;i++){                                          // Sveglio tutti i figli 
      oper.sem_num = i ;
      oper.sem_op = -1;
      oper.sem_flg = 0;
      ret = semop( idsem, &oper, 1);
    }	

    start=start+4*righeOper+nproc;                                          

    for(i=0;i<righeOper;i++){                                       // Inserisco i risultati in memoria
      risultati[i]=*start;	
      start++;
    }

    // PASSO NUMERO 5: il padre attende che tutti i figli abbiano terminato

    for(i=1;i<=nproc;i++){
        wait(&statusarr[i]);                                        // Aspetta che tutti i figli terminino            
    }
    
    // PASSO NUMERO 6: il padre stampa su di un file di output i risultati ottenuti

    if ((file_stampa = open("Stampa.txt",O_CREAT | O_TRUNC | O_WRONLY, 0700)) == -1) {
      write(2,"OPEN: -1",sizeof(char)*8);
      unlink("Stampa.txt");
      exit(1);
    }

    buffer=malloc(sizeof(float)+(sizeof(int)*2+sizeof(char)*7)*righeOper);
    tempo=buffer;
    for(i=0;i<righeOper;i++){
      buffer+=sprintf(buffer,"%d %c %d = %.2f\n",(int)mem[i*4+2],int_to_op(mem[i*4+1]),(int)mem[i*4+3],mem[4*righeOper+nproc+i]);   
    }

    write(file_stampa,tempo,sizeof(char)*strlen(tempo));
    close(file_stampa);
    write(1,"\nFile Stampa.txt creato\n",sizeof(char)*24);  
    
    // PASSO NUMERO 7: il padre libera le risorse utilizzate

    ret = semctl(idsem, 0, IPC_RMID);                               // Rimuove il set di semafori   
    if (ret == -1) {
      write(2,"SEMCTL: -1",sizeof(char)*10);
      exit(1);
    }
    
    ret = shmctl(shmid, IPC_RMID, NULL);                            // Cancella il segmento di memoria condivisa     
    if (ret == -1) {
      write(2,"SEMCTL: -1",sizeof(char)*10);
      exit(1);
    }
  }
  free(stampa);                                                     // Libera la risorsa stampa   

  // PASSO NUMERO 8: terminazione processo padre

  exit(0);
}
