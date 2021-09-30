/* Use Case 1 : Create addressbook_service DB */
CREATE DATABASE addressbook_service;
SHOW DATABASES;
USE addressbook_service;

/* Use Case 2 : Create address book table */
CREATE TABLE addressbook (firstName VARCHAR(150) NOT NULL, 
			lastName VARCHAR(150) NOT NULL, 
			phoneNumber BIGINT UNSIGNED NOT NULL , 
			email VARCHAR(150) NOT NULL, 
			city VARCHAR(150) NOT NULL, 
			state VARCHAR(150) NOT NULL,
			zip BIGINT UNSIGNED NOT NULL, 
			PRIMARY KEY(firstName));
DESCRIBE addressbook;

/* Use Case 3 : Insert values into addressbook table */
INSERT INTO addressbook VALUES ('Jake', 'Peralta', 9481448524, 'jake@gmail.com', 'Brooklyn', 'New York', 436648),
				('Rosa','Diaz',9273648576,'rosa@gmail.com','Albany', 'New York', 538827),
				('Scully','Hitchcock', 9374991722,'scully@hitchcock.com','Sacaramento', 'California',730485),
				('Ashika','Chadaga',8392483756,'ashika@gmail.com','Bangalore','Karnataka',630284),
				('Izuku','Midoriya',8372946583,'izuku@gmail.com','Kodagu', 'Karnataka',736457),
				('Gon','Freecss','7495860284','gon@gmail.com','Mangalore','Karnataka',749273),
				('Sagarika','Satish',7465927465,'sagarika@gmail.com','Bangalore','Karnataka',563829);
SELECT * FROM addressbook;

/* Use Case 4 : Edit existing contact in the table using First Name */
UPDATE addressbook SET lastName = 'Chadaga Satish' WHERE firstName = 'Ashika';
UPDATE addressbook SET phoneNumber = '8475628374' WHERE firstName = 'Ashika';
UPDATE addressbook SET email = 'ashikacs@gmail.com' WHERE firstName = 'Ashika';
UPDATE addressbook SET city = 'Kodagu' WHERE firstName = 'Ashika';
UPDATE addressbook SET state = 'Kerala' WHERE firstName = 'Ashika';
UPDATE addressbook SET zip = '560043' WHERE firstName = 'Ashika';

/* Use Case 5 : Delete a Person Using Person's name */
DELETE FROM addressbook WHERE firstName = 'Sagarika';
SELECT * FROM addressbook;

/* Use Case 6 : Retrieve person details belonging to city or state */
SELECT * FROM addressbook WHERE city = 'Kodagu' OR state = 'New York';
SELECT * FROM addressbook WHERE city = 'Mangalore';
SELECT * FROM addressbook WHERE state = 'New York';

/* Use Case 7 : Size of address book by city and state*/ 
SELECT city, COUNT(city) AS COUNT_BY_CITY FROM addressbook GROUP BY city;
SELECT state, COUNT(state) As COUNT_BY_STATE FROM addressbook GROUP BY state;

/* Use Case 8 : Sort by name for a given city*/
SELECT * FROM addressbook where city = 'Kodagu' ORDER BY firstName;

/* Use Case 9 : Alter table to add addressBookName and Contact type column*/
ALTER TABLE addressbook ADD addressBookName VARCHAR(150) NOT NULL;
ALTER TABLE addressbook ADD contactType VARCHAR(150) NOT NULL;
DESCRIBE addressbook;

UPDATE addressbook SET addressBookName = 'Book1', contactType = 'Family' WHERE firstName = 'Ashika';
UPDATE addressbook SET addressBookName = 'Book1', contactType = 'Friend' WHERE firstName = 'Gon';
UPDATE addressbook SET addressBookName = 'Book1', contactType = 'Profession' WHERE firstName = 'Izuku';
UPDATE addressbook SET addressBookName = 'Book2', contactType = 'Family' WHERE firstName = 'Jake';
UPDATE addressbook SET addressBookName = 'Book2', contactType = 'Friend' WHERE firstName = 'Rosa';
UPDATE addressbook SET addressBookName = 'Book2', contactType = 'Profession' WHERE firstName = 'Scully';

/* Use Case 10 : Count number of contacts by type*/
SELECT contactType, COUNT(city) AS COUNT_BY_CONTACT_TYPE FROM addressbook GROUP BY contactType;

/* Use Case 11 : Add person to friend nad family*/
INSERT INTO addressbook VALUES ('Katsuki', 'Bakugo', 6475968773, 'katsuki@gmail.com', 'Akita', 'Tokyo', 569937, 'book1', 'family,friend');



/* --------------------------------------------------------------------------- ER DIAGRAM IMPLEMENTATION ----------------------------------------------------------------------------------------------------- */


/* USE CASE 13 */

CREATE TABLE address_book( addressbook_id INT NOT NULL, addressbook_name VARCHAR(50) NOT NULL, PRIMARY KEY (addressbook_id));

/*
mysql> DESCRIBE address_book;
+------------------+-------------+------+-----+---------+-------+
| Field            | Type        | Null | Key | Default | Extra |
+------------------+-------------+------+-----+---------+-------+
| addressbook_id   | int(11)     | NO   | PRI | NULL    |       |
| addressbook_name | varchar(50) | NO   |     | NULL    |       |
+------------------+-------------+------+-----+---------+-------+
2 rows in set (0.01 sec)
*/

CREATE TABLE addressbook_type( addressbook_id INT NOT NULL, type_name VARCHAR(50) NOT NULL, PRIMARY KEY (addressbook_id, type_name), FOREIGN KEY (addressbook_id) REFERENCES address_book(addressbook_id));

/*
mysql> DESCRIBE addressbook_type;
+----------------+-------------+------+-----+---------+-------+
| Field          | Type        | Null | Key | Default | Extra |
+----------------+-------------+------+-----+---------+-------+
| addressbook_id | int(11)     | NO   | PRI | NULL    |       |
| type_name      | varchar(50) | NO   | PRI | NULL    |       |
+----------------+-------------+------+-----+---------+-------+
2 rows in set (0.00 sec)
*/

CREATE TABLE address(address_id INT NOT NULL, city VARCHAR(50) NOT NULL, state VARCHAR(50) NOT NULL, zip BIGINT NOT NULL, PRIMARY KEY (address_id));

/*
mysql> DESCRIBE address;
+------------+-------------+------+-----+---------+-------+
| Field      | Type        | Null | Key | Default | Extra |
+------------+-------------+------+-----+---------+-------+
| address_id | int(11)     | NO   | PRI | NULL    |       |
| city       | varchar(50) | NO   |     | NULL    |       |
| state      | varchar(50) | NO   |     | NULL    |       |
| zip        | bigint(20)  | NO   |     | NULL    |       |
+------------+-------------+------+-----+---------+-------+
4 rows in set (0.00 sec)
*/

CREATE TABLE contact(contact_id INT NOT NULL, first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, phone_number BIGINT NOT NULL, email VARCHAR(50) NOT NULL, address_id INT NOT NULL, PRIMARY KEY (contact_id), FOREIGN KEY(address_id) REFERENCES address(address_id));

/*
mysql> DESCRIBE contact;
+--------------+-------------+------+-----+---------+-------+
| Field        | Type        | Null | Key | Default | Extra |
+--------------+-------------+------+-----+---------+-------+
| contact_id   | int(11)     | NO   | PRI | NULL    |       |
| first_name   | varchar(50) | NO   |     | NULL    |       |
| last_name    | varchar(50) | NO   |     | NULL    |       |
| phone_number | bigint(20)  | NO   |     | NULL    |       |
| email        | varchar(50) | NO   |     | NULL    |       |
| address_id   | int(11)     | NO   | MUL | NULL    |       |
+--------------+-------------+------+-----+---------+-------+
6 rows in set (0.00 sec)
*/


CREATE TABLE addressbook_contact( contact_id INT NOT NULL, addressbook_id INT NOT NULL, PRIMARY KEY(contact_id, addressbook_id), FOREIGN KEY(contact_id) REFERENCES contact(contact_id), FOREIGN KEY (addressbook_id) REFERENCES address_book(addressbook_id));

/*
mysql> DESCRIBE addressbook_contact;
+----------------+---------+------+-----+---------+-------+
| Field          | Type    | Null | Key | Default | Extra |
+----------------+---------+------+-----+---------+-------+
| contact_id     | int(11) | NO   | PRI | NULL    |       |
| addressbook_id | int(11) | NO   | PRI | NULL    |       |
+----------------+---------+------+-----+---------+-------+
2 rows in set (0.00 sec)
*/


INSERT INTO address_book VALUES(1, 'addressBook1'),(2, 'addressBook2');

/*
mysql> SELECT * FROM address_book;
+----------------+------------------+
| addressbook_id | addressbook_name |
+----------------+------------------+
|              1 | addressBook1     |
|              2 | addressBook2     |
+----------------+------------------+
2 rows in set (0.00 sec)
*/


INSERT INTO addressbook_type VALUES (1,'Family'),(1,'Friend'),(1,'Profession'),(2,'Family'),(2,'Friend'),(2,'Profession');

/*
mysql> SELECT * FROM addressbook_type;
+----------------+------------+
| addressbook_id | type_name  |
+----------------+------------+
|              1 | Family     |
|              1 | Friend     |
|              1 | Profession |
|              2 | Family     |
|              2 | Friend     |
|              2 | Profession |
+----------------+------------+
6 rows in set (0.00 sec)
*/

INSERT INTO address VALUES (1,'Brooklyn', 'New York', 436648), (2,'Albany', 'New York', 538827), (3,'Sacaramento', 'California',730485), (4,'Bangalore','Karnataka',630284), (5,'Kodagu', 'Karnataka',736457), (6,'Mangalore','Karnataka',749273);

/*
mysql> SELECT * FROM address;
+------------+-------------+------------+--------+
| address_id | city        | state      | zip    |
+------------+-------------+------------+--------+
|          1 | Brooklyn    | New York   | 436648 |
|          2 | Albany      | New York   | 538827 |
|          3 | Sacaramento | California | 730485 |
|          4 | Bangalore   | Karnataka  | 630284 |
|          5 | Kodagu      | Karnataka  | 736457 |
|          6 | Mangalore   | Karnataka  | 749273 |
+------------+-------------+------------+--------+
6 rows in set (0.00 sec)
*/

INSERT INTO contact VALUES (1,'Jake', 'Peralta', 9481448524, 'jake@gmail.com',1), (2,'Rosa','Diaz',9273648576,'rosa@gmail.com',2), (3,'Scully','Hitchcock', 9374991722,'scully@hitchcock.com',3), (4,'Ashika','Chadaga',8392483756,'ashika@gmail.com', 4), (5,'Izuku','Midoriya',8372946583,'izuku@gmail.com',5), (6,'Gon','Freecss','7495860284','gon@gmail.com',6), (7,'Sagarika','Satish',7465927465,'sagarika@gmail.com', 4);

/*
mysql> SELECT * FROM contact;
+------------+------------+-----------+--------------+----------------------+------------+
| contact_id | first_name | last_name | phone_number | email                | address_id |
+------------+------------+-----------+--------------+----------------------+------------+
|          1 | Jake       | Peralta   |   9481448524 | jake@gmail.com       |          1 |
|          2 | Rosa       | Diaz      |   9273648576 | rosa@gmail.com       |          2 |
|          3 | Scully     | Hitchcock |   9374991722 | scully@hitchcock.com |          3 |
|          4 | Ashika     | Chadaga   |   8392483756 | ashika@gmail.com     |          4 |
|          5 | Izuku      | Midoriya  |   8372946583 | izuku@gmail.com      |          5 |
|          6 | Gon        | Freecss   |   7495860284 | gon@gmail.com        |          6 |
|          7 | Sagarika   | Satish    |   7465927465 | sagarika@gmail.com   |          4 |
+------------+------------+-----------+--------------+----------------------+------------+
7 rows in set (0.00 sec)
*/

INSERT INTO addressbook_contact VALUES(1,2),(2,1),(3,2),(4,1),(5,2),(6,1),(7,2);

/*
mysql> SELECT * FROM addressbook_contact;
+------------+----------------+
| contact_id | addressbook_id |
+------------+----------------+
|          2 |              1 |
|          4 |              1 |
|          6 |              1 |
|          1 |              2 |
|          3 |              2 |
|          5 |              2 |
|          7 |              2 |
+------------+----------------+
7 rows in set (0.00 sec)
*/


/* REPEATED USE CASE 6*/

SELECT * FROM contact JOIN address ON contact.address_id = address.address_id WHERE city = 'Kodagu' OR state = 'New York';

/*
mysql> SELECT * FROM contact JOIN address ON contact.address_id = address.address_id WHERE city = 'Kodagu' OR state = 'New York';
+------------+------------+-----------+--------------+-----------------+------------+------------+----------+-----------+--------+
| contact_id | first_name | last_name | phone_number | email           | address_id | address_id | city     | state     | zip    |
+------------+------------+-----------+--------------+-----------------+------------+------------+----------+-----------+--------+
|          1 | Jake       | Peralta   |   9481448524 | jake@gmail.com  |          1 |          1 | Brooklyn | New York  | 436648 |
|          2 | Rosa       | Diaz      |   9273648576 | rosa@gmail.com  |          2 |          2 | Albany   | New York  | 538827 |
|          5 | Izuku      | Midoriya  |   8372946583 | izuku@gmail.com |          5 |          5 | Kodagu   | Karnataka | 736457 |
+------------+------------+-----------+--------------+-----------------+------------+------------+----------+-----------+--------+
3 rows in set (0.00 sec)
*/

SELECT * FROM contact JOIN address ON contact.address_id = address.address_id WHERE city = 'Mangalore';

/*
mysql> SELECT * FROM contact JOIN address ON contact.address_id = address.address_id WHERE city = 'Mangalore';
+------------+------------+-----------+--------------+---------------+------------+------------+-----------+-----------+--------+
| contact_id | first_name | last_name | phone_number | email         | address_id | address_id | city      | state     | zip    |
+------------+------------+-----------+--------------+---------------+------------+------------+-----------+-----------+--------+
|          6 | Gon        | Freecss   |   7495860284 | gon@gmail.com |          6 |          6 | Mangalore | Karnataka | 749273 |
+------------+------------+-----------+--------------+---------------+------------+------------+-----------+-----------+--------+
1 row in set (0.00 sec)

*/

SELECT * FROM contact JOIN address ON contact.address_id = address.address_id WHERE state = 'New York';

/*
mysql> SELECT * FROM contact JOIN address ON contact.address_id = address.address_id WHERE state = 'New York';
+------------+------------+-----------+--------------+----------------+------------+------------+----------+----------+--------+
| contact_id | first_name | last_name | phone_number | email          | address_id | address_id | city     | state    | zip    |
+------------+------------+-----------+--------------+----------------+------------+------------+----------+----------+--------+
|          1 | Jake       | Peralta   |   9481448524 | jake@gmail.com |          1 |          1 | Brooklyn | New York | 436648 |
|          2 | Rosa       | Diaz      |   9273648576 | rosa@gmail.com |          2 |          2 | Albany   | New York | 538827 |
+------------+------------+-----------+--------------+----------------+------------+------------+----------+----------+--------+
2 rows in set (0.00 sec)

*/


/* REPEATED USE CASE 7*/

SELECT city, COUNT(contact_id) AS COUNT_BY_CITY FROM contact JOIN address ON contact.address_id = address.address_id GROUP BY city;

/*
mysql> SELECT city, COUNT(contact_id) AS COUNT_BY_CITY FROM contact JOIN address ON contact.address_id = address.address_id GROUP BY city;
+-------------+---------------+
| city        | COUNT_BY_CITY |
+-------------+---------------+
| Albany      |             1 |
| Bangalore   |             2 |
| Brooklyn    |             1 |
| Kodagu      |             1 |
| Mangalore   |             1 |
| Sacaramento |             1 |
+-------------+---------------+
6 rows in set (0.00 sec)

*/

SELECT state, COUNT(contact_id) As COUNT_BY_STATE FROM contact JOIN address ON contact.address_id = address.address_id GROUP BY state;

/*
mysql> SELECT state, COUNT(contact_id) As COUNT_BY_STATE FROM contact JOIN address ON contact.address_id = address.address_id GROUP BY state;
+------------+----------------+
| state      | COUNT_BY_STATE |
+------------+----------------+
| California |              1 |
| Karnataka  |              4 |
| New York   |              2 |
+------------+----------------+
3 rows in set (0.00 sec)

*/


/* REPEATED USE CASE 8*/

SELECT * FROM contact JOIN address ON contact.address_id = address.address_id where city = 'Bangalore' ORDER BY first_name;

/*
mysql> SELECT * FROM contact JOIN address ON contact.address_id = address.address_id where city = 'Bangalore' ORDER BY first_name;
+------------+------------+-----------+--------------+--------------------+------------+------------+-----------+-----------+--------+
| contact_id | first_name | last_name | phone_number | email              | address_id | address_id | city      | state     | zip    |
+------------+------------+-----------+--------------+--------------------+------------+------------+-----------+-----------+--------+
|          4 | Ashika     | Chadaga   |   8392483756 | ashika@gmail.com   |          4 |          4 | Bangalore | Karnataka | 630284 |
|          7 | Sagarika   | Satish    |   7465927465 | sagarika@gmail.com |          4 |          4 | Bangalore | Karnataka | 630284 |
+------------+------------+-----------+--------------+--------------------+------------+------------+-----------+-----------+--------+
2 rows in set (0.00 sec)
*/


ALTER TABLE contact ADD COLUMN date_added DATE;

update contact set date_added = '2010-01-21' where contact_id = 1;
update contact set date_added = '2016-05-18' where contact_id = 2;
update contact set date_added = '2013-08-11' where contact_id = 3;
update contact set date_added = '2019-03-22' where contact_id = 4;
update contact set date_added = '2020-01-21' where contact_id = 5;
update contact set date_added = '2017-04-18' where contact_id = 6;
update contact set date_added = '2010-01-21' where contact_id = 7;


INSERT INTO contact VALUES (8,'Amy', 'Gonzales', 1234567890, 'amy@gmail.com',3);


