.section .data
.global nA
.global nB
.global nC
.global nD
.global nE
.global nF
nA:
	.long 0
nB:
	.long 0
nC:
	.long 0
nD:
	.long 0
nE:
	.long 0
nF:
	.long 0
stABCpass:
	.ascii "Inserire il numero totale passeggeri per le file A, B, C: "
stABCpass_len:
	.long . - stABCpass

stDEFpass:
	.ascii "Inserire il numero totale passeggeri per le file D, E, F: "
stDEFpass_len:
	.long . - stDEFpass
stERRpass:
	.ascii "Somma totali file diverso da totale passeggeri\n"
stERRpass_len:
	.long . - stERRpass





.section .text
.global posti

posti:
	movl $4,%eax	#richiesta inserimeto dati per a,b,c
	movl $1,%ebx
	leal stABCpass,%ecx
	movl stABCpass_len,%edx
	int $0x80

	call atoi	#chiamo la funzione atoi per prendere il valore 
	movl %eax,nA 	#e lo salvo nella variabile specifica e ripeto per tutti i valori
	call atoi
	movl %eax,nB 
	call atoi
	movl %eax,nC
	
	
	cmpl $30,nA #confronto ognuna di queste variabili con il n. max di posti in una fila
	jg posti #se lo supera reinserisce i 3 valori. Controllo tutti e 3
	cmpl $30,nB
	jg posti
	cmpl $30,nC
	jg posti


	
postiDEF:

	movl $4,%eax	#stessa cosa delle file abc
	movl $1,%ebx
	leal stDEFpass,%ecx
	movl stDEFpass_len,%edx
	int $0x80

	call atoi
	movl %eax,nD
	call atoi
	movl %eax,nE
	call atoi
	movl %eax,nF
	
	cmpl $30,nD
	jg postiDEF
	cmpl $30,nE
	jg postiDEF
	cmpl $30,nF
	jg postiDEF

	xorl %eax,%eax #azzero eax e sommo ad esso tutti le variabili
	addl nA,%eax
	addl nB,%eax
	addl nC,%eax
	addl nD,%eax
	addl nE,%eax
	addl nF,%eax
	
	cmpl totP,%eax #confronto questo valore col totale inserito all'inizio
	
	je bias #se sono uguali procedo al calcolo dei bias

	movl $4,%eax	#altrimenti stampo errore
	movl $1,%ebx
	leal stERRpass,%ecx
	movl stERRpass_len,%edx
	int $0x80
	
	jmp inizioD #e torno ad inserire i 7 parametri


	

	

	
	
	




	




