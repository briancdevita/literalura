# 📚 LiterAlura

Challenge del programa Oracle Next Education (ONE) de Alura Latam.  
Aplicación de consola en Java que permite consultar libros desde la API de Gutendex y almacenarlos en una base de datos PostgreSQL.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 3.2.4
- Maven
- Spring Data JPA
- PostgreSQL
- Jackson (para manejo de JSON)
- API externa: [Gutendex](https://gutendex.com/)

---

## 🎯 Funcionalidades implementadas

1. 🔍 Buscar libro por título y guardarlo en base de datos.
2. 📚 Listar todos los libros registrados.
3. ✍️ Listar todos los autores registrados.
4. 🧓 Listar autores vivos en un año específico.
5. 🌐 Listar libros por idioma (ES, EN, FR, PT).
6. ❌ Manejo de errores:
   - Libros no encontrados.
   - Duplicados evitados en base de datos.

---

## 🗂️ Estructura del proyecto

src/
└── main/
└── java/
└── com.alura.literalura/
├── model/ # Entidades y records
├── repository/ # Interfaces JPA
├── service/ # Lógica de negocio (API y DB)
└── Principal.java # Menú principal y flujo de la app

---

## ⚙️ Cómo ejecutar el proyecto

1. Cloná el repositorio:
```bash
git clone https://github.com/tu-usuario/literalura.git

2. Configurá tu base de datos PostgreSQL y ajustá application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update


3: Ejecutá la aplicación desde tu IDE o con:
./mvnw spring-boot:run

