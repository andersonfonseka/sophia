# Sophia

> Uma linguagem de programação em português criada para ensinar lógica de programação de forma simples, intuitiva e acessível.

![Versão](https://img.shields.io/badge/version-1.0.0-blue)
![Licença](https://img.shields.io/badge/license-MIT-green)

---

**Status:** ✅ Versão 1.0.0 — Primeira versão estável

## Sophia 1.0.0

A Sophia é uma **linguagem de programação educacional** desenvolvida para facilitar o aprendizado de lógica de programação.

Seu propósito **não é competir com linguagens de produção** como Java, Python, C#, JavaScript ou outras linguagens profissionais de mercado.

A Sophia foi projetada para que o estudante possa concentrar sua atenção na **lógica do algoritmo**, e não na complexidade da sintaxe.

A linguagem pode ser utilizada diretamente pelo **CLI**, através de arquivos `.sph`, ou de forma interativa pelo **REPL**.

---

# Por que Sophia?

Aprender programação envolve compreender variáveis, tipos, expressões, condições, repetições, funções, parâmetros, retorno, escopo e decomposição de problemas.

Linguagens profissionais, além desses conceitos, possuem uma grande quantidade de recursos, bibliotecas, frameworks e convenções necessários para o desenvolvimento de sistemas reais.

A Sophia faz uma escolha diferente: **reduzir deliberadamente a complexidade para priorizar o aprendizado da lógica de programação**.

Na Sophia, o código procura se aproximar da linguagem natural:

```text
numero idade recebe 20

se idade maior ou igual a 18
    escreva "Maior de idade"
senao
    escreva "Menor de idade"
fim
```

A intenção do algoritmo pode ser compreendida sem que o estudante precise conhecer inicialmente conceitos como `if`, `{}`, `&&`, `System.out.println()` ou `;`.

> **Programação deve ser aprendida pela lógica, não pela quantidade de símbolos que precisamos decorar.**

---

# Objetivo

O principal objetivo da Sophia é servir como uma **porta de entrada para o aprendizado de programação**.

Ela pode ser utilizada em Ensino Fundamental, Ensino Médio, Cursos Técnicos, Universidades, cursos livres, introdução à programação, introdução a algoritmos e disciplinas de lógica de programação.

A Sophia também pode ser utilizada para demonstrar conceitos relacionados à construção de linguagens de programação, como análise léxica, análise sintática, AST, interpretação, execução de programas, escopo, funções e gerenciamento de contexto.

---

# O posicionamento da Sophia

A Sophia **não pretende substituir linguagens profissionais**. Ela possui um propósito diferente.

A proposta é:

```text
                    SOPHIA
                       │
                       ▼
              Aprender programação
                       │
                       ▼
               Lógica e algoritmos
                       │
             ┌─────────┼─────────┐
             ▼         ▼         ▼
           Java      Python      C#
             │         │         │
             └─────────┼─────────┘
                       ▼
             Engenharia de Software
```

A Sophia pode ser entendida como uma **ponte para a programação profissional**.

Depois de aprender lógica, algoritmos, estruturas de controle, funções e abstração com Sophia, o estudante pode avançar para linguagens como Java, Python, C#, JavaScript, C++, Kotlin, Go ou Rust.

A intenção não é transformar a Sophia em uma alternativa a essas linguagens.

**A simplicidade da Sophia é uma decisão de projeto, não uma limitação que precisa ser eliminada.**

---

# O que a Sophia é

A Sophia é:

- uma linguagem educacional;
- uma linguagem em português;
- uma ferramenta para ensino de algoritmos;
- uma linguagem interpretada;
- uma experiência prática de construção de linguagens;
- uma linguagem que pode ser utilizada diretamente pelo terminal;
- uma linguagem que possui um REPL interativo.

# O que a Sophia não é

A Sophia não pretende ser:

- uma linguagem empresarial;
- uma plataforma de backend;
- uma substituta de Java ou Python;
- uma linguagem para aplicações de produção;
- uma plataforma completa de desenvolvimento;
- uma concorrente das linguagens profissionais de mercado.

Essa delimitação faz parte do projeto.

---

# Características

A Sophia possui uma sintaxe baseada predominantemente em palavras em português.

## Tipos

```text
numero
texto
logico
```

## Variáveis

```text
numero idade recebe 20
texto nome recebe "Sophia"
logico ativa recebe verdadeiro
```

## Entrada e saída

```text
escreva "Digite seu nome:"
leia nome
escreva nome
```

## Operações matemáticas

```text
mais
menos
vezes
dividido por
resto de
```

## Comparações

```text
igual a
diferente de
maior que
menor que
maior ou igual a
menor ou igual a
```

## Operadores lógicos

```text
e
ou
nao
```

## Condições

```text
se
senao
fim
```

## Repetições

```text
enquanto
para
fim
```

## Funções

```text
funcao dobro
    parametro numero valor
    retorno numero

inicio
    retorne valor vezes 2
fim
```

Chamando a função:

```text
escreva dobro 10
```

Resultado:

```text
20
```

---

# REPL

A Sophia possui um REPL (Read-Eval-Print Loop) que permite utilizar a linguagem de forma interativa.

Para iniciar:

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar repl
```

O REPL permite trabalhar interativamente com variáveis, estruturas de controle e funções durante a sessão.

```text
sophia> numero contador recebe 0
sophia> contador recebe contador mais 1
sophia> contador recebe contador mais 1
sophia> escreva contador
2
```

## Blocos multilinha

```text
sophia> numero i recebe 1
sophia> enquanto i menor ou igual a 3
... escreva i
... i recebe i mais 1
... fim
1
2
3
```

Também são suportadas estruturas aninhadas:

```text
sophia> numero i recebe 1
sophia> enquanto i menor ou igual a 3
... se i igual a 2
... escreva "dois"
... senao
... escreva "outro"
... fim
... i recebe i mais 1
... fim
outro
dois
outro
```

## Funções no REPL

```text
sophia> funcao dobro
... parametro numero valor
... retorno numero
... inicio
... retorne valor vezes 2
... fim

sophia> escreva dobro 3
6
```

Funções também podem receber identificadores como argumentos e chamar outras funções.

---

# CLI

O CLI permite executar programas Sophia armazenados em arquivos `.sph`.

## Executar

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar executar programa.sph
```

## Verificar

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar verificar programa.sph
```

## Modo detalhado

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar executar programa.sph --verbose
```

O modo `--verbose` permite visualizar código, símbolos e AST.

## REPL

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar repl
```

## Ajuda

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar ajuda
```

## Versão

```bash
java -jar sophia-cli-0.0.1-SNAPSHOT.jar versao
```

---

# Exemplo completo

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

Resultado:

```text
Maior de idade
```

---

# Arquitetura

```text
Código Sophia
      │
      ▼
Analisador Léxico
      │
      ▼
Analisador Sintático
      │
      ▼
AST
      │
      ▼
Interpretador
      │
      ▼
Contexto de Execução
      │
      ▼
Resultado
```

O analisador léxico transforma o código-fonte em símbolos. O analisador sintático transforma os símbolos em uma AST. O interpretador percorre a AST e executa os comandos.

Exemplo de AST:

```text
Programa
├── Titulo
├── Funcoes
└── Comandos
```

---

# Estrutura do projeto

```text
src
└── org.sophia
    ├── lexico
    ├── sintatico
    ├── compilador
    │   └── ast
    │       ├── comando
    │       ├── expressao
    │       └── funcao
    ├── runtime
    └── util
```

O projeto também possui o módulo CLI:

```text
sophia-cli
```

---

# Testes

A Sophia possui uma suíte automatizada cobrindo os principais recursos da linguagem:

- tipos;
- atribuição;
- operadores;
- comparações;
- condicionais;
- `enquanto`;
- `para`;
- funções;
- escopo;
- retorno;
- argumentos;
- chamadas de funções;
- variáveis não declaradas;
- funções inexistentes;
- tipos de retorno inválidos;
- retorno ausente;
- quantidade incorreta de argumentos;
- divisão por zero.

A suíte atual possui **17 testes, com 17 aprovados e nenhuma falha**.

---

# Filosofia da linguagem

A Sophia segue alguns princípios fundamentais.

### 1. Português

A linguagem utiliza o português como elemento central da sua sintaxe.

### 2. Expressividade

O código deve procurar expressar claramente a intenção do algoritmo.

### 3. Simplicidade

Novos recursos não devem comprometer a simplicidade da linguagem.

### 4. Aprendizado da lógica

A linguagem prioriza o raciocínio algorítmico em vez da memorização de símbolos.

### 5. Código legível

Um programa Sophia deve ser compreensível mesmo por alguém que esteja iniciando seus estudos em programação.

### 6. Simplicidade como decisão de projeto

A Sophia não pretende reproduzir toda a complexidade das linguagens profissionais. Sua simplicidade é uma característica intencional.

---

# Roadmap

A versão **1.0.0** estabelece o núcleo educacional da Sophia.

A evolução futura deve priorizar melhorias que aumentem a qualidade da experiência de aprendizado, sem comprometer a simplicidade da linguagem.

Possíveis evoluções incluem:

- melhoria da experiência do REPL;
- mensagens de erro mais didáticas;
- documentação;
- exemplos educacionais;
- material didático;
- integração com ambientes educacionais;
- ferramentas de apoio ao professor;
- integração futura com uma IDE existente.

A evolução da Sophia **não deve transformar a linguagem em uma linguagem profissional tradicional**.

---

# Exemplos

Os exemplos da linguagem estão disponíveis em:

```text
sophia-sintatico/src/main/resources/exemplos/
```

---

# Contribuição

Contribuições são bem-vindas através de Issues, Pull Requests e discussões sobre a evolução da linguagem.

Toda contribuição deve respeitar os princípios de simplicidade e foco educacional da Sophia.

---

# Licença

Este projeto está licenciado sob a licença MIT.

---

# Autor

Desenvolvido por **Anderson Fonseca**.

---

> **"Programação deve ser aprendida pela lógica, não pela quantidade de símbolos que precisamos decorar."**
