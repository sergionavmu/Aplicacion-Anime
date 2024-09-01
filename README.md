# G7


# Documentación MarkDown App Anime

<img src="app/src/main/res/drawable/logo.jpg" alt="Logo aplicación" width="150"/>
---
Título: "El Rincon del Otaku"
<br>Autores: "Maria y Sergio"
<br>Date: "23/04/2024"
---
Video Defensa: https://drive.google.com/file/d/1ukZgWT0OaCOZu2zfAvboiGqMeDRJ5xZG/view?usp=sharing
---

## Presentacion del Proyecto

El proyecto se trata de implementar tanto el backend como el frontend de una app de Anime que ofrece la posibilidad al usuario de visualizar los animes
de esta temporada, pudiendo ver la informacion en si de estos y sus diferentes capitulos, asi tambien con la posibilidad de agregarlos a la pantalla de favoritos
para tener los animes que mas les gusten. El usuario puede ver sus datos en su perfil como tambien cambiarlos o eliminando su cuenta.

---

## Estilo y Diseño

Hemos decidido darle un estilo "Aesthetic", enfocado mas en el mundo cultural del anime, adaptando asi al maximo a la tematica de la aplicación, para que al usuario,
le parezca divertida y agradable. En toda la aplicacion hemos seguido el mismo estilo utilizando colores como el morado, el negro y el blanco, asi como un fondo de pantalla
general para todas las pantallas.

### MockUp
Aqui os mostramos un ejemplo el diseño de una de nuestras pantallas, en este caso el Login, donde se aprecia este estilo "Aesthetic" y una paleta de colores de un morado suave donde se aprecia
una calle japonesa, junto con el logo de la aplicacion y los campos donde hay que insertar nuestras credenciales para poder acceder.

![Login.png](Files%2Fimagenes%2FLogin.png)

### Comparativa y toma de decisiones
Dentro de los parámetros establecidos, hemos mantenido la integridad del diseño original, realizando solo ajustes en los estilos de los TextInput y las Cards de los animes. 
Además, hemos limitado la función de "likes" a la página específica de cada anime, eliminando la opción de puntuación. Asimismo, hemos mejorado el diseño de las páginas individuales de los animes, 
otorgándoles una apariencia más atractiva y organizada.
En conclusion el diseño original nos ha parecido bien desde un principio y hemos decidido hacerle unos cambios minimos

#### Pantallas:

![Captura de pantalla 2024-05-12 162539.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20162539.png) ![Login.png](Files%2Fimagenes%2FLogin.png)<br>
![Captura de pantalla 2024-05-12 162658.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20162658.png) ![animes.png](Files%2Fimagenes%2Fanimes.png) <br>
![Captura de pantalla 2024-05-12 163019.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20163019.png) ![Detalles anime.png](Files%2Fimagenes%2FDetalles%20anime.png)<br>

---

## Configuracion y datos de la Aplicacion

### Contenedor Docker
Nuestro contenedor docker ejecuta 2 servicios: "DB" para ejecutar y configurar MYSQL conectando con nuestra base de datos "Anime", y "Adminer" para la administracion web en localhost de esta misma bbdd

![Captura de pantalla 2024-05-12 164117.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20164117.png)
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

![Captura de pantalla 2024-05-12 170158.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20170158.png)


### Diagrama Relacional

![Captura de pantalla 2024-05-12 165528.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20165528.png)

---

## Estructura del Back

Hemos dividido el proyecto por carpetas para facilitar su localización. La estructura es la siguiente:

- **Java**:
    - **Dao**: Maneja la interacción con la base de datos de las diferentes clases creadas.
    - **Model**: Contiene las clases utilizadas en el proyecto.
    - **Service**: Encargado de toda la lógica del programa.
    - **API**: Contiene las clases controladoras para manejar las solicitudes HTTP.


En **Dao**, hemos creado clases separadas para manejar la interacción con la base de datos. Esto se ha hecho para mantener un enfoque ordenado y facilitar las modificaciones.

En **Modelo**, se han creado clases con atributos, getters, setters y el método toString para las diferentes entidades del proyecto.

El **Service** maneja la lógica del proyecto, gestionando diferentes operaciones.

En **API**, se encuentran las clases controladoras que manejan las solicitudes HTTP entrantes y dirigen el flujo de ejecución adecuado a través del servicio correspondiente.

#### DAO

- ***Clases para interactuar con la base de datos y manejar operaciones de inserción, actualización y eliminación***

  - **AnimeRepository**: Gestiona las operaciones relacionadas con los animes en la base de datos.
  - **FavoritoRepository**: Gestiona las operaciones relacionadas con los favoritos en la base de datos.
  - **UserRepository**: Gestiona las operaciones relacionadas con los usuarios en la base de datos.
  - **VideoRepository**: Gestiona las operaciones relacionadas con los videos en la base de datos.
  - **HandlerSax**: Clase SAX personalizada para parsear un documento XML y crear objetos Java (`Anime`, `Video`, `User`, `Favorito`) a partir de él.

![Captura de pantalla 2024-05-12 171829.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20171829.png)
![Captura de pantalla 2024-05-12 171846.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20171846.png)
![Captura de pantalla 2024-05-12 171201.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20171201.png)

#### Modelo

- ***Clases que representan las entidades del sistema***

  - **Anime**: Representa un anime con diferentes atributos como `id`, `name`, `description`, etc.
  - **Favorito**: Representa los favoritos de los usuarios.
  - **User**: Modelo para usuarios con atributos como `name`, `email`, `password`, etc.
  - **Video**: Modelo para videos asociados a los animes.

![Captura de pantalla 2024-05-12 171320.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20171320.png)

#### Service

- ***Maneja la lógica del proyecto, gestionando diferentes operaciones***

  - **AnimeService**: Gestiona las operaciones relacionadas con los animes, como obtener, agregar, actualizar y eliminar animes.
  - **FavoritoService**: Maneja las operaciones relacionadas con los favoritos de los usuarios.
  - **UserService**: Maneja las operaciones relacionadas con los usuarios, como registro, inicio de sesión, etc.
  - **VideoService**: Gestiona las operaciones relacionadas con los videos asociados a los animes.

![Captura de pantalla 2024-05-12 171133.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20171133.png)

#### API

- ***Contiene las clases controladoras para manejar las solicitudes HTTP***

  - **AnimeController**: Controlador para gestionar las solicitudes relacionadas con los animes, como obtener, agregar, actualizar y eliminar animes.
  - **FavoritoController**: Controlador para manejar las solicitudes relacionadas con los favoritos de los usuarios.
  - **UserController**: Controlador para gestionar las solicitudes relacionadas con los usuarios, como registro, inicio de sesión, etc.
  - **VideoController**: Controlador para gestionar las solicitudes relacionadas con los videos asociados a los animes.

![Captura de pantalla 2024-05-12 165951.png](Files%2Fimagenes%2FCaptura%20de%20pantalla%202024-05-12%20165951.png)
