

CREATE TABLE IF NOT EXISTS TAGS(
	id integer auto_increment,
	tag VARCHAR(50),
    UNIQUE tag_u (tag),
    index tag_index (tag, id),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS USERS (
	id integer auto_increment,
	uu_id VARCHAR(40),
	date datetime,
    index user_index(id),
	PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS USER_TAG_RELATION(
	user integer,
	tag integer,
	UNIQUE usertag_u (tag, user),
    index usertag_index (tag, user),
	FOREIGN KEY (user) REFERENCES Users(id),
	FOREIGN KEY (tag) REFERENCES Tags(id)
);


/*
CREATE TABLE IF NOT EXISTS Requests(
	id VARCHAR(40),
	user VARCHAR(40),
	content TEXT,
	PRIMARY KEY(id),
    date timestamp default current_timestamp,
	FOREIGN KEY (user) REFERENCES Users(id) 
); 

CREATE TABLE IF NOT EXISTS RequestTagRelation(
	request VARCHAR(40),
	tag VARCHAR(50),
	PRIMARY KEY (request, tag),
	FOREIGN KEY (request) REFERENCES Requests(id),
	FOREIGN KEY (tag) REFERENCES Tags(tag)
);

CREATE TABLE IF NOT EXISTS Responses(
	id VARCHAR(40),
	user VARCHAR(40),
	request VARCHAR(40),
	date timestamp default current_timestamp,
	content TEXT,
	PRIMARY KEY(id),
	FOREIGN KEY (user) REFERENCES Users(id),
	FOREIGN KEY (request) REFERENCES Requests(id)
);


CREATE TABLE IF NOT EXISTS VerifiedBy(
	user VARCHAR(40),
	request	VARCHAR(40),
	PRIMARY KEY (user, request),
	FOREIGN KEY (user) REFERENCES Users(id),
	FOREIGN KEY (request) REFERENCES Requests(id)
	
);
*/
/*
	***********************************  extended database **********************************
*/

/*
CREATE TABLE IF NOT EXISTS Images(
	id VARCHAR(40),
	image BLOB, 
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS RegUsers (
	id VARCHAR(40),
	email VARCHAR(100) unique,
	username VARCHAR(25) unique,
	password varchar(25),
	user_key VARCHAR(40),
	date timestamp default current_timestamp,
    FOREIGN KEY (id) REFERENCES Users(id),
	PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS UserImages(
	user VARCHAR(40),
	image VARCHAR(40),
	PRIMARY KEY (user),
	FOREIGN KEY (user) REFERENCES Users(id),
	FOREIGN KEY (image) REFERENCES Images(id)
);
*/