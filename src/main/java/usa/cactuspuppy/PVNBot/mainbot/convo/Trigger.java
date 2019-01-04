package usa.cactuspuppy.PVNBot.mainbot.convo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Getter
public class Trigger {
    private Pattern pattern;
    private Type type;
    private Priority priority;

    public Trigger(String regex, Type t, Priority p) {
        pattern = Pattern.compile(regex);
        type = t;
        priority = p;
    }

    public enum Type {
        POSITIVE,
        NEUTRAL,
        NEGATIVE,
        QUESTION
    }

    public enum Priority {

        HIGHEST (6),
        HIGHER (5),
        HIGH (4),
        NORMAL (3),
        LOW (2),
        LOWER (1),
        LOWEST (0);

        int rank;

        Priority(int rank) {
            this.rank = rank;
        }
    }

    public static class PriorityComparator implements Comparator<Priority> {
        @Override
        public int compare(Priority o1, Priority o2) {
            return o1.rank - o2.rank;
        }
    }
}
