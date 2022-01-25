create database contactsappdb;

use contactsappdb;

-- Users table
CREATE TABLE user (
	uid int AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    maxcid int
);

-- Contacts Table
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

CREATE INDEX name ON contact(name);
CREATE INDEX email ON contact(email);

-- Deleted Contacts table for Bin
CREATE TABLE deleted_contact (
	cid int,
	uid int,
    address VARCHAR(255),
    email VARCHAR(255),
    expirydate DATETIME,
    name VARCHAR(255),
    phone VARCHAR(255),
    score int,
    fav bit(1),
    PRIMARY KEY(uid, cid)
);
CREATE INDEX expiry_date ON deleted_contact(expirydate DESC);

-- Password Reset Token Table
CREATE TABLE password_reset_token (
	uid int PRIMARY KEY,
    expirydate DATETIME,
    token VARCHAR(255)
);

-- Misc queries
select * from user;
select * from contact;
select * from password_reset_token;
select * from deleted_contact;
delete from contact where uid = 47;
delete from user where uid = 51;
alter table password_reset_token drop column expirty_date;