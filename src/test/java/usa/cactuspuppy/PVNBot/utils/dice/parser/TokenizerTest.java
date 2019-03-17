package usa.cactuspuppy.PVNBot.utils.dice.parser;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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

    @Test
    public void tokenize2() {
        List<Tokenizer.Token> tokens = Tokenizer.tokenize("(2+2)*(3-1.5)");
        StringBuilder builder = new StringBuilder();
        for (Tokenizer.Token t : tokens) {
            builder.append(" ").append(t.getTokenID()).append(" ").append(t.getSequence()).append("\n");
        }
        System.out.println(builder.toString());
    }

    @Test
    public void testNegNum() {
        List<Tokenizer.Token> tokens = Tokenizer.tokenize("(2+2)*(3--1.5)");
        StringBuilder builder = new StringBuilder();
        for (Tokenizer.Token t : tokens) {
            builder.append(" ").append(t.getTokenID()).append(" ").append(t.getSequence()).append("\n");
        }
        System.out.println(builder.toString());

        tokens = Tokenizer.tokenize("(2+2)*(3 + -1.5)");
        builder = new StringBuilder();
        for (Tokenizer.Token t : tokens) {
            builder.append(" ").append(t.getTokenID()).append(" ").append(t.getSequence()).append("\n");
        }
        System.out.println(builder.toString());
    }

    @Test
    public void negativeVsMinus() {
        List<Tokenizer.Token> tokens = Tokenizer.tokenize("(4d6k3 - 10) / 2");
        StringBuilder builder = new StringBuilder();
        for (Tokenizer.Token t : tokens) {
            builder.append(" ").append(t.getTokenID()).append(" ").append(t.getSequence()).append("\n");
        }
        System.out.println(builder.toString());
    }
}