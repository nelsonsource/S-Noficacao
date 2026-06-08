# S-Notificacao

API para gestão de **pedidos de documentos académicos**, estudantes, funcionários, levantamentos e documentos, desenvolvida com Spring Boot, Spring Security (JWT), JPA/Hibernate e MySQL.



## Tecnologias principais

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Security** (JWT, authorities: `ADMIN`, `SECRETARIA`, `ALUNO`)
- **Spring Data JPA / Hibernate**
- **MySQL**
- **SpringDoc OpenAPI (Swagger UI)**
- **Maven**



##  Funcionalidades

- Autenticação JWT com três perfis de acesso
- CRUD de **estudantes**, **funcionários**, **documentos**, **campos de documentos**
- Criação e gestão de **pedidos** (solicitações de documentos)
- Registo de **levantamentos** (entrega de documentos)
- Paginação, ordenação e filtros (nome, curso, data, estado)
- Documentação interativa com **Swagger UI**



## Perfis e permissões resumidas

| Perfil         | Permissões principais                                                                    |
|----------------|------------------------------------------------------------------------------------------|
| **ADMIN**      | Acesso total a todos os endpoints (criar/editar/remover qualquer recurso)                |
| **SECRETARIA** | Processar pedidos, registar levantamentos, visualizar estudantes/funcionários/documentos |
| **ALUNO**      | Criar pedidos, consultar **apenas os seus próprios pedidos**                             |
-------------------------------------------------------------------------------------------------------------

> A segurança é controlada via `SecurityConfig` + `@PreAuthorize` nos controllers.



## Como executar localmente

### Pré‑requisitos

- JDK 21
- Maven
- MySQL

### 1. Clonar o repositório

```bash
git clone git@github.com:nelsonsource/S-notificacao.git
cd sistema-notificacao
