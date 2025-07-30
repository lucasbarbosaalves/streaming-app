# Streaming App - MS do Administrador do Catálogo (Em construção)

Este repositório contém o microsserviço **catalog-admin**, parte do projeto Streaming App (um clone do Netflix). O
objetivo deste microsserviço é gerenciar o catálogo de conteúdos, como categorias, membros do elenco, gêneros, entre
outros.

## Estrutura do Projeto

O projeto está dividido em múltiplos módulos, seguindo os princípios de arquitetura limpa e DDD (Domain-Driven Design):

- **domain/**: Contém as regras de negócio e entidades do domínio. Não depende de frameworks externos, focando apenas na
  lógica do negócio.
- **application/**: Implementa os casos de uso da aplicação, orquestrando as operações do domínio e integrando com os
  módulos de infraestrutura.
- **infrastructure/**: Responsável pela integração com tecnologias externas (banco de dados, mensageria, APIs, etc).
  Aqui ficam as implementações concretas dos contratos definidos nos outros módulos.

Cada módulo possui seu próprio arquivo `build.gradle`, permitindo o gerenciamento independente de dependências e
facilitando a modularização.

## Tecnologias Utilizadas

- **Java**
- **Gradle**
- **Docker**
- **JUnit**
- **Spring Framework**
- **RabbitMQ**
- **Flyway**
- **MySQL**

## Gerenciamento de Dependências

Cada módulo possui seu próprio `build.gradle`, permitindo dependências específicas e builds independentes. O arquivo
`settings.gradle` registra todos os módulos do projeto.

## Como funciona o microsserviço

O **catalog-admin** é responsável por operações como:

- Cadastro, atualização e remoção de categorias, gêneros e membros do elenco
- Gerenciamento de vídeos no catálogo
- Listagem e busca de entidades do catálogo
- Integração com outros microsserviços do ecossistema Streaming App

A arquitetura modular permite que cada parte do sistema evolua de forma independente, facilitando testes, manutenção e
escalabilidade.

## Como subir o ambiente Docker

1. Execute `docker-compose up -d` para subir os serviços necessários (RabbitMQ e MySQL).
2. Para visualizar o RabbitMQ, acesse `http://localhost:15672` (usuário e senha padrão: guest/guest).

## Como rodar a aplicação

1. Certifique-se que o Docker está rodando.
2. Execute `./gradlew bootRun` para iniciar o microsserviço com Spring.
3. A API estará disponível em `http://localhost:8080`.

## Como rodar os testes

- Execute `./gradlew test` para rodar todos os testes unitários e de integração.
- Os relatórios de teste podem ser encontrados em `build/reports/tests/test/index.html` de cada módulo.

