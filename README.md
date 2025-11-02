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

<h2 id="routes">📍 API Endpoints</h2>

​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /auth/register</kbd>     | register a new user [response details](#post-auth-detail)
| <kbd>POST /auth/login</kbd>     | authenticate a user [request details](#post-auth-login)
| <kbd>GET /auth/me</kbd>     | get information about logged user [request details](#get-auth-me)

<h3 id="post-auth-register">POST /auth/register</h3>

**REQUEST**
```json
{
  "name": "Carlos Eduardo",
  "email": "carlos@gmail.com",
  "password": "123456789"
}
```

<h3 id="post-auth-login">POST /auth/login</h3>

**REQUEST**
```json
{
  "login": "carlos@gmail.com",
  "password": "123456789"
}
```

**RESPONSE**
```json
{
  "token": "OwoMRHsaQwyAgVoc3OXmL1JhMVUYXGGBbCTK0GBgiYitwQwjf0gVoBmkbuyy0pSi"
}
```

<h3 id="get-auth-me">GET /auth/me</h3>

**RESPONSE**
```json
{
  "id": "1",
  "name": "Carlos Eduardo",
  "email": "carlos@gmail.com"
}
```