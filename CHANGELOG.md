# Changelog

Todas as mudanças importantes deste projeto serão documentadas neste arquivo.

O formato segue o padrão Keep a Changelog.

---

## [0.7.0] - 2026-07-10

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

## [0.6.0] - 2026-07-09

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