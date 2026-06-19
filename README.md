# Sistema Veterinario

## Descripción
Este es un sistema integral para la gestión de una clínica veterinaria basado en una arquitectura de microservicios distribuidos con Java y Spring Boot.

### Componentes de Infraestructura
- ms-eureka: Servidor de descubrimiento para registrar los servicios.
- ms-gateway: API Gateway que centraliza el enrutamiento y las peticiones.
- ms-auth: Microservicio de seguridad y emisión de tokens JWT.

## Microservicios
- mascota
- citas
- historiasClinicas
- dueno
- veterinario
- recetas
- laboratorio
- facturacion
- vacunacion
- farmacia
- ms-auth

## Tecnologías
- Java
- Spring Boot
- Maven
- Contenedores: Docker & Docker Compose

## Cómo ejecutar
1. Abrir cada microservicio
2. Ejecutar con mvn spring-boot:run
3. Para levantar el docker
docker compose up -d --build
4. Para limpiar docker:
docker compose down -v

#Swagger
http://localhost:8082/swagger-ui.html
-Para poder abrir el swagger utilizar esa url y solo cambiar el localhost
