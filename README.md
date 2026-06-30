# API Naruto Fullstack

Projeto fullstack para gerenciamento de ninjas, missões e autenticação de usuários, desenvolvido com Spring Boot no backend e React com TypeScript no frontend.

O projeto simula uma aplicação real com autenticação JWT, controle de permissões por role, CRUD de ninjas, integração com banco PostgreSQL, documentação Swagger e testes automatizados.

---

## Tecnologias utilizadas

### Backend

- Java
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

### Frontend

- React
- TypeScript
- Vite
- CSS
- Vitest
- React Testing Library

---

## Funcionalidades

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

---

## Regra de negócio principal

Ninjas menores de 18 anos não podem participar de missões Rank A.

Essa regra é aplicada tanto no cadastro quanto na atualização de ninjas.

---

## Segurança

A API utiliza autenticação JWT.

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

---

## Variáveis de ambiente

### Backend

A aplicação utiliza uma variável de ambiente para a chave JWT:

```env
JWT_SECRET=sua-chave-secreta-aqui
```

No Windows, exemplo:

```powershell
setx JWT_SECRET "sua-chave-secreta-aqui"
```

A chave real não deve ser enviada para o GitHub.

---

### Frontend

Dentro da pasta `frontend`, crie o arquivo `.env`:

```env
VITE_API_URL=http://localhost:8080
```

Para testes, também pode existir o arquivo:

```text
frontend/.env.test
```

Com o conteúdo:

```env
VITE_API_URL=http://localhost:8080
```

---

## Banco de dados

Banco utilizado:

```text
PostgreSQL
```

Nome do banco:

```text
narutodb
```

---

## Como rodar o backend

Na raiz do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou rode diretamente pela IDE NetBeans.

A API ficará disponível em:

```text
http://localhost:8080
```

---

## Documentação Swagger

Com o backend rodando, acesse:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

---

## Como rodar o frontend

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

---

## Testes do backend

Na raiz do projeto, execute:

```powershell
.\mvnw.cmd test
```

Testes implementados:

- Testes do JwtService
- Testes do UserService
- Testes de segurança do NinjaController
- Testes de regras de negócio do NinjaService

---

## Testes do frontend

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

---

## Validação de qualidade do frontend

Dentro da pasta `frontend`, execute:

```powershell
npm.cmd run lint
npm.cmd run build
```

O comando `lint` valida o padrão do código.

O comando `build` gera a versão de produção do frontend.

---

## Estrutura do projeto

```text
ApiNarutonetbeans/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── types/
│   │   ├── utils/
│   │   └── test/
│   ├── package.json
│   └── vite.config.ts
├── pom.xml
└── README.md
```

---

## Status do projeto

Projeto em desenvolvimento com foco em aprendizado, boas práticas e construção de portfólio fullstack.

Principais pontos já implementados:

- Backend REST com Spring Boot
- Autenticação JWT
- Autorização por roles
- PostgreSQL
- DTOs e mappers
- Tratamento global de exceções
- Swagger
- Frontend React com TypeScript
- CRUD integrado
- Testes automatizados
- Validação com lint e build