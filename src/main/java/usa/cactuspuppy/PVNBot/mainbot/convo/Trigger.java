package usa.cactuspuppy.PVNBot.mainbot.convo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class Trigger {
    private Pattern pattern;
    private Type type;
    private Priority priority;


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

    Trigger(String regex, Type type, Priority priority) {
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        this.type = type;
        this.priority = priority;
    }

    public static class PriorityComparator implements Comparator<Priority> {
        @Override
        public int compare(Priority o1, Priority o2) {
            return o1.rank - o2.rank;
        }
    }
}
