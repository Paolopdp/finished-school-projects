
.section .data																																														
.global x
.global y
.global z
.global spazio

count:
	.byte 0

spazio:
	.byte 0
x:
	.byte 0
y:
	.byte 0
z:
	.byte 0
testo:
	.ascii "Inserisci parametri x y z: "
testo_len:
	.long . - testo
fail_text:
	.ascii "Failure controllo codice. Modalita' safe inserita.\n"
fail_text_len:
	.long . - fail_text


.section .text

	.global _start

_start:

	movl $4,%eax	#stampo richiesta dati
	movl $1,%ebx
	leal testo,%ecx
	movl testo_len,%edx
	int $0x80

	movl $3,%eax #carico x
	movl $0,%ebx
	leal x, %ecx
	movl $1,%edx
	int $0x80
	
	movl $3,%eax #carico spazio
	movl $0,%ebx
	leal spazio, %ecx
	movl $1,%edx
	int $0x80
	
	
	movl $3,%eax #carico y
	movl $0,%ebx
	leal y, %ecx
	movl $1,%edx
	int $0x80
	
	movl $3,%eax #carico spazio
	movl $0,%ebx
	leal spazio, %ecx
	movl $1,%edx
	int $0x80
	
	movl $3,%eax #carico z
	movl $0,%ebx
	leal z, %ecx
	movl $1,%edx
	int $0x80

	movl $3,%eax #carico spazio
	movl $0,%ebx
	leal spazio, %ecx
	movl $1,%edx
	int $0x80
	
	incb count #ogni volta che viene chiamato il main count si incrementa
	cmpb $3,count #se diventa ==3
	je failure #va all'etichetta failure
	
	call chk #qui viene fatto il controllo di correttezza del codice

failure:

	#stampa la stringa di failure ed esce dal programma
	movl $4,%eax 
	movl $1,%ebx
	leal fail_text,%ecx
	movl fail_text_len,%edx
	int $0x80

	movl $1,%eax #exit
	movl $0,%ebx
	int $0x80

	
