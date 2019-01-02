package usa.cactuspuppy.PVNBot.utils.dice.parser;

import org.junit.Test;

import java.util.LinkedList;

public class TokenizerTest {

    @Test
    public void tokenize() {
        LinkedList<Tokenizer.Token> tokens = Tokenizer.tokenize("3(d20k3D2r1 + 5)");
        StringBuilder builder = new StringBuilder();
        for (Tokenizer.Token t : tokens) {
            builder.append(" ").append(t.getTokenID()).append(" ").append(t.getSequence()).append("\n");
        }
        System.out.println(builder.toString());
    }
}