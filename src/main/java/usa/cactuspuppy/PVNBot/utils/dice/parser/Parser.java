package usa.cactuspuppy.PVNBot.utils.dice.parser;

import java.util.LinkedList;
import java.util.List;

import static usa.cactuspuppy.PVNBot.utils.dice.parser.Tokenizer.Token;

public class Parser {
    public static void parse(List<Token> tok) {
        LinkedList<Token> tokens = new LinkedList<>(tok);
        Token lookahead = tokens.getFirst();
    }
}
