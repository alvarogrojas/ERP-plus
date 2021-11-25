ALTER TABLE invoice_detail ADD salida_bodega boolean NOT NULL DEFAULT 0; 

-- inicializacion del nuevo campo
update invoice_detail iv set iv.salida_bodega = (select i.salida_bodega from invoice i where i.id = iv.parent_id);

-- nuevo campo quantity returned
ALTER TABLE invoice_detail ADD quantity_returned double DEFAULT 0; 

ALTER TABLE invoice_detail ADD inventario_id int; 

ALTER TABLE invoice_detail ADD CONSTRAINT fk_invoice_detail_inventario FOREIGN KEY ( inventario_id ) 
REFERENCES inventario( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE invoice_detail ADD discount_porcentage double default 0;
ALTER TABLE invoice_detail ADD discount_amount double default 0;




















