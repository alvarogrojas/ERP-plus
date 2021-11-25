# DATABASES

 
 
 
 
 
 drop table general_parameter;
 CREATE TABLE `general_parameter` (
   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
   `idx` int(11) DEFAULT '1',
   `code` varchar(12) NOT NULL,
   `val` varchar(128) NOT NULL,
   `name` varchar(32) NOT NULL,
   `status` varchar(12) NOT NULL,
   `description` varchar(400) DEFAULT '',
   `create_date` date DEFAULT NULL,
   `id_user` int(11) unsigned DEFAULT NULL,
   `update_date` date DEFAULT NULL,
   `type` enum('STRING','INTEGER') DEFAULT 'STRING',
   `int_val` int(11) DEFAULT '0',
   PRIMARY KEY (`id`),
   UNIQUE KEY `general_parametes_id_uindex` (`id`),
   KEY `general_parametes_code_index` (`code`) USING BTREE
 ) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
 insert into general_parameter select * from centrocostoserp.general_parameter;
 
 
 
    alter table user add column display_name varchar(120) default null;
    alter table user add column address varchar(120) default null;
    alter table user add column phone varchar(32) default null;
    update user a, article d set a.display_name = d.description, a.address=d.location, a.phone=d.brand   where d.cost_center_id=9 and a.employee_id=d.id;
 
 alter table user_role add column id integer(5) auto_increment UNIQUE FIRST;
 SET @id=0;
 CREATE TEMPORARY TABLE id_temp
 AS
 (SELECT user_id, role_id, @id:=@id+1 AS id FROM user_role ORDER BY role_id ASC);
 
 #UPDATE user_role a, id_temp b
 #SET a.id = b.id
 #WHERE a.role_id = b.role_id and a.user_id=b.user_id;
 
 drop table id_temp;
 alter table permission_detail add column id integer(5);
 
 SET @id=0;
 CREATE TEMPORARY TABLE id_temp
 AS
 (SELECT role_id, permission_id, @id:=@id+1 AS id FROM permission_detail ORDER BY role_id ASC);
 
 UPDATE permission_detail a, id_temp b
 SET a.id = b.id
 WHERE a.role_id = b.role_id and a.permission_id=b.permission_id;
 
 drop table id_temp;
 
 
 alter table costs_center change column createdDate created_date datetime;
 
  alter table costs_center change column lastUpdatedDate last_updated_date datetime;
 
  alter table user add column name varchar(70) default null;
 
  alter table user add column username varchar(70) default null;
 
 
  update user set username=email;
 
  update user set name=username;
 
 # https://www.browserling.com/tools/bcrypt #
 
 update user set password="$2a$10$kWp7SkYWDQCnN0EuU08YM.4ZOD4FZWQ4IPia7JWgmk2bBUptISeh2";
 
 ################
 FIX CUENTAS POR COBRAR ISSUE CON EXONERACIONES
 
 
 alter table bill_collect add column `exonerated` double DEFAULT '0';
 alter table bill_collect_detail add column `exonerated` double DEFAULT '0';
 
 
 alter table client change address address varchar(80) default null;
 
 
 
 CREATE INDEX labort_date_detail_index ON labor_cost_detail(labor_date);
 
 UPDATE bill_collect
    SET tax=(SELECT iv FROM invoice WHERE bill_collect.bill_number=invoice.number and invoice.exonerated is not null 
 and invoice.exonerated>0);
 
 UPDATE bill_collect
    SET exonerated=(SELECT exonerated FROM invoice WHERE bill_collect.bill_number=invoice.number and invoice.exonerated is not null 
 and invoice.exonerated>0);
 
 UPDATE bill_collect_detail
    SET exonerated=tax, tax=0, total=sub_total where bill_collect_detail.bill_collect_id in 
 	(select id from bill_collect where 
 			bill_collect.exonerated is not null 
 				and bill_collect.exonerated>0);
 
 alter table user add column status enum('Activo','Inactivo','Borrado') NOT NULL DEFAULT 'Activo';
 
 CREATE INDEX invoice_total_index ON invoice(total);
 CREATE INDEX invoice_date_index ON invoice(date);
 update user set password="$2a$10$vBbaSDnZ6PFg7FPqILMSDOozi7JW.7bnMb6vWrAsI3KNdReJJ4T/q" where id!=1;
 
 alter table contact_client add column status varchar(64) default 'Activo';
 
 CREATE INDEX pendiente_estado_index ON pendiente_mensaje_hacienda(estado);
 ```