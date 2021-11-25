ALTER TABLE invoice_nota_credito_detail ADD ingresado_bodega boolean NOT NULL DEFAULT 0; 

ALTER TABLE invoice_nota_credito_detail ADD cost_center_id int UNSIGNED; 

ALTER TABLE invoice_nota_credito_detail 
ADD CONSTRAINT fk_invoice_nota_credito_detail_cost_center 
FOREIGN KEY ( cost_center_id ) REFERENCES costs_center( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE invoice_nota_credito_detail MODIFY parent_id int UNSIGNED NOT NULL; 

ALTER TABLE invoice_nota_credito ALTER COLUMN status SET DEFAULT 'Edicion';

ALTER TABLE invoice_nota_credito_detail ADD CONSTRAINT fk_invoice_nota_credito_detail_nota_credito FOREIGN KEY ( parent_id )
REFERENCES invoice_nota_credito( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE invoice_nota_credito_detail ADD inventario_id int;

ALTER TABLE invoice_nota_credito_detail ADD CONSTRAINT fk_invoice_nota_credito_detail_inventario FOREIGN KEY ( inventario_id ) 
REFERENCES inventario( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE invoice_nota_credito_detail ADD invoice_detail_id int UNSIGNED NOT NULL;

ALTER TABLE invoice_nota_credito_detail ADD discount_porcentage double DEFAULT 0;

ALTER TABLE invoice_nota_credito_detail ADD discount_amount double NULL DEFAULT 0;














 
