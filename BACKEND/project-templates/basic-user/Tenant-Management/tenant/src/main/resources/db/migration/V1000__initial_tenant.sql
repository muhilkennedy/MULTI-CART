/* BASE TRANSACTION TABLES */
create table if not exists tenant (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, locale varchar(12) default 'en_US', timezone varchar(64), name varchar(50) NOT NULL, uniquename varchar(50) NOT NULL UNIQUE, parent bigint);
create table if not exists tenantdetails (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, contact varchar(15), emailid varchar(128), street varchar(512), city varchar(124), pincode varchar(20), details JSON, businessemail varchar(128), businessemailpassword varchar(512), tagline varchar(1024), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), UNIQUE KEY `con_tenant_email` (`tenantid`,`emailid`));


/*Initial data load*/
insert into tenant (rootid, name, uniquename ) values (1, "Dev Tenant", "devTenant");
insert into tenant (rootid, name, uniquename ) values (2, "Prod Tenant", "prodTenant");

insert into tenantdetails (rootid, tenantid, contact, emailid, street, city, pincode, tagline, businessemail ) values (1, 1, "1234567890", "noreplyeventemail@gmail.com", "street", "mpm", "603104", "Vazhka oru vattam da!", "noreplyeventemail@gmail.com");