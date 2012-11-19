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
('Carota',3,1,10,'KG',5,'2012-11-19','http://www.franciavincenzo.altervista.org/casa/IMMAGINI/carota.jpg'); 