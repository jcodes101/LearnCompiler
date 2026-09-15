// these token types represent the differnt types of tokens 
// that the lexical analyzer can identify

// for example -> 'float prog ( float k ) {' would be...
// FLOAT IDENTIFIER LEFT_PAREN FLOAT IDENTIFIER RIGHT_PARENT LEFT_BRACE

// a token type essentially describes what a lexeme represents,
// the lexer creates these token types -> then the parser uses them to check the syntax of the program

public enum TokenType {
    FLOAT,
    IDENTIFIER,
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    SEMICOLON,
    EQUALS,
    MULTIPLY,
    DIVIDE,
    INVALID,
    EOF
}