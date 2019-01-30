package usa.cactuspuppy.PVNBot.utils.dice.parser;

import java.util.LinkedList;
import java.util.List;

import static usa.cactuspuppy.PVNBot.utils.dice.parser.Tokenizer.Token;

public class Parser {
    private LinkedList<Token> tokens;
    private Token lookahead;

    public ExpressionNode parse(List<Token> tok) throws ParserException, EvalException {
        tokens = new LinkedList<>(tok);
        lookahead = tokens.getFirst();

        ExpressionNode expr = expression();
        if (lookahead.getTokenID() != Token.EPSILON) {
            throw new ParserException(String.format("Unexpected symbol %s found", lookahead));
        }

        return expr;
    }

    private void nextToken() {
        if (!tokens.isEmpty()) {
            lookahead = tokens.pop();
        } else {
            lookahead = new Token(Token.EPSILON, "");
        }
    }

    private ExpressionNode expression() {
        ExpressionNode expr = signedTerm();
        return sumOp(expr);
    }

    private ExpressionNode sumOp(ExpressionNode expr) {
        if (lookahead.getTokenID() == Token.ADD_SUB) {
            AdditionExpressionNode sum;
            if (expr.getType() == ExpressionNode.ADDITION_NODE) {
                sum = (AdditionExpressionNode) expr;
            } else {
                sum = new AdditionExpressionNode(expr, true);
            }

            boolean pos = lookahead.getSequence().equals("+");
            nextToken();
            ExpressionNode t = term();
            sum.add(t, pos);
            return sumOp(sum);
        }
        return expr;
    }

    private ExpressionNode signedTerm() {
        if (lookahead.getTokenID() == Token.ADD_SUB) {
            boolean pos = lookahead.getSequence().equals("+");
            nextToken();
            ExpressionNode t = term();
            if (pos) {
                return t;
            } else {
                return new AdditionExpressionNode(t, false);
            }
        }
        return term();
    }

    private ExpressionNode term() {
        ExpressionNode f = factor();
        return termOp(f);
    }

    private ExpressionNode termOp(ExpressionNode expr) {
        if (lookahead.getTokenID() == Token.MUL_DIV) {
            MultiplicationExpressionNode prod;

            if (expr.getType() == ExpressionNode.MULTIPLICATION_NODE) {
                prod = (MultiplicationExpressionNode) expr;
            } else {
                prod = new MultiplicationExpressionNode(expr, true);
            }

            boolean pos = lookahead.getSequence().equals("*");
            nextToken();
            ExpressionNode f = signedFactor();
            prod.add(f, pos);

            return termOp(prod);
        }

        return expr;
    }

    private ExpressionNode signedFactor() {
        if (lookahead.getTokenID() == Token.ADD_SUB) {
            boolean pos = lookahead.getSequence().equals("+");
            nextToken();
            ExpressionNode a = factor();
            if (pos) {
                return a;
            } else {
                return new AdditionExpressionNode(a, false);
            }
        }
        return factor();
    }

    private ExpressionNode factor() {
        ExpressionNode a = argument();
        return factorOp(a);
    }

    private ExpressionNode factorOp(ExpressionNode expr) {
        if (lookahead.getTokenID() == Token.POWER) {
            nextToken();
            ExpressionNode expon = signedFactor();

            return new ExponentiationExpressionNode(expr, expon);
        }

        return expr;
    }

    private ExpressionNode argument() {
        if (lookahead.getTokenID() == Token.OPEN_PAREN) {
            nextToken();
            ExpressionNode expr = expression();

            if (lookahead.getTokenID() != Token.CLOSE_PAREN) {
                throw new ParserException("Closing parentheses expected; got " + lookahead.getSequence() + " instead");
            }

            nextToken();
            return expr;
        } else {
            return value();
        }
    }

    private ExpressionNode value() {
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
            ExpressionNode expr = new DiceExpressionNode(lookahead.getSequence());
            nextToken();
            return expr;
        }
        if (lookahead.getTokenID() == Token.EPSILON) { throw new ParserException("Unexpected end of input while parsing"); }
        throw new ParserException("Unexpected symbol " + lookahead.getSequence() + " while parsing");
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
