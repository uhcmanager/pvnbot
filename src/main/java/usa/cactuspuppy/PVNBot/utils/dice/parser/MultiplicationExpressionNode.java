package usa.cactuspuppy.PVNBot.utils.dice.parser;

public class MultiplicationExpressionNode extends SequenceExpressionNode {
    public MultiplicationExpressionNode() {
        super();
    }

    public MultiplicationExpressionNode(ExpressionNode a, boolean pos) {
        super(a, pos);
    }

    @Override
    public int getType() {
        return ExpressionNode.MULTIPLICATION_NODE;
    }

    @Override
    public double getValue() throws Parser.EvalException {
        double prod = 1.0;
        for (Term t : terms) {
            if (t.isPositive()) {
                if (prod > Double.MAX_VALUE / t.getExpression().getValue()) {
                    throw new Parser.EvalException("");
                }
                prod *= t.getExpression().getValue();
            } else {
                if (prod < Double.MIN_VALUE / t.getExpression().getValue()) {
                    throw new Parser.EvalException("");
                }
                prod /= t.getExpression().getValue();
            }
        }
        return prod;
    }
}
