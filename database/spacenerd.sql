DROP SCHEMA IF EXISTS spacenerd;
CREATE SCHEMA spacenerd;
USE spacenerd;

CREATE TABLE Utente(
    Email varchar(100) NOT NULL COLLATE utf8_bin,
    Pass varchar(50) NOT NULL COLLATE utf8_bin,
    Tipo bit NOT NULL, -- 0 utente registrato, 1 admin
    PRIMARY KEY(Email)
);

CREATE TABLE DatiSensibili(
    Email varchar(50) NOT NULL COLLATE utf8_bin,
    Nome varchar(16) NOT NULL,
    Cognome varchar(16) NOT NULL,
    DataNascita date NOT NULL,
    Via varchar(16) NOT NULL,
    Civico int NOT NULL,
    Provincia varchar(50) NOT NULL,
    Comune varchar(50) NOT NULL,
    PRIMARY KEY(Email),
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IndirizzoSpedizione(
    IdIndirizzo int NOT NULL auto_increment,
    Nome varchar(16) NOT NULL,
    Cognome varchar(16) NOT NULL,
    Via varchar(16) NOT NULL,
    Civico int NOT NULL,
    Provincia varchar(50) NOT NULL,
    Comune varchar(50) NOT NULL,
    Cap int NOT NULL,
    PRIMARY KEY(IdIndirizzo)
);

CREATE TABLE Utilizza(
    Email varchar(50) NOT NULL COLLATE utf8_bin,
    IdIndirizzo int NOT NULL,
    PRIMARY KEY(Email, IdIndirizzo),
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(IdIndirizzo) REFERENCES IndirizzoSpedizione(IdIndirizzo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE MetodoPagamento(
    IdMetodo int NOT NULL auto_increment,
    NumeroCarta varchar(50) NOT NULL,
    DataScadenza date NOT NULL,
    Ccv int NOT NULL,
    Titolare varchar(50) NOT NULL,
    PRIMARY KEY(IdMetodo)
);

CREATE TABLE Registra(
    Email varchar(50) NOT NULL COLLATE utf8_bin,
    IdMetodo int NOT NULL,
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(IdMetodo) REFERENCES MetodoPagamento(IdMetodo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Ordine(
    IdOrdine int NOT NULL auto_increment,
    PrezzoTotale float NOT NULL,
    DataOrdine date NOT NULL,
    Fattura varchar(16) NOT NULL UNIQUE,
    Email varchar(50) NOT NULL COLLATE utf8_bin,
    PRIMARY KEY(IdOrdine),
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Manga(
    IdManga int NOT NULL auto_increment,
    Prezzo float NOT NULL,
    Descrizione varchar(50) NOT NULL,
    NumeroArticoli int NOT NULL,
    CasaEditrice varchar(16) NOT NULL,
    Lingua varchar(16) NOT NULL,
    NumeroPagine int NOT NULL,
    Immagine varchar(50),
    FlagVisibilita bit NOT NULL, -- 0 non visibile, 1 si
    PRIMARY KEY(IdManga)
);

CREATE TABLE Pop(
    IdPop int NOT NULL auto_increment,
    Prezzo float NOT NULL,
    Descrizione varchar(50) NOT NULL,
    NumeroArticoli int NOT NULL,
    NumeroSerie int NOT NULL,
    Serie varchar(16) NOT NULL,
    FlagVisibilita bit NOT NULL, -- 0 non visibile, 1 si
    PRIMARY KEY(IdPop)
);

CREATE TABLE Figure(
    IdFigure int NOT NULL auto_increment,
    Prezzo float NOT NULL,
    Descrizione varchar(50) NOT NULL,
    NumeroArticoli int NOT NULL,
    Materiale varchar(16) NOT NULL,
    Altezza int NOT NULL,
    Personaggio varchar(50) NOT NULL,
    FlagVisibilita bit NOT NULL, -- 0 non visibile, 1 si
    PRIMARY KEY(IdFigure)
);

CREATE TABLE ComprendeManga(
    IdOrdine int NOT NULL,
    IdManga int NOT NULL,
    Quantita int NOT NULL,
    PrezzoUnitario float NOT NULL,
    FOREIGN KEY(IdOrdine) REFERENCES Ordine(IdOrdine) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(IdManga) REFERENCES Manga(IdManga) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ComprendePop(
    IdOrdine int NOT NULL,
    IdPop int NOT NULL,
    Quantita int NOT NULL,
    PrezzoUnitario float NOT NULL,
    FOREIGN KEY(IdOrdine) REFERENCES Ordine(IdOrdine) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(IdPop) REFERENCES Pop(IdPop) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ComprendeFigure(
    IdOrdine int NOT NULL,
    IdFigure int NOT NULL,
    Quantita int NOT NULL,
    PrezzoUnitario float NOT NULL,
    FOREIGN KEY(IdOrdine) REFERENCES Ordine(IdOrdine) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(IdFigure) REFERENCES Figure(IdFigure) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ImmaginePop(
    IdImmagine int NOT NULL auto_increment,
    Nome varchar(50) NOT NULL,
    IdPop int NOT NULL,
    PRIMARY KEY(IdImmagine),
    FOREIGN KEY(IdPop) REFERENCES Pop(IdPop) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ImmagineFigure(
    IdImmagine int NOT NULL auto_increment,
    Nome varchar(50) NOT NULL,
    IdFigure int NOT NULL,
    PRIMARY KEY(IdImmagine),
    FOREIGN KEY(IdFigure) REFERENCES Figure(IdFigure) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO Utente(Email, Pass, Tipo)
VALUES('d.folgieri@studenti.unisa.it', 'Donato2024!', 1);
INSERT INTO Utente(Email, Pass, Tipo)
VALUES('s.cirma@studenti.unisa.it', 'Simone2024!', 0);
INSERT INTO Utente(Email, Pass, Tipo)
VALUES('a.digiorgio@studenti.unisa.it', 'Antonio2024!', 0);

INSERT INTO DatiSensibili(Email, Nome, Cognome, DataNascita, Via, Civico, Provincia, Comune)
VALUES('d.folgieri@studenti.unisa.it', 'Donato', 'Folgieri', '2000-05-17', 'Napoli', '12', 'Caserta', 'San Felice');
INSERT INTO DatiSensibili(Email, Nome, Cognome, DataNascita, Via, Civico, Provincia, Comune)
VALUES('s.cirma@studenti.unisa.it', 'Simone', 'Cirma', '2001-04-24', 'Roma', '15', 'Caserta', 'San Marco');
INSERT INTO DatiSensibili(Email, Nome, Cognome, DataNascita, Via, Civico, Provincia, Comune)
VALUES('a.digiorgio@studenti.unisa.it', 'Antonio', 'di Giorgio', '2000-12-05', 'Torino', '23', 'Napoli', 'Frattaminore');

INSERT INTO IndirizzoSpedizione(Nome, Cognome, Via, Civico, Provincia, Comune, Cap)
VALUES('Simone', 'Cirma', 'Roma', '15', 'Caserta', 'San Marco', '81020');
INSERT INTO IndirizzoSpedizione(Nome, Cognome, Via, Civico, Provincia, Comune, Cap)
VALUES('Simone', 'Cirma', 'Geronimo', '18', 'Caserta', 'San Marco' , '81020');
INSERT INTO IndirizzoSpedizione(Nome, Cognome, Via, Civico, Provincia, Comune, Cap)
VALUES('Antonio', 'di Giorgio', 'Torino', '23', 'Napoli', 'Frattaminore', '80020');
INSERT INTO IndirizzoSpedizione(Nome, Cognome, Via, Civico, Provincia, Comune, Cap)
VALUES('Antonio', 'di Giorgio', 'Paolo Rossi', '22', 'Napoli', 'Frattaminore', '80020');

INSERT INTO Utilizza(Email, IdIndirizzo)
VALUES('s.cirma@studenti.unisa.it', '1');
INSERT INTO Utilizza(Email, IdIndirizzo)
VALUES('s.cirma@studenti.unisa.it', '2');
INSERT INTO Utilizza(Email, IdIndirizzo)
VALUES('a.digiorgio@studenti.unisa.it', '3');
INSERT INTO Utilizza(Email, IdIndirizzo)
VALUES('a.digiorgio@studenti.unisa.it', '4');

INSERT INTO MetodoPagamento(NumeroCarta, DataScadenza, Ccv, Titolare)
VALUES('5489125896547845', '2024-12-12', '854', 'Simone Cirma');
INSERT INTO MetodoPagamento(NumeroCarta, DataScadenza, Ccv, Titolare)
VALUES('5478963214956854', '2024-06-12', '874', 'Simone Cirma');
INSERT INTO MetodoPagamento(NumeroCarta, DataScadenza, Ccv, Titolare)
VALUES('5487695821453574', '2024-12-12', '779', 'Antonio di Giorgio');

INSERT INTO Registra(Email, IdMetodo)
VALUES('s.cirma@studenti.unisa.it', '1');
INSERT INTO Registra(Email, IdMetodo)
VALUES('a.digiorgio@studenti.unisa.it', '3');
INSERT INTO Registra(Email, IdMetodo)
VALUES('s.cirma@studenti.unisa.it', '2');

INSERT INTO Ordine(PrezzoTotale, DataOrdine, Fattura, Email)
VALUES('150', '2022-12-12', 'Fattura1.pdf', 's.cirma@studenti.unisa.it');
INSERT INTO Ordine(PrezzoTotale, DataOrdine, Fattura, Email)
VALUES('200', '2022-12-13', 'Fattura2.pdf', 's.cirma@studenti.unisa.it');
INSERT INTO Ordine(PrezzoTotale, DataOrdine, Fattura, Email)
VALUES('120', '2022-12-12', 'Fattura3.pdf', 'a.digiorgio@studenti.unisa.it');
INSERT INTO Ordine(PrezzoTotale, DataOrdine, Fattura, Email)
VALUES('85', '2022-12-12', 'Fattura4.pdf', 'a.digiorgio@studenti.unisa.it');

INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'One Piece New Edition vol 1', '10', 'StarComics', 'Italiano', '200', 'imgOP1.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'One Piece New Edition vol 2', '10', 'StarComics', 'Italiano', '200', 'imgOP2.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'One Piece New Edition vol 3', '10', 'StarComics', 'Italiano', '200', 'imgOP3.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'One Piece New Edition vol 4', '10', 'StarComics', 'Italiano', '200', 'imgOP4.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'One Piece New Edition vol 5', '1', 'StarComics', 'Italiano', '200', 'imgOP5.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'DragonBall Evergreen Edition vol 38', '25', 'StarComics', 'Italiano', '200', 'imgDB38.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'DragonBall Evergreen Edition vol 39', '25', 'StarComics', 'Italiano', '200', 'imgDB39.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'DragonBall Evergreen Edition vol 40', '25', 'StarComics', 'Italiano', '200', 'imgDB40.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'DragonBall Evergreen Edition vol 41', '25', 'StarComics', 'Italiano', '200', 'imgDB41.jpg', 1);
INSERT INTO Manga(Prezzo, Descrizione, NumeroArticoli, CasaEditrice, Lingua, NumeroPagine, Immagine, FlagVisibilita)
VALUES('5.20', 'DragonBall Evergreen Edition vol 42', '0', 'StarComics', 'Italiano', '200', 'imgDB42.jpg', 1);

INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Zoro', '10', '923', 'One Piece', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Luffy', '10', '1273', 'One Piece', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Goku', '10', '948', 'DragonBall', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Naruto', '10', '932', 'Naruto', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Tony Stark', '1', '580', 'Marvel', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Zoro Limited Edition', '0', '1288', 'One Piece', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Eren', '10', '1321', 'Attacck On Titan', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Scarlet Witch', '10', '823', 'Marvel', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Captain America', '10', '1200', 'Marvel', 1);
INSERT INTO Pop(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)
VALUES('16.90', 'FunkoPop Vegeta Limited Edition', '10', '614', 'DragonBall', 1);

INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('100', 'Action Figure One Piece', '10', 'PVC', '28', 'Luffy Gear 5', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('120', 'Action Figure One Piece', '10', 'PVC', '40', 'Zoro', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('80', 'Action Figure DragonBall', '10', 'PVC', '30', 'Goku Super Sayan 4', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('120', 'Action Figure Naruto', '0', 'PVC', '32', 'Itachi', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('100', 'Action Figure DragonBall', '1', 'PVC', '40', 'Goku Super Sayan', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('25', 'Action Figure DragonBall', '10', 'PVC', '15', 'Goku Banpresto', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('25', 'Action Figure DragonBall', '10', 'PVC', '15', 'Freezer Banpresto', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('40', 'Action Figure One Piece', '10', 'PVC', '26', 'TrafalgarLaw Banpresto', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('55', 'Action Figure Death Note', '10', 'PVC', '40', 'Ruyk', 1);
INSERT INTO Figure(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)
VALUES('30', 'Action Figure DragonBall', '10', 'PVC', '25', 'Jiren', 1);

INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('1', '1', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('1', '2', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('1', '3', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('1', '4', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('1', '5', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('2', '6', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('2', '7', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('2', '8', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('4', '1', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('3', '6', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('3', '7', '1', '5.20');
INSERT INTO ComprendeManga(IdOrdine, IdManga, Quantita, PrezzoUnitario)
VALUES('3', '8', '1', '5.20');

INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('1', '1', '1', '100');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('1', '2', '1', '120');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('1', '6', '1', '25');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('3', '1', '1', '100');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('3', '2', '1', '120');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('4', '1', '1', '100');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('4', '5', '1', '100');
INSERT INTO ComprendeFigure(IdOrdine, IdFigure, Quantita, PrezzoUnitario)
VALUES('4', '6', '1', '25');

INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('1', '6', '1', '16.90');
INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('1', '2', '1', '16.90');
INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('1', '5', '1', '16.90');
INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('1', '4', '1', '16.90');
INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('1', '9', '1', '16.90');
INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('3', '6', '1', '16.90');
INSERT INTO ComprendePop(IdOrdine, IdPop, Quantita, PrezzoUnitario)
VALUES('3', '2', '1', '16.90');

INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popzoro1.jpg', '1');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popzoro2.jpg', '1');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popluffy1.jpg', '2');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popluffy2.jpg', '2');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popgoku1.jpg', '3');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popgoku2.jpg', '3');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popnaruto1.jpg', '4');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popnaruto2.jpg', '4');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_poptonystark1.jpg', '5');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_poptonystark2.jpg', '5');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popzorolimited1.jpg', '6');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popzorolimited2.jpg', '6');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_poperen1.jpg', '7');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_poperen2.jpg', '7');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popsw1.jpg', '8');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popsw2.jpg', '8');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popcpt1.jpg', '9');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popcpt2.jpg', '9');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popvegeta1.jpg', '10');
INSERT INTO ImmaginePop(Nome, IdPop)
VALUES('img_popvegeta2.jpg', '10');

INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('luffyG5_1.jpg', '1');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('luffyG5_2.jpg', '1');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('luffyG5_3.jpg', '1');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('zoro1.jpg', '2');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('zoro2.jpg', '2');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('zoro3.jpg', '2');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('gokussj4_1.jpg', '3');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('gokussj4_2.jpg', '3');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('gokussj4_3.jpg', '3');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('itachi1.jpg', '4');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('itachi2.jpg', '4');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('itachi3.jpg', '4');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('goku_ss1.jpg', '5');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('goku_ss2.jpg', '5');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('goku_ss3.jpg', '5');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('goku_ban1.jpg', '6');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('goku_ban2.jpg', '6');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('goku_ban3.jpg', '6');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('freezer_ban1.jpg', '7');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('freezer_ban2.jpg', '7');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('freezer_ban3.jpg', '7');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('tl_ban1.jpg', '8');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('tl_ban2.jpg', '8');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('tl_ban3.jpg', '8');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('ryuk1.jpg', '9');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('ryuk2.jpg', '9');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('ryuk3.jpg', '9');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('jiren1.jpg', '10');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('jiren2.jpg', '10');
INSERT INTO ImmagineFigure(Nome, IdFigure)
VALUES('jiren3.jpg', '10');