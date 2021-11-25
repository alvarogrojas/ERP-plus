DROP TABLE IF EXISTS `distrito_cr`;
DROP TABLE IF EXISTS `canton_cr`;
DROP TABLE IF EXISTS `provincia_cr`;

CREATE TABLE `provincia_cr` (
 `id` int(11) not null auto_increment, 
  `codigo_provincia` varchar(2)  NOT NULL,
  `nombre_provincia` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT INTO `provincia_cr` (`codigo_provincia`,`nombre_provincia`) VALUES 
 ('1','San José'),
 ('2','Alajuela'),
 ('3','Cartago'),
 ('4','Heredia'),
 ('5','Guanacaste'),
 ('6','Puntarenas'),
 ('7','Limón');
 
 

CREATE TABLE `canton_cr` (
   `id` int(11) not null auto_increment,
   `provincia_id` int(11),
   `codigo_canton` varchar(2)  NOT NULL, 
   `nombre_canton` varchar(45) NOT NULL,
   PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `canton_cr` (`codigo_canton`,`provincia_id`,`nombre_canton`) VALUES 
 ('01',1,'San José'),
 ('02',1,'Escazú'),
 ('03',1,'Desamparados'),
 ('04',1,'Puriscal'),
 ('05',1,'Tarrazú'),
 ('06',1,'Aserrí'),
 ('07',1,'Mora'),
 ('08',1,'Goicoechea'),
 ('09',1,'Santa Ana'),
 ('10',1,'Alajuelita'),
 ('11',1,'Vasquez de Coronado'),
 ('12',1,'Acosta'),
 ('13',1,'Tibás'),
 ('14',1,'Moravia'),
 ('15',1,'Montes de Oca'),
 ('16',1,'Turrubares'),
 ('17',1,'Dota'),
 ('18',1,'Curridabat'),
 ('19',1,'Pérez Zeledón'),
 ('20',1,'León Cortés'),
 ('01',2,'Alajuela'),
 ('02',2,'San Ramón'),
 ('03',2,'Grecia'),
 ('04',2,'San Mateo'),
 ('05',2,'Atenas'),
 ('06',2,'Naranjo'),
 ('07',2,'Palmares'),
 ('08',2,'Poás'),
 ('09',2,'Orotina'),
 ('10',2,'San Carlos'),
 ('11',2,'Alfaro Ruiz'),
 ('12',2,'Valverde Vega'),
 ('13',2,'Upala'),
 ('14',2,'Los Chiles'),
 ('15',2,'Guatuso'),
 ('01',3,'Cartago'),
 ('02',3,'Paraíso'),
 ('03',3,'La Unión'),
 ('04',3,'Jiménez'),
 ('05',3,'Turrialba'),
 ('06',3,'Alvarado'),
 ('07',3,'Oreamuno'),
 ('08',3,'El Guarco'),
 ('01',4,'Heredia'),
 ('02',4,'Barva'),
 ('03',4,'Santo Domingo'),
 ('04',4,'Santa Bárbara'),
 ('05',4,'San Rafael'),
 ('06',4,'San Isidro'),
 ('07',4,'Belén'),
 ('08',4,'Flores'),
 ('09',4,'San Pablo'),
 ('10',4,'Sarapiquí '),
 ('01',5,'Liberia'),
 ('02',5,'Nicoya'),
 ('03',5,'Santa Cruz'),
 ('04',5,'Bagaces'),
 ('05',5,'Carrillo'),
 ('06',5,'Cañas'),
 ('07',5,'Abangares'),
 ('08',5,'Tilarán'),
 ('09',5,'Nandayure'),
 ('10',5,'La Cruz'),
 ('11',5,'Hojancha'),
 ('01',6,'Puntarenas'),
 ('02',6,'Esparza'),
 ('03',6,'Buenos Aires'),
 ('04',6,'Montes de Oro'),
 ('05',6,'Osa'),
 ('06',6,'Aguirre'),
 ('07',6,'Golfito'),
 ('08',6,'Coto Brus'),
 ('09',6,'Parrita'),
 ('10',6,'Corredores'),
 ('11',6,'Garabito'),
 ('01',7,'Limón'),
 ('02',7,'Pococí'),
 ('03',7,'Siquirres '),
 ('04',7,'Talamanca'),
 ('05',7,'Matina'),
 ('06',7,'Guácimo');
  


ALTER TABLE canton_cr MODIFY provincia_id int(11) NOT NULL;

ALTER TABLE `canton_cr`
    ADD CONSTRAINT `FK_CANTON_PROVINCIA`
   FOREIGN KEY (`provincia_id`) REFERENCES `provincia_cr` (`id`);


CREATE TABLE `distrito_cr` (
  `id` int(11) not null auto_increment,
   `canton_id` int(11), 
  `codigo_distrito` varchar(2) NOT NULL,
  `nombre_distrito` varchar(45) NOT NULL,
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;














