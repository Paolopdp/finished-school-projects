#
# GNU as
#
EXE = run
AS = as 
LD = ld 
FLAGS = -gstabs

OBJ = main.o bias.o chk.o atoi.o dinamico.o emergenza.o posti.o itoa.o nega.o

all: $(EXE)

clean:
	rm -f *.o *% core $(EXE)

install: $(EXE)
	cp $(EXE) /usr/bin/

$(EXE): $(OBJ)
	$(LD) -o $(EXE) $(OBJ)

main.o : main.s
	$(AS) $(FLAGS) -o main.o main.s

bias.o : bias.s
	$(AS) $(FLAGS) -o bias.o bias.s

chk.o : chk.s
	$(AS) $(FLAGS) -o chk.o chk.s

atoi.o : atoi.s
	$(AS) $(FLAGS) -o atoi.o atoi.s

dinamico.o : dinamico.s
	$(AS) $(FLAGS) -o dinamico.o dinamico.s

emergenza.o : emergenza.s
	$(AS) $(FLAGS) -o emergenza.o emergenza.s

posti.o : posti.s
	$(AS) $(FLAGS) -o posti.o posti.s

itoa.o : itoa.s
	$(AS) $(FLAGS) -o itoa.o itoa.s
nega.o : nega.s
	$(AS) $(FLAGS) -o nega.o nega.s


