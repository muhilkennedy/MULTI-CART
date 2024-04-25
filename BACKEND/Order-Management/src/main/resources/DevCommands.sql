set SQL_SAFE_UPDATES=0;

show tables;
delete from employee where emailid='muhilkennedy@gmail.com';


create database tenantmanagement;
drop database tenantmanagement;

use tenantmanagement;
show tables;
select * from jobrunr_recurring_jobs;

select * from tenant;
select * from tenantdetails;
select * from tenantsubscription;
select * from employee limit 0, 5000;
select count(*) from employee;
select * from employeeinfo; 
select * from permission;
select count(*) from configtype;
select * from emailtemplate;
select * from filestore;
select * from employeerole;

insert into tenantsubscription (rootid, tenantid, active, timecreated, timeupdated, startdate, enddate) values (-1, -1, 1, 1, 1, '2023-01-23',  '2050-01-23');

select * from tenant_seq;

select * from TenantSubscription ;
select * from employee;

SELECT * FROM TenantSubscription WHERE startdate >= '2023-09-30';


delete from employee where rootid!=1;

select * from employeerole er;
select * from employee e join employeerole er on e.rootid=er.employeeid inner join role r on er.roleid=r.rootid inner join rolepermission rp on r.rootid=rp.roleid inner join permission p on p.rootid=rp.permissionid where p.permission='admin' and e.tenantid=2;

select e1_0 from employee e1_0 join employeerole e2_0 on e1_0.rootid=e2_0.employeeid join role r1_0 on e2_0.roleid=r1_0.rootid join rolepermission r2_0 on r1_0.rootid=r2_0.roleid join permission p1_0 on p1_0.rootid=r2_0.permissionid where e1_0.tenantid = 2 and p1_0.permission='admin';

select * from role;
select * from rolepermission;
select p.* from Permission p inner join rolepermission rp on p.rootid=rp.permissionid inner join role r on r.rootid=rp.roleid where r.rootid=1;


INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (5, 2, 2, 2);
INSERT INTO rolepermission (rootid, tenantid, roleid, permissionid) values (6, 2, 2, 3);
INSERT INTO employeerole (rootid, tenantid, roleid, employeeid) values (2, 2, 2, 2);

select e1_0.rootid,e1_0.active,e1_0.createdby,e1_0.designation,e1_0.emailid,e1_0.fname,e1_0.lname,e1_0.locale,e1_0.mobile,e1_0.mobilehash,e1_0.modifiedby,e1_0.password,e1_0.reportsto,e1_0.tenantid,e1_0.timecreated,e1_0.timeupdated,e1_0.timezone,e1_0.uniquename,e1_0.version from employee e1_0 where e1_0.tenantid = -1 limit 0,5;


select * from QRTZ_JOB_DETAILS;
select * from QRTZ_FIRED_TRIGGERS;
select * from QRTZ_BLOB_TRIGGERS;
select * from QRTZ_CRON_TRIGGERS;
select * from QRTZ_SIMPLE_TRIGGERS;
select * from QRTZ_SIMPROP_TRIGGERS;
select * from QRTZ_SCHEDULER_STATE;
select * from QRTZ_TRIGGERS;
select * from audit;
show tables;
select * from quartzjobinfo;

select * from task;
select * from taskassignee;

select * from task t inner join taskassignee as ts on ts.taskid = t.rootid where ts.assigneeid=-1;
