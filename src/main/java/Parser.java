import java.io.IOException;

public class Parser {
    private Lexeme current;
    private Lexer lexer;

    public Parser(Lexer lexer) throws IOException, ParsingException {
        this.lexer = lexer;
        current = lexer.getLexeme();
    }

    public int parseExpr() throws IOException, ParsingException {
        int temp = parseTerm();

        while((current.type == LexemeType.PLUS) || (current.type == LexemeType.MINUS)){
            Lexeme prev = current;
            current = lexer.getLexeme();
            if(prev.type == LexemeType.PLUS)
                temp += parseTerm();
            else
                temp -= parseTerm();
        }
        return temp;
    }

    private int parseTerm() throws IOException, ParsingException{
        int temp = parseFactor();
        while((current.type == LexemeType.MULTIPLY) || (current.type == LexemeType.DIVIDE)) {
            Lexeme prev = current;
            current = lexer.getLexeme();
            if(prev.type == LexemeType.MULTIPLY)
                temp *= parseFactor();
            else
                temp /= parseFactor();
        }
        return temp;
    }

    private int parseFactor() throws IOException, ParsingException{
        int temp = parsePower();

        if(current.type == LexemeType.POWER) {
            current = lexer.getLexeme();
            temp =   (int)Math.pow(temp, parsePower());
        }
        return temp;
    }

    private int parsePower() throws IOException, ParsingException{
        int sgn = 1;
        if(current.type == LexemeType.MINUS) {
            current = lexer.getLexeme();
            sgn = -1;
        }
        return sgn*parseAtom();
    }

    private int parseAtom() throws IOException, ParsingException {
        int temp;

        if(current.getType() == LexemeType.NUMBER) {
            temp = Integer.parseInt(current.text);
            current = lexer.getLexeme();
            return temp;
        }
        else if(current.type == LexemeType.OPENING_BRACKET) {
            current = lexer.getLexeme();
            temp = parseExpr();
            if(current.type == LexemeType.CLOSING_BRACKET) {
                current = lexer.getLexeme();
                return temp;
            }
        }
        throw new ParsingException("Parser: Unexpected lexeme");
    }
}
