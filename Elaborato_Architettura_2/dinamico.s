.section .data
.global totP


totP:
	.long



testo:
	.ascii "Modalita' controllo dinamico inserita\n"
testo_len:
	.long . - testo

sttotpass:
	.ascii "Inserire il numero totale dei passegeri a bordo(xxx): "

sttotpass_len:
	.long . - sttotpass




.section .text
.global dinamico
.global inizioD
dinamico:
	movl $4,%eax	#testo modalità
	movl $1,%ebx
	leal testo,%ecx
	movl testo_len,%edx
	int $0x80

inizioD:
	movl $4,%eax	#testo numero totale passeggeri
	movl $1,%ebx
	leal sttotpass,%ecx
	movl sttotpass_len,%edx
	int $0x80
	
	call atoi #chiamo atoi per prelevare il valore
	movl %eax,totP #e lo salvo in totP
	
	cmpl $180,%eax #confronto questo valore con il massimo n. di posti sull'aereo
	jg inizioD #se maggiori ricomincio
	jle posti #altrimenti vado alla funzione posti
	
	
	
