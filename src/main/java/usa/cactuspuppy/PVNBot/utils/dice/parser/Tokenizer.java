package usa.cactuspuppy.PVNBot.utils.dice.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes an input string for parsing
 */
public class Tokenizer {
    private static Map<Integer, TokenDef> tokenDefs = new HashMap<>();
    static {
        addTokenDef("(\\d*)d(\\d+)(\\w+)", 1); //dice rolls
        addTokenDef("\\(", 2); //open paren
        addTokenDef("\\)", 3); //close paren
        addTokenDef("[+-]", 4); //add & sub
        addTokenDef("[*/]", 5); //mul & div
        addTokenDef("\\^", 6); //power
        addTokenDef("[0-9]+", 7); //numbers
    }

    public static void addTokenDef(String regex, int token) {
        tokenDefs.put(token, new TokenDef(Pattern.compile("^(" + regex + ")"), token));
    }

    @AllArgsConstructor
    @Getter
    private static class TokenDef {
        private Pattern pattern;
        private int tokenID;
    }

    @AllArgsConstructor
    @Getter
    public static class Token {
        public static final int EPSILON = 0;
        public static final int DICE = 1;
        public static final int OPEN_PAREN = 2;
        public static final int CLOSE_PAREN = 3;
        public static final int ADD_SUB = 4;
        public static final int MUL_DIV = 5;
        public static final int POWER = 6;
        public static final int NUMBER = 7;

        private int tokenID;
        private String sequence;
    }

    public static LinkedList<Token> tokenize(String s) throws TokenizerException {
        LinkedList<Token> tokens = new LinkedList<>();
        s = s.trim();
        while (!s.equals("")) {
            boolean found = false;
            for (TokenDef def : tokenDefs.values()) { //check all possible tokens for match
                Matcher m = def.getPattern().matcher(s);
                if (m.find()) {
                    found = true;
                    String seq = m.group().trim();
                    tokens.add(new Token(def.getTokenID(), seq));
                    s = m.replaceFirst("").trim(); //eat token
                    break;
                }
            }
            if (!found) throw new TokenizerException("Unexpected character in input: " + s);
        }
        return tokens;
    }

    public static class TokenizerException extends RuntimeException {
        public TokenizerException(String s) {
            super(s);
        }
    }
}
