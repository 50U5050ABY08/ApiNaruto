# API Naruto Fullstack
[![CI](https://github.com/50U5050ABY08/ApiNaruto/actions/workflows/ci.yml/badge.svg)](https://github.com/50U5050ABY08/ApiNaruto/actions/workflows/ci.yml)
[![CD](https://github.com/50U5050ABY08/ApiNaruto/actions/workflows/cd.yml/badge.svg)](https://github.com/50U5050ABY08/ApiNaruto/actions/workflows/cd.yml)
## Português

Projeto fullstack para gerenciamento de ninjas, missões e autenticação de usuários, desenvolvido com **Spring Boot** no backend e **React com TypeScript** no frontend.

O projeto simula uma aplicação real com autenticação JWT, controle de permissões por role, CRUD de ninjas, integração com banco PostgreSQL, documentação Swagger/OpenAPI e testes automatizados no backend e frontend.

---

## English

Fullstack project for managing ninjas, missions and user authentication, built with **Spring Boot** on the backend and **React with TypeScript** on the frontend.

The project simulates a real-world application with JWT authentication, role-based authorization, ninja CRUD operations, PostgreSQL integration, Swagger/OpenAPI documentation and automated tests on both backend and frontend.

---

# Tecnologias utilizadas / Technologies Used

## Backend

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Lombok
- Bean Validation
- Swagger / OpenAPI
- JUnit
- Mockito
- MockMvc

## Frontend

- React
- TypeScript
- Vite
- CSS
- Vitest
- React Testing Library

---

# Funcionalidades / Features

## Português

- Cadastro de usuários
- Login com JWT
- Controle de acesso por role
- Listagem de ninjas
- Cadastro de ninjas
- Edição de ninjas
- Exclusão de ninjas apenas para ADMIN
- Associação de ninja com missão
- Validação de regras de negócio
- Tratamento padronizado de erros
- Documentação da API com Swagger
- Testes automatizados no backend e frontend

## English

- User registration
- Login with JWT
- Role-based access control
- Ninja listing
- Ninja creation
- Ninja update
- Ninja deletion restricted to ADMIN users
- Ninja and mission association
- Business rule validation
- Standardized error handling
- API documentation with Swagger
- Automated tests on backend and frontend

---

# Regra de negócio principal / Main Business Rule

## Português

Ninjas menores de 18 anos não podem participar de missões Rank A.

Essa regra é aplicada tanto no cadastro quanto na atualização de ninjas.

## English

Ninjas under 18 years old cannot participate in Rank A missions.

This rule is applied both when creating and updating ninjas.

---

# Segurança / Security

## Português

A API utiliza autenticação JWT e autorização baseada em roles.

Rotas públicas:

```text
POST /auth/register
POST /auth/login
```

Rotas protegidas:

```text
GET /ninjas
POST /ninjas
PUT /ninjas/{id}
GET /missoes
```

Rota restrita a ADMIN:

```text
DELETE /ninjas/{id}
```

O frontend também oculta ações administrativas para usuários comuns, mas a segurança real é garantida pelo backend.

## English

The API uses JWT authentication and role-based authorization.

Public routes:

```text
POST /auth/register
POST /auth/login
```

Protected routes:

```text
GET /ninjas
POST /ninjas
PUT /ninjas/{id}
GET /missoes
```

ADMIN-only route:

```text
DELETE /ninjas/{id}
```

The frontend also hides administrative actions from regular users, but the real security enforcement is handled by the backend.

---

# Variáveis de ambiente / Environment Variables

## Português

O projeto utiliza variáveis de ambiente para evitar que senhas, chaves JWT e configurações sensíveis sejam enviadas para o GitHub.

## English

The project uses environment variables to prevent passwords, JWT secrets and sensitive configuration from being pushed to GitHub.

---

## Backend

### Português

O backend utiliza as seguintes variáveis:

```env
DB_URL=jdbc:postgresql://localhost:5432/narutodb
DB_USERNAME=postgres
DB_PASSWORD=sua-senha-do-postgres
JWT_SECRET=sua-chave-jwt-grande-e-secreta
```

No Windows, exemplo de configuração:

```powershell
setx DB_URL "jdbc:postgresql://localhost:5432/narutodb"
setx DB_USERNAME "postgres"
setx DB_PASSWORD "sua-senha-do-postgres"
setx JWT_SECRET "sua-chave-jwt-grande-e-secreta"
```

Após configurar com `setx`, é necessário fechar e abrir novamente o terminal ou a IDE para que as variáveis fiquem disponíveis.

O arquivo principal do backend utiliza essas variáveis em:

```text
src/main/resources/application.properties
```

Exemplo seguro:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/narutodb}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=1h
```

A senha do banco e a chave JWT real não devem ser enviadas para o GitHub.

### English

The backend uses the following environment variables:

```env
DB_URL=jdbc:postgresql://localhost:5432/narutodb
DB_USERNAME=postgres
DB_PASSWORD=your-postgres-password
JWT_SECRET=your-large-and-secure-jwt-secret
```

Windows configuration example:

```powershell
setx DB_URL "jdbc:postgresql://localhost:5432/narutodb"
setx DB_USERNAME "postgres"
setx DB_PASSWORD "your-postgres-password"
setx JWT_SECRET "your-large-and-secure-jwt-secret"
```

After using `setx`, close and reopen the terminal or IDE so the variables become available.

The main backend configuration file uses these variables at:

```text
src/main/resources/application.properties
```

Secure example:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/narutodb}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=1h
```

The real database password and JWT secret must not be pushed to GitHub.

---

## Frontend

### Português

Dentro da pasta `frontend`, existe um arquivo de exemplo:

```text
frontend/.env.example
```

Conteúdo:

```env
VITE_API_URL=http://localhost:8080
```

Para rodar localmente, crie um arquivo chamado:

```text
frontend/.env
```

Com o conteúdo:

```env
VITE_API_URL=http://localhost:8080
```

Para testes do frontend, também pode ser criado localmente:

```text
frontend/.env.test
```

Com o conteúdo:

```env
VITE_API_URL=http://localhost:8080
```

Os arquivos `.env` e `.env.test` são ignorados pelo Git. Apenas o `.env.example` deve ser versionado.

### English

Inside the `frontend` folder, there is an example file:

```text
frontend/.env.example
```

Content:

```env
VITE_API_URL=http://localhost:8080
```

To run the project locally, create a file named:

```text
frontend/.env
```

With the following content:

```env
VITE_API_URL=http://localhost:8080
```

For frontend tests, you can also create locally:

```text
frontend/.env.test
```

With the following content:

```env
VITE_API_URL=http://localhost:8080
```

The `.env` and `.env.test` files are ignored by Git. Only `.env.example` should be versioned.

---

# Banco de dados / Database

## Português

Banco utilizado:

```text
PostgreSQL
```

Nome do banco:

```text
narutodb
```

## English

Database used:

```text
PostgreSQL
```

Database name:

```text
narutodb
```

---

# Como rodar o backend / How to Run the Backend

## Português

Na raiz do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou rode diretamente pela IDE NetBeans.

A API ficará disponível em:

```text
http://localhost:8080
```

## English

From the project root, run:

```powershell
.\mvnw.cmd spring-boot:run
```

Or run it directly from NetBeans IDE.

The API will be available at:

```text
http://localhost:8080
```

---

# Documentação Swagger / Swagger Documentation

## Português

Com o backend rodando, acesse:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## English

With the backend running, access:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

---

---

# Como rodar com Docker / How to Run with Docker

## Português

O projeto possui configuração Docker para subir a aplicação completa com:

```text
PostgreSQL
Backend Spring Boot
Frontend React com Nginx
```

Serviços definidos no `docker-compose.yml`:

```text
postgres
backend
frontend
```

Portas utilizadas:

```text
PostgreSQL Docker → localhost:5433
Backend API       → localhost:8080
Frontend Docker   → localhost:5174
```

Antes de subir os containers, configure as variáveis de ambiente no PowerShell:

```powershell
$env:DB_PASSWORD="sua-senha-do-postgres"
$env:JWT_SECRET="sua-chave-jwt-grande-e-secreta"
```

Para subir toda a aplicação:

```powershell
docker compose up -d --build
```

Para verificar os containers em execução:

```powershell
docker ps
```

Para ver os logs do backend:

```powershell
docker logs api-naruto-backend --tail 80
```

Para testar a API:

```powershell
curl.exe -I http://localhost:8080/v3/api-docs
```

Resultado esperado:

```text
HTTP/1.1 200
```

Para testar rota protegida:

```powershell
curl.exe -i http://localhost:8080/ninjas
```

Resultado esperado:

```text
HTTP/1.1 401
```

Para acessar o frontend Docker:

```text
http://localhost:5174
```

Para parar os containers:

```powershell
docker compose down
```

Para parar e remover também o volume do banco:

```powershell
docker compose down -v
```

Atenção: `docker compose down -v` remove os dados persistidos do PostgreSQL Docker.

---

## English

The project includes Docker configuration to run the complete application with:

```text
PostgreSQL
Spring Boot Backend
React Frontend with Nginx
```

Services defined in `docker-compose.yml`:

```text
postgres
backend
frontend
```

Used ports:

```text
Docker PostgreSQL → localhost:5433
Backend API       → localhost:8080
Docker Frontend   → localhost:5174
```

Before starting the containers, configure the environment variables in PowerShell:

```powershell
$env:DB_PASSWORD="your-postgres-password"
$env:JWT_SECRET="your-large-and-secure-jwt-secret"
```

To start the full application:

```powershell
docker compose up -d --build
```

To check running containers:

```powershell
docker ps
```

To check backend logs:

```powershell
docker logs api-naruto-backend --tail 80
```

To test the API:

```powershell
curl.exe -I http://localhost:8080/v3/api-docs
```

Expected result:

```text
HTTP/1.1 200
```

To test a protected route:

```powershell
curl.exe -i http://localhost:8080/ninjas
```

Expected result:

```text
HTTP/1.1 401
```

To access the Docker frontend:

```text
http://localhost:5174
```

To stop the containers:

```powershell
docker compose down
```

To stop the containers and also remove the database volume:

```powershell
docker compose down -v
```

Warning: `docker compose down -v` removes the persisted Docker PostgreSQL data.

---

## Demonstração / Demo

### Fluxo principal da API

A API permite registrar usuário, fazer login, receber um token JWT, criar missões, cadastrar ninjas, listar ninjas e aplicar controle de acesso por perfil.

### Main API flow

The API allows user registration, login, JWT token generation, mission creation, ninja creation, ninja listing, and role-based access control.

---

### 1. Registrar usuário / Register user

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "demo_user",
    "password": "123456"
  }'
```

---

### 2. Fazer login / Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "demo_user",
    "password": "123456"
  }'
```

Resposta esperada / Expected response:

```json
{
  "token": "JWT_TOKEN_AQUI",
  "role": "ROLE_USER"
}
```

---

### 3. Listar ninjas com token JWT / List ninjas with JWT token

```bash
curl -X GET http://localhost:8080/ninjas \
  -H "Authorization: Bearer JWT_TOKEN_AQUI"
```

---

### 4. Criar missão / Create mission

```bash
curl -X POST http://localhost:8080/missoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer JWT_TOKEN_AQUI" \
  -d '{
    "missao": "Exame Chunin",
    "rankingDaMissao": "B"
  }'
```

---

### 5. Criar ninja vinculado à missão / Create ninja linked to mission

```bash
curl -X POST http://localhost:8080/ninjas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer JWT_TOKEN_AQUI" \
  -d '{
    "nome": "Naruto Uzumaki",
    "email": "naruto@konoha.com",
    "idade": 18,
    "missaoId": 1
  }'
```

---

### 6. Atualizar ninja / Update ninja

```bash
curl -X PUT http://localhost:8080/ninjas/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer JWT_TOKEN_AQUI" \
  -d '{
    "nome": "Naruto Uzumaki Atualizado",
    "email": "naruto@konoha.com",
    "idade": 19,
    "missaoId": 1
  }'
```

---

### 7. Regra de autorização / Authorization rule

Usuários com `ROLE_USER` podem acessar, listar, cadastrar e atualizar dados permitidos pela API.

Users with `ROLE_USER` can access, list, create, and update API resources according to the configured permissions.

A exclusão de ninjas é restrita a usuários com `ROLE_ADMIN`.

Deleting ninjas is restricted to users with `ROLE_ADMIN`.

```bash
curl -X DELETE http://localhost:8080/ninjas/1 \
  -H "Authorization: Bearer JWT_TOKEN_AQUI"
```

Resultado esperado para usuário comum / Expected result for regular user:

```http
403 Forbidden
```

Resultado esperado para administrador / Expected result for administrator:

```http
204 No Content
```

---

### Observação de segurança / Security note

Não utilize tokens reais, senhas reais ou credenciais sensíveis nos exemplos do README.

Do not use real tokens, real passwords, or sensitive credentials in the README examples.

Use apenas valores demonstrativos, como:

Use only placeholder values, such as:

```text
<JWT_TOKEN_AQUI>
demo_user
123456
```
---

---

# Como rodar o frontend / How to Run the Frontend

## Português

Entre na pasta do frontend:

```powershell
cd frontend
```

Instale as dependências:

```powershell
npm.cmd install
```

Rode o projeto:

```powershell
npm.cmd run dev
```

O frontend ficará disponível em:

```text
http://localhost:5173
```

## English

Enter the frontend folder:

```powershell
cd frontend
```

Install dependencies:

```powershell
npm.cmd install
```

Run the project:

```powershell
npm.cmd run dev
```

The frontend will be available at:

```text
http://localhost:5173
```

---

# Testes do backend / Backend Tests

## Português

Na raiz do projeto, execute:

```powershell
.\mvnw.cmd test
```

Testes implementados:

- Testes do JwtService
- Testes do UserService
- Testes de segurança do NinjaController
- Testes de regras de negócio do NinjaService

## English

From the project root, run:

```powershell
.\mvnw.cmd test
```

Implemented tests:

- JwtService tests
- UserService tests
- NinjaController security tests
- NinjaService business rule tests

---

# Testes do frontend / Frontend Tests

## Português

Entre na pasta do frontend:

```powershell
cd frontend
```

Execute:

```powershell
npm.cmd run test
```

Testes implementados:

- Testes de utilitários de erro da API
- Testes dos services com fetch simulado
- Testes do LoginForm
- Testes do NinjaList
- Testes do NinjaForm
- Testes de integração do App

## English

Enter the frontend folder:

```powershell
cd frontend
```

Run:

```powershell
npm.cmd run test
```

Implemented tests:

- API error utility tests
- Service tests with mocked fetch
- LoginForm tests
- NinjaList tests
- NinjaForm tests
- App integration tests

---

# Validação de qualidade do frontend / Frontend Quality Validation

## Português

Dentro da pasta `frontend`, execute:

```powershell
npm.cmd run lint
npm.cmd run build
```

O comando `lint` valida o padrão do código.

O comando `build` gera a versão de produção do frontend.

## English

Inside the `frontend` folder, run:

```powershell
npm.cmd run lint
npm.cmd run build
```

The `lint` command validates code quality rules.

The `build` command generates the production version of the frontend.

---

# Estrutura do projeto / Project Structure

```text
ApiNarutonetbeans/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── types/
│   │   ├── utils/
│   │   └── test/
│   ├── .env.example
│   ├── package.json
│   └── vite.config.ts
├── pom.xml
└── README.md
```

---

# Status do projeto / Project Status

## Português

Projeto em desenvolvimento com foco em aprendizado, boas práticas e construção de portfólio fullstack.

Principais pontos já implementados:

- Backend REST com Spring Boot
- Autenticação JWT
- Autorização por roles
- PostgreSQL
- DTOs e mappers
- Tratamento global de exceções
- Swagger/OpenAPI
- Frontend React com TypeScript
- CRUD integrado
- Testes automatizados
- Validação com lint e build
- Configuração segura com variáveis de ambiente

## English

Project under development with a focus on learning, best practices and building a fullstack portfolio.

Main features already implemented:

- REST backend with Spring Boot
- JWT authentication
- Role-based authorization
- PostgreSQL
- DTOs and mappers
- Global exception handling
- Swagger/OpenAPI
- React frontend with TypeScript
- Integrated CRUD
- Automated tests
- Lint and build validation
- Secure configuration with environment variables