// this class is needed to store the actual token info, 
// being able to display the lexemes and token types

// the lexer creates Tokens and the parser is what reads Tokens

public class Token {

    // stores the kind of token such as... FLOAT, INDENTIFIER, etc...
    private final TokenType type;

    // store the actual text from the input
    private final String lexeme;

    // holds the line number where the lexeme is
    private final int line;

    // stores the column number where the lexeme is
    private final int column;

    // this is the constructor that gives the type, actual text and 
    // then line/column number when a token is created
    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    // getter functuins
    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    // this formats a Token so that the lexeme, type, 
    // line/column are able to be displayed properly
    @Override
    public String toString() {
        return String.format(
            "%-15s %-15s Line: %d Column: %d",
            lexeme,
            type,
            line,
            column
        );
    }
}