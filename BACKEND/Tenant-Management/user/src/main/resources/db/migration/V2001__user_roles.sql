/*roles and permissions*/
create table if not exists role (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, rolename varchar(32) NOT NULL, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), UNIQUE KEY `con_role` (`tenantid`,`rolename`));
CREATE TABLE IF NOT EXISTS permission (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, permission varchar(32) UNIQUE);
create table if not exists rolepermission (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, roleid bigint, permissionid bigint, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), CONSTRAINT FOREIGN KEY(roleid) REFERENCES role(rootid), CONSTRAINT FOREIGN KEY(permissionid) REFERENCES permission(rootid));
create table if not exists employeerole (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, roleid bigint, employeeid bigint, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), CONSTRAINT FOREIGN KEY(roleid) REFERENCES role(rootid), CONSTRAINT FOREIGN KEY(employeeid) REFERENCES employee(rootid));

/*default data load*/
INSERT INTO permission (rootid, permission) values(19011996, "SuperUser");
INSERT INTO permission (rootid, permission) values(28072023, "Admin");
INSERT INTO permission (rootid, permission) values(22101999, "CustomerSupport");
INSERT INTO permission (rootid, permission) values(789, "ManageUsers");
INSERT INTO permission (rootid, permission) values(5099, "EditUsers");
INSERT INTO permission (rootid, permission) values(22, "EditOrders");
INSERT INTO permission (rootid, permission) values(218, "MangeOrders");
INSERT INTO permission (rootid, permission) values(18, "EditPromotions");
INSERT INTO permission (rootid, permission) values(9890, "ManageProducts");
INSERT INTO permission (rootid, permission) values(1064, "EditProducts");
INSERT INTO permission (rootid, permission) values(2012, "ManageCategories");
INSERT INTO permission (rootid, permission) values(28, "EditCategories");
INSERT INTO permission (rootid, permission) values(897, "PointOfSale");
INSERT INTO permission (rootid, permission) values(148, "ManageReporting");
INSERT INTO permission (rootid, permission) values(150, "ManagePromotions");
INSERT INTO permission (rootid, permission) values(710, "ManageCoupons");
INSERT INTO permission (rootid, permission) values(1765, "EditCoupons");


/*initial data load*/
INSERT INTO role (rootid, tenantid, rolename) values (-1, -1, "CustomerSupportAdmin");
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (-1, -1, -1, 19011996);
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (-2, -1, -1, 28072023);
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (-3, -1, -1, 22101999);
INSERT INTO employeerole (rootid, tenantid, roleid, employeeid) values (-1, -1, -1, -1);