import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private int current;
    private Reader reader;

    Lexer(Reader reader) throws IOException {
        current = reader.read();
        this.reader = reader;
    }

    private String readDigit(int prev) throws IOException {
        StringBuilder value = new StringBuilder();
        value.append(prev);
        while(Character.isDigit(current)){
            prev = current;
            value.append(prev);
            current = reader.read();
        };
        return value.toString();
    }

    Lexeme getLexeme() throws IOException, ParsingException {
        while(current == ' ') {
            current = reader.read();
        }
        int prev = current;
        current = reader.read();

        switch(prev) {
            case -1:
                return new Lexeme(LexemeType.EOF, "EOF");
            case '-':
                return new Lexeme(LexemeType.MINUS, "-");
            case '+':
                return new Lexeme(LexemeType.PLUS, "+");
            case '*':
                return new Lexeme(LexemeType.MULTIPLY, "*");
            case '/':
                return new Lexeme(LexemeType.DIVIDE, "/");
            case '^':
                return new Lexeme(LexemeType.POWER, "^");
            case '(':
                return new Lexeme(LexemeType.OPENING_BRACKET, "(");
            case ')':
                return new Lexeme(LexemeType.CLOSING_BRACKET, ")");
            default:
                if(Character.isDigit(prev)) {
                    String value = readDigit(prev);
                    return new Lexeme(LexemeType.NUMBER, value);
                }

                else
                    throw new ParsingException("Lexer: Invalid lexeme");
        }
    }
}
