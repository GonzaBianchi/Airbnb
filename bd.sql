CREATE DATABASE airbnb;
USE airbnb;
CREATE TABLE tipo_usuario(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL
); 

CREATE TABLE usuario(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	password VARCHAR(255) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(255) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	fecha_creacion DATETIME NOT NULL,
	fecha_modificacion DATETIME DEFAULT NULL
);

CREATE TABLE usuario_tipo_usuario( 
	id_tipo_usuario INTEGER NOT NULL,
	id_usuario BIGINT NOT NULL,
	PRIMARY KEY(id_tipo_usuario, id_usuario),
	FOREIGN KEY(id_tipo_usuario) REFERENCES tipo_usuario(id),
	FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

CREATE TABLE tipo_hospedaje(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL
);

CREATE TABLE pais(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL
);

CREATE TABLE ciudad(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	id_pais INTEGER NOT NULL,
	FOREIGN KEY(id_pais) REFERENCES pais(id)
);

CREATE TABLE servicio(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL
);

CREATE TABLE hospedaje(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(100) NOT NULL,
	imagen VARCHAR(255),
	precio_por_noche NUMERIC(8,2) NOT NULL,
	fecha_creacion DATETIME NOT NULL,
	fecha_modificacion DATETIME NOT NULL,
	id_tipo_hospedaje INTEGER NOT NULL,
	id_ciudad INTEGER NOT NULL,
	FOREIGN KEY(id_tipo_hospedaje) REFERENCES tipo_hospedaje(id),
	FOREIGN KEY(id_ciudad) REFERENCES ciudad(id)
);

CREATE TABLE servicio_hospedaje(
	id_servicio INTEGER NOT NULL,
	id_hospedaje BIGINT NOT NULL,
	PRIMARY KEY(id_servicio, id_hospedaje),
	FOREIGN KEY(id_servicio) REFERENCES servicio(id),
	FOREIGN KEY(id_hospedaje) REFERENCES hospedaje(id)
);

CREATE TABLE reserva(
	id_hospedaje BIGINT NOT NULL,
	id_usuario BIGINT NOT NULL,
	fecha_check_in DATE NOT NULL,
	fecha_check_out DATE NOT NULL,
	cant_niños NUMERIC(2,0) DEFAULT 0,
	cant_adultos NUMERIC(2,0) DEFAULT 0,
	cant_bebes NUMERIC(2,0) DEFAULT 0,
	cant_mascotas NUMERIC(2,0) DEFAULT 0,
	importe_total NUMERIC(10,2) NOT NULL,
	fecha_creacion DATETIME NOT NULL,
	fecha_modificacion DATETIME DEFAULT NULL,
	PRIMARY KEY(id_hospedaje, id_usuario),
	FOREIGN KEY(id_hospedaje) REFERENCES hospedaje(id),
	FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

INSERT INTO tipo_usuario (nombre) VALUES
('ADMINISTRADOR'), ('INQUILINO'), ('ANFITRION');

INSERT INTO tipo_hospedaje (nombre) VALUES
('CASA'), ('DEPARTAMENTO'), ('CABAÑA'), ('HOTEL'), ('CASA DE HUSPEDES'), ('MANSION'), ('DOMO');

INSERT INTO pais (nombre) 
VALUES 
    ('Francia'),
    ('España'),
    ('Estados Unidos'),
    ('China'),
    ('Italia'),
    ('Turquía'),
    ('México'),
    ('Tailandia'),
    ('Alemania'),
    ('Reino Unido');

INSERT INTO ciudad (nombre, id_pais) VALUES
    -- Ciudades de Francia
    ('París', 1),
    ('Marsella', 1),
    ('Lyon', 1),
    ('Toulouse', 1),
    ('Niza', 1),
    ('Nantes', 1),
    ('Estrasburgo', 1),
    ('Montpellier', 1),
    ('Burdeos', 1),
    ('Lille', 1),

    -- Ciudades de España
    ('Madrid', 2),
    ('Barcelona', 2),
    ('Valencia', 2),
    ('Sevilla', 2),
    ('Zaragoza', 2),
    ('Málaga', 2),
    ('Bilbao', 2),
    ('Las Palmas de Gran Canaria', 2),
    ('Palma de Mallorca', 2),
    ('Alicante', 2),

    -- Ciudades de Estados Unidos
    ('Nueva York', 3),
    ('Los Ángeles', 3),
    ('Chicago', 3),
    ('Houston', 3),
    ('Phoenix', 3),
    ('Filadelfia', 3),
    ('San Antonio', 3),
    ('San Diego', 3),
    ('Dallas', 3),
    ('San José', 3),

    -- Ciudades de China
    ('Pekín', 4),
    ('Shanghái', 4),
    ('Cantón', 4),
    ('Shenzhen', 4),
    ('Chongqing', 4),
    ('Tianjin', 4),
    ('Hangzhou', 4),
    ('Wuhan', 4),
    ('Xi\'an', 4),
    ('Chengdu', 4),

    -- Ciudades de Italia
    ('Roma', 5),
    ('Milán', 5),
    ('Nápoles', 5),
    ('Turín', 5),
    ('Palermo', 5),
    ('Génova', 5),
    ('Bolonia', 5),
    ('Florencia', 5),
    ('Bari', 5),
    ('Catania', 5),

    -- Ciudades de Turquía
    ('Estambul', 6),
    ('Ankara', 6),
    ('Izmir', 6),
    ('Bursa', 6),
    ('Adana', 6),
    ('Gaziantep', 6),
    ('Konya', 6),
    ('Antalya', 6),
    ('Kayseri', 6),
    ('Mersin', 6),

    -- Ciudades de México
    ('Ciudad de México', 7),
    ('Guadalajara', 7),
    ('Monterrey', 7),
    ('Puebla', 7),
    ('Tijuana', 7),
    ('León', 7),
    ('Ciudad Juárez', 7),
    ('Cancún', 7),
    ('Chihuahua', 7),
    ('Mérida', 7),

    -- Ciudades de Tailandia
    ('Bangkok', 8),
    ('Chiang Mai', 8),
    ('Phuket', 8),
    ('Pattaya', 8),
    ('Nonthaburi', 8),
    ('Udon Thani', 8),
    ('Hat Yai', 8),
    ('Nakhon Ratchasima', 8),
    ('Khon Kaen', 8),
    ('Surat Thani', 8),

    -- Ciudades de Alemania
    ('Berlín', 9),
    ('Hamburgo', 9),
    ('Múnich', 9),
    ('Colonia', 9),
    ('Fráncfort', 9),
    ('Stuttgart', 9),
    ('Düsseldorf', 9),
    ('Dortmund', 9),
    ('Essen', 9),
    ('Leipzig', 9),

    -- Ciudades de Reino Unido
    ('Londres', 10),
    ('Birmingham', 10),
    ('Glasgow', 10),
    ('Liverpool', 10),
    ('Mánchester', 10),
    ('Edimburgo', 10),
    ('Leeds', 10),
    ('Bristol', 10),
    ('Sheffield', 10),
    ('Newcastle', 10);

INSERT INTO servicio (nombre) VALUES
('Wifi'), ('Cocina'), ('Lavarropas'), ('Secadora'), ('Aire acondicionado'), 
('Calefacción'), ('Zona de trabajo'), ('Televisor'), ('Secador de pelo'), ('Plancha');
