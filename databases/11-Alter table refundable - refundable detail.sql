ALTER TABLE refundable ADD ingresado_bodega boolean DEFAULT 0;

ALTER TABLE refundable_detail ADD ingresado_bodega boolean DEFAULT 0;

ALTER TABLE refundable_detail ADD inventario_id int; 

ALTER TABLE refundable_detail ADD CONSTRAINT fk_refundable_detail_inventario FOREIGN KEY ( inventario_id )
REFERENCES inventario( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE refundable_detail ADD type_line enum('Producto', 'Servicio');

alter table refundable_detail add quantity double default 0;

alter table refundable_detail add price double default 0;

ALTER TABLE refundable_detail ADD barcode varchar(48); 

ALTER TABLE refundable_detail ADD bodega_id int UNSIGNED;

ALTER TABLE refundable_detail ADD CONSTRAINT fk_refundable_detail_bodega FOREIGN KEY ( bodega_id )
REFERENCES  bodega( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE refundable_detail ADD producto_id int; 

ALTER TABLE refundable_detail ADD CONSTRAINT fk_refundable_detail_producto FOREIGN KEY ( producto_id )
REFERENCES producto( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;






 

