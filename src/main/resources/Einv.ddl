DROP TABLE EinvUsers;
create table EinvUsers(
    ID BIGINT auto_increment,
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not NULL,
	createTime Timestamp NOT NULL,
	lastLoginTime Timestamp
);
DROP TABLE EinvAuthorities;
create table EinvAuthorities (
    ID BIGINT auto_increment,
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_einvauthorities_users foreign key(username) references EinvUsers(username)
);
create unique index ix_einvauth_username on EinvAuthorities (username,authority);

-- User user/pass
INSERT INTO EinvUsers (username, password, enabled, createTime)
  values ('rick',
    '$2a$10$N2GFZyeiJTRpDQr./8m52ern7hZ74f1k/QvPBfmZxf0RtcpE73pny',1, CURRENT_timestamp);
INSERT INTO EinvAuthorities (username, authority)
  values ('rick', 'ROLE_USER');

DROP TABLE params;
create table PARAMS(
    ID BIGINT auto_increment,
	P_K varchar(50) not null primary key,
	P_V varchar(200) not null,
	P_D varchar(500) ,
	createTime Timestamp NOT NULL
);


--drop table EINVDETIAL;
CREATE TABLE EINVDETIAL (
	ID BIGINT NOT NULL,
	AMOUNT DECIMAL(19,2),
	DESCRIPTION VARCHAR(255),
	QUANTITY DECIMAL(19,2),
	ROW_NUM INTEGER,
	UNIT_PRICE DECIMAL(19,2),
	TITLE_ID BIGINT NOT NULL,
	CONSTRAINT CONSTRAINT_3 PRIMARY KEY (ID)
);
CREATE INDEX EINV_DETIAL_INDEX1 ON EINVDETIAL (TITLE_ID);
CREATE UNIQUE INDEX PRIMARY_KEY_3 ON EINVDETIAL (ID);


-- PUBLIC.EINVDETIAL foreign keys

ALTER TABLE EINVDETIAL ADD CONSTRAINT FK7AF2EP9EPM5SV5N2EC1KMU9P1 FOREIGN KEY (TITLE_ID) REFERENCES PUBLIC.EINVTITLE(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

--drop table EINVTITLE;
CREATE TABLE EINVTITLE (
	ID BIGINT NOT NULL,
	AMOUNT DECIMAL(19,2),
	BUYER_BAN VARCHAR(255),
	CARD_NO VARCHAR(255),
	CARD_TYPE VARCHAR(255),
	CURRENCY VARCHAR(255),
	DONATE_MARK INTEGER NOT NULL,
	INV_DONATABLE BOOLEAN,
	INV_NUM VARCHAR(255),
	INV_PERIOD VARCHAR(255),
	INV_STATUS VARCHAR(255),
	INV_DATE DATE,
	INVOICE_TIME VARCHAR(255),
	ROW_NUM INTEGER,
	SELLER_ADDRESS VARCHAR(255),
	SELLER_BAN VARCHAR(255),
	SELLER_NAME VARCHAR(255),
	CONSTRAINT CONSTRAINT_4 PRIMARY KEY (ID)
);
CREATE INDEX EINV_TITLE_INDEX1 ON EINVTITLE (INV_NUM);
CREATE UNIQUE INDEX PRIMARY_KEY_4 ON EINVTITLE (ID);
CREATE UNIQUE INDEX UK_D6MTBYVFDE4FHH3M3JLRQI8HB_INDEX_4 ON EINVTITLE (INV_NUM);