
.section .data
testo:
	.ascii "Modalita' controllo emergenza inserita\n"
testo_len:
	.long . - testo
.section .text
.global emergenza

emergenza:
	#la funzione si limita a stampare il testo di modalità e uscire dal programma
	movl $4,%eax 
	movl $1,%ebx
	leal testo,%ecx
	movl testo_len,%edx
	int $0x80

	movl $1,%eax #exit
	movl $0,%ebx
	int $0x80
