/*roles and permissions*/
create table if not exists role (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, rolename varchar(32) NOT NULL, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), UNIQUE KEY `con_role` (`tenantid`,`rolename`));
CREATE TABLE IF NOT EXISTS permission (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, permission varchar(32) UNIQUE);
create table if not exists rolepermission (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, roleid bigint, permissionid bigint, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), CONSTRAINT FOREIGN KEY(roleid) REFERENCES role(rootid), CONSTRAINT FOREIGN KEY(permissionid) REFERENCES permission(rootid));
create table if not exists employeerole (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, roleid bigint, employeeid bigint, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), CONSTRAINT FOREIGN KEY(roleid) REFERENCES role(rootid), CONSTRAINT FOREIGN KEY(employeeid) REFERENCES employee(rootid));

/*default data load*/
INSERT INTO permission (rootid, permission) values(1, "SuperUser");
INSERT INTO permission (rootid, permission) values(2, "Admin");
INSERT INTO permission (rootid, permission) values(3, "CustomerSupport");
INSERT INTO permission (rootid, permission) values(4, "ManageUsers");
INSERT INTO permission (rootid, permission) values(5, "EditUsers");
INSERT INTO permission (rootid, permission) values(6, "EditOrders");
INSERT INTO permission (rootid, permission) values(7, "MangeOrders");
INSERT INTO permission (rootid, permission) values(8, "EditPromotions");
INSERT INTO permission (rootid, permission) values(9, "ManageProducts");
INSERT INTO permission (rootid, permission) values(10, "EditProducts");
INSERT INTO permission (rootid, permission) values(11, "ManageCategories");
INSERT INTO permission (rootid, permission) values(12, "EditCategories");
INSERT INTO permission (rootid, permission) values(13, "PointOfSale");
INSERT INTO permission (rootid, permission) values(14, "ManageReporting");
INSERT INTO permission (rootid, permission) values(15, "ManagePromotions");
INSERT INTO permission (rootid, permission) values(16, "ManageCoupons");
INSERT INTO permission (rootid, permission) values(17, "EditCoupons");


/*initial data load*/
INSERT INTO role (rootid, tenantid, rolename) values (1, 1, "CustomerSupportAdmin");
INSERT INTO role (rootid, tenantid, rolename) values (2, 1, "Admin");
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (1, 1, 1, 1);
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (2, 1, 1, 2);
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (3, 1, 1, 3);
INSERT INTO employeerole (rootid, tenantid, roleid, employeeid) values (1, 1, 1, 1);