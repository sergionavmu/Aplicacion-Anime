# Página de configuraciones


## Configuracion y datos de la Aplicacion

### Contenedor Docker
Nuestro contenedor docker ejecuta 2 servicios: "DB" para ejecutar y configurar MYSQL conectando con nuestra base de datos "Anime", y "Adminer" para la administracion web en localhost de esta misma bbdd

![Captura de pantalla 2024-05-12 164117.png](..%2F..%2FFiles%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20164117.png)

[docker-compose.yml](docker%2Fdocker-compose.yml)


## Base de Datos
Nuestra base de datos se divide en 4 tablas junto a los procedures:

- #### Anime:
Almacena información sobre los animes, como su título, descripción, tipo, año de lanzamiento, imagen, nombre original, clasificación, demografía y género.

- #### Users:
Contiene información sobre los usuarios del sistema, como su nombre, correo electrónico, contraseña y número de teléfono.

- #### Favoritos:
Relaciona a los usuarios con los animes que tienen en su lista de favoritos.

- #### Videos:
Almacena los videos de cada capitulo asociados a cada anime, como la URL del video, la imagen principal de ese anime y la descripcion.

### Procedures
Por cada tabla hemos hecho varios procedures (Get, Insert, Update y Delete ), para que se ejecuten nuestras llamadas a la bbdd y se puedan realizar varias operaciones

![Captura de pantalla 2024-05-12 170158.png](..%2F..%2FFiles%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20170158.png)


### Diagrama Relacional

![Captura de pantalla 2024-05-12 165528.png](..%2F..%2FFiles%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20165528.png)![Captura de pantalla 2024-05-12 165528.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20165528.png)

---

Frameworks utilizados

Spring Data JPA

