package usa.cactuspuppy.PVNBot.utils.dice.parser;

import org.junit.Before;
import org.junit.Test;
import usa.cactuspuppy.PVNBot.utils.dice.DiceRoller;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private TestRNG testRNG = new TestRNG();
    public class TestRNG extends Random {
        private int count = 0;
        @Override
        public int nextInt() {
            return count++;
        }

        @Override
        public int nextInt(int bound) {
            return count++ % bound;
        }

        void resetCount() { count = 0; }
    }

    //Set RNG to output 1,2,3... for predictability
    @Before
    public void setUp() throws Exception {
        DiceRoller.setRng(testRNG);
        testRNG.resetCount();
    }

    @Test
    public void manualTest() {
        AdditionExpressionNode innerSum =
                new AdditionExpressionNode();
        innerSum.add(new ConstantExpressionNode(1), true);
        innerSum.add(new ConstantExpressionNode(3), true);

        ExpressionNode expo =
                new ExponentiationExpressionNode(
                        new ConstantExpressionNode(2),
                        new ConstantExpressionNode(4));

        MultiplicationExpressionNode prod =
                new MultiplicationExpressionNode();
        prod.add(new ConstantExpressionNode(3), true);
        prod.add(expo, true);

        AdditionExpressionNode expression =
                new AdditionExpressionNode();
        expression.add(prod, true);
        expression.add(innerSum, true);

        assertEquals(52.0, expression.getValue(), 0.01);
    }

    @Test
    public void tokenTest() {
        List<Tokenizer.Token> tokens = Tokenizer.tokenize("(2+2)*-1*(3-1)");
        Parser testParser = new Parser();
        ExpressionNode expr = testParser.parse(tokens);
        System.out.println(testParser.getFormula());
        assertEquals(-8, expr.getValue(), 0.01);
    }

    @Test
    public void fullTest() {
        Parser testParser = new Parser();
        ExpressionNode expr = testParser.parse(Tokenizer.tokenize("3*2^4 + 4d6 + (1+3)^0.5"));
        System.out.println(testParser.getFormula());
        assertEquals(60, expr.getValue(), 0.01);
    }

    @Test
    public void testDivision() {
        Parser testParser = new Parser();
        ExpressionNode expr = testParser.parse(Tokenizer.tokenize("1/2"));
        System.out.println(testParser.getFormula());
        assertEquals(0.5, expr.getValue(), 0.01);

        testParser = new Parser();
        expr = testParser.parse(Tokenizer.tokenize("-1/2"));
        System.out.println(testParser.getFormula());
        assertEquals(-0.5, expr.getValue(), 0.01);

        testParser = new Parser();
        expr = testParser.parse(Tokenizer.tokenize("-1/-2"));
        System.out.println(testParser.getFormula());
        assertEquals(0.5, expr.getValue(), 0.01);

        testParser = new Parser();
        expr = testParser.parse(Tokenizer.tokenize("-1/-0.5"));
        System.out.println(testParser.getFormula());
        assertEquals(2, expr.getValue(), 0.01);
    }

    @Test
    public void testZero() {
        Parser testParser = new Parser();
        ExpressionNode expr = testParser.parse(Tokenizer.tokenize("1*0"));
        System.out.println(testParser.getFormula());
        assertEquals(0, expr.getValue(), 0.01);

        testParser = new Parser();
        expr = testParser.parse(Tokenizer.tokenize("1+0"));
        System.out.println(testParser.getFormula());
        assertEquals(1, expr.getValue(), 0.01);

        testParser = new Parser();
        expr = testParser.parse(Tokenizer.tokenize("2-0"));
        System.out.println(testParser.getFormula());
        assertEquals(2, expr.getValue(), 0.01);
    }

    @Test(expected = Parser.EvalException.class)
    public void testDiv0() {
        Parser testParser = new Parser();
        ExpressionNode expr = testParser.parse(Tokenizer.tokenize("1/0"));
        System.out.println(testParser.getFormula());
        assertEquals(0, expr.getValue(), 0.01);
    }

    @Test(expected = Parser.ParserException.class)
    public void testInvalid() {
        Parser testParser = new Parser();
        testParser.parse(Tokenizer.tokenize("---1"));
    }

    @Test
    public void testNegNum() {
        Parser testPar = new Parser();
        List<Tokenizer.Token> tokens = Tokenizer.tokenize("(2+2)*(3--1.5)");
        ExpressionNode expr = testPar.parse(tokens);
        System.out.println(testPar.getFormula());
        assertEquals(18.0, expr.getValue(), 0.001);

        testPar = new Parser();
        tokens = Tokenizer.tokenize("(2+2)*(3 + -1.5)");
        expr = testPar.parse(tokens);
        System.out.println(testPar.getFormula());
        assertEquals(6.0, expr.getValue(), 0.001);
    }
}