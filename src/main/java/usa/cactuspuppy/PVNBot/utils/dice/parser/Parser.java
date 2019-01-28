package usa.cactuspuppy.PVNBot.utils.dice.parser;

import java.util.LinkedList;
import java.util.List;

import static usa.cactuspuppy.PVNBot.utils.dice.parser.Tokenizer.Token;

public class Parser {
    private LinkedList<Token> tokens;
    private Token lookahead;

    public void parse(List<Token> tok) {
        tokens = new LinkedList<>(tok);
        lookahead = tokens.getFirst();

        try {
            expression();
        } catch (ParserException | EvalException e) {
            //TODO: Handle parsing exception
        }
    }

    private void nextToken() {
        if (!tokens.isEmpty()) {
            lookahead = tokens.pop();
        } else {
            lookahead = new Token(Token.EPSILON, "");
        }
    }

    private void expression() throws ParserException {
        signedTerm();
        sumOp();
    }

    private void sumOp() throws ParserException {
        if (lookahead.getTokenID() == Token.ADD_SUB) {
            nextToken();
            term();
            sumOp();
        }
    }

    private void signedTerm() throws ParserException {
        if (lookahead.getTokenID() == Token.ADD_SUB) {
            nextToken();
            term();
        } else {
            term();
        }
    }

    private void term() throws ParserException {
        factor();
        termOp();
    }

    private void termOp() throws ParserException {
        if (lookahead.getTokenID() == Token.MUL_DIV) {
            nextToken();
            signedFactor();
            termOp();
        }
    }

    private void signedFactor() throws ParserException {
        if (lookahead.getTokenID() == Token.ADD_SUB) {
            nextToken();
            factor();
        } else {
            factor();
        }
    }

    private void factor() throws ParserException {
        argument();
        factorOp();
    }

    private void factorOp() throws ParserException {
        if (lookahead.getTokenID() == Token.POWER) {
            nextToken();
            signedFactor();
        }
    }

    private void argument() throws ParserException {
        if (lookahead.getTokenID() == Token.OPEN_PAREN) {
            nextToken();
            expression();

            if (lookahead.getTokenID() != Token.CLOSE_PAREN) {
                throw new ParserException("Closing parentheses expected; got " + lookahead.getSequence() + " instead");
            }

            nextToken();
        } else {
            value();
        }
    }

    private ExpressionNode value() throws ParserException {
        if (lookahead.getTokenID() == Token.NUMBER) {
            double value;
            try {
                value = Double.valueOf(lookahead.getSequence());
            } catch (NumberFormatException e) {
                throw new ParserException("Double overflow on " + lookahead.getSequence());
            }
            ExpressionNode expr = new ConstantExpressionNode(value);
            nextToken();
            return expr;
        } else if (lookahead.getTokenID() == Token.DICE) {
            ExpressionNode expr = new DiceExpressionNode();
            nextToken();
            return expr;
        } else {
            throw new ParserException("Unexpected symbol " + lookahead.getSequence() + " while parsing");
        }
    }

    public static class ParserException extends RuntimeException {
        ParserException(String message) {
            super(message);
        }
    }

    public static class EvalException extends RuntimeException {
        EvalException(String message) {
            super(message);
        }
    }
}
