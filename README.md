# Sophia

> Uma linguagem de programação em português criada para ensinar lógica de programação de forma simples, intuitiva e acessível.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-orange)
![Versão](https://img.shields.io/badge/version-0.6.0-blue)
![Licença](https://img.shields.io/badge/license-MIT-green)

---

## O que é a Sophia?

A Sophia é uma linguagem de programação desenvolvida para facilitar o aprendizado de algoritmos e programação.

Seu principal objetivo é permitir que crianças, adolescentes e adultos aprendam programação utilizando uma sintaxe próxima da linguagem natural, reduzindo a necessidade de memorizar símbolos e convenções presentes em linguagens tradicionais.

Ao invés de escrever:

```java
if (idade >= 18 && ativo) {
    System.out.println("Permitido");
}
```

na Sophia escrevemos:

```text
se idade maior ou igual a 18
e ativo igual a verdadeiro

    escreva "Permitido"

fim
```

O foco está na lógica, não na sintaxe.

---

# Objetivos

- Tornar a programação mais acessível para iniciantes.
- Ensinar lógica de programação antes da sintaxe das linguagens tradicionais.
- Servir como ferramenta didática para escolas, universidades e cursos livres.
- Demonstrar, de forma prática, o funcionamento de um compilador e interpretador.

---

# Exemplo

```text
programa "Maioridade"

inicio

numero idade recebe 20

se idade maior ou igual a 18

    escreva "Maior de idade"

senao

    escreva "Menor de idade"

fim

fim
```

Saída

```
Maior de idade
```

---

# Recursos implementados

## Tipos

- ✅ numero
- ✅ texto
- ✅ logico

## Variáveis

- ✅ declaração
- ✅ atribuição

## Operações matemáticas

- ✅ mais
- ✅ menos
- ✅ vezes
- ✅ dividido por
- ✅ resto de

## Comparações

- ✅ igual a
- ✅ diferente de
- ✅ maior que
- ✅ menor que
- ✅ maior ou igual a
- ✅ menor ou igual a

## Operadores lógicos

- ✅ e
- ✅ ou
- ✅ nao

## Estruturas de controle

- ✅ se
- ✅ senao

---

# Arquitetura

O projeto foi desenvolvido seguindo as etapas clássicas de construção de um compilador.

```
Código Sophia
      │
      ▼
Analisador Léxico
      │
      ▼
Analisador Sintático
      │
      ▼
AST (Árvore Sintática Abstrata)
      │
      ▼
Interpretador
      │
      ▼
Resultado
```

---

# Estrutura do projeto

```
src
└── org.sophia
    ├── lexico
    ├── sintatico
    ├── ast
    │   ├── comando
    │   └── expressao
    ├── runtime
    └── util
```

---

# Roadmap

## v0.6

- ✅ Lexer
- ✅ Parser
- ✅ AST
- ✅ Interpretador
- ✅ Operações matemáticas
- ✅ Comparações
- ✅ Operadores lógicos
- ✅ Condições (`se` / `senao`)

## v0.7

- ⏳ enquanto

## v0.8

- ⏳ para

## v0.9

- ⏳ funções

## v1.0

- ⏳ documentação completa
- ⏳ playground web
- ⏳ IDE
- ⏳ primeiros materiais didáticos

---

# Filosofia

A Sophia foi criada para que o código possa ser lido quase como um texto.

Exemplo:

```text
se salario maior que 5000
e ativo igual a verdadeiro

    escreva "Cliente Premium"

fim
```

Mesmo pessoas que nunca programaram conseguem compreender a intenção do algoritmo.

---

# Público-alvo

- Ensino Fundamental
- Ensino Médio
- Cursos Técnicos
- Universidades
- Cursos livres de programação
- Pessoas que desejam aprender lógica de programação pela primeira vez

---

# Princípios da Sophia

A Sophia seguirá estes princípios durante toda sua evolução.

- A linguagem deve ser escrita em português.
- A sintaxe deve privilegiar palavras ao invés de símbolos.
- O código deve ser legível por pessoas iniciantes.
- Recursos novos não devem comprometer a simplicidade da linguagem.
- A linguagem deve ser adequada para o ensino de programação.
- Toda nova funcionalidade deve manter compatibilidade com versões anteriores sempre que possível.

---

# Como contribuir

Contribuições são bem-vindas.

Caso encontre algum problema ou tenha sugestões de melhoria, abra uma Issue ou envie um Pull Request.

---

# Licença

Este projeto está licenciado sob a licença MIT.

---

# Autor

Desenvolvido por **Anderson Fonseca**.

---

> **"Programação deve ser aprendida pela lógica, não pela quantidade de símbolos que precisamos decorar."**