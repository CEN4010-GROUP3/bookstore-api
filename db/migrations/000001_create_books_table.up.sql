
CREATE TABLE books (
  isbn varchar(14),
  name varchar(100),
  description text,
  author varchar(100),
  genre varchar(50),
  publisher varchar(100),
  year_published int,
  copies_sold int,
  price NUMERIC(10,2),
PRIMARY KEY (isbn)
);

