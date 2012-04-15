create table czujniki{--temperatura, okna, oswietlenie
	id_cz serial primary key,
	nazwa_cz varchar[30]
};
create table funkcje_p{
	id_f serial primary key,
	nazwa_f varchar[30]
};
create table pomiary{
	id_p serial,
	id_pom integer references czujniki,
	czas timestamp,
	wartosc real,
	primary key(id_czuj, czas)
};
create table reguly{
	id_r serial primary key,
	id_czuj integer references czujniki,
	param1 real not null, 
	param2 real not null,
	param3 real,
	typ integer references funkcje_p --rodzaj funkcji przynaleznosci gauss, dzwonowa, trapez, z, s
};

/* 
insert into czujniki(nazwa_cz) values 	('temperatura1'),
										('temperatura2'),
										('temperatura3'),
										('okno1'),
										('okno2'),
										('oswietlenie1'),
										('oswietlenie2'),
										('zmierzchowy');
insert into funkcje_p(nazwa_f) values 	('gauss'),
										('trapezowa'),
										('dzwonowa'),
										('z'),
										('s');
insert into pomiary(id_pom,czas,wartosc) values (' ',now(),'  ');
insert into reguly(id_czuj,param1,param2,param3,typ) values ('1','2','4','6','3');--funkcja dzwonowa dla temp  */