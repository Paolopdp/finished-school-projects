.section .data
meno:
	.ascii "-"
val:
	.long 0
.section .text
.global nega
nega:
	#la funzione prende il valore di eax salvandolo momentaneamente in val
	#se questo valore è negativo verrà fatta la stampa del segno "-" e il valore verrà negato
	#in modo da essere positivo per la funzione itoa. Nel caso di valore positivo esso torna alla chiamante
	movl %eax,val 
	cmpl $0,val
	jge fine

	negl val
	movl $4,%eax	
	movl $1,%ebx
	leal meno,%ecx
	movl $1,%edx
	int $0x80

	movl val,%eax
fine:
	ret
