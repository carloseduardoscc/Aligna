<h1 align="center" style="font-weight: bold;">Aligna 📅</h1>

<p align="center">
 <a href="#tech">Technologies</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Collaborators</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
    <b>Aligna is a personal study project, a Java-based backend API that connects professionals and clients through service scheduling.</b>
</p>

<details><summary><h3>What's the idea behind it and how does it work?</h3></summary>

<p>     Aligna is a personal study project, a backend API that connects professionals and clients. Professionals can create profiles and register services, while clients can search for available services, request reservations, and track their requests. Professionals receive these requests and can accept or reject them, ensuring a complete workflow for service booking and management.
</p>

</details>

<h2 id="technologies">💻 Technologies</h2>

- Java
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- REST Controller
- Spring Test
- MapStruct
- H2 Database
- Swagger


<h2 id="star_*ted">🚀 Getting started</h2>

Ensure you have Java 21 installed, then just clone the repo and run the maven run command.

<h3>Prerequisites</h3>


- [Java 21](https://www.oracle.com/br/java/technologies/downloads/#java21)

<h3>Cloning</h3>

Run this to clone the repository locally:

```bash
git clone git@github.com:carloseduardoscc/Aligna.git
```

<h3>Starting</h3>

How to start your project

```bash
cd Aligna
mvnw spring-boot:run
```

<h3>Acessing Swagger Documentation</h3>
After starting the application, you can access the Swagger documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

<h2 id="routes">📍 API Endpoints</h2>

​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /auth/register</kbd>     | register a new user\
| <kbd>POST /auth/login</kbd>     | authenticate a user
| <kbd>GET /auth/me</kbd>     | get information about logged user
| <kbd>GET /users/{id}</kbd>     | find a user by ID 
| <kbd>POST /users/professional-profile</kbd>     | register a professional profile 