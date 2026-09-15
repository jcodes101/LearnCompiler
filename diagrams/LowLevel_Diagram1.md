sample1.txt
│
│ raw source code
▼
LearnCompiler.java
│
▼
Lexer.java
│
│ breaks source into pieces
▼
Token.java +
TokenType.java
│
│ list of tokens
▼
Parser.java
│
│ checks tokens against EBNF
▼
parseProgram()
│
├── parseKeyword()
├── parseIdentifier()
├── parseDeclares()
├── parseAssign()
│ └── parseExpr()
│ └── parseExpr()
│
▼
VALID / INVALID
