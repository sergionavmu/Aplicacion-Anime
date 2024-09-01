
CREATE PROCEDURE getAllAnimes ()
BEGIN
   SELECT id, name, description, type, year, image, originalName, rating, demography, genre, image2, active
   FROM anime.animes;
END //

CREATE PROCEDURE getAnimeById (IN idAnime INT)
BEGIN
    SELECT id, name, description, type, year, image, originalName, rating, demography, genre, image2, active
    FROM anime.animes WHERE id = idAnime;
END //

CREATE PROCEDURE getAnimeByName (IN animeName VARCHAR(255))
BEGIN
    SELECT id, name, description, type, year, image, originalName, rating, demography, genre, image2, active
    FROM anime.animes WHERE name LIKE CONCAT("%", animeName, "%");
END //


CREATE PROCEDURE insertAnime (IN name VARCHAR(255), IN description VARCHAR(10000), IN type VARCHAR(50), IN year INT, IN image VARCHAR(255), IN originalName VARCHAR(255), IN rating VARCHAR(50), IN demography VARCHAR(50), IN genre VARCHAR(255), IN image2 VARCHAR(255), IN active VARCHAR(50))
BEGIN
    INSERT INTO anime.animes (name, description, type, year, image, originalName, rating, demography, genre, image2, active)
    VALUES (name, description, type, year, image, originalName, rating, demography, genre, image2, active);
END //

CREATE PROCEDURE updateAnimeById (IN idAnime INT, IN newName VARCHAR(255), IN newDescription VARCHAR(255), IN newType VARCHAR(50), IN newYear INT, IN newImage VARCHAR(255), IN newOriginalName VARCHAR(255), IN newRating VARCHAR(50), IN newDemography VARCHAR(50), IN newGenre VARCHAR(50), IN newImage2 VARCHAR(255), IN newActive VARCHAR(50))
BEGIN
    UPDATE anime.animes
    SET name = newName, description = newDescription, type = newType, year = newYear, image = newImage, originalName = newOriginalName, rating = newRating, demography = newDemography, genre = newGenre, image2 = newImage2, active = newActive
    WHERE id = idAnime;
END //

CREATE PROCEDURE deleteAnimeById (IN idAnime INT)
BEGIN
   DELETE FROM anime.animes WHERE id = idAnime;
END //

-----------------------------------------------------------------------------------------------------------------------------------
/* Procedure Usuarios */

CREATE PROCEDURE getAllUsers ()
BEGIN
   SELECT id, name, email, password, phone
   FROM anime.users;
END //

CREATE PROCEDURE getUserById (IN idUser INT)
BEGIN
    SELECT id, name, email, password, phone
    FROM anime.users WHERE id = idUser;
END //

CREATE PROCEDURE postLogin (IN userEmail VARCHAR(30), IN userPassword VARCHAR(30))
BEGIN
    DECLARE userCount INT;
    SELECT COUNT(*) INTO userCount
    FROM anime.users
    WHERE email = userEmail AND password = userPassword;
    IF userCount > 0 THEN
        SELECT 'Login successful.' AS message;
    ELSE
        SELECT 'Invalid email or password.' AS message;
    END IF;
END //

CREATE PROCEDURE getUserByEmail (IN userEmail VARCHAR(30))
BEGIN
    SELECT id, name, email, password, phone
    FROM anime.users WHERE email = userEmail;
END //


CREATE PROCEDURE insertUser (IN name VARCHAR(50), IN email VARCHAR(30), IN password VARCHAR(30), IN phone VARCHAR(30))
BEGIN
    INSERT INTO anime.users (name, email, password, phone)
    VALUES (name, email, password, phone);
END //

CREATE PROCEDURE updateUserByEmail (IN userEmail varchar(50), IN newName VARCHAR(30), IN newPassword VARCHAR(30), IN newPhone VARCHAR(30))
BEGIN
    UPDATE anime.users
    SET name = newName, password = newPassword, phone = newPhone
    WHERE email = userEmail;
END //


CREATE PROCEDURE deleteUserByEmail (IN userEmail varchar(50))
BEGIN
   DELETE FROM anime.users WHERE email = userEmail;
END //

-- Procedure Favoritos

CREATE PROCEDURE getAllFavoritos ()
BEGIN
   SELECT idanime, iduser
   FROM anime.favoritos;
END //

CREATE PROCEDURE getFavoritoById (IN userId INT)
BEGIN
    SELECT idanime, iduser
    FROM anime.favoritos WHERE iduser = userId;
END //

CREATE PROCEDURE getFavoritoByUserByAnime (IN userId INT, IN animeId INT)
BEGIN
    SELECT idanime, iduser
    FROM anime.favoritos WHERE iduser = userId AND idanime = animeId;
END //


CREATE PROCEDURE insertFavorito (IN iduser INT, IN idanime varchar(50))
BEGIN
    INSERT INTO anime.favoritos (iduser, idanime)
    VALUES (iduser, idanime);
END //

CREATE PROCEDURE deleteFavoritoById (IN userId INT, IN animeId INT)
BEGIN
   DELETE FROM anime.favoritos WHERE iduser = userId AND idanime = animeId;
END //




-----------------------------------------------------------------------------------------------------------------------------------
/* Procedure VIDEOS */

CREATE PROCEDURE getAllVideos ()
BEGIN
   SELECT idanime, episode, url, image
   FROM anime.videos;
END //

CREATE PROCEDURE getVideosById (IN idVideo INT)
BEGIN
    SELECT idanime, episode, url, image
    FROM anime.videos WHERE idanime = idVideo;
END //

CREATE PROCEDURE getVideoByAnimeId (IN animeId INT)
BEGIN
    SELECT *
    FROM anime.videos WHERE idanime = animeId;
END //

CREATE PROCEDURE insertVideo (IN idanime INT, IN episode INT, IN url VARCHAR(200), IN image VARCHAR(200))
BEGIN
    INSERT INTO anime.videos (idanime, episode, url, image)
    VALUES (idanime, episode, url, image);
END //

CREATE PROCEDURE updateVideoById (IN animeId INT, IN idEpisode INT, IN newUrl VARCHAR(200), IN newImage VARCHAR(200))
BEGIN
    UPDATE anime.videos
    SET url = newUrl, image = newImage
    WHERE idanime = animeId AND episode = idEpisode;
END //


CREATE PROCEDURE deleteVideoById (IN idAnime INT, IN idEpisode INT)
BEGIN
   DELETE FROM anime.videos WHERE idanime = idAnime AND episode = idEpisode;
END //