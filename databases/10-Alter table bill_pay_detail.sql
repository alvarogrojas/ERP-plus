ALTER TABLE bill_pay_detail ADD ingresado_bodega boolean NOT NULL DEFAULT 0; 

-- inicializacion del nuevo campo
update bill_pay_detail bpd set bpd.ingresado_bodega = (select bp.ingresado_bodega from bill_pay bp where bpd.bill_pay_id = bp.id);


ALTER TABLE bill_pay_detail ADD inventario_id int;

ALTER TABLE bill_pay_detail ADD CONSTRAINT fk_bill_pay_detail_inventario_inventario FOREIGN KEY ( inventario_id ) 
REFERENCES inventario( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE bill_pay_detail ADD barcode varchar(48);

ALTER TABLE bill_pay_detail ADD producto_id int;
 
ALTER TABLE bill_pay_detail ADD CONSTRAINT fk_bill_pay_detail_producto FOREIGN KEY ( producto_id ) 
REFERENCES producto( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;











 
