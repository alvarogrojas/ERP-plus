ALTER TABLE client ADD provincia_cr_id int;
ALTER TABLE client ADD CONSTRAINT fk_client_provincia_cr FOREIGN KEY ( provincia_cr_id ) REFERENCES provincia_cr( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE client ADD canton_cr_id int;
ALTER TABLE client ADD CONSTRAINT fk_client_canton_cr FOREIGN KEY ( canton_cr_id ) REFERENCES canton_cr( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE client ADD distrito_cr_id int;
ALTER TABLE client ADD CONSTRAINT fk_client_distrito_cr FOREIGN KEY ( distrito_cr_id ) REFERENCES distrito_cr( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE client MODIFY address varchar(164) NULL; 



  

 