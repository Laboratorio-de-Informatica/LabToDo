# LabToDo

## Descripción del Proyecto

LabToDo es una aplicación web que permite a los usuarios gestionar las tareas del laboratorio de informática. Los usuarios pueden añadir tareas a su lista, marcarlas como completadas y añadir comentarios a cada tarea. Cada tarea registra su fecha de creación y el usuario que la ha realizado.

La aplicación también cuenta con un sistema de autenticación, permitiendo a los usuarios iniciar sesión en sus cuentas. Además, los usuarios tienen roles específicos que determinan sus permisos dentro de la aplicación. Esta estructura de roles permite una gestión de tareas flexible y segura, adaptándose a las necesidades de diferentes tipos de usuarios.

## Estructura del Proyecto

- `src/main/java/edu/eci/labinfo/labtodo/`: Contiene el código fuente de la aplicación.
  - `data/`: Contiene las interfaces de los repositorios para interactuar con la base de datos.
    - [`UserRepository.java`](src/main/java/edu/eci/labinfo/labtodo/data/UserRepository.java)
    - [`TaskRepository.java`](src/main/java/edu/eci/labinfo/labtodo/data/TaskRepository.java)
    - [`SemesterRepository.java`](src/main/java/edu/eci/labinfo/labtodo/data/SemesterRepository.java)
    - [`CommentRepository.java`](src/main/java/edu/eci/labinfo/labtodo/data/CommentRepository.java)
  - `controller/`: Contiene los controladores de la aplicación.
    - [`TaskController.java`](src/main/java/edu/eci/labinfo/labtodo/controller/TaskController.java)
    - [`SemesterController.java`](src/main/java/edu/eci/labinfo/labtodo/controller/SemesterController.java)
    - [`LoginController.java`](src/main/java/edu/eci/labinfo/labtodo/controller/LoginController.java)
    - [`AdminController.java`](src/main/java/edu/eci/labinfo/labtodo/controller/AdminController.java)
  - `service/`: Contiene los servicios de la aplicación.
    - [`CommentService.java`](src/main/java/edu/eci/labinfo/labtodo/service/CommentService.java)
    - [`SemesterService.java`](src/main/java/edu/eci/labinfo/labtodo/service/SemesterService.java)
    - [`UserService.java`](src/main/java/edu/eci/labinfo/labtodo/service/UserService.java)
    - [`TaskService.java`](src/main/java/edu/eci/labinfo/labtodo/service/TaskService.java)
- `src/main/resources/`: Contiene los archivos de configuración de la aplicación.
  - [`application.properties`](src/main/resources/application.properties)
- [`pom.xml`](pom.xml): Archivo de configuración de Maven para la construcción y gestión del proyecto.

## Cómo Construir y Ejecutar el Proyecto

1. Descarga el código del proyecto utilizando

    ```bash
    git clone https://github.com/Laboratorio-de-Informatica/LabToDo.git
    cd LabToDo
    ```

2. Asegúrate de tener Docker instalado o una base de datos MySQL disponible.

    Para el uso de Docker, puedes utilizar el comando para ejecutar un contenedor de MySQL:

    ```bash
    docker run -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest
    ```

    En caso de que tengas una base de datos MySQL disponible, puedes cambiar las propiedades de la base de datos en el archivo `application.properties` que se encuentra en la ruta `src\main\resources\` para que la aplicación se conecte a tu base de datos.

    ```properties
    spring.datasource.url=${MYSQL_URL}
    spring.datasource.username=${MYSQL_USERNAME}
    spring.datasource.password=${MYSQL_PASSWORD}
    ```

    Ejemplo de ejecución local con un contenedor de Docker:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/labtodo?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=my-secret-pw
    ```

    En el previo ejemplo el nombre de la base de datos es `labtodo` ésta se crea si no existe, el usuario es `root` y la contraseña es `my-secret-pw`. Puedes cambiar estos valores según tus necesidades. El usuario de acceso a la base de datos debe tener permisos de creación de tablas y de inserción, actualización y eliminación de datos.

3. Si cambias propiedades de la base de datos como la contraseña o el usuario en el archivo `application.properties`, debes compilar el proyecto de nuevo con Maven. Para hacerlo, utiliza el comando:

    ```bash
    mvn package
    ```

4. Una vez compilado el proyecto, puedes ejecutarlo con el comando:

    ```bash
    java -jar target/labtodo.jar
    ```

## Dependencias

Se está utilizando el framework de Spring Boot para el desarrollo de la aplicación. Las dependencias utilizadas son:

- ![Java Version](https://img.shields.io/badge/Java-v17.0.9-orange)

    La versión de Java indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Maven Version](https://img.shields.io/badge/Maven-v3.9.5-lightgrey)

    Maven es la herramienta utilizada para la construcción del proyecto. La versión de Maven indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Spring Boot Version](https://img.shields.io/badge/SpringBoot-v3.2.0-green)

    Spring Boot es el framework utilizado para el desarrollo de la aplicación. La versión de Spring Boot indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Spring Security Cypto Version](https://img.shields.io/badge/SpringBootSecurity-v6.2.0-blue)

    Spring Security Crypto es la dependencia utilizada para la encriptación de contraseñas. La versión de Spring Security Crypto indicada es la versión utilizada para el desarrollo de la aplicación.

- ![MySQL Conector Version](https://img.shields.io/badge/MySQL-v8.2.0-purple)

    MySQL Conector es la dependencia utilizada para la conexión con la base de datos MySQL. La versión de MySQL Conector indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Join Faces Version](https://img.shields.io/badge/JoinFaces-v5.2.0-red)

    Join Faces es la dependencia utilizada para la integración de PrimeFaces con Spring Boot. La versión de Join Faces indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Prime Faces Version](https://img.shields.io/badge/PrimeFaces-v13.0.3-yellow)

    Prime Faces es la dependencia utilizada para la interfaz gráfica de la aplicación. La versión de Prime Faces indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Embedded Tomcat Version](https://img.shields.io/badge/EmbeddedTomcat-v10.1.16-brown)

    Embedded Tomcat es la dependencia utilizada para el servidor web embebido de la aplicación. La versión de Embedded Tomcat indicada es la versión utilizada para el desarrollo de la aplicación.

- ![Lombok Version](https://img.shields.io/badge/Lombok-v1.18.30-lightblue)

    Lombok es la dependencia utilizada para la generación de getters, setters, constructores y otros métodos. La versión de Lombok indicada es la versión utilizada para el desarrollo de la aplicación.

**Nota:**

Si se desea utilizar una versión diferente de las dependencias indicadas, se debe cambiar la versión en el archivo `pom.xml` que se encuentra en la raíz del proyecto. Luego de esto se debe compilar el proyecto de nuevo con Maven.

## Diagramas

Los casos de uso de la aplicación son los siguientes:

![Casos de Uso](diagrams/Casos%20de%20Uso.png)

El diagrama de conceptos es el siguiente:

![Diagrama de Conceptos](diagrams/Conceptos.png)