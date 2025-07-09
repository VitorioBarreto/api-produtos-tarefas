# Meu Projeto Spring Boot

Este é um projeto Spring Boot completo com autenticação JWT, gerenciamento de usuários, produtos e tarefas.

## 📋 Visão Geral

O projeto consiste em uma API RESTful com as seguintes características principais:
- Autenticação JWT segura
- Autorização baseada em roles (USER e ADMIN)
- CRUD completo para Produtos e Tarefas
- Validação de dados
- Tratamento de exceções global
- Padrão DTO para responses
- Banco de dados relacional (configuração necessária)

## 🛠️ Tecnologias Utilizadas

- **Backend**:
  - Java 17+
  - Spring Boot 3.x
  - Spring Security
  - JWT (JSON Web Tokens)
  - JPA/Hibernate
  - Maven (gerenciamento de dependências)

## 🔐 Autenticação e Autorização

O sistema utiliza JWT para autenticação com os seguintes endpoints:

- `POST /api/auth/login` - Autentica um usuário e retorna um token JWT
- `POST /api/auth/register` - Registra um novo usuário com role USER

Roles disponíveis:
- `ROLE_USER` - Permissões básicas
- `ROLE_ADMIN` - Permissões totais

## 📚 Endpoints da API

### 🔧 Tarefas (`/tarefas`)
- `GET /tarefas` - Lista todas as tarefas (USER/ADMIN)
- `GET /tarefas/{id}` - Busca tarefa por ID (USER/ADMIN)
- `GET /tarefas/status/{concluida}` - Filtra tarefas por status (true/false) (USER/ADMIN)
- `POST /tarefas` - Cria nova tarefa (ADMIN)
- `PUT /tarefas/{id}` - Atualiza tarefa existente (ADMIN)
- `DELETE /tarefas/{id}` - Remove tarefa (ADMIN)

### 🛒 Produtos (`/api/produtos`)
- `GET /api/produtos` - Lista todos os produtos (USER/ADMIN)
- `GET /api/produtos/{id}` - Busca produto por ID (USER/ADMIN)
- `POST /api/produtos` - Cria novo produto (ADMIN)
- `PUT /api/produtos/{id}` - Atualiza produto existente (ADMIN)
- `DELETE /api/produtos/{id}` - Remove produto (ADMIN)

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Banco de dados configurado (ex: PostgreSQL, MySQL)

### Passos para execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/meu-projeto-spring.git
   cd meu-projeto-spring
   ```

2. Configure o banco de dados no `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/nome_banco
   spring.datasource.username=usuario
   spring.datasource.password=senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Configure o JWT no `application.properties`:
   ```properties
   jwt.secret=sua-chave-secreta-base64-aqui-pelo-menos-32-caracteres
   jwt.expiration.ms=86400000 # 24 horas
   ```

4. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

## 📦 Estrutura do Projeto

```
src/main/java/com/vitoriobarreto/meu_projeto/
├── config/            # Configurações do Spring
├── controller/        # Controladores REST
├── dto/               # Objetos de Transferência de Dados
├── exception/         # Classes de exceção customizadas
├── model/             # Entidades JPA
├── repository/        # Interfaces JpaRepository
├── security/          # Configurações de segurança
│   ├── jwt/           # Componentes JWT
│   └── services/      # Serviços de segurança
├── service/           # Lógica de negócio
└── MeuProjetoApplication.java # Classe principal
```

## 🤝 Contribuição

Contribuições são bem-vindas! Siga os passos:

1. Faça um fork do projeto
2. Crie sua branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

Desenvolvido com ❤️ por [Vitório Barreto]
