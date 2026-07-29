# Changelog

Todas as mudanças importantes deste projeto serão documentadas neste arquivo.

O formato segue o padrão Keep a Changelog.

---

## [0.8.0]

### Adicionado

- Comando `leia`
- Suporte à leitura de variáveis dos tipos `texto`, `numero` e `logico`
- Conversão automática de entrada para `String`, `BigDecimal` e `Boolean`
- Suporte a comentários utilizando `comentario` ... `fim comentario`
- Operador `resto`
- AST para o comando `Leia`
- Programa **Sophia Showcase** cobrindo os principais recursos da linguagem

### Alterado

- Refatoração da avaliação numérica através do método `numero()`
- Refatoração da conversão textual através do método `texto()`
- Concatenação entre textos, números e valores lógicos
- Impressão de valores lógicos utilizando `verdadeiro` e `falso`
- Melhorias na organização do interpretador e do analisador sintático

### Corrigido

- Correções na leitura de valores lógicos
- Correções na concatenação envolvendo tipos diferentes
- Correções na avaliação de comparadores
- Correções na execução do comando `leia`

---

## [0.7.0]

### Adicionado

- Comando `enquanto`
- Comando `para`
- Suporte a laços aninhados
- AST para os comandos `Enquanto` e `Para`
- Execução de laços no interpretador
- Impressão da árvore sintática (`toTree`) para estruturas de repetição
- Conjunto completo de testes para `enquanto`
- Conjunto completo de testes para `para`

### Alterado

- Conversão automática entre `Integer`, `Long`, `Double` e `BigDecimal` durante avaliações numéricas.
- Atualização do contexto de variáveis durante a execução do comando `para`.
- Melhorias na impressão da AST.

### Corrigido

- Erro de `ClassCastException` durante operações numéricas.
- Avaliação correta do operador `não`.
- Avaliação correta de operadores lógicos aninhados.
- Correções na execução de comparações envolvendo variáveis do laço.

---

## [0.6.0]

### Adicionado

- Lexer
- Parser
- AST
- Interpretador
- Variáveis (`numero`, `texto`, `logico`)
- Atribuições
- Operações matemáticas
- Comparações
- Operadores lógicos (`e`, `ou`, `nao`)
- Estruturas condicionais (`se` / `senao`)
- Impressão da árvore sintática (`toTree`)

---

## [0.5.0]

### Adicionado

- Primeira versão funcional do lexer.
- Primeiras estruturas da AST.