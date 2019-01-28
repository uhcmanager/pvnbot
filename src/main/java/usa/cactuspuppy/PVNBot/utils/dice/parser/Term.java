package usa.cactuspuppy.PVNBot.utils.dice.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Term {
    private boolean positive;
    private ExpressionNode expression;
}
