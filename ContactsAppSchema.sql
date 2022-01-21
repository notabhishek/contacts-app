create database contactsappdb;

use contactsappdb;

CREATE TABLE user (
	uid int AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    email VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    maxcid int
);

CREATE TABLE contact (
	uid int,
	cid int,
    address VARCHAR(255),
    email VARCHAR(255),
    name VARCHAR(255),
    phone VARCHAR(255),
    score int,
    fav bit(1),
    PRIMARY KEY(uid, cid)
);

select * from user;
select * from contact;