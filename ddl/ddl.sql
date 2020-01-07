DROP DATABASE IF EXISTS uniship;
CREATE DATABASE uniship;
USE uniship;

CREATE TABLE utente(
    email varchar(50) primary key,
    nome varchar(30) NOT NULL,
    password varchar(60) NOT NULL,
    tipo enum(
        'admin',
        'ufficio_carriere',
        'azienda',
        'studente') NOT NULL
);

CREATE TABLE studente(
    email varchar(50) primary key,
    cognome varchar(30) NOT NULL,
    codice_fiscale char(16) NOT NULL,
    matricola varchar(30) NOT NULL,
    data_di_nascita date NOT NULL,
    cittadinanza varchar(30) NOT NULL,
    residenza varchar(30) NOT NULL,
    numero varchar(30) NOT NULL,
    foreign key (email) references utente(email)
        ON DELETE cascade
        ON UPDATE cascade
);

CREATE TABLE azienda(
    email varchar(50) primary key,
    partita_iva char(11) NOT NULL,
    indirizzo varchar(50) NOT NULL,
    rappresentante varchar(50) NOT NULL,
    codice_ateco varchar(10) NOT NULL,
    numero_dipendenti int NOT NULL,
    foreign key (email) references utente(email)
        ON DELETE cascade
        ON UPDATE cascade
);

CREATE TABLE tirocinio(
    id int primary key AUTO_INCREMENT,
    ore_totali double(8, 2) NOT NULL, # modificato da date a time in quanto tiene traccia solo delle ore
    tutor_esterno varchar(50) NOT NULL,
    ore_svolte double(8, 2), # modificato da date a time in quanto tiene traccia solo delle ore
    path varchar(100),
    motivazioni varchar(100),
    stato enum(
        'Non completo',
        'Da Valutare',
        'Da Convalidare',
        'Rifiutata',
        'Accettata') NOT NULL,
	studente varchar(50) NOT NULL,
    azienda varchar(50) NOT NULL,
	foreign key (studente) references studente(email)
        ON DELETE cascade
        ON UPDATE cascade,
	foreign key (azienda) references azienda(email)
        ON DELETE cascade
        ON UPDATE cascade
    
);

create table richiestadisponibilita(
    studente varchar(50),
    azienda varchar(50),
    stato enum(
        'Valutazione',
        'Accettata',
        'Rifiutata') NOT NULL,
    motivazioni varchar(100) NOT NULL,
    primary key(studente,azienda),
    foreign key (studente) references studente(email)
        ON DELETE cascade
        ON UPDATE cascade,
    foreign key (azienda) references azienda(email)
        ON DELETE cascade
        ON UPDATE cascade
);

create table attivitaregistro(
    id int auto_increment,
    tirocinio int,
    data date NOT NULL,
    attivita varchar(50) NOT NULL,
    ore_svolte long NOT NULL,
    primary key(id,tirocinio),
    foreign key (tirocinio) references tirocinio(id)
        ON DELETE cascade
        ON UPDATE cascade
);

INSERT INTO `utente` VALUES ('f.ferrucci@unisa.it','Filomena','password','admin'),('g.gullo@studenti.unisa.it','Gerardo','password','studente'),('info@clarotech.it','ClaroTech','password','azienda'),('info@hotelMisericodia.it','Hotel Misericordia','password','azienda'),('ufficiocarriere@unisa.it','Ufficio Carriere','password','ufficio_carriere');
INSERT INTO `studente` VALUES ('g.gullo@studenti.unisa.it','Gullo','BNGRLL91P51H987N','0512105269','1994-05-16','Italia','Napoli','3354961184');
INSERT INTO `azienda` VALUES ('info@clarotech.it','02188520544','via cardinale,16','Paquale Di Franco','46692',20),('info@hotelMisericodia.it','02188520547','via caracciolo,69','Biagio Boi','66669',100);