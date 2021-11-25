-- Scripts para productos

CREATE TABLE `unidad_medida` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` char(20) NOT NULL,
  `simbolo` char(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Tabla de unidades de medida';


 CREATE TABLE `fabricante` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Fabricante del producto'; 


CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` char(150) DEFAULT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT 'Activo',
  `fecha_ingreso` date NOT NULL,
  `ingresado_por` int(10) unsigned DEFAULT NULL,
  `fecha_ultimo_cambio` date NOT NULL,
  `ultimo_cambio_por` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_categoria_ingresado_por` (`ingresado_por`),
  KEY `fk_categoria_ultimo_cambio_por` (`ultimo_cambio_por`),
  CONSTRAINT `fk_categoria_ingresado_por` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_categoria_ultimo_cambio_por` FOREIGN KEY (`ultimo_cambio_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de categorias de productos'; 


CREATE TABLE `tipo_documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- Scripts tabla multiplicador
CREATE TABLE `multiplicador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `factor` double DEFAULT '0',
  `nombre` varchar(50) NOT NULL,
  `codigo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;


CREATE TABLE `tienda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `direccion` varchar(250) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `ubicacion_bodega` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bodega_id` int(10) unsigned NOT NULL,
  `estante` varchar(10) DEFAULT NULL,
  `fila` varchar(10) DEFAULT NULL,
  `columna` varchar(10) DEFAULT NULL,
  `ultimo_cambio_id` int(10) unsigned DEFAULT NULL COMMENT 'Usuario que modifica el registro',
  `fecha_ultimo_cambio` date DEFAULT NULL COMMENT 'Fecha del ultimo cambio al registro',
  PRIMARY KEY (`id`),
  KEY `fk_ubicacion_bodega_bodega` (`bodega_id`),
  CONSTRAINT `fk_ubicacion_bodega_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 



 CREATE TABLE `producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `fabricante_id` int(11) DEFAULT NULL,
  `margen_utilidad` double DEFAULT NULL,
  `precio_list` double DEFAULT NULL,
  `unidad_medida_id` int(11) NOT NULL,
  `gravado` int(11) DEFAULT '1',
  `sku` varchar(64) DEFAULT NULL,
  `ultimo_cambio_id` int(10) unsigned NOT NULL,
  `fecha_ultimo_cambio` date NOT NULL,
  `existencia_maxima` double DEFAULT '0',
  `existencia_minima` double DEFAULT '0',
  `punto_reorden` double DEFAULT '0',
  `catalogo` varchar(32) DEFAULT NULL,
  `descripcion_ingles` varchar(128) DEFAULT NULL,
  `descripcion_espanol` varchar(128) DEFAULT NULL,
  `unidad_empaque` double DEFAULT '0',
  `unidad_empaque_codigo` varchar(8) DEFAULT NULL,
  `pais_origen` varchar(32) DEFAULT NULL,
  `tiempo_fabricacion` int(11) DEFAULT '0',
  `notas` varchar(200) DEFAULT NULL,
  `despacho_desde` varchar(32) DEFAULT NULL,
  `responsable_producto` varchar(48) DEFAULT NULL,
  `alto` double DEFAULT '0',
  `alto_unidad_medida_id` int(11) DEFAULT NULL,
  `ancho` double DEFAULT NULL,
  `ancho_unidad_medida_id` int(11) DEFAULT NULL,
  `peso_kg` double DEFAULT NULL,
  `termino_entrega` varchar(8) DEFAULT NULL,
  `codigo_cabys` varchar(13) NOT NULL,
  `simbolo_descuento` varchar(8) DEFAULT NULL,
  `multiplicador_id` int(11) DEFAULT NULL,
  `manejo_bodega` varchar(10) NOT NULL DEFAULT 'SIMPLE',
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  KEY `fk_producto_multiplicador` (`multiplicador_id`),
  KEY `fk_producto_unidad_medida` (`ancho_unidad_medida_id`),
  KEY `fk_producto_unidad_medida_0` (`alto_unidad_medida_id`),
  KEY `fk_producto_fabricante` (`fabricante_id`),
  KEY `fk_producto_user` (`ultimo_cambio_id`),
  KEY `idx_producto_codigo` (`codigo`) USING BTREE,
  KEY `idx_producto_codigo_cabys` (`codigo_cabys`) USING BTREE,
  KEY `idx_producto_descripcion_espanol` (`descripcion_espanol`) USING BTREE,
  CONSTRAINT `fk_producto_fabricante` FOREIGN KEY (`fabricante_id`) REFERENCES `fabricante` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_multiplicador` FOREIGN KEY (`multiplicador_id`) REFERENCES `multiplicador` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_unidad_medida` FOREIGN KEY (`ancho_unidad_medida_id`) REFERENCES `unidad_medida` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_unidad_medida_0` FOREIGN KEY (`alto_unidad_medida_id`) REFERENCES `unidad_medida` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_user` FOREIGN KEY (`ultimo_cambio_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_user_ultimo_cambio` FOREIGN KEY (`ultimo_cambio_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8; 

CREATE TABLE `producto_categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoria_id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  `fecha_ingreso` date NOT NULL,
  `ingresado_por` int(10) unsigned DEFAULT NULL,
  `fecha_ultimo_cambio` date NOT NULL,
  `ultimo_cambio_por` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_producto_categoria_producto` (`producto_id`),
  KEY `fk_producto_categoria` (`categoria_id`),
  KEY `fk_producto_categoria_ingresado_por` (`ingresado_por`),
  KEY `fk_producto_categoria_ultimo_cambio_por` (`ultimo_cambio_por`),
  KEY `idx_producto_categoria_estado` (`estado`) USING HASH,
  CONSTRAINT `fk_producto_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_categoria_ingresado_por` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_categoria_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_categoria_ultimo_cambio_por` FOREIGN KEY (`ultimo_cambio_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `producto_descuento` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `producto_id` int(11) NOT NULL,
  `cantidad_minima_inclusive` double NOT NULL DEFAULT '0',
  `cantidad_maxima_inclusive` double DEFAULT '0',
  `estado` enum('ACTIVA','INACTIVA') DEFAULT 'ACTIVA',
  `fecha_ingreso` date NOT NULL,
  `ingresado_por_id` int(10) unsigned NOT NULL,
  `fecha_ultima_actualizacion` date NOT NULL,
  `ultima_actualizacion_por_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_producto_descuento_producto` (`producto_id`),
  KEY `fk_producto_descuento_actaulizado_por` (`ultima_actualizacion_por_id`),
  KEY `fk_producto_descuento_user_ingresado_por` (`ingresado_por_id`),
  CONSTRAINT `fk_producto_descuento_actaulizado_por` FOREIGN KEY (`ultima_actualizacion_por_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_descuento_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_descuento_user_ingresado_por` FOREIGN KEY (`ingresado_por_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE `inventario_bodega` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bodega_id` int(10) unsigned DEFAULT NULL,
  `cantidad_disponible` double DEFAULT '0',
  `cantidad_congelada` double NOT NULL DEFAULT '0',
  `producto_id` int(11) NOT NULL,
  `cantidad_total_anual` double DEFAULT NULL,
  `cantidad_cotizada` double NOT NULL DEFAULT '0',
  `cantidad_apartado` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_inventario_bodega_bodega` (`bodega_id`),
  KEY `fk_inventario_bodega_producto` (`producto_id`),
  CONSTRAINT `fk_inventario_bodega_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventario_bodega_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `inventario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `producto_id` int(11) NOT NULL,
  `fecha` date DEFAULT NULL COMMENT 'fecha de ingreso del lote de inventario',
  `tienda_id` int(11) NOT NULL,
  `cantidad_entrada` double DEFAULT '0',
  `costo_entrada` double DEFAULT '0',
  `total_entrada` double DEFAULT '0',
  `cantidad_salida` double DEFAULT '0',
  `costo_salida` double DEFAULT '0',
  `total_salida` double DEFAULT '0',
  `cantidad_saldo` double DEFAULT '0' COMMENT 'cantidad_saldo es el resultado de  cantidad_entrada - cantidad_salida',
  `costo_saldo` double DEFAULT '0',
  `total_saldo` double DEFAULT '0' COMMENT 'Resultado de multiplicar cantidad_saldo * costo_saldo',
  `ultimo_cambio_id` int(10) unsigned NOT NULL,
  `fecha_ultimo_cambio` date NOT NULL,
  `bodega_id` int(10) unsigned DEFAULT NULL,
  `ubicacion_bodega_id` int(11) DEFAULT NULL,
  `disponible` int(11) NOT NULL DEFAULT '0',
  `barcode` varchar(48) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_inventario_tienda` (`tienda_id`),
  KEY `fk_inventario_user` (`ultimo_cambio_id`),
  KEY `fk_inventario_bodega` (`bodega_id`),
  KEY `fk_inventario_producto` (`producto_id`),
  CONSTRAINT `fk_inventario_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventario_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventario_tienda` FOREIGN KEY (`tienda_id`) REFERENCES `tienda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventario_user` FOREIGN KEY (`ultimo_cambio_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `inventario_bodega_venta_detalle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inventario_bodega_id` int(11) NOT NULL,
  `mes` varchar(10) NOT NULL,
  `year` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT '0',
  `fecha_ultimo_cambio` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_inventario_bodega_venta_detalle` (`inventario_bodega_id`),
  CONSTRAINT `fk_inventario_bodega_venta_detalle` FOREIGN KEY (`inventario_bodega_id`) REFERENCES `inventario_bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE `salida_inventario_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inventario_id` int(11) DEFAULT NULL,
  `venta_id` int(11) DEFAULT NULL,
  `venta_detalle_id` int(11) DEFAULT NULL,
  `cantidad` double DEFAULT '0',
  `fecha_hora_salida` date DEFAULT NULL,
  `responsable_id` int(10) unsigned DEFAULT NULL,
  `tipo_salida` varchar(10) DEFAULT NULL COMMENT 'Tipo salida = UEPS, PEPS',
  `bodega_id` int(10) unsigned NOT NULL,
  `barcode`  varchar(48),
  PRIMARY KEY (`id`),
  KEY `fk_salida_inventario_log` (`inventario_id`),
  KEY `fk_salida_inventario_log_bodega` (`bodega_id`),
  KEY `fk_salida_inventario_log_user` (`responsable_id`),
  CONSTRAINT `fk_salida_inventario_log` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_salida_inventario_log_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_salida_inventario_log_user` FOREIGN KEY (`responsable_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8; 


-- inicializacion de tipos de documento
INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'COMPRA' );

INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'VENTA' );

INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'DEVOLUCION' );

INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'REEMBOLSO' );

INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'REQUISICION' );

INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'TRASLADO' );

INSERT INTO tipo_documento
	( id, tipo) VALUES ( 0, 'INICIALIZADOR' );


CREATE TABLE `entradas_inventario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(10) unsigned NOT NULL,
  `documento_origen_id` int(11) NOT NULL,
  `tipo_documento_id` int(11) NOT NULL,
  `detalle` varchar(200) DEFAULT NULL,
  `cantidad` double NOT NULL DEFAULT '0',
  `costo` double DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `fecha` date NOT NULL,
  `compra_detalle_id` int(11) NOT NULL,
  `inventario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_entradas_inventario_user` (`usuario_id`),
  KEY `fk_entradas_inventario_tipo_documento` (`tipo_documento_id`),
  KEY `fk_entradas_inventario_inventario` (`inventario_id`),
  CONSTRAINT `fk_entradas_inventario_inventario` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entradas_inventario_tipo_documento` FOREIGN KEY (`tipo_documento_id`) REFERENCES `tipo_documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entradas_inventario_user` FOREIGN KEY (`usuario_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `salidas_inventario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(10) unsigned NOT NULL,
  `documento_origen_id` int(11) NOT NULL,
  `tipo_documento_id` int(11) NOT NULL,
  `detalle` varchar(200) DEFAULT NULL,
  `cantidad` double NOT NULL DEFAULT '0',
  `costo` double DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `fecha` date NOT NULL,
  `venta_detalle_id` int(11) NOT NULL,
  `inventario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_salidas_inventario_user` (`usuario_id`),
  KEY `fk_salidas_inventario` (`tipo_documento_id`),
  KEY `fk_salidas_inventario_inventario` (`inventario_id`),
  CONSTRAINT `fk_salidas_inventario` FOREIGN KEY (`tipo_documento_id`) REFERENCES `tipo_documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_salidas_inventario_inventario` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_salidas_inventario_user` FOREIGN KEY (`usuario_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 



CREATE TABLE `cliente_descuento` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(10) unsigned NOT NULL,
  `categoria_id` int(11) NOT NULL,
  `porcentaje` double NOT NULL DEFAULT '0',
  `descripcion` varchar(64) DEFAULT NULL,
  `estado` varchar(16) DEFAULT 'Activo' COMMENT 'Valores = Activo, Inactivo',
  PRIMARY KEY (`id`),
  KEY `fk_cliente_descuento_client` (`client_id`),
  KEY `fk_cliente_descuento` (`categoria_id`),
  CONSTRAINT `fk_cliente_descuento_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_descuento_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB  DEFAULT CHARSET=utf8; 


-- SCRIPTS ISSUE 84 Crear tabla cotizacion

CREATE TABLE `cotizacion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cotizacion_number` varchar(32) NOT NULL,
  `fecha_emision` date NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `cliente_id` int(10) unsigned NOT NULL,
  `contact_id` int(10) unsigned DEFAULT NULL,
  `asunto` varchar(32) NOT NULL,
  `direccion` varchar(80) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `credit_days` smallint(5) unsigned NOT NULL DEFAULT '0',
  `vendedor_id` int(10) unsigned NOT NULL,
  `telefono_vendedor` varchar(32) NOT NULL,
  `estado` enum('Edicion','Enviada','No Adjudicada','Vencida','Aprobada') NOT NULL DEFAULT 'Edicion',
  `correo_vendedor` varchar(64) NOT NULL,
  `observaciones` varchar(250) DEFAULT NULL,
  `sub_total` double NOT NULL DEFAULT '0',
  `iva` double NOT NULL DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `ingresado_por` int(10) unsigned NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `fecha_ultima_modificacion` date NOT NULL,
  `currency_id` int(10) unsigned NOT NULL,
  `bodega_id` int(10) unsigned NOT NULL,
  `exonerated` double DEFAULT '0',
  `total_descuentos` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_cotizacion_client` (`cliente_id`),
  KEY `fk_cotizacion_contact_client` (`contact_id`),
  KEY `fk_cotizacion_user` (`vendedor_id`),
  KEY `fk_cotizacion_ingresado_por` (`ingresado_por`),
  KEY `fk_cotizacion_currency` (`currency_id`),
  KEY `fk_cotizacion_bodega` (`bodega_id`),
  CONSTRAINT `fk_cotizacion_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_client` FOREIGN KEY (`cliente_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_contact_client` FOREIGN KEY (`contact_id`) REFERENCES `contact_client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_currency` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_ingresado_por` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_user` FOREIGN KEY (`vendedor_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;  

CREATE TABLE `cotizacion_detalle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cotizacion_id` int(10) unsigned NOT NULL,
  `line_number` smallint(5) unsigned NOT NULL,
  `descripcion` varchar(64) NOT NULL,
  `entrega` varchar(32) DEFAULT NULL,
  `cantidad` double NOT NULL DEFAULT '0',
  `precio_unitario` double NOT NULL DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `porcentaje_descuento` double NOT NULL DEFAULT '0',
  `iva_id` int(10) unsigned NOT NULL,
  `por_tax` double DEFAULT '0',
  `tax` double DEFAULT '0',
  `exonerated` double DEFAULT '0',
  `sub_total` double DEFAULT '0',
  `cost_center_id` int(11) unsigned DEFAULT NULL,
  `descuento_id` int(10) unsigned DEFAULT NULL,
  `descuento_monto` double DEFAULT '0',
  `tipo_descuento` enum('Producto','Cliente') NOT NULL DEFAULT 'Cliente',
  `descuento_porcentaje` double DEFAULT '0',
  `inventario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cotizacion_detalle` (`cotizacion_id`),
  KEY `fk_cotizacion_detalle_taxes_iva` (`iva_id`),
  KEY `fk_cotizacion_detalle_cost_center` (`cost_center_id`),
  KEY `fk_cotizacion_detalle_cliente_descuento` (`descuento_id`),
  KEY `fk_cotizacion_detalle_inventario` (`inventario_id`),
  CONSTRAINT `fk_cotizacion_detalle` FOREIGN KEY (`cotizacion_id`) REFERENCES `cotizacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_detalle_cost_center` FOREIGN KEY (`cost_center_id`) REFERENCES `costs_center` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_detalle_inventario` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_detalle_taxes_iva` FOREIGN KEY (`iva_id`) REFERENCES `taxes_iva` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `cotizacion_historico_version` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `version_number` smallint(5) unsigned NOT NULL,
  `cotizacion_id` int(10) unsigned NOT NULL,
  `cotizacion_number` varchar(32) NOT NULL,
  `fecha_emision` date NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `cliente_id` int(10) unsigned NOT NULL,
  `contact_id` int(10) unsigned DEFAULT NULL,
  `asunto` varchar(32) NOT NULL,
  `direccion` varchar(80) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `credit_days` smallint(5) unsigned NOT NULL DEFAULT '0',
  `vendedor_id` int(10) unsigned NOT NULL,
  `telefono_vendedor` varchar(32) NOT NULL,
  `estado` enum('Edicion','Enviada','No Adjudicada','Vencida','Aprobada') NOT NULL DEFAULT 'Edicion',
  `correo_vendedor` varchar(64) NOT NULL,
  `observaciones` varchar(250) DEFAULT NULL,
  `sub_total` double NOT NULL DEFAULT '0',
  `iva` double NOT NULL DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `ingresado_por` int(10) unsigned NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `fecha_ultima_modificacion` date NOT NULL,
  `currency_id` int(10) unsigned NOT NULL,
  `bodega_id` int(10) unsigned NOT NULL,
  `exonerated` double DEFAULT '0',
  `total_descuentos` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_cotizacion_historico_parent` (`cotizacion_id`),
  KEY `fk_cotizacion_historico_client` (`cliente_id`),
  KEY `fk_cotizacion_historico_contact_client` (`contact_id`),
  KEY `fk_cotizacion_historico_user` (`vendedor_id`),
  KEY `fk_cotizacion_historico_ingresado_por` (`ingresado_por`),
  KEY `fk_cotizacion_historico_currency` (`currency_id`),
  KEY `fk_cotizacion_historico_version_bodega` (`bodega_id`),
  CONSTRAINT `fk_cotizacion_historico_client` FOREIGN KEY (`cliente_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_contact_client` FOREIGN KEY (`contact_id`) REFERENCES `contact_client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_currency` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_ingresado_por` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_parent` FOREIGN KEY (`cotizacion_id`) REFERENCES `cotizacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_user` FOREIGN KEY (`vendedor_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_version_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;    


CREATE TABLE `cotizacion_historico_version_detalle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cotizacion_historico_version_id` int(10) unsigned NOT NULL,
  `line_number` smallint(5) unsigned NOT NULL,
  `descripcion` varchar(64) NOT NULL,
  `entrega` varchar(32) DEFAULT NULL,
  `cantidad` double NOT NULL DEFAULT '0',
  `precio_unitario` double NOT NULL DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `porcentaje_descuento` double NOT NULL DEFAULT '0',
  `iva_id` int(10) unsigned NOT NULL,
  `por_tax` double DEFAULT '0',
  `tax` double DEFAULT '0',
  `exonerated` double DEFAULT '0',
  `sub_total` double DEFAULT '0',
  `inventario_id` int(11) NOT NULL,
  `cost_center_id` int(11) unsigned DEFAULT NULL,
  `descuento_id` int(10) unsigned DEFAULT NULL,
  `descuento_monto` double DEFAULT '0',
  `tipo_descuento` enum('Producto','Cliente') NOT NULL DEFAULT 'Cliente',
  `descuento_porcentaje` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_cotizacion_historico_detalle` (`id`),
  KEY `fk_cotizacion_historico_detalle_parent` (`cotizacion_historico_version_id`),
  KEY `fk_cotizacion_historico_detalle_taxes_iva` (`iva_id`),
  KEY `fk_cotizacion_historico_version_detalle_cost_center` (`cost_center_id`),
  KEY `fk_cotizacion_historico_version_detalle_cliente_descuento` (`descuento_id`),
  CONSTRAINT `fk_cotizacion_historico_detalle_parent` FOREIGN KEY (`cotizacion_historico_version_id`) REFERENCES `cotizacion_historico_version` (`id`),
  CONSTRAINT `fk_cotizacion_historico_detalle_taxes_iva` FOREIGN KEY (`iva_id`) REFERENCES `taxes_iva` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cotizacion_historico_version_detalle_cost_center` FOREIGN KEY (`cost_center_id`) REFERENCES `costs_center` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `documento_consecutivo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empresa` varchar(64) NOT NULL,
  `tipo_documento` varchar(64) NOT NULL,
  `doc_origen_id` int(11) NOT NULL,    
  `actual_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- Alters bill_pay
ALTER TABLE bill_pay ADD ingresado_bodega bit DEFAULT 0;

-- actualizar bill_pay historico
update bill_pay set ingresado_bodega = true;

-- Actualizar campos de la tabla bodega 
alter table bodega add  `ultimo_cambio_id` int(11) unsigned DEFAULT NULL; 
alter table bodega add   `fecha_ultimo_cambio` date DEFAULT NULL; 
alter table bodega add `tienda_id` int(11) NULL;
alter table bodega add   `manejo_bodega` varchar(10) DEFAULT 'SIMPLE';
alter table bodega add KEY `fk_bodega_tienda` (`tienda_id`);
alter table bodega add KEY `fk_bodega_user_ultimo_cambio` (`ultimo_cambio_id`);
ALTER TABLE bodega ADD CONSTRAINT fk_bodega_tienda FOREIGN KEY ( tienda_id ) REFERENCES tienda( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
alter table bodega add CONSTRAINT `fk_bodega_user_ultimo_cambio` FOREIGN KEY (`ultimo_cambio_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE `requisicion_bodega` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `consecutivo` varchar(32) NOT NULL,
  `fecha_solicitada` date NOT NULL,
  `fecha_aprobada` date DEFAULT NULL,
  `fecha_despachada` date DEFAULT NULL,
  `motivo_salida` varchar(200) NOT NULL,
  `bodega_id` int(10) unsigned DEFAULT NULL,
  `estado` enum('Edicion','Pendiente Aprobar','Rechazado','Aprobado','Despachado') NOT NULL DEFAULT 'Edicion',
  `monto_solicitado` double NOT NULL DEFAULT '0',
  `monto_aprobado` double NOT NULL DEFAULT '0',
  `ingresado_por` int(10) unsigned NOT NULL,
  `fecha_ultima_modificacion` date NOT NULL,
  `salida_bodega` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_requisicion_bodega_bodega` (`bodega_id`),
  KEY `fk_requisicion_bodega_user_ingresado_por_user` (`ingresado_por`),
  CONSTRAINT `fk_requisicion_bodega_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_requisicion_bodega_user_ingresado_por_user` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 


CREATE TABLE `requisicion_bodega_detalle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `requisicion_id` int(10) unsigned NOT NULL,
  `costs_center_id` int(10) unsigned NOT NULL,
  `cantidad_solicitada` double NOT NULL DEFAULT '0',
  `cantidad_despachada` double DEFAULT '0',
  `precio_unidad` double NOT NULL DEFAULT '0',
  `monto_total_solicitado` double NOT NULL DEFAULT '0',
  `monto_total_despachado` double DEFAULT '0',
  `salida_bodega` tinyint(1) NOT NULL DEFAULT '0',
  `quantity_returned` double DEFAULT '0',
  `inventario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_requisicion_bodega_detalle` (`requisicion_id`),
  KEY `fk_requisicion_bodega_detalle_costs_center` (`costs_center_id`),
  KEY `fk_requisicion_bodega_detalle_inventario` (`inventario_id`),
  CONSTRAINT `fk_requisicion_bodega_detalle_costs_center` FOREIGN KEY (`costs_center_id`) REFERENCES `costs_center` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_requisicion_bodega_detalle_inventario` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_requisicion_bodega_detalle_requisicion` FOREIGN KEY (`requisicion_id`) REFERENCES `requisicion_bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 

CREATE TABLE `motivo_devolucion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  `ingresa_bodega` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `devolucion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `consecutivo` char(32) NOT NULL,
  `tipo` enum('DEV-NC','DEV-RQ') NOT NULL,
  `referencia_id` int(10) unsigned NOT NULL,
  `bodega_id` int(10) unsigned DEFAULT NULL,
  `estado` enum('Edicion','Ingresado','Anulado','Pendiente') NOT NULL DEFAULT 'Edicion',
  `monto` double NOT NULL DEFAULT '0',
  `ingresado_por` int(10) unsigned NOT NULL,
  `fecha_ultima_actualizacion` date NOT NULL,
  `ultima_actualizacion_por` int(10) unsigned NOT NULL,
  `ingresado_bodega` tinyint(1) NOT NULL DEFAULT '0',
  `fecha_devolucion` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_devolucion_bodega` (`bodega_id`),
  KEY `fk_devolucion_user` (`ingresado_por`),
  KEY `fk_devolucion_user_actualizacion` (`ultima_actualizacion_por`),
  CONSTRAINT `fk_devolucion_bodega` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_devolucion_user` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_devolucion_user_actualizacion` FOREIGN KEY (`ultima_actualizacion_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;  

CREATE TABLE `devolucion_detalle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devolucion_id` int(10) unsigned NOT NULL,
  `linea_parent_id` int(10) unsigned NOT NULL,
  `motivo_id` int(10) unsigned DEFAULT NULL,
  `cantidad` double NOT NULL DEFAULT '0',
  `precio` double NOT NULL DEFAULT '0',
  `ingresado_bodega` tinyint(1) NOT NULL DEFAULT '0',
  `inventario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_devolucion_detalle_motivo_devolucion` (`motivo_id`),
  KEY `fk_devolucion_detalle_devolucion` (`devolucion_id`),
  KEY `fk_devolucion_detalle_inventario` (`inventario_id`),
  CONSTRAINT `fk_devolucion_detalle_devolucion` FOREIGN KEY (`devolucion_id`) REFERENCES `devolucion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_devolucion_detalle_inventario` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_devolucion_detalle_motivo_devolucion` FOREIGN KEY (`motivo_id`) REFERENCES `motivo_devolucion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `recurso` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tipo_referencia` enum('Producto') NOT NULL DEFAULT 'Producto',
  `referencia_id` int(10) unsigned NOT NULL,
  `uri_recurso` varchar(300) NOT NULL,
  `nombre_recurso` varchar(64) NOT NULL,
  `tipo_recurso` enum('Fotografia','Documento','Hipervinculo','Video','Audio') DEFAULT 'Fotografia',
  `fecha_agregado` date DEFAULT NULL,
  `agregado_por` int(10) unsigned DEFAULT NULL,
  `fecha_ultima_actualizacion` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `categoria_descuentos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `categoria_id` int(11) NOT NULL,
  `nombre_descuento` varchar(64) NOT NULL,
  `porcentaje_descuento` double NOT NULL DEFAULT '0',
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  `fecha_ingreso` date NOT NULL,
  `ingresado_por` int(10) unsigned NOT NULL,
  `fecha_ultimo_cambio` date NOT NULL,
  `ultimo_cambio_por` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_categoria_descuentos_categoria` (`categoria_id`),
  KEY `fk_categoria_descuentos_ingresado_por` (`ingresado_por`),
  KEY `fk_categoria_descuentos_ultimo_cambio_por` (`ultimo_cambio_por`),
  KEY `idx_categoria_descuentos_esatdo` (`estado`) USING HASH,
  CONSTRAINT `fk_categoria_descuentos_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_categoria_descuentos_ingresado_por` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_categoria_descuentos_ultimo_cambio_por` FOREIGN KEY (`ultimo_cambio_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `traslado_justificacion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  `es_desecho` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `traslado` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `consecutivo` varchar(32) DEFAULT NULL,
  `fecha_traslado` date DEFAULT NULL,
  `ingresado_por` int(10) unsigned NOT NULL,
  `fecha_ultima_modificacion` date NOT NULL,
  `bodega_origen_id` int(10) unsigned DEFAULT NULL,
  `bodega_destino_id` int(10) unsigned DEFAULT NULL,
  `estado` enum('Edicion','Trasladado','Cancelado') DEFAULT 'Edicion',
  PRIMARY KEY (`id`),
  KEY `fk_traslado_user` (`ingresado_por`),
  KEY `fk_traslado_bodega_origen` (`bodega_origen_id`),
  KEY `fk_traslado_bodega_destino` (`bodega_destino_id`),
  CONSTRAINT `fk_traslado_bodega_destino` FOREIGN KEY (`bodega_destino_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_traslado_bodega_origen` FOREIGN KEY (`bodega_origen_id`) REFERENCES `bodega` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_traslado_user` FOREIGN KEY (`ingresado_por`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `traslado_detalle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `traslado_id` int(10) unsigned NOT NULL,
  `cost_center_id` int(10) unsigned NOT NULL,
  `cantidad_traslado` double DEFAULT '0',
  `inventario_id` int(11) NOT NULL,
  `costo_total` double DEFAULT '0',
  `justificacion_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_traslado_detalle_traslado` (`traslado_id`),
  KEY `fk_traslado_detalle_cost_center` (`cost_center_id`),
  KEY `fk_traslado_detalle_inventario` (`inventario_id`),
  KEY `fk_traslado_detalle_justificacion` (`justificacion_id`),
  CONSTRAINT `fk_traslado_detalle_cost_center` FOREIGN KEY (`cost_center_id`) REFERENCES `costs_center` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_traslado_detalle_inventario` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_traslado_detalle_justificacion` FOREIGN KEY (`justificacion_id`) REFERENCES `traslado_justificacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_traslado_detalle_traslado` FOREIGN KEY (`traslado_id`) REFERENCES `traslado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



 




 

 

 

 





