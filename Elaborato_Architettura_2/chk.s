
.section .data

errore:
	.ascii "Codice errato, inserire nuovamente il codice\n"
errore_len:
	.long . - errore

.section .text

.global chk

chk:
	subb $48,x #sottraggo 48 ai 3 parametri presi nel main
	subb $48,y #questo serve a trasformarli nel corretto valore(dovuto alla conversione nella tabella ascii)
	subb $48,z
	
	cmpb $3,x #se il primo valore è 3 salto a confrontare il secondo
	je cmp3y
	
	
	cmpb $9,x #se il primo valore è 9 salto a confrontare il secondo
	je cmp9y
	movl $4,%eax #altrimenti stampo la stringa di errore e torno all'inzio del programma
	movl $1,%ebx
	leal errore,%ecx
	movl errore_len,%edx
	int $0x80
	call _start
	
cmp3y:
	cmpb $3,y #se il secondo valore è 3(e il primo è 3) controllo il terzo
	je cmp2z
	movl $4,%eax #altrimenti stampo la stringa di errore e torno all'inzio del programma
	movl $1,%ebx
	leal errore,%ecx
	movl errore_len,%edx
	int $0x80
	call _start
cmp9y:
	cmpb $9,y #se il secondo valore è 9(e il primo è 9) controllo il terzo
	je cmp2z
	movl $4,%eax #altrimenti stampo la stringa di errore e torno all'inzio del programma
	movl $1,%ebx
	leal errore,%ecx
	movl errore_len,%edx
	int $0x80
	call _start
cmp2z:
	cmpb $2,z #se il terzo valore è 2 salto a goto
	je goto
	movl $4,%eax #altrimenti stampo la stringa di errore e torno all'inzio del programma
	movl $1,%ebx
	leal errore,%ecx
	movl errore_len,%edx
	int $0x80
	call _start

goto:
	cmpb $3,x #verifico nuovamente il primo valore per sapere quale controllo devo effettuare
	je dinamico
	jne emergenza


	
	
