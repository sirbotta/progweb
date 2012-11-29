INSERT INTO CATEGORY (name) VALUES('Ortaggi'),('Legumi'),('Frutta'),('Frutta Secca');

INSERT INTO USERS (username,password,role) VALUES 
('nicola','nicola','seller'),
('simone','simone','seller'),
('mercante','mercante','seller'),
('venditore','venditore','seller'),
('cliente','cliente','buyer'),
('alberto','alberto','buyer'),
('andrea','andrea','buyer'),
('samuele','samuele','buyer'); 





INSERT INTO PRODUCTS (name,seller_id,category_id,quantity,um,price,date_timeinsert,url_image) VALUES 
('Cavolfiore',3,1,10,'Kg',10,'2012-12-19','cavolfiore.jpg'),
('Finocchio',3,1,600,'g',12.45,'2012-11-19','finocchio.jpg'),
('Pomodoro',3,1,2,'Kg',7,'2012-11-19','pomodoro.jpg'),
('Radicchio',3,1,2,'Kg',7,'2012-08-03','radicchio.jpg'),
('Rucola',2,1,700,'g',1.60,'2012-10-7','rucola.jpg'),
('Zucchine',3,1,2,'Kg',7,'2012-11-19','zucchine.jpg'),
('Fagioli',1,2,5,'Kg',20,'2012-11-19','fagioli.jpg'),
('Lenticchie',1,2,500,'g',3.50,'2012-11-19',''),
('Farro',1,2,300,'g',4.30,'2012-11-19',''),
('Mele',2,3,5,'Kg',9,'2012-11-19','mela.jpg'),
('Arance',2,3,300,'g',12,'2012-11-19','arancia.jpg'),
('Fragole',2,3,300,'g',12,'2012-11-19','fragole.jpg'),
('Kiwi',2,3,300,'g',12,'2012-11-19','kiwi.jpg'),
('Banane',2,3,300,'g',12,'2012-11-19','banane.jpg'),
('Arachidi',4,4,10,'KG',6,'2012-11-19','arachidi.jpg'),
('Mandorle',4,4,300,'g',9,'2012-11-19','mandorle.jpg'),
('Nocciole',4,4,3,'KG',12,'2012-11-19','nocciole.jpg'),
('Pistacchi',4,4,3,'KG',12,'2012-11-19','pistacchi.jpg'),
('Noci',4,4,700,'g',6,'2012-11-19','noci.jpg'); 




INSERT INTO ORDERS (DATE_TIMESTAMP, PRODUCT_ID, BUYER_ID, QUANTITY, TOTAL_PRICE, URL_RECEIPT) 
	VALUES (CURRENT_DATE, 14, 5, 10, 15.0, 'ricevutatest.pdf')