/**
 * this is the main class that orchestrates everything else
 * 
 * - it reads a sample program from an input file
 * - sends the source code to the lexical analyzer
 * - displays the generates lexemes and token types
 * - sneds the tokens to the recursive descent parser
 * - displays whether the program can be generated  by the LearnCompiler BNF grammar
 * - displays the first error if the program is invalid
 */

// compile: javac -d out src/*.java
// run: java -cp out LearnCompiler

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class LearnCompiler {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("              LearnCompiler");
        System.out.println("==========================================");
        System.out.println();

        System.out.print("Enter the sample program file name: ");

        String fileName = scanner.nextLine();

        try {

            String sourceCode = Files.readString(Path.of(fileName));

            System.out.println();
            System.out.println("========== INPUT PROGRAM ==========");
            System.out.println(sourceCode);

            // here is the lexical Analysis
            Lexer lexer = new Lexer(sourceCode);

            List<Token> tokens = lexer.tokenize();

            System.out.println();
            System.out.println("========== LEXEMES AND TOKENS ==========");

            for (Token token : tokens) {

                if (token.getType() != TokenType.EOF) {
                    System.out.println(token);
                }
            }

            // the syntax Analysis
            Parser parser = new Parser(tokens);

            boolean valid = parser.parse();

            System.out.println();
            System.out.println("========== SYNTAX ANALYSIS ==========");

            if (valid) {

                System.out.println(
                        "The Sample Program conforms to the BNF grammar"
                );

            } else {

                System.out.println(
                        "The Sample Program does not conform to the LearnCompiler BNF Grammar"
                );

                System.out.println();

                System.out.println(
                        "First Syntax Error:"
                );

                System.out.println(parser.getFirstError());
            }

        } catch (IOException e) {

            System.out.println(
                    "Error: Unable to read the input file."
            );

            System.out.println(
                    "Please check that the file name and path are correct."
            );
        }

        scanner.close();
    }
}