package usa.cactuspuppy.PVNBot.mainbot.convo;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.regex.Pattern;

@AllArgsConstructor
public class Response {
    private static LinkedHashMap<Trigger.Priority, List<Trigger>> triggers = new LinkedHashMap<>();
    private static HashMap<Trigger.Type, List<String>> responses = new HashMap<>();
    static {
        for (Trigger.Priority p : Trigger.Priority.values()) {
            triggers.put(p, new ArrayList<>());
        }
        for (Trigger.Type t : Trigger.Type.values()) {
            responses.put(t, new ArrayList<>());
        }
        //TODO: Add triggers
        addTrigger(new Trigger(Pattern.compile("PupBot"), Trigger.Type.NEUTRAL, Trigger.Priority.LOW));
        //TODO: Add reponses
        addResponse("Hello world!", Trigger.Type.NEUTRAL);
    }

    private String content;

    public static void addTrigger(Trigger t) {
        triggers.get(t.getPriority()).add(t);
    }

    public static void addResponse(String response, Trigger.Type type) { responses.get(type).add(response); }

    public boolean shouldRespond() {
        return triggers.keySet().stream().sorted(new Trigger.PriorityComparator()).map(p -> triggers.get(p)).flatMap(Collection::stream).anyMatch(t -> t.getPattern().matcher(content).find());
    }

    /**
     * Prepare a response based on the message
     * @return formattable string with placeholders<br>
     * %1$s - Author as mention
     */
    public String get() {
        //Get type of message
        Trigger.Type type = null;
        for (Trigger.Priority p : triggers.keySet()) {
            for (Trigger t : triggers.get(p)) {
                if (t.getPattern().matcher(content).find()) {
                    type = t.getType();
                }
            }
        }
        if (type == null) return null;
        Random rng = new Random();
        int bound = responses.get(type).size();
        if (bound == 0) return null;
        int index = rng.nextInt(bound);
        return responses.get(type).get(index);
    }
}
