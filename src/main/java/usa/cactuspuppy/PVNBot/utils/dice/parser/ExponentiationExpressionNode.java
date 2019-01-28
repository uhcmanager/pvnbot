package usa.cactuspuppy.PVNBot.utils.dice.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExponentiationExpressionNode implements ExpressionNode {
    private ExpressionNode base;
    private ExpressionNode exponent;

    @Override
    public int getType() {
        return ExpressionNode.EXPONENTIATION_NODE;
    }

    @Override
    public double getValue() {
        return Math.pow(base.getValue(), exponent.getValue());
    }
}
