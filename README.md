# 📚 API de Resenhas Literárias

Esta é uma API RESTful para gerenciamento de livros, gêneros literários, avaliações, recomendações personalizadas e muito mais. A API foi projetada para ser escalável, segura e eficiente, utilizando práticas recomendadas de desenvolvimento e CI/CD.

## 🚀 Funcionalidades

A API oferece várias funcionalidades, incluindo, mas não se limitando a:

- **Gerenciamento de Livros**: Criar, atualizar, listar e excluir livros.
- **Gerenciamento de Autores**: Criar, listar e buscar autores.
- **Avaliações de Livros**: Permite que os usuários avaliem e comentem livros.
- **Recomendações Personalizadas**: Sugerir livros com base no histórico de leitura e preferências.
- **Gêneros Literários**: Gerenciar e listar gêneros literários.
- **Pesquisa Avançada de Livros**: Consultas SQL avançadas para buscar livros por autor, gênero, avaliação e mais.
- **Histórico de Leituras**: Monitoramento das leituras realizadas por cada usuário, permitindo sugestões mais precisas.
- **Funcionalidade de Lista de Desejos**: Permite que os usuários criem e compartilhem listas de livros desejados.
- **Relatórios e Estatísticas**: Geração de relatórios sobre livros mais populares, mais avaliados, entre outros.

## 🛠️ Tecnologias e Arquitetura

- **Backend**: Desenvolvido com **Java 17**, utilizando o framework **Spring Boot** para APIs RESTful.
- **Banco de Dados**: **MySQL**, com uso de **JPA/Hibernate** para mapeamento de entidades. Consultas SQL avançadas são otimizadas para alta performance.
- **Segurança**:
  - **Autenticação**: Utiliza **JWT** (JSON Web Token) para autenticação.
  - **Criptografia**: Senhas de usuários são protegidas com o algoritmo **PBKDF2**.
  - **Controle de Acesso**: Implementação de **Roles & Permissions** para garantir que apenas usuários autorizados acessem determinadas funcionalidades.
- **CI/CD**: Integração contínua e entrega contínua com **GitHub Actions** e **Jenkins**, incluindo testes automatizados e deploy em servidores de produção.
- **Maven**: Gerenciamento de dependências e construção automatizada com **Maven**.
- **Testes**: Implementação de testes unitários com **JUnit** e **Mockito**, além de testes de integração com **Postman**.

## 🔒 Práticas de Segurança

A segurança da aplicação é uma prioridade, com diversas camadas de proteção implementadas:

1. **Autenticação e Autorização**:
   - **JWT** é utilizado para a autenticação de usuários, garantindo sessões seguras.
   - **PBKDF2** é utilizado para criptografar as senhas dos usuários.
   - Implementação de **OAuth 2.0** para integração com sistemas externos de autenticação, como Google e Facebook.

2. **Proteção contra ataques comuns**:
   - **CORS**: Configuração adequada para evitar requisições maliciosas de outros domínios.
   - **Rate Limiting**: Limitação de requisições para evitar abusos do sistema.
   - **Prevenção de SQL Injection**: Utilização de Prepared Statements para garantir a segurança nas consultas ao banco de dados.
   - **XSS (Cross-Site Scripting)**: Proteção contra injeção de scripts maliciosos nas entradas do usuário.

## 🔍 Consultas SQL Avançadas

A API suporta consultas complexas para facilitar a busca de livros e autores.

## 🧩 Design Patterns

A aplicação utiliza diversos padrões de design para garantir um código limpo, escalável e de fácil manutenção:

- **Factory Pattern**: Usado para criar objetos de tipo genérico de acordo com os parâmetros fornecidos, como a criação de novos livros ou autores.
- **Singleton Pattern**: Para gerenciar instâncias de objetos compartilhados, como a conexão com o banco de dados.
- **Observer Pattern**: Implementado para notificar os usuários sobre novas avaliações ou recomendações.
- **Decorator Pattern**: Utilizado para adicionar funcionalidades extras em objetos sem alterar seu comportamento original, como filtros de busca.

## ⚙️ Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/Jondrewd/API-livros.git

1. Rode a Aplicação:
   ```bash
   ./mvnw spring-boot:run

