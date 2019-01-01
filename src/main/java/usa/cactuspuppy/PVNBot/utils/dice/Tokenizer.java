package usa.cactuspuppy.PVNBot.utils.dice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public class Token {
        private int
    }
}
