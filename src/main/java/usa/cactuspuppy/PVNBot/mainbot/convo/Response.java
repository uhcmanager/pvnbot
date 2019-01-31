package usa.cactuspuppy.PVNBot.mainbot.convo;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class Response {
    private static LinkedHashMap<Trigger.Priority, List<Trigger>> triggers = new LinkedHashMap<>();
    static {
        for (Trigger.Priority p : Trigger.Priority.values()) {
            triggers.put(p, new ArrayList<>());
        }
    }

    private String content;

    public static void addTrigger(Trigger t) {
        triggers.get(t.getPriority()).add(t);
    }

    public boolean shouldRespond() {
        return triggers.keySet().stream().sorted(new Trigger.PriorityComparator()).map(p -> triggers.get(p)).flatMap(Collection::stream).anyMatch(t -> t.getPattern().matcher(content).find());
    }

    /**
     * Prepare a response based on the message
     * @return formattable string with placeholders<br>
     * %1$s - Author as mention
     */
    public String get() {
        //TODO: Return a response
        return null;
    }
}
