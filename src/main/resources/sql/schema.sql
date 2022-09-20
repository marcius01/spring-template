CREATE TABLE userEntity (
	id int8 NOT NULL,
	username varchar(255) NULL,
	"password" varchar(255) NULL,
	roles text NULL,
	CONSTRAINT userentity_pkey PRIMARY KEY (id),
	CONSTRAINT userentity_username_key UNIQUE (username)
);

CREATE TABLE hibernate_sequences (
	sequence_name varchar NOT NULL,
	next_val int4 NOT NULL,
	sequence_next_hi_value int8 NULL
);
