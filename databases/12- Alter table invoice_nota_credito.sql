ALTER TABLE invoice_nota_credito MODIFY status enum('Emitida','Anulada', 'Edicion', 'Pendiente' ) NOT NULL DEFAULT 'Emitida';

ALTER TABLE invoice_nota_credito ADD ingresado_bodega boolean NOT NULL DEFAULT 0; 

ALTER TABLE invoice_nota_credito ADD discount_total double DEFAULT 0;








