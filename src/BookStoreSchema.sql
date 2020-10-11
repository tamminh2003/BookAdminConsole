Create database IF NOT EXISTS BOOKSTORE;
Use BOOKSTORE;


CREATE TABLE IF NOT EXISTS users (
   uid int NOT NULL AUTO_INCREMENT,
   lastname VARCHAR(30),
   firstname VARCHAR(30),
   username VARCHAR(30),
   pass VARCHAR(30),
   isadmin boolean,
   PRIMARY KEY (uid)
   );

INSERT INTO users (uid, lastname,firstname,username,pass, isadmin)  VALUES
(1, 'AdminFirst','AdminLast','Admin','pass1', true),
(2, 'UserFname', 'UserLname', 'user1', 'pass2', false);

 
 CREATE TABLE IF NOT EXISTS session (
 sessionid VARCHAR(32) PRIMARY KEY,
 username VARCHAR(30));

 
  CREATE TABLE IF NOT EXISTS book_category (
  cid int,
  categorytitle varchar(100) NOT NULL,
  PRIMARY KEY (cid)
  );
INSERT INTO book_category (cid, categorytitle) VALUES
  (1, 'Fantasy'),
  (2, 'Adventure'),
  (3, 'Romance'),
  (4, 'Academic');

 
CREATE TABLE IF NOT EXISTS books (
  bid int,
  cid int NOT NULL,
  booktitle varchar(100) NOT NULL,
  description varchar(250) NOT NULL,
  author varchar(100) NOT NULL,
  publisheddate timestamp NOT NULL,
  isbn char(12) unique,
  price decimal,
  noofpages int,
  FOREIGN KEY (cid) REFERENCES book_category(cid),
  PRIMARY KEY (bid));

INSERT INTO books (bid, booktitle,description, author, publisheddate, isbn,price,noofpages,cid) VALUES
  (1, 'Star Wars','My First  Book Description', 'Alexander Freed', '2020-07-02 12:08:17.320053-03', '987152912461',26.25,368,1),
  (2, 'My SQL Book','My  SQL Book Description' ,'John Mayer', '2016-07-03 09:22:45.050088-07', '85730092371',4.99,600,4),
  (3, 'My Second SQL Book','My SecondSQL Book', 'Cary Flint', '2015-10-18 14:05:44.547516-07', '52312096781',10.95,780,4);


CREATE TABLE IF NOT EXISTS comments (
  id int,
  bid integer NOT NULL,
  reviewername varchar(255),
  commentcontent varchar(255),
  commentdate timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (bid) REFERENCES books(bid) ON DELETE CASCADE
);

INSERT INTO comments (id, bid, reviewername, commentcontent, commentdate) VALUES
  (1, 1, 'John Smith', 'First review', '2020-08-10 05:50:11.127281-02'),
  (2, 1, 'Anonymous', 'Second review', '2020-08-13 15:05:12.673382-05'),
  (3, 2, 'Alice', 'Another review', '2017-10-22 23:47:10.407569-07');