#!/usr/bin/python

"""
Codice per l'esame
"""

#P1
"""
dato un dizionario che associa gli id del FASTA file alla stringa di DNA, ritorna il numero di record nel dizionario
"""
def get_num_records(records):
	return len(records.keys())

#P2
"""
dato un dizionario che associa gli id del FASTA file alla stringa di DNA, ritorna un dizionario che associa gli id del dizionario alla lunghezza della stringa.
"""
def get_length_map(records):
	new_records = {}
	for k in records.keys():
		new_records[k]=len(records[k])
	return new_records

	
"""
dato un dizionario che associa gli id alla lunghezza della stringa, ritorna la lunghezza della stringa minima e la lunghezza della stringa massima
"""
def get_min_max_length(len_map):
	ret=sorted(len_map.values())
	return(ret[0],ret[-1])

"""
dato un dizionario che associa gli id alla lunghezza della stringa, ritorna il numero delle sequenze che hanno lunghezza minima e massima
"""
def get_min_max_num(len_map):
	maxc,minc=0,0
	ret=sorted(len_map.values())
	for k in len_map.keys():
		if len_map[k]==ret[-1]:
			maxc=maxc+1
		elif len_map[k]==ret[0]:
			minc=minc+1
	return (minc,maxc)

"""
dato un dizionario che associa gli id alla lunghezza della stringa, ritorna la lista di id associati alle sequenze che hanno lunghezza minima e massima
"""
def get_min_max_length_id(len_map):
	minlist=[]
	maxlist=[]
	minn,maxx=get_min_max_length(len_map)
	for k in len_map.keys():
		if len_map[k]==maxx:
			maxlist.append(k)
		elif len_map[k]==minn:
			minlist.append(k)
	return (minlist,maxlist)


#P3
"""
data una sequenza di dna ed un frame di lettura, restituisce la lista di tutti gli ORF contenuti nella stringa di DNA.
"""
def get_ORF_list(dna,frame=0) :
	orf_list=[]
	orf=["atg"]
	stop_codons=["tga","tag","taa"]
	for i in range(frame,len(dna),3):
		codon=dna[i:i+3].lower()
		if codon in orf:
			for k in range(i+3,len(dna),3):
				codon=dna[k:k+3].lower()
				if codon in stop_codons:
					orf_list.append(dna[i:k+3])		
					break	
	return orf_list

"""
dato un dizionario che associa gli id del FASTA file alla stringa di DNA, restituisce un dizionario che associa gli id alla lista di ORF contenuti nella sequenza corrispondente.
"""
def get_ORF_map(records,frame=0) :
	orf_map={}	
	for k in records.keys():
		orf_map[k]=get_ORF_list(records[k])
	return orf_map
	
"""
un dizionario che associa gli id alla lista degli ORF per quella sequenza, restituisce l'id associato all'ORF piu' lungo, l'ORF piu' lungo e la lunghezza di tale ORF (in caso ci fossero piu' ORF massimali restituire uno qualsiasi di questi ORF).
"""	
def longest_ORF(orf_map):
	maxn=0
	for k in orf_map.keys():
		for i in range (0,len(orf_map[k]),1):
			if len(orf_map[k][i])>maxn:
				maxn=len(orf_map[k][i])
				maxid=k
				maxorf=orf_map[k][i]
	return (maxid,maxorf,maxn)


#P4	
"""
data una lunghezza n ed una lista di sequenze di nucleotidi, restituisce un dizionario che associa tutte le sottostringhe ripetute di lunghezza n (i.e., sottostringhe che compaiono almeno una volta in almeno una sequenza) al loro numero di occorrenze in tutte le sequenze. 
"""
def get_all_repeats(n,seq_list):
	diz={}
	test=''.join(seq_list)
	for i in range(0,len(test),1):
		trip=test[i:i+3]
		if trip not in diz.keys():
			diz[trip]=1
		else:
			diz[trip]=diz[trip]+1
	return diz

"""
dato un dizionario che associa sottostringhe al numero di occorrenze, ritorna la sottostringa con il numero massimo di occorrenze (ed il numero di occorrenze)
"""
def most_frequent_repeat(rep_map):	
	max=0
	for k in rep_map.keys():
		if rep_map[k]>max:
			max=rep_map[k]
			substrmax=k
	return substrmax,max

"""
dato un dizionario che associa sottostringhe al numero di occorrenze ed un numero di occorrenze occ, ritorna l'insieme delle stringhe che ha un numero di occorrenze associato pari almeno a occ.
"""
def filter_repeats(rep_map,occ):
	diz2={}

	for k in rep_map.keys():
		if rep_map[k]>occ:
			diz2[k]=rep_map[k]
	stringhe=diz2.keys()
	return stringhe


if __name__ == "__main__" :
	filename = input("Scegliere file fasta:\n\ta) dna.simple.fasta\n\tb) dna.long.fasta\n")
	if (filename == 'a'):		
		filename = 'dna.simple.fasta'
	else: 
		filename = 'dna.long.fasta'

	from fastautil import read_fasta


	#test P1
	records = read_fasta(filename)	
	print("Test numero record, a) 5 b) 28 ")
	print("numero record = %s " % get_num_records(records))


	#test P2
	len_map = get_length_map(records)
	#print("dizionario lunghezze = %s " % len_map)	
	min_seq,max_seq = get_min_max_length(len_map)
	print("test lunghezza a) min: 29 max: 112 b) min: 186 max: 1686")	
	print("lunghezza seq minima %s e massima %s " % (min_seq,max_seq))	
	n_min,n_max = get_min_max_num(len_map)	
	print("numero lunghezza seq a) min: 2 max: 1 b) min: 1 max: 1")	
	print("numero seq con lunghezza minima %s e massima %s " % (n_min,n_max))	
	id_min,id_max =	get_min_max_length_id(len_map)
	print("id seq a) min: ['id2','id3'] max: ['id4'] b) min: ['lcl|EU819142.1_cds_ACF06958.1_18'] max: ['lcl|EU819142.1_cds_ACF06952.1_12']")	
	print("id seq con lunghezza minima %s e massima %s " % (id_min,id_max))	

	#test P3
	test_string = 'CATGCTATGGTGCCCTAAAAGATGACGCCCTACCCCCCCCCTAGTGATTAGTTTCGAGACATACTGTGTTT'
	print("test_string contiene 2 ORF in frame 0 ed 1 in frame 1")	
	print("lista ORF associata alla stringa %s : %s" % (test_string,get_ORF_list(test_string)))	
	orf_map	= get_ORF_map(records)
	#print("dizionario ORF = %s " % orf_map)	
	print("numero ORF totali: a) 2 b) 253 ")	
	#print("numero ORF totali: %s " % orf_map.values())	
	print("numero ORF totali: %d " % sum([len(k) for k in orf_map.values()]))	
	id_lorf,lorf,length_lorf = longest_ORF(orf_map)
	#print("id sequenza ORF piu' lungo = %s, ORF piu' lungo = %s, lunghezza ORF = %d" % (id_lorf,lorf,length_lorf))	
	print("id ORF piu' lungo  e lunghezza orf a) id4, 54 b) lcl|EU819142.1_cds_ACF06952.1_12, 1686")	
	print("id sequenza ORF piu' lungo = %s, lunghezza ORF = %d" % (id_lorf,length_lorf))	

	#Test P4
	n = 3
	rep_map = get_all_repeats(n,records.values())	
	#print('dizionario delle sottostringhe ripetute di lunghezza %d : %s' % (n,rep_map))	
		
	m_f_rep,rep_n = most_frequent_repeat(rep_map)
	print("sottostringa ripetuta piu' di frequente e numero di occorrenze a) CCC, 70 b) GCC, 954")
	print("sottostringa ripetuta piu' di frequente nel dizionario = %s , numero di occorrenze = %d" % (m_f_rep,rep_n))

	occ = 30
	rep_set = filter_repeats(rep_map,occ)
	print("dimensione insieme a) 1 b) 64 ")
	print("dimensione insieme di tutte le sottostringhe ripetute che occorrono almeno %d volte = %s " % (occ,len(rep_set)))
	
	
		



