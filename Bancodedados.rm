---

## Documentação do Esquema do Banco de Dados - Rede Social

### Banco de Dados
- **Nome**: RedeSocial

### Tabelas

#### 1. USUARIO
- **Descrição**: Armazena informações dos usuários da rede social.
- **Colunas**:
  - `COD_USUARIO`: ID do usuário (chave primária, auto-incremento).
  - `NOM_USUARIO`: Nome de usuário.
  - `DES_SENHA`: Senha do usuário.
  - `DES_EMAIL`: E-mail do usuário.
  - `COD_MENSAGEM`: ID da mensagem associada ao usuário (chave estrangeira).
  - `COD_SESSAO`: ID da sessão associada ao usuário (chave estrangeira).

#### 2. SESSAO
- **Descrição**: Registra as sessões de login e logout dos usuários.
- **Colunas**:
  - `COD_SESSAO`: ID da sessão (chave primária, auto-incremento).
  - `DTA_LOGIN`: Data e hora do login.
  - `DTA_LOGOUT`: Data e hora do logout.

#### 3. AMIGO
- **Descrição**: Mantém o registro das amizades entre os usuários.
- **Colunas**:
  - `COD_AMIZADE`: ID da amizade (chave primária, auto-incremento).
  - `QTA_AMIGO_USUARIO`: Quantidade de amigos que o usuário tem.
  - `DES_AMIGO_USUARIO`: Descrição da amizade.
  - `COD_AMIGO`: ID do amigo (usuário amigo).
  - `COD_USUARIO`: ID do usuário (chave estrangeira).

#### 4. MENSAGEM
- **Descrição**: Armazena as mensagens trocadas entre os usuários.
- **Colunas**:
  - `COD_MENSAGEM`: ID da mensagem (chave primária, auto-incremento).
  - `DES_MENSAGEM`: Conteúdo da mensagem.
  - `DTA_ENVIO`: Data e hora de envio da mensagem.
  - `COD_REMETENTE`: ID do usuário que enviou a mensagem.
  - `COD_DESTINATARIO`: ID do usuário que recebeu a mensagem.

### Relacionamentos

- **USUARIO -> MENSAGEM**: Um usuário pode estar associado a várias mensagens, mas cada mensagem está associada a apenas um usuário como remetente.
- **USUARIO -> SESSAO**: Um usuário pode ter várias sessões, mas cada sessão está associada a apenas um usuário.
- **AMIGO -> USUARIO**: A tabela AMIGO referencia a tabela USUARIO duas vezes: uma para o usuário e outra para o amigo, representando uma relação muitos-para-muitos entre os usuários.

### Observações

- As tabelas `SESSAO` e `MENSAGEM` devem ser criadas antes da tabela `USUARIO` devido às dependências das chaves estrangeiras.
- A coluna `COD_MENSAGEM` na tabela `USUARIO` sugere que cada usuário está associado a apenas uma mensagem. Se a intenção é associar várias mensagens a cada usuário, essa relação pode precisar ser revista.
- A tabela `AMIGO` poderia potencialmente ser simplificada para apenas conter os IDs dos usuários amigos, removendo a necessidade da coluna `QTA_AMIGO_USUARIO` e `DES_AMIGO_USUARIO`, a menos que essas colunas sirvam a um propósito específico não evidente pela estrutura atual.

---
