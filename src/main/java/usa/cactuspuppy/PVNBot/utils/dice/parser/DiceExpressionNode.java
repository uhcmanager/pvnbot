package usa.cactuspuppy.PVNBot.utils.dice.parser;

import usa.cactuspuppy.PVNBot.utils.dice.DiceRoller;

public class DiceExpressionNode implements ExpressionNode {
    /**
     * How this dice roll should be represented in the formula
     */
    private String stringRep;
    private double value;
    private boolean valueSet = false;

    public DiceExpressionNode(String stringRep) {
        this.stringRep = stringRep;
        setValue();
    }

    @Override
    public int getType() {
        return ExpressionNode.DICE_NODE;
    }

    @Override
    public double getValue() {
        if (!valueSet) {
            setValue();
        }
        return value;
    }

    public void setValue() {
        DiceRoller.RollResult result = DiceRoller.parseSingleRoll(stringRep);
        if (!result.isSuccess()) {
            throw new Parser.EvalException(result.getReason());
        }
        value = result.getResult();
        stringRep = result.getFormula();
        valueSet = true;
}
}
