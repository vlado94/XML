INSERT INTO FIRMA (NAZIV, ADRESA,PIB,URI,BROJ_RACUNA) VALUES('FIRMA_A','Sime Milosevica 8','11111111111','http://localhost:8080/RESTApi/FIRMA_A','123456789123456789');
INSERT INTO FIRMA (NAZIV, ADRESA,PIB,URI,BROJ_RACUNA) VALUES('FIRMA_B','Dositejeva 12','22222222222','http://localhost:8080/RESTApi/FIRMA_B','555222222222222222');
INSERT INTO FIRMA (NAZIV, ADRESA,PIB,URI,BROJ_RACUNA) VALUES('FIRMA_C','Mise Dimitrijevica 14','23332222222','http://localhost:8080/RESTApi/FIRMA_C','555233322222222333');



INSERT INTO FIRMAS (MAIL, PASSWORD,FIRMA_ID) VALUES('1',1,1);
INSERT INTO FIRMAS (MAIL, PASSWORD,FIRMA_ID) VALUES('2',2,2);
INSERT INTO FIRMAS (MAIL, PASSWORD,FIRMA_ID) VALUES('3',3,3);

INSERT INTO POSLOVNI_SARADNICI (FIRMA1_ID,FIRMA2_ID) VALUES (1,2);
INSERT INTO POSLOVNI_SARADNICI (FIRMA1_ID,FIRMA2_ID) VALUES (2,1);
INSERT INTO POSLOVNI_SARADNICI (FIRMA1_ID,FIRMA2_ID) VALUES (3,1);
INSERT INTO POSLOVNI_SARADNICI (FIRMA1_ID,FIRMA2_ID) VALUES (1,3);


INSERT INTO FAKTURA(adresa_dobavljaca, adresa_kupca, broj_racuna, datum_racuna, datum_valute, iznos_rabata, iznos_za_uplatu, jedinica_mere, jedinicna_cena, kolicina, naziv_dobavljaca, naziv_kupca, naziv_robe_ili_usluge, obradjena, oznaka_valute, pib_dobavljaca, pib_kupca, procena_rabata, redni_broj, ukupan_porez, ukupan_porez_stavka, ukupan_rabat, ukupno_robaiusluge, umanjeno_za_rabat, uplata_na_racun, vrednost, vrednost_robe, vrednost_usluga) values('bulevar', 'trifkovicev trg', '111111111111111111', '2017-06-08 02:00:00', '2017-06-08 02:00:00', '32', '1250', 'kg', '1433', '343', 'ftn', 'levi', 'ds', 0, 'din', '23213123431', NULL, '123', '1', '34', '232', '34', '434', '222', '12', '43434', '3434', '434');
