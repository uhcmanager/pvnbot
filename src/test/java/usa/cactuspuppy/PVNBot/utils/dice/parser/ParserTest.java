package usa.cactuspuppy.PVNBot.utils.dice.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void sanityTest() {
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
}