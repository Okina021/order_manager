# 📦 Orders Manager API

Este projeto implementa uma API REST para gerenciamento de pedidos (orders), clientes (customers), produtos (products) e categorias (categories). O sistema permite criar, atualizar, listar e excluir essas entidades, garantindo uma gestão eficiente dos pedidos.

## 🚀 Funcionalidades

### 📌 Autenticação e Segurança
- Login via JWT (JSON Web Token)
- Proteção de endpoints com Spring Security

### 📌 Customers (Clientes)
- Criar um novo cliente
- Atualizar informações de um cliente existente
- Listar todos os clientes paginados
- Buscar cliente por ID
- Excluir um cliente

### 📌 Orders (Pedidos)
- Criar um novo pedido para um cliente
- Listar todos os pedidos paginados
- Buscar pedido por ID
- Excluir um pedido
- Atualizar status do pedido

### 📌 Products (Produtos)
- Criar um novo produto
- Listar todos os produtos paginados
- Buscar produto por ID
- Atualizar um produto

### 📌 Categories (Categorias)
- Criar uma nova categoria
- Listar todas as categorias paginadas
- Buscar categoria por ID
- Buscar categoria pelo nome

### 📌 Order Items (Itens do Pedido)
- Criar um novo item de pedido
- Listar todos os itens do pedido paginados
- Buscar item de pedido por ID

## 🛠️ Tecnologias Utilizadas
- Java 21
- Spring Boot (Spring Web, Spring Security, Spring Data JPA, Validation)
- JWT (JSON Web Token) (Autenticação)
- Hibernate
- MySQL
- Docker (opcional para subir o banco de dados)
- Swagger/OpenAPI (documentação da API)
- Maven

## ⚙️ Pré-requisitos
Antes de rodar o projeto, certifique-se de ter instalado:
- Java 17 ou superior
- Maven ou Gradle
- Banco de dados MySQL (ou outro de sua preferência)
- Postman, Insomnia ou outro cliente HTTP para testar a API

## 📥 Instalação e Execução

### 🔹 Clonando o repositório
```bash
git clone git@github.com:Okina021/order_manager.git
cd order_manager
```

### 🔹 Configurando o Banco de Dados
Certifique-se de que tem um banco de dados MySQL rodando. Se estiver usando Docker, pode subir um container MySQL com:
```bash
docker run --name mysql-orders -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=orders_db -p 3306:3306 -d mysql:latest
```
Caso prefira configurar manualmente, altere as credenciais no `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/orders_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 🔹 Rodando a aplicação
```bash
mvn spring-boot:run
```
Ou, se estiver usando Gradle:
```bash
./gradlew bootRun
```

## 🔑 Autenticação e Segurança
A API utiliza JWT para autenticação.
Antes de acessar endpoints protegidos, você deve obter um token JWT.

### 📌 Gerando Token (Login)
Faça uma requisição POST para:
```bash
POST /api/v1/auth/login
```
**Body:**
```json
{
  "username": "seu_email@example.com",
  "password": "sua_senha"
}
```
**Resposta (Exemplo):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5..."
}
```

### 📌 Usando o Token
Após obter o token, inclua no header das requisições protegidas:
```makefile
Authorization: Bearer SEU_TOKEN
```

## 📖 Documentação da API (Swagger)
Após rodar a aplicação, acesse a documentação interativa:
🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Lá você pode testar os endpoints diretamente no navegador.

## 🛠️ Testando a API
Você pode testar a API com Postman, Insomnia ou usando curl.

Exemplo de requisição para criar um cliente (com autenticação):
```bash
curl -X POST http://localhost:8080/api/v1/customers \
     -H "Authorization: Bearer SEU_TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
          "name": "João Silva",
          "email": "joao@email.com",
          "phone": "11999999999"
     }'
```

## 🏗️ Estrutura do Projeto
```bash
order_manager/
│-- src/main/java/com/example/project_orders_manager/
│   ├── controllers/        # Controladores da API
│   ├── domain/dto/         # DTOs (Data Transfer Objects)
│   ├── domain/entities/    # Entidades do banco de dados
│   ├── domain/enums/       # Enumeradores
│   ├── domain/exeptions/   # Camada de tratamento de exceções personalisadas
│   ├── repositories/       # Camada de acesso ao banco (Spring Data JPA)
│   ├── services/           # Regras de negócio (Service Layer)
│   ├── security/           # Implementação de JWT e Spring Security
│   ├── config/             # Configurações (Swagger, Segurança, etc.)
│-- src/main/resources/
│   ├── application.properties  # Configuração do banco de dados
│-- pom.xml (dependências do Maven)
```

## 📝 Melhorias Futuras
- Implementação de testes unitários com JUnit
- Cache para melhorar a performance das consultas

## 👨‍💻 Autor
Desenvolvido por **Okina021** 🚀

Se precisar de melhorias ou tiver dúvidas, fique à vontade para contribuir ou abrir uma issue.
