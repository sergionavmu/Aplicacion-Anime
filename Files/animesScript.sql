USE anime;

CREATE TABLE animes (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(10000) NOT NULL,
  type ENUM('TV','Film','OVA','Special') NOT NULL,
  year INT NOT NULL,
  image VARCHAR(1000) NOT NULL,
  originalname VARCHAR(200) NOT NULL,
  rating VARCHAR(50) NOT NULL,
  demography VARCHAR(200) NOT NULL,
  genre VARCHAR(200) NOT NULL,
  image2 VARCHAR(1000) NOT NULL,
  active VARCHAR(200) NOT NULL
);

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(30) NOT NULL,
  password VARCHAR(30) NOT NULL,
  phone VARCHAR(30) NOT NULL
);

CREATE TABLE favoritos (
  iduser INT NOT NULL,
  idanime INT NOT NULL,
  status boolean,
  PRIMARY KEY (iduser, idanime),
  FOREIGN KEY (iduser) REFERENCES users(id),
  FOREIGN KEY (idanime) REFERENCES animes(id)
);

CREATE TABLE videos (
  idanime INT NOT NULL,
  episode INT NOT NULL,
  url VARCHAR(200) NOT NULL,
  image VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (idanime, episode),
  FOREIGN KEY (idanime) REFERENCES animes(id)
);
