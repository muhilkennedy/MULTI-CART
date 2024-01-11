/* Base Supplier tables*/
create table if not exists supplier (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, uniquename varchar(64) NOT NULL, name varchar(128) NOT NULL, description varchar(255), emailid varchar(50) NOT NULL, contact varchar(15) NOT NULL, secondarycontact varchar(15));
/*create table if not exists supplierinfo (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0);*/