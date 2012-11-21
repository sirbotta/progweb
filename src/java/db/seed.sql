INSERT INTO CATEGORY (name) VALUES('Ortaggi'),('Legumi'),('Frutta'),('Semi');

INSERT INTO USERS (username,password,role) VALUES 
('ciccio','ciccio','seller'),
('pino','pino','seller'),
('mercante','mercante','seller'),
('elettra','elettra','seller'),
('canto','canto','buyer'),
('gino','gino','buyer'),
('perla','perla','buyer'),
('scana','scana','buyer'); 





INSERT INTO PRODUCTS (name,seller_id,category_id,quantity,um,price,date_timeinsert,url_image) VALUES 
('Carota',3,1,10,'KG',13,'2012-11-19','http://www.franciavincenzo.altervista.org/casa/IMMAGINI/carota.jpg'),
('Cavolo',3,1,600,'g',21,'2012-11-19','http://www.cookaround.com/yabbse1/attachment.php?attachmentid=392116&d=1347383903'),
('Insalata',3,1,900,'g',7,'2012-11-19','http://www.albanesi.it/Alimentazione/cibi/Imma/insalata.jpg'),
('Fasoli',1,2,5,'KG',9,'2012-11-19','http://www.giallozafferano.it/images/prodotti/fagioli.jpg'),
('Lenticchie',1,2,300,'g',12,'2012-11-19','http://www.giallozafferano.it/images/prodotti/lenticchie380m.jpg'),
('Farro',1,2,10,'KG',6,'2012-11-19','http://nutritionknowhow.org/wordpress/wp-content/uploads/2011/11/Farro.jpg'),
('Mele',2,3,5,'KG',9,'2012-11-19','http://www.giallozafferano.it/images/prodotti/mele_380m.jpg'),
('Angurie',2,3,300,'g',12,'2012-11-19','http://aulascienze.scuola.zanichelli.it/wp-content/uploads/image/Giuliam%20Bianconi/angurie.jpg'),
('Fichi',2,3,10,'KG',6,'2012-11-19','http://www.giallozafferano.it/images/prodotti/fichi.jpg'),
('Melograni',4,4,300,'g',9,'2012-11-19','http://www.orlandosidee.de/italiano/bilder/melograno.jpg'),
('Granturco',4,4,3,'KG',12,'2012-11-19','http://www.accademiaarsantiqua.net/sites/default/files/admin/user9/semi-mais-big.jpg'),
('Sesamo',4,4,700,'g',6,'2012-11-19','http://energiasottile.altervista.org/wp-content/uploads/2011/06/semi-sesamo2.jpg'); 




INSERT INTO APP.ORDERS (DATE_TIMESTAMP, PRODUCT_ID, BUYER_ID, QUANTITY, TOTAL_PRICE, URL_RECEIPT) 
	VALUES (CURRENT_DATE, 3, 5, 10, 15.0, 'ddfdf')