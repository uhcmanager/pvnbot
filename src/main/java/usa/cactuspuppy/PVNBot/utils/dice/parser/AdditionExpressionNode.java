package usa.cactuspuppy.PVNBot.utils.dice.parser;

public class AdditionExpressionNode extends SequenceExpressionNode {
    public AdditionExpressionNode() {
        super();
    }

    public AdditionExpressionNode(ExpressionNode a, boolean pos) {
        super(a, pos);
    }

    @Override
    public int getType() {
        return ExpressionNode.ADDITION_NODE;
    }

    @Override
    public double getValue() throws Parser.EvalException {
        double sum = 0.0;
        for (Term t : terms) {
            if ((t.getExpression().getValue() > 0 && t.isPositive()) || (t.getExpression().getValue() < 0 && !t.isPositive())) {
                if (sum > Double.MAX_VALUE - t.getExpression().getValue()) {
                    throw new Parser.EvalException("Positive overflow while summing");
                }
                sum += t.getExpression().getValue();
            } else {
                if (sum < Double.MIN_VALUE + t.getExpression().getValue()) {
                    throw new Parser.EvalException("Negative overflow while summing");
                }
                sum -= t.getExpression().getValue();
            }
        }
        return sum;
    }
}
