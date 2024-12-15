# SocialTripper Spring Boot Backend Application

## Opis projektu
REST API implemented for SocialTripper in Spring Boot.

---

## Contains
- handling Postgres data base with Hibernate ORMs
- stored procedures and functions used in data base are stored in directory /RestApi/src/main/resources/db/functions
- handling Neo4j graph database with ORMs
- entities, dtos, exceptions defined
- self implemented mappers between entities and dtos
- dealing with multimedia with Azure Blob Storage SDK
- endpoints defined

---

## Requirements
- **Java 17** or newer version
- **Maven** for dependencies
- **Docker** for containerization

---

## Build

docker compose up --build

For running all services on docker and building all images. Run in /RestApi directory.
