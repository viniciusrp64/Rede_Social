
# Rede Social em JavaFX

Este projeto é uma aplicação de rede social desenvolvida em Java, utilizando a biblioteca JavaFX para a interface gráfica do usuário. A aplicação permite aos usuários interagirem através de mensagens e manterem uma lista de amigos. O sistema utiliza um banco de dados MySQL para armazenar as informações dos usuários, suas sessões, amizades e mensagens trocadas.

## Requisitos

- Java Development Kit (JDK) 8 ou superior
- MySQL Server 5.7 ou superior
- JavaFX SDK para o desenvolvimento da interface gráfica
- JDBC Driver para conexão com o banco de dados MySQL

## Configuração do Ambiente

1. Instale a JDK apropriada para seu sistema operacional.
2. Instale o MySQL Server e o MySQL Workbench.
3. Instale o JavaFX SDK e configure-o em seu ambiente de desenvolvimento.
4. Clone o repositório do projeto para sua máquina local.

## Configuração do Banco de Dados

Execute os scripts SQL fornecidos para criar e configurar o banco de dados `RedeSocial`. Certifique-se de que o banco de dados esteja rodando antes de iniciar a aplicação.

## Executando a Aplicação

1. Abra o projeto no seu ambiente de desenvolvimento integrado (IDE) preferido.
2. Resolva quaisquer dependências que possam estar faltando (normalmente, o IDE pode ajudar com isso).
3. Execute a classe `Main` para iniciar a aplicação.

## Estrutura do Projeto

O projeto está estruturado da seguinte maneira:

- `src/main/java/br/com/projeto/redesocial`: Contém o código-fonte da aplicação.
  - `Main.java`: Classe principal que inicia a aplicação JavaFX.
  - `MenuController.java`: Controlador responsável pela lógica da interface do menu principal.
- `src/main/resources/br/com/projeto/redesocial`: Contém os arquivos FXML e recursos visuais.

## Integrantes

Este projeto foi desenvolvido por:

- *Matheus Fernandes*
  - RA: 12116183

- *Talita Paiva Diniz*
  - RA: 12114621

- *Thiago Henrique Adão*
  - RA: 12113928

- *Vinícius de Aquino Reis*
  - RA: 12118578

- *Vinícius Ribeiro*
  - RA: 12118124

## Funcionalidades

- Login de usuários
- Exibição e gerenciamento de lista de amigos
- Envio e recebimento de mensagens
- Adição e remoção de amigos
