# Sources
SRCS= main.c
OBJS=$(SRCS:.c = .o)
EXECUTABLE= main.x

# Config
CC=gcc
CFLAGS= -c
LD=gcc

# Target

all: $(EXECUTABLE)  
	@echo Building $(EXECUTABLE)

main.o: main.c 
	$(CC) $(CFLAGS) -o $@ $< 

main.x: main.o
	$(CC) -o $@ $<

clean:
	rm -f $(EXECUTABLE)
	rm -f $(OBJS)

.PHONY: all clean
