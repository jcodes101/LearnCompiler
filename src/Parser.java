// this class is used to checks whether tokens follow the grammar
// the parser takes the tokens from teh lexer and checks how the grammar follows


import java.util.List;

public class Parser {

    // this is the list of tokens that were produced by the lexical analyzer
    //  and the parser is what uses this list to do the syntax analysis
    private final List<Token> tokens;

    // keeps track of which token the parser is currently on
    private int current = 0;

    // stores the first error found
    private String firstError = null;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // this is a prelimanary check to see if the program spotted a bad token,
    // but if not then keep going
    public boolean parse() {

        checkInvalidTokens();

        if (firstError != null) {
            return false;
        }

        parseProgram();

        return firstError == null;
    }

    /*
     * <program> -> <keyword> <ident> ( <keyword> <ident> ) { <declares> <assign> }
     */

    // this walks through the grammar from left to right
    private void parseProgram() {

        parseKeyword();

        parseIdentifier();

        consume(
                TokenType.LEFT_PAREN,
                "Expected '(' after program identifier."
        );

        parseKeyword();

        parseIdentifier();

        consume(
                TokenType.RIGHT_PAREN,
                "Expected ')' after parameter declaration."
        );

        consume(
                TokenType.LEFT_BRACE,
                "Expected '{' before program body."
        );

        parseDeclares();

        parseAssign();

        consume(
                TokenType.RIGHT_BRACE,
                "Expected '}' after program body."
        );
    }

    /*
     * <declares> -> <keyword> <ident> ; | <keyword> <ident> ; <declares>
     *
     * parse one or more declarations.
     */
    private void parseDeclares() {

        if (!check(TokenType.FLOAT)) {
            error(
                    "Expected 'float' declaration at the beginning of the program body."
            );
            return;
        }

        // this handles the recursive declaration rule, instead of going on by one,
        // it goes in a loop
        while (check(TokenType.FLOAT)) {

            parseKeyword();

            parseIdentifier();

            consume(
                    TokenType.SEMICOLON,
                    "Expected ';' after declaration."
            );
        }
    }

    /*
     * <assign> -> <ident> = <expr>
     */
    private void parseAssign() {

        parseIdentifier();

        consume(
                TokenType.EQUALS,
                "Expected '=' after identifier in assignment."
        );

        parseExpr();

        consume(
                TokenType.SEMICOLON,
                "Expected ';' after assignment."
        );
    }

    /*
     * <expr> ->
     *     <ident> {*|/} <expr>
     *     | <ident>
     */
    private void parseExpr() {

        parseIdentifier();

        if (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            parseExpr();
        }
    }

    /*
     * <keyword> -> float
     */
    private void parseKeyword() {

        consume(
                TokenType.FLOAT,
                "Expected keyword 'float'."
        );
    }

    /*
     * <ident> ->
     *     a <ident>
     *     | b <ident>
     *     ...
     *     | z <ident>
     *     | epsilon
     *
     * in the lexical analyzer, identifiers are already grouped
     * into IDENTIFIER tokens.
     */
    private void parseIdentifier() {

        if (check(TokenType.IDENTIFIER)) {
            advance();
        } else {
            error(
                    "Expected an identifier, but found '" +
                    peek().getLexeme() +
                    "'."
            );
        }
    }

    // this is a check that looks to see if the current token macthes one of the defined token types
    private boolean match(TokenType... types) {

        for (TokenType type : types) {

            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    // this consume function is essentially checking what the expected token type is,
    // and if something else is given, it throws an error

    // example being: if the program sees a '{' then 'advance()', otherwise it thrpws an error
    private void consume(TokenType type, String message) {

        if (check(type)) {
            advance();
            return;
        }

        error(message);
    }

    private boolean check(TokenType type) {

        if (isAtEnd()) {
            return type == TokenType.EOF;
        }

        return peek().getType() == type;
    }

    private void checkInvalidTokens() {

        for (Token token : tokens) {

            if (token.getType() == TokenType.INVALID) {

                firstError = String.format(
                        "Lexical error at line %d, column %d: invalid lexeme '%s'.",
                        token.getLine(),
                        token.getColumn(),
                        token.getLexeme()
                );

                return;
            }
        }
    }

    private Token advance() {

        if (!isAtEnd()) {
            current++;
        }

        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private void error(String message) {

        if (firstError == null) {

            Token token = peek();

            firstError = String.format(
                    "Syntax error at line %d, column %d: %s",
                    token.getLine(),
                    token.getColumn(),
                    message
            );
        }
    }

    public String getFirstError() {
        return firstError;
    }
}