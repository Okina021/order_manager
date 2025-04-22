# 📦 Orders Manager API

Este projeto implementa uma API REST completa para gerenciamento de pedidos (orders), clientes (customers), produtos (products) e categorias (categories). O sistema permite criar, atualizar, listar e excluir essas entidades, garantindo uma gestão eficiente e integrada dos pedidos em um ambiente empresarial.

## 🚀 Visão Geral

O Order Manager é uma solução robusta para empresas que precisam gerenciar seu fluxo de pedidos de forma eficiente. A API foi desenvolvida seguindo as melhores práticas de desenvolvimento, com foco em segurança, escalabilidade e facilidade de uso.

## 🔑 Funcionalidades

### 🔐 Autenticação e Segurança

* Login via JWT (JSON Web Token)
* Proteção de endpoints com Spring Security
* Controle de acesso baseado em perfis de usuário
* Tokens com tempo de expiração configurável

### 👥 Customers (Clientes)

* Criar um novo cliente
* Atualizar informações de um cliente existente
* Listar todos os clientes paginados
* Buscar cliente por ID
* Excluir um cliente
* Validação de dados de entrada (email, telefone, etc.)

### 📋 Orders (Pedidos)

* Criar um novo pedido para um cliente
* Listar todos os pedidos paginados
* Buscar pedido por ID
* Excluir um pedido
* Atualizar status do pedido
* Filtrar pedidos por status, data ou cliente
* Cálculo automático de valores totais

### 🛒 Products (Produtos)

* Criar um novo produto
* Listar todos os produtos paginados
* Buscar produto por ID
* Atualizar um produto
* Controle de estoque integrado
* Validação de preços e disponibilidade

### 🏷️ Categories (Categorias)

* Criar uma nova categoria
* Listar todas as categorias paginadas
* Buscar categoria por ID
* Buscar categoria pelo nome
* Associação de produtos a múltiplas categorias

### 📝 Order Items (Itens do Pedido)

* Criar um novo item de pedido
* Listar todos os itens do pedido paginados
* Buscar item de pedido por ID
* Atualizar quantidade de itens
* Cálculo automático de subtotais

## 🛠️ Tecnologias Utilizadas

* **Java 21** - Linguagem de programação principal
* **Spring Boot** - Framework para desenvolvimento de aplicações Java
  * Spring Web - Para criação de endpoints REST
  * Spring Security - Para segurança e autenticação
  * Spring Data JPA - Para acesso a dados
  * Validation - Para validação de dados de entrada
* **JWT (JSON Web Token)** - Para autenticação segura
* **Hibernate** - ORM para mapeamento objeto-relacional
* **MySQL** - Banco de dados relacional
* **Docker** - Para containerização (opcional)
* **Swagger/OpenAPI** - Para documentação interativa da API
* **Maven** - Gerenciamento de dependências e build
* **UUID** - Identificadores únicos para entidades

## ⚙️ Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

* Java 17 ou superior
* Maven ou Gradle
* Banco de dados MySQL (ou outro de sua preferência)
* Postman, Insomnia ou outro cliente HTTP para testar a API

## 🚀 Instalação e Execução

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

### 🔹 Executando a aplicação

Com Maven:

```bash
mvn spring-boot:run
```

Ou, se estiver usando Gradle:

```bash
./gradlew bootRun
```

## 🔐 Autenticação e Segurança

A API utiliza JWT para autenticação. Antes de acessar endpoints protegidos, você deve obter um token JWT.

Faça uma requisição POST para:

```
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

Após obter o token, inclua no header das requisições protegidas:

```
Authorization: Bearer SEU_TOKEN
```

## 📖 Documentação da API (Swagger)

Após rodar a aplicação, acesse a documentação interativa:
🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Lá você pode testar os endpoints diretamente no navegador.

## 🧪 Testando a API

Você pode testar a API com Postman, Insomnia ou usando curl.

Exemplo de requisição para criar um cliente (com autenticação):

```bash
          curl --location 'localhost:8080/api/v1/customers' \
          --header 'Content-Type: application/json' \
          --header 'Authorization: Bearer TOKEN \
          --data-raw '{
          	"name": "Test",
          	"surname": "Test",
              "doc": "99999999999",
              "email":"test@example.com",
              "type":"PF" 
          }'
```

## 📁 Estrutura do Projeto

```
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

## 🔄 Fluxo de Dados

1. O cliente faz uma requisição autenticada para a API
2. O controlador recebe a requisição e valida os dados de entrada
3. O serviço processa a requisição e aplica as regras de negócio
4. O repositório interage com o banco de dados
5. O serviço retorna os dados processados
6. O controlador formata a resposta e a envia ao cliente

## 📊 Modelos de Dados

### Customer (Cliente)
- id: UUID (identificador único)
- name: String (nome do cliente)
- email: String (email do cliente, único)
- phone: String (telefone do cliente)
- createdAt: LocalDateTime (data de criação)
- updatedAt: LocalDateTime (data de atualização)

### Product (Produto)
- id: UUID (identificador único)
- name: String (nome do produto)
- description: String (descrição do produto)
- price: BigDecimal (preço do produto)
- stock: Integer (quantidade em estoque)
- categories: List<Category> (categorias do produto)
- createdAt: LocalDateTime (data de criação)
- updatedAt: LocalDateTime (data de atualização)

### Category (Categoria)
- id: UUID (identificador único)
- name: String (nome da categoria, único)
- description: String (descrição da categoria)
- products: List<Product> (produtos da categoria)
- createdAt: LocalDateTime (data de criação)
- updatedAt: LocalDateTime (data de atualização)

### Order (Pedido)
- id: UUID (identificador único)
- customer: Customer (cliente que fez o pedido)
- status: OrderStatus (status do pedido: PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
- items: List<OrderItem> (itens do pedido)
- totalAmount: BigDecimal (valor total do pedido)
- createdAt: LocalDateTime (data de criação)
- updatedAt: LocalDateTime (data de atualização)

### OrderItem (Item do Pedido)
- id: UUID (identificador único)
- order: Order (pedido ao qual o item pertence)
- product: Product (produto do item)
- quantity: Integer (quantidade do produto)
- unitPrice: BigDecimal (preço unitário do produto no momento da compra)
- subtotal: BigDecimal (preço unitário * quantidade)

## 🔜 Melhorias Futuras

* Implementação de testes unitários com JUnit
* Cache para melhorar a performance das consultas
* Sistema de notificações por email
* Dashboard administrativo
* Relatórios de vendas e estoque
* Integração com sistemas de pagamento
* Implementação de GraphQL para consultas mais flexíveis
* Suporte a múltiplos idiomas

## 👨‍💻 Autor

Desenvolvido por **Okina021** 🚀

Se precisar de melhorias ou tiver dúvidas, fique à vontade para contribuir ou abrir uma issue.

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
