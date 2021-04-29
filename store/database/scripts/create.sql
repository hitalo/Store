

CREATE DATABASE store
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	
CREATE SCHEMA people;


CREATE TABLE people.people (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR NOT NULL,
	email VARCHAR NOT NULL UNIQUE,
	address VARCHAR NOT NULL
);

CREATE TABLE people.user (
	id SERIAL PRIMARY KEY NOT NULL,
	login VARCHAR NOT NULL UNIQUE,
	password TEXT NOT NULL,
	people_id INTEGER REFERENCES people.people(id) ON DELETE CASCADE
);

CREATE TABLE people.employee (
	id SERIAL PRIMARY KEY NOT NULL,	
	people_id INTEGER REFERENCES people.people(id) ON DELETE CASCADE
);

CREATE TABLE people.permission (
	value VARCHAR PRIMARY KEY NOT NULL,
	name VARCHAR NOT NULL,
	description TEXT
);

CREATE TABLE people.role (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR NOT NULL UNIQUE,
	description TEXT
);

CREATE TABLE people.user_permission (	
	user_id INTEGER REFERENCES people.user(id) ON DELETE CASCADE,
	permission_id VARCHAR REFERENCES people.permission(value) ON DELETE CASCADE
);

CREATE TABLE people.user_role (	
	user_id INTEGER REFERENCES people.user(id) ON DELETE CASCADE,
	role_id INTEGER REFERENCES people.role(id) ON DELETE CASCADE
);

CREATE TABLE people.role_permission (	
	role_id INTEGER REFERENCES people.role(id) ON DELETE CASCADE,
	permission_id INTEGER REFERENCES people.permission(id) ON DELETE CASCADE
);


CREATE SCHEMA product;

CREATE TABLE product.product (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR NOT NULL,
	description TEXT,
	value NUMERIC(9,2) NOT NULL CHECK(value > 0),
	unit VARCHAR(10) NOT NULL,
	image VARCHAR NOT NULL DEFAULT 'product_placeholder.png',
	stock NUMERIC NOT NULL DEFAULT 0
);

CREATE TABLE product.tag (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR NOT NULL
);

CREATE TABLE product.cart (
	user_id INTEGER REFERENCES people.user(id) ON DELETE CASCADE,
	product_id INTEGER REFERENCES product.product(id) ON DELETE CASCADE,
	amount NUMERIC NOT NULL
);

CREATE TABLE product.product_tag (
	product_id INTEGER REFERENCES product.product(id) ON DELETE CASCADE,
	tag_id INTEGER REFERENCES product.tag(id) ON DELETE CASCADE
);


CREATE SCHEMA config;


CREATE TABLE config.config (
	logo VARCHAR NOT NULL DEFAULT 'logo_placeholder.png',
	name VARCHAR NOT NULL,
	primary_color VARCHAR(30) NOT NULL,
	secundary_color VARCHAR(30) NOT NULL
);


CREATE SCHEMA logs;


CREATE TABLE logs.buy (
	id SERIAL PRIMARY KEY NOT NULL,
	resume TEXT NOT NULL,
	date TIMESTAMP NOT NULL DEFAULT now()
);



INSERT INTO people.permission (name, value, description) VALUES ('ADMIN', '001_001', 'System admin, max controll');
INSERT INTO people.permission (name, value, description) VALUES ('USER', '001_002', 'Normal user/consumer');
INSERT INTO people.permission (name, value, description) VALUES ('EMPLOYEE', '001_003', 'Employees can manage products');

INSERT INTO config.config (name, primary_color, secundary_color) values ('MyStore', '#0066ff', '#ffffff');

INSERT INTO people.people (name, email, address) VALUES ('Admin', 'admin@email.com', '');
INSERT INTO people.user (login, password, people_id) VALUES ('admin', 'admin', (select id from people.people where name='Admin'));
INSERT INTO people.employee (people_id) VALUES ((select id from people.people where name='Admin'));
