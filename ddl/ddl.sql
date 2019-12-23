DROP DATABASE IF EXISTS uniship;
create database uniship;
use uniship;

create table utente(
email varchar(50) primary key,
nome varchar(30) NOT NULL,
password varchar(20) NOT NULL,
tipo enum("Admin","UfficioCarriere","azienda","studente")
);

create table studente(
email varchar(50) primary key,
cognome varchar(30) NOT NULL,
codiceFiscale char(11) NOT NULL,
matricola varchar(30) NOT NULL,
data_di_nascita date NOT NULL,
cittadinanza varchar(30) NOT NULL,
residenza varchar(30) NOT NULL,
numero varchar(30) NOT NULL,
foreign key (email) references utente(email)
ON DELETE cascade
ON UPDATE no action

);

create table azienda(
email varchar(50) primary key,
partita_iva char(11) NOT NULL,
indirizzo varchar(50) NOT NULL,
rappresentate varchar(50) NOT NULL,
codice_ateco varchar(10) NOT NULL,
numero_dipendenti int NOT NULL,
foreign key (email) references utente(email)
ON DELETE cascade
ON UPDATE no action
);

create table tirocinio(
id varchar(10) primary key,
ore_totali date NOT NULL,
tutor_esterno varchar(50) NOT NULL,
ore_svolte date NOT NULL,
path varchar(100),
stato enum("Non Completo","Da Valutare","Da Convalidare","Rifiutata","Accettata") NOT NULL
);

create table richiestadisponibilita(
studente varchar(50),
azienda varchar(50),
stato enum("Valutazione","Accettata","Rifiutata") NOT NULL,
motivazioni varchar(100),
primary key(studente,azienda),
foreign key (studente) references studente(email)
ON DELETE cascade
ON UPDATE no action,
foreign key (azienda) references azienda(email)
ON DELETE cascade
ON UPDATE no action 
);

create table attivitaregistro(
id int auto_increment,
tirocinio varchar(10),
data date NOT NULL,
attivita varchar(50) NOT NULL,
ore_svolte time NOT NULL,
primary key(id,tirocinio),
foreign key (tirocinio) references tirocinio(id)
ON DELETE cascade
ON UPDATE no action
);
