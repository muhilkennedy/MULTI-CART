/*This table is for storing file location only*/
create table if not exists filestore (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, blobinfo blob, mediaurl varchar(1024), storetype varchar(64), extension varchar(64), aclrestricted BOOL DEFAULT FALSE, clientfile BOOL DEFAULT FALSE, size bigint, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0);
/*This table stores all site side configurations (encrypted)*/
create table if not exists configstore (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, name varchar(512) NOT NULL UNIQUE, val blob NOT NULL, configtype varchar(64) NOT NULL, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0,  UNIQUE KEY `con_config` (`tenantid`,`name`, `configtype`));
/*These table stores email/sms templates*/
create table if not exists emailtemplate (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, name varchar(64), filestoreid bigint, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, CONSTRAINT FOREIGN KEY(filestoreid) REFERENCES filestore(rootid));
create table if not exists smstemplate (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, name varchar(64), filestoreid bigint, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, CONSTRAINT FOREIGN KEY(filestoreid) REFERENCES filestore(rootid));
/*Common Audit table*/
create table if not exists audit(rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, auditid varchar(64) UNIQUE, message varchar(2048), operation varchar(32));