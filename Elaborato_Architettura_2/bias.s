.section .data
Px:
	.long 0
Py:
	.long 0
Pz:
	.long 0
bias1:
	.long 0
bias2:
	.long 0
bias3:
	.long 0
bias4:
	.long 0
temp:
	.long 0
bias1print:
	.ascii "Bias 1:\n"
bias1print_len:
	.long . - bias1print

bias2print:
	.ascii "Bias 2:\n"
bias2print_len:
	.long . - bias2print

bias3print:
	.ascii "Bias 3:\n"
bias3print_len:
	.long . - bias3print

bias4print:
	.ascii "Bias 4:\n"
bias4print_len:
	.long . - bias4print
.section .text
.global bias
bias:
	xorl %eax,%eax #calcolo la differenza di peso nA-nF e salvo il contenuto in Px
	addl nA,%eax
	subl nF,%eax
	movl %eax,Px

	xorl %eax,%eax #calcolo la differenza di peso nB-nE e salvo il contenuto in Py
	addl nB,%eax
	subl nE,%eax
	movl %eax,Py

	xorl %eax,%eax #calcolo la differenza di peso nC-nD e salvo il contenuto in PZ
	addl nC,%eax
	subl nD,%eax
	movl %eax,Pz

BIAS1:
	movl Px,%eax #divisione x/2 (eax/ebx) e salvo in bias1
	sarl $1,%eax
	movl %eax,bias1 

	movl $3,%eax #moltiplicazione x/2*k1 (k1=3)
	mull bias1

	movl %eax,bias1 #mezza equazione salvata in bias1

	movl Py,%eax #divisione y/2 salvando in temp il risultato
	sarl $1,%eax
	movl %eax,temp

	movl $6,%eax #moltiplicazione y/2*k2 (k2=6)
	mull temp
	
	addl %eax,bias1 #somma tra le 2 parti

	movl $4,%eax #stampa stringa per il bias
	movl $1,%ebx
	leal bias1print,%ecx
	movl bias1print_len,%edx
	int $0x80
		
	movl bias1,%eax #inizializzo eax per itoa e nega
	
	call nega #nego il valore in caso sia negativo per poterlo stampare
	call itoa #stampo il valore


BIAS2:
	movl Py,%eax #divisione y/2 (eax/ebx) e salvo in bias2
	sarl $1,%eax
	movl %eax,bias2

	movl $6,%eax #moltiplicazione y/2*k2 (k2=6)
	mull bias2
	movl %eax,bias2 #mezza equazione salvata in bias2

	movl Pz,%eax #divisione z/2 salvando in temp il risultato
	sarl $1,%eax
	movl %eax,temp

	movl $12,%eax #moltiplicazione z/2*k3(k3=12)
	mull temp
	
	addl %eax,bias2 #somma tra le 2 parti

	movl $4,%eax #stampa stringa per il bias	
	movl $1,%ebx
	leal bias2print,%ecx
	movl bias2print_len,%edx
	int $0x80
		
	movl bias2,%eax #inizializzo eax per itoa e nega
	
	call nega #nego il valore in caso sia negativo per poterlo stampare
	call itoa #stampo il valore

	

BIAS3:
	movl Py,%eax #divisione y/2 (eax/ebx) e salvo in bias3
	sarl $1,%eax
	movl %eax,bias3

	movl $6,%eax #moltiplicazione y/2*k2 (k2=3)
	mull bias3

	movl %eax,bias3 #mezza equazione salvata in bias3
	negl bias3 #nego il suo valore

	movl Pz,%eax #divisione z/2 salvando in temp il risultato
	sarl $1,%eax
	movl %eax,temp

	movl $12,%eax #moltiplicazione z/2*k3 (k3=12)
	mull temp
	
	subl %eax,bias3 #sottrazione tra le 2 parti

	movl $4,%eax #stampa stringa per il bias
	movl $1,%ebx
	leal bias3print,%ecx
	movl bias3print_len,%edx
	int $0x80
		
	movl bias3,%eax #inizializzo eax per itoa e nega
	
	call nega #nego il valore in caso sia negativo per poterlo stampare
	call itoa #stampo il valore

BIAS4:
	movl Px,%eax #divisione x/2 (eax/ebx) e salvo in bias4
	sarl $1,%eax
	movl %eax,bias4

	movl $3,%eax #moltiplicazione x/2*k1 (k1=3)
	mull bias4

	movl %eax,bias4 #mezza equazione salvata in bias4
	negl bias4 #nego il valore

	movl Py,%eax #divisione y/2 salvando in temp il risultato
	sarl $1,%eax
	movl %eax,temp

	movl $6,%eax #moltiplicazione y/2*k2 (k2=6)
	mull temp
	
	subl %eax,bias4 #sottrazione tra le 2 parti

	movl $4,%eax #stampa stringa per il bias
	movl $1,%ebx
	leal bias4print,%ecx
	movl bias4print_len,%edx
	int $0x80
		
	movl bias4,%eax #inizializzo eax per itoa e nega
	
	call nega #nego il valore in caso sia negativo per poterlo stampare
	call itoa #stampo il valore


	movl $1,%eax #exit
	movl $0,%ebx
	int $0x80

	
	
	

	
	
	
	

