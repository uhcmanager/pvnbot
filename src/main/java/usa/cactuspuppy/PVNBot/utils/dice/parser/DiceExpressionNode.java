package usa.cactuspuppy.PVNBot.utils.dice.parser;

import usa.cactuspuppy.PVNBot.utils.dice.DiceRoller;

import java.util.Map;

public class DiceExpressionNode implements ExpressionNode {
    private String roll;
    private double value;
    private boolean valueSet = false;

    public DiceExpressionNode(String roll) {
        this.roll = roll;
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
        DiceRoller.RollResult result = DiceRoller.parseSingleRoll(roll);
        if (!result.isSuccess()) {
            throw new Parser.EvalException(result.getReason());
        }
        //TODO: set value
}
}
