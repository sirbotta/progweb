--creare un db su derby di nome jbp come impostato nel contentx

drop table orders;
drop table products;
drop table category;
drop table users;

create table USERS (
id INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,
INCREMENT BY 1),
username VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
role VARCHAR(200) NOT NULL,
CONSTRAINT pk_users PRIMARY KEY (id)
);

create table CATEGORY (
id INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,
INCREMENT BY 1),
name VARCHAR(100) NOT NULL,
CONSTRAINT pk_category PRIMARY KEY (id)
);

create table PRODUCTS (
id INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,
INCREMENT BY 1),
name VARCHAR(100) NOT NULL,
seller_id INT NOT NULL,
category_id INT NOT NULL,
quantity INT NOT NULL,
UM VARCHAR(100) NOT NULL,
price DOUBLE NOT NULL,
date_timeinsert DATE NOT NULL,
url_image VARCHAR(255),
CONSTRAINT pk_products PRIMARY KEY (id),
CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES CATEGORY(id)
);


create table ORDERS (
id INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,
INCREMENT BY 1),
date_timestamp DATE NOT NULL,
product_id INT NOT NULL,
buyer_id INT NOT NULL,
quantity INT NOT NULL,
total_price DOUBLE NOT NULL,
url_receipt VARCHAR(255) NOT NULL,
CONSTRAINT pk_orders PRIMARY KEY (id),
CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES PRODUCTS(id),
CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES USERS(id)
);