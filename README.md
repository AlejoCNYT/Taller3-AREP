# Servidor Web con Inversión de Control en Java

Este proyecto consiste en la implementación de un servidor web en Java, capaz de servir páginas HTML e imágenes PNG, además de proveer un framework de inversión de control (IoC) para la construcción de aplicaciones web a partir de POJOs.

## Características
- Servidor web para responder a peticiones HTTP.
- Servir archivos estáticos (HTML, PNG).
- Implementación de un framework IoC para detectar y manejar componentes anotados con `@RestController`.
- Soporte para `@GetMapping` y `@RequestParam`.
- Exploración del classpath en busca de controladores anotados.

## Requisitos
- Java 8 o superior
- Apache Maven

## Instalación y Ejecución
### Clonar el repositorio
```sh
git clone <URL_DEL_REPOSITORIO>
cd Taller3-AREP-main
```

### Compilar y ejecutar
```sh
mvn clean package
java -cp target/classes co.edu.eci.arep.appsvr.EciBoot co.edu.eci.arep.appsvr.GreetingController
```
![imagen](https://github.com/user-attachments/assets/f962a8f0-b91c-44ff-924e-e08c64f93580)


## Uso del Framework IoC
El servidor permite registrar y manejar controladores de la siguiente manera:

```java
@RestController
public class GreetingController {
    
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hola " + name;
    }
}
```

## Estructura del Proyecto
```
Taller3-AREP-main/
├── pom.xml
├── src/
│   ├── main/java/co/edu/eci/arep/appsvr/
│   │   ├── EciBoot.java
│   │   ├── GetMapping.java
│   │   ├── GreetingController.java
│   │   ├── RequestParam.java
│   │   ├── RestController.java
│   ├── main/resources/
│   ├── test/java/
```

## Autores
- [Tu Nombre]

## Licencia
Este proyecto se distribuye bajo la licencia MIT.

