package usa.cactuspuppy.PVNBot.mainbot.convo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Trigger {
    public String trigger;
    public Type type;

    public enum Type {
        POSITIVE,
        NEUTRAL,
        NEGATIVE,
        QUESTION;

        private Type() {}
    }
}
