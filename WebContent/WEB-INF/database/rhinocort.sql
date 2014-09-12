drop table tbl_userinfo;
create table tbl_userinfo(
	id				int NOT NULL primary key auto_increment,
	name			varchar(255),
	userCode		varchar(20),
	telephone		varchar(20),
	email			varchar(100),
	region			varchar(20),
	regionCenter	varchar(20),
	level			varchar(20),
	superior		varchar(20),
	createdate 		datetime,
	modifydate		datetime
);

drop table tbl_hospital;
create table tbl_hospital(
	id				    int NOT NULL primary key auto_increment,
	name			    varchar(1000),
	code			    varchar(20),
	city			    varchar(20),
	province		    varchar(20),
	region			    varchar(20),
	regionCenter		varchar(20),
	dsmCode             varchar(20),
	dsmName             varchar(255),
	saleCode            varchar(20),
	saleName            varchar(200),
	level			    varchar(10),
	isKPI				varchar(1)
);

drop table tbl_rhinocort_data;
create table tbl_rhinocort_data(
	id				int NOT NULL primary key auto_increment,
	hospitalCode	varchar(100),
	num1			int,
	num2			int,
	num3			int,
	num4			int,
	num5			int,
	operatorName	varchar(20),
    dsmCode         varchar(20),
	createdate		datetime,
	updatedate		datetime
);

drop table tbl_hos_user;
create table tbl_hos_user(
    hosCode           varchar(20),
    userCode          varchar(20),
    isPrimary         char(1)
);

 ALTER  TABLE tbl_hos_user ADD INDEX INDEX_HOSUSER_USERCODE (userCode);
 ALTER  TABLE tbl_hos_user ADD INDEX INDEX_HOSUSER_HOSCODE (hosCode);
 ALTER  TABLE tbl_userinfo ADD INDEX INDEX_USERINFO_USERCODE (userCode);
 ALTER  TABLE tbl_hospital ADD INDEX INDEX_HOSPITAL_CODE (code);
 ALTER  TABLE tbl_rhinocort_data ADD INDEX INDEX_RHIDATA_HOSCODE (hospitalCode);
 ALTER  TABLE tbl_rhinocort_data ADD INDEX INDEX_RHIDATA_CREATEDATE (createdate);
 
show index from tbl_hos_user;
show index from tbl_userinfo;
show index from tbl_hospital;
show index from tbl_rhinocort_data;


insert into tbl_userinfo values(null,'张宇',null,'18501622299',null,null,null,'Sarton',null,now(),now());
insert into tbl_userinfo values(null,'王磊','8210719','18502162223','Leon.wang@Astrazeneca.com',null,null,'Bu Head',null,now(),now());
insert into tbl_userinfo values(null,'陈晖','5200077','13701672272','Hui.Chen@astrazeneca.com',null,null,'MD',null,now(),now());
insert into tbl_userinfo values(null,'陈芩暘',null,'15618443615',null,null,null,'PM',null,now(),now());
insert into tbl_userinfo values(null,'张磊',null,'13601840450',null,null,null,'Sarton',null,now(),now());
insert into tbl_userinfo values(null,'顾炯亮',null,'13801856666',null,null,null,'Sarton',null,now(),now());
insert into tbl_userinfo values(null,'胡彬',null,'18001665445',null,null,null,'Sarton',null,now(),now());
insert into tbl_userinfo values(null,'吴延林',null,'13761330223',null,null,null,'Sarton',null,now(),now());
insert into tbl_userinfo values(null,'胡彦斌',null,'18616022662',null,null,null,'Sarton',null,now(),now());
insert into tbl_userinfo values(null,'吴晓岚',null,'13788905258',null,null,null,'Sarton',null,now(),now());


alter table tbl_rhinocort_data add num6 int;
alter table tbl_rhinocort_data add num7 int;
