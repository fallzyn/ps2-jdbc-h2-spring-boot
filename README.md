# Gerenciador de Nomes — JDBC + PostgreSQL

Solução do exemplo **Gerenciador de Nomes**, utilizando JDBC para persistência dos dados em um banco PostgreSQL hospedado no Supabase.

Esta versão corresponde à implementação final desenvolvida em aula.

## Objetivo

O projeto parte da interface `GerenciadorNomes`, que define as operações básicas de CRUD:

* adicionar um nome;
* obter os nomes cadastrados;
* atualizar um nome;
* remover um nome.

Na versão inicial do projeto, essas operações eram implementadas pela classe `GerenciadorNomesLista`, utilizando uma `List<String>` em memória.

Nesta solução foi criada uma nova implementação:

```text
GerenciadorNomes
       ▲
       │
       ├── GerenciadorNomesLista
       │      List<String>
       │
       └── GerenciadorNomesBD
              JDBC + PostgreSQL
```

A interface `GerenciadorNomes` permanece a mesma. Apenas a implementação responsável pelo armazenamento dos dados foi substituída.

## Tecnologias utilizadas

* Java
* Spring Boot
* Maven
* JDBC
* PostgreSQL (Supabase)

O Spring Boot é utilizado como estrutura básica para criação e execução do projeto. O acesso ao banco de dados é realizado diretamente através da API JDBC.

## Operações implementadas

| Operação | Método        | SQL      |
| -------- | ------------- | -------- |
| Create   | `adicionar()` | `INSERT` |
| Read     | `obter()`     | `SELECT` |
| Update   | `atualizar()` | `UPDATE` |
| Delete   | `remover()`   | `DELETE` |

A implementação utiliza os principais elementos da API JDBC:

* `DriverManager`
* `Connection`
* `PreparedStatement`
* `ResultSet`

## Banco de dados

O projeto utiliza PostgreSQL no Supabase. Antes de executar, configure as variáveis de ambiente com os dados de conexão do seu projeto:

```text
SUPABASE_JDBC_URL=jdbc:postgresql://HOST:5432/postgres?sslmode=require
SUPABASE_DB_USER=SEU_USUARIO
SUPABASE_DB_PASSWORD=SUA_SENHA
```

No Linux ou macOS, defina essas variáveis no terminal antes de executar:

```bash
export SUPABASE_JDBC_URL='jdbc:postgresql://HOST:5432/postgres?sslmode=require'
export SUPABASE_DB_USER='SEU_USUARIO'
export SUPABASE_DB_PASSWORD='SUA_SENHA'
```

Não coloque a senha no código ou no repositório. A tabela deve ser criada previamente no SQL Editor do Supabase:

A tabela utilizada pelo projeto é:

```sql
CREATE TABLE IF NOT EXISTS nomes (
    nome VARCHAR(256) NOT NULL UNIQUE
);
```

Os dados são armazenados no PostgreSQL e **permanecem disponíveis entre diferentes execuções da aplicação**.

Por esse motivo, executar o programa várias vezes pode produzir resultados diferentes. Por exemplo, uma tentativa de inserir novamente um nome já cadastrado não será aceita devido à restrição `UNIQUE`.

Esse comportamento é intencional e demonstra a diferença entre o armazenamento temporário da implementação com `List<String>` e a persistência realizada pelo banco de dados.

## Executando

No terminal, execute:

```bash
mvn spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A classe `GerenciadorNomesApplication` implementa `CommandLineRunner`, portanto o exemplo é executado automaticamente após a inicialização da aplicação.

## Conceito principal

O restante da aplicação continua trabalhando com:

```java
GerenciadorNomes
```

Na implementação inicial:

```java
GerenciadorNomes gerenciador =
    new GerenciadorNomesLista();
```

Na solução com JDBC:

```java
GerenciadorNomes gerenciador =
    new GerenciadorNomesBD(connection);
```

As chamadas realizadas pela aplicação continuam as mesmas:

```java
gerenciador.adicionar(...);
gerenciador.obter();
gerenciador.atualizar(...);
gerenciador.remover(...);
```

Assim, o exemplo também demonstra como uma interface permite substituir uma implementação sem alterar o código que utiliza seu contrato.
