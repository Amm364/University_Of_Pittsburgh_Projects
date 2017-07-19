-- Alex Mitro
-- Amm364@pitt.edu

DROP TABLE Claim CASCADE CONSTRAINTS;
DROP TABLE Customers CASCADE CONSTRAINTS;
DROP TABLE Policy CASCADE CONSTRAINTS;
DROP TABLE Car CASCADE CONSTRAINTS;



CREATE TABLE Claim(
	damage		varchar(32),
	made_date	varchar(6) PRIMARY KEY,
	ssn		number(9),
	VIN_num		number(10)
);

CREATE TABLE Customers(
	ssn		number(9) PRIMARY KEY,
	fname		varchar(32),
	mname		varchar(32),
	lname		varchar(32),
	DL_num		number(8),
	state		varchar(32),
	made_date	varchar(8),
	PID		number(10)
);

CREATE TABLE Policy(
	PID 		number(10) PRIMARY KEY,
	deductible	number(10),
	VIN_num		number(10),
	ssn		number(9)
);

CREATE TABLE Car(
	VIN_num         number(10) PRIMARY KEY,
	make		varchar(32),
	color		varchar(20),
	year		number(4),
	model		varchar(32),
	made_date	varchar(8),
	PID		number(10)
);

ALTER TABLE Claim
ADD FOREIGN KEY (ssn)
REFERENCES Customers(ssn);

ALTER TABLE Claim
ADD FOREIGN KEY (VIN_num)
REFERENCES Car(VIN_num);

ALTER TABLE Car
ADD FOREIGN KEY (made_date)
REFERENCES Claim(made_date);

ALTER TABLE Car
ADD FOREIGN KEY (PID)
REFERENCES Policy(PID);

ALTER TABLE Policy
ADD FOREIGN KEY (ssn)
REFERENCES Customers(ssn);

ALTER TABLE Policy
ADD FOREIGN KEY (VIN_num)
REFERENCES Car(VIN_num);

ALTER TABLE Customers
ADD FOREIGN KEY (made_date)
REFERENCES Claim(made_date);

ALTER TABLE Customers
ADD FOREIGN KEY (PID)
REFERENCES Policy(PID);
