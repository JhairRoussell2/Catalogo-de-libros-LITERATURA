<h1 align="center" style="font-size: 50px;">📚 LiterAlura — Catálogo de Libros 📚</h1>

<p align="center">
  <img src="./paraREADME/1.png" alt="Logo de LiterAlura">
</p>

---

## 📌 **Índice**
1. [Descripción del Proyecto](#-descripción-del-proyecto)
2. [Estado del Proyecto](#-estado-del-proyecto)
3. [Características del Proyecto](#-características-del-proyecto)
4. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
5. [Acceso al Proyecto](#-acceso-al-proyecto)
6. [Instrucciones para Ejecutar](#-instrucciones-para-ejecutar)
7. [Personas Desarrolladoras del Proyecto](#-personas-desarrolladoras-del-proyecto)
8. [Licencia](#-licencia)

---

## 📖 **Descripción del Proyecto**

**LiterAlura** es un **catálogo de libros** que consulta la API pública **Gutendex** (metadata de Project Gutenberg), convierte la respuesta **JSON** a objetos Java y **persiste** libros y autores en **PostgreSQL** usando **Spring Data JPA**.  
La interacción es **por consola**: un menú permite buscar y registrar libros por título, listar libros/autores, ver autores vivos en un año, y filtrar libros por idioma.

**Campos clave** que maneja el sistema:
- **Libro**: título, autor (único por libro en este reto), idioma (primer idioma), descargas.
- **Autor**: nombre, año de nacimiento, año de fallecimiento.

---

## 🚀 **Estado del Proyecto**

<h4 align="center">
🎉🚀 Proyecto Completado 🚀🎉
</h4>

---

## 🔧 **Características del Proyecto**

- **Interfaz de consola**: Menú simple y validado con `Scanner`.
- **Búsqueda por título** (API Gutendex): toma el **primer resultado** y lo registra.
- **Persistencia en PostgreSQL**: entidades JPA `Libro` y `Autor`, relaciones y restricciones únicas.
- **Listado de libros y autores**: lectura desde la base de datos.
- **Autores vivos en un año**: consulta por año (nac. ≤ año y (sin fallecimiento o fallec. > año)).
- **Libros por idioma + conteo**: filtra por código de idioma (`es`, `en`, etc.) y muestra el total.
- **Manejo de JSON con Jackson**: DTOs (`BookDto`, `AuthorDto`, `GutendexResponse`) con `@JsonAlias` y `@JsonIgnoreProperties`.
- **Cliente HTTP**: `java.net.http.HttpClient` con manejo de redirecciones y cabeceras.

### 📌 **Ejemplo de uso**:

1. El usuario ejecuta la app y ve el menú:

<p align="center">
  <img src="./paraREADME/Ejm1.jpg" alt="Menú de LiterAlura">
</p>

2. Elige **1) buscar libro por título**, ingresa por ejemplo **“quijote”**.  
   El primer resultado se convierte y **se guarda** en la base.

<p align="center">
  <img src="./paraREADME/Ejm2.jpg" alt="Búsqueda y guardado">
</p>

3. Con **2) listar libros registrados**, se muestran los libros guardados:

<p align="center">
  <img src="./paraREADME/Ejm3.jpg" alt="Listado de libros">
</p>

4. Con **4) autores vivos en un determinado año**, ingresa por ejemplo **1550**:

<p align="center">
  <img src="./paraREADME/Ejm4.jpg" alt="Autores vivos en año">
</p>
5. Y de la misma forma todas las funcionalidades están activas, puedes probarlas por tu cuneta!
---

## 💻 **Tecnologías Utilizadas**

- **Java 17+**
- **Spring Boot 3.5.4**
- **Maven**
- **Spring Data JPA**
- **PostgreSQL 16+** (pgAdmin opcional)
- **Jackson** (`spring-boot-starter-json`)
- **HTTP Client** (`java.net.http`)
- **API Gutendex** (metadatos de libros de Project Gutenberg)

---

## 📁 **Acceso al Proyecto**

1. **Clonar el Repositorio**:
   - `git clone https://github.com/JhairRoussell2/literatura.git`

2. **Abrir en IntelliJ IDEA**:
   - Abre la carpeta del proyecto.
   - Ejecuta la clase **`LiteraturaApplication.java`** (paquete `com.Jhair.literatura`), o usa Maven Wrapper.

---

## 🛠️ **Instrucciones para Ejecutar**

1. **Requisitos previos**:
   - **Java 17+**
   - **PostgreSQL 16+** instalado y en ejecución (puerto por defecto **5432**).
   - Base de datos creada:
     ```sql
     CREATE DATABASE literatura;
     ```
2. **Configurar credenciales** en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literatura
   spring.datasource.username=postgres
   spring.datasource.password=TU_PASSWORD
   spring.datasource.driver-class-name=org.postgresql.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   # App de consola
   spring.main.web-application-type=none
   
3. **(Maven) Dependencias clave** ya incluidas en el `pom.xml`:
   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-json</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   <dependency>
     <groupId>org.postgresql</groupId>
     <artifactId>postgresql</artifactId>
     <scope>runtime</scope>
   </dependency>
4. **Compilar y ejecutar**:
   - Con Maven Wrapper:
     ```bash
     mvnw clean package
     mvnw spring-boot:run
     ```
   - O desde IntelliJ ejecutando `LiteraturaApplication`.

5. **Uso básico desde el menú**:
   - **1**: buscar libro por título (guarda libro y autor).
   - **2**: listar libros registrados.
   - **3**: listar autores registrados.
   - **4**: autores vivos en un determinado año.
   - **5**: libros por idioma (muestra lista y total).
   - **0**: salir.

---

## 🧑‍💻 **Personas Desarrolladoras del Proyecto**

| [<img src="https://avatars.githubusercontent.com/u/181286163?v=4" width=115><br><sub>Jhair Roussell Melendez Blas</sub>](https://github.com/JhairRoussell2) |
| :---: |

---

## 📜 **Licencia**

Este proyecto está licenciado bajo la **Licencia MIT**.
