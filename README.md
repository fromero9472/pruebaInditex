
# Price Service

## Descripción

Este proyecto implementa un servicio para gestionar precios de productos en diferentes marcas y fechas de aplicación. Utiliza una arquitectura en capas, siguiendo el patrón de **adaptadores** (Hexagonal Architecture), para garantizar una separación clara entre la lógica de negocio, los datos y las interfaces de usuario.

El servicio está desarrollado en **Spring Boot** y hace uso de **JPA** para interactuar con la base de datos, implementando consultas eficientes para obtener el precio más relevante para un producto y marca en una fecha específica.

## Tecnologías y herramientas

- **Spring Boot**: Framework principal utilizado para crear la aplicación.
- **JPA (Java Persistence API)**: Para la interacción con la base de datos.
- **Spring Data JPA**: Proporciona una implementación automática de repositorios para las entidades de la base de datos.
- **H2 Database (para desarrollo y pruebas)**: Base de datos en memoria para simplificar el desarrollo.
- **Swagger**: Para la documentación de la API REST.
- **JUnit / Mockito**: Para pruebas unitarias.
- **Jacoco**: Para monitorizar la cobertura de pruebas.
- **SonarQube**: Para análisis estático del código y calidad.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **Git**: Control de versiones.

## Arquitectura

La arquitectura del proyecto sigue el patrón **Hexagonal** (o arquitectura de puertos y adaptadores), donde las distintas capas están bien separadas:

1. **Capa de presentación (Controller)**: Gestiona las solicitudes HTTP y delega las tareas en los servicios de negocio. 
    - Clase: `PriceController`.
2. **Capa de dominio (Service)**: Contiene la lógica de negocio, delegando las operaciones a los repositorios de la base de datos.
    - Clase: `PriceService`.
3. **Capa de persistencia (Repository)**: Interactúa con la base de datos utilizando **Spring Data JPA** para realizar las consultas necesarias.
    - Clase: `PriceRepository`.
4. **Capa de infraestructura (Adapter)**: Conecta las interfaces del dominio con implementaciones concretas (repositorios, en este caso).
5. **Excepciones globales (ControllerAdvice)**: Manejo centralizado de errores a través de `@ControllerAdvice`.
    - Clase: `GlobalExceptionHandler`.

### Diagrama de arquitectura

```
+------------------------+
|       Controller       |
+------------------------+
           |
           v
+------------------------+
|        Service         |
+------------------------+
           |
           v
+------------------------+
|     Repository         |
+------------------------+
           |
           v
+------------------------+
|        Database        |
+------------------------+
```

## Instalación

### Prerequisitos

- **JDK 11+**: Asegúrate de tener instalado Java 11 o superior.
- **Maven**: Usado para gestionar dependencias y compilar el proyecto.
- **IDE**: Recomendamos usar IntelliJ IDEA, Eclipse o VS Code.

### Pasos para ejecutar el proyecto

1. **Clona el repositorio**

```bash
git clone https://github.com/tu-usuario/price-service.git
cd price-service
```

2. **Compila el proyecto con Maven**

```bash
mvn clean install
```

3. **Ejecuta la aplicación**

Puedes ejecutar la aplicación usando el siguiente comando:

```bash
mvn spring-boot:run
```

4. **Accede a la documentación de la API (Swagger)**

Una vez la aplicación esté en funcionamiento, puedes acceder a la documentación interactiva de la API a través de:

```
http://localhost:8080/api/swagger-ui/index.html
```

5. **Base de datos (H2)**

Si estás utilizando H2 como base de datos en memoria, puedes acceder a la consola de H2 en:

```
http://localhost:8080/h2-console
```

Configuración por defecto:

- **URL JDBC**: jdbc:h2:mem:testdb
- **Usuario**: sa
- **Contraseña**: (vacío)

## API Endpoints

### Obtener el precio de un producto

`GET /api/v1/prices`

Este endpoint devuelve el precio más relevante de un producto y marca en una fecha específica.

#### Parámetros:

- `productId` (long): ID del producto.
- `brandId` (long): ID de la marca.
- `applicationDate` (string): Fecha de aplicación en formato `yyyy-MM-dd'T'HH:mm:ss`.

#### Ejemplo de solicitud:

```http
GET /api/v1/prices?productId=35455&brandId=1&applicationDate=2025-01-15T10:00:00
```

#### Respuesta:

```json
{
  "productId": 35455,
  "brandId": 1,
  "price": 35.50,
  "currency": "EUR",
  "startDate": "2025-01-01T00:00:00",
  "endDate": "2025-01-31T23:59:59"
}
```

## Excepciones y manejo de errores

El servicio maneja diferentes tipos de excepciones:

- **PriceNotFoundException**: Cuando no se encuentra un precio para los parámetros proporcionados.
- **PriceServiceException**: Excepciones relacionadas con errores internos del servidor.
- **IllegalArgumentException**: Cuando los parámetros de la solicitud son incorrectos.

Las excepciones son manejadas globalmente utilizando `@ControllerAdvice`, lo que proporciona respuestas consistentes en caso de error.

### Ejemplo de error:

```json
{
  "error": "Sin contenido. No se encontraron precios para los parámetros proporcionados.",
  "message": "No se encontró precio para el producto 35455 en la marca 1.",
  "status": 404
}
```

## Pruebas

### Pruebas Unitarias

Las pruebas unitarias se encuentran en el paquete `src/test/java/com/inditex/` y se han implementado para cubrir los casos más importantes del servicio, incluyendo el manejo de excepciones y la validación de los precios devueltos.

### Pruebas de Integración

Las pruebas de integración aseguran que el servicio interactúe correctamente con la base de datos y que los endpoints funcionen como se espera. Puedes ejecutarlas con:

```bash
mvn test
```

### Cobertura de código

La cobertura de código se monitoriza utilizando **Jacoco**. Para ver el informe de cobertura después de ejecutar las pruebas, abre el archivo `target/site/jacoco/index.html`.

## Buenas prácticas y estándares de código

El proyecto sigue las siguientes prácticas y estándares:

- **Código limpio**: Nombres descriptivos de clases y métodos.
- **Comentarios y documentación**: Se añaden comentarios cuando es necesario para explicar la lógica del código.
- **Análisis de calidad del código**: Se utiliza **SonarQube** para analizar la calidad del código y se sigue un proceso de revisión de código con **Pull Requests**.
- **Gestión de excepciones**: Uso de `@ControllerAdvice` para manejo global de excepciones.

## Contribuciones

Si deseas contribuir al proyecto, por favor sigue estos pasos:

1. Forkea el repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commits (`git commit -am 'Añadir nueva funcionalidad'`).
4. Sube los cambios a tu repositorio (`git push origin feature/nueva-funcionalidad`).
5. Crea un **Pull Request**.

