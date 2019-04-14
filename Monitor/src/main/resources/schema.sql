CREATE TABLE employee
(
 employeeName varchar(100) NOT NULL,
  employeeId varchar(11) NOT NULL ,
 employeeAddress varchar(100) DEFAULT NULL,
 employeeEmail varchar(100) DEFAULT NULL,
 PRIMARY KEY (employeeId)
);


CREATE TABLE hosts
(
  hostId varchar(40) not null,
  os varchar(255),
  monitorId varchar(40) not null,
  PRIMARY KEY (hostId)
);

CREATE TABLE metrics
(
  metricId varchar(40) not null,
  type varchar(40) not null,
  unit varchar(40) not null,
  hostId varchar(40) not null,
  userId varchar(40),
  monitorId varchar(40) not null,
  
  PRIMARY KEY (metricId)
);

CREATE TABLE measurements
(
  metricId varchar(40) not null,
  val varchar(48) not null,
  ts timestamp not null
);