CREATE TABLE ratings (
userid varchar(255),
bookid varchar(255),
rating NUMERIC(1,1),
bookcomments VarChar,
createdDateTime timestamp default current_timestamp,
PRIMARY KEY (userid, bookid)
);

