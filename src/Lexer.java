// this class breaks the ebnf source code into tokens, this class is used to 
// identify what the words and symbols are, that aer found in the lexeme,
// this class turns what words/symbols are seen and turns them into the TokenTypes, ex: float -> FLOAT, ) -> RIGHT_PAREN

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    // this is for the input source code taht the lexer is going over
    private final String input;

    // keeps track of the current character being analyzed
    private int position = 0;

    // keep track of current line + column number
    private int line = 1;
    private int column = 1;

    // constructor to input the file to be analyzed
    public Lexer(String input) {
        this.input = input;
    }

    // this is teh method that reads the source code char by char and forms characters into lexemes,
    // and then each lexeme is given a TokenType and stored in a list, then that final list is given back to the parser
    public List<Token> tokenize() {

        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {

            char current = input.charAt(position);

            // this ignores whitespace
            if (Character.isWhitespace(current)) {
                advance(current);
                continue;
            }

            int startLine = line;
            int startColumn = column;

            // for either identifier or keyword
            // basically if a letter is seen, it could be one of the two
            if (Character.isLetter(current)) {

                StringBuilder word = new StringBuilder();

                while (position < input.length()
                        && Character.isLetterOrDigit(input.charAt(position))) {

                    word.append(input.charAt(position));
                    advance(input.charAt(position));
                }

                // finally give back the list of words/identifiers found
                String lexeme = word.toString();

                if (lexeme.equals("float")) {

                    tokens.add(new Token(
                            TokenType.FLOAT,
                            lexeme,
                            startLine,
                            startColumn
                    ));

                } else if (lexeme.chars().allMatch(Character::isLetter)) {

                    tokens.add(new Token(
                            TokenType.IDENTIFIER,
                            lexeme,
                            startLine,
                            startColumn
                    ));

                } else {

                    tokens.add(new Token(
                            TokenType.INVALID,
                            lexeme,
                            startLine,
                            startColumn
                    ));
                }

                continue;
            }

            // for single character tokens
            switch (current) {

                case '(':
                    tokens.add(new Token(
                            TokenType.LEFT_PAREN,
                            "(",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case ')':
                    tokens.add(new Token(
                            TokenType.RIGHT_PAREN,
                            ")",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case '{':
                    tokens.add(new Token(
                            TokenType.LEFT_BRACE,
                            "{",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case '}':
                    tokens.add(new Token(
                            TokenType.RIGHT_BRACE,
                            "}",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case ';':
                    tokens.add(new Token(
                            TokenType.SEMICOLON,
                            ";",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case '=':
                    tokens.add(new Token(
                            TokenType.EQUALS,
                            "=",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case '*':
                    tokens.add(new Token(
                            TokenType.MULTIPLY,
                            "*",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                case '/':
                    tokens.add(new Token(
                            TokenType.DIVIDE,
                            "/",
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;

                default:
                    // this is for invalid characters
                    tokens.add(new Token(
                            TokenType.INVALID,
                            String.valueOf(current),
                            startLine,
                            startColumn
                    ));
                    advance(current);
                    break;
            }
        }

        tokens.add(new Token(
                TokenType.EOF,
                "",
                line,
                column
        ));

        return tokens;
    }

    private void advance(char current) {

        position++;

        if (current == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
    }
}