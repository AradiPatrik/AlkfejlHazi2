create table Customer
(
  id integer PRIMARY KEY AUTOINCREMENT,
  name text NOT NULL,
  age integer,
  female integer,
  rented integer,
  student integer,
  grantee integer,
  qualification text,
  UNIQUE (name)
);

create table Book
(
  id integer PRIMARY KEY AUTOINCREMENT,
  author text NOT NULL,
  title text NOT NULL,
  year integer NOT NULL,
  category text NOT NULL,
  price integer NOT NULL,
  pieces integer NOT NULL,
  ancient integer NOT NULL,
  UNIQUE (title)
);

create table SoldBookInstances
(
  id integer PRIMARY KEY AUTOINCREMENT,
  id_book integer,
  id_customer integer,
  sellDate timestamp,
  FOREIGN KEY (id_book) REFERENCES Book(id),
  FOREIGN KEY (id_customer) REFERENCES Customer(id),
  UNIQUE (id_book, id_customer)
);
