ALTER TABLE invoice ADD salida_bodega boolean NOT NULL DEFAULT 0;

update invoice set salida_bodega = true where status = 'Pagada';

ALTER TABLE invoice ADD discount_total double DEFAULT 0;





 