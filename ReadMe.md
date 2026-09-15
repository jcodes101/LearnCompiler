# LearnCompiler

#### Lexical Analyzer - reads the file and produces lexemes/tokens

###$ Recursive-Descent Parser - checks the tokens against the LearnCompiler EBNF grammar

#### The ideal flow:

Sample Program File
↓
Lexical Analyzer
↓
Lexemes + Token Types
↓
Recursive-Descent Parser
↓
Syntax Analysis
↓
┌───────────────────────────────────────┐
│ Valid │
│ "The Sample Program is generated..." │
│ │
│ OR │
│ Invalid │
│ "The Sample Program cannot..." │
│ + first syntax error │
└───────────────────────────────────────┘

- Essentially what is happening...
  - The EBNF is being translated into Java. So from the sameples, ex... ->

  `
  <program> -> <keyword> <ident> (<keyword><ident>) { <declares> <assign> }

      <declares> -> <keyword> <ident> ;
                  | <keyword> <ident> ; <declares>

      <assign> -> <ident> = <expr>

      <expr> -> <ident> {*|/} <expr>
              | <ident>

      <keyword> -> float

      <ident> -> a <ident> | b <ident> ... | z <ident> | ε

  `

  ...then everything is being parsed by it's own grammar rule. Once a certain sequence/token is seen a specific function will then be invoked.
