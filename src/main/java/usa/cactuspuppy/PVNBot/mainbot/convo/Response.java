package usa.cactuspuppy.PVNBot.mainbot.convo;

import lombok.AllArgsConstructor;

import java.util.*;

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
        //Neutral
        addTrigger(new Trigger("PupBot", Trigger.Type.NEUTRAL, Trigger.Priority.LOW));
        //Positive
        addTrigger(new Trigger("PupBot.*great", Trigger.Type.POSITIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("PupBot.*good", Trigger.Type.POSITIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("PupBot.*best", Trigger.Type.POSITIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("best.*PupBot", Trigger.Type.POSITIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("good.*PupBot", Trigger.Type.POSITIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("great.*PupBot", Trigger.Type.POSITIVE, Trigger.Priority.NORMAL));
        //Negative
        addTrigger(new Trigger("problem.*PupBot", Trigger.Type.NEGATIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("PupBot.*problem", Trigger.Type.NEGATIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("PupBot.*sucks", Trigger.Type.NEGATIVE, Trigger.Priority.NORMAL));
        addTrigger(new Trigger("PupBot.*suck", Trigger.Type.NEGATIVE, Trigger.Priority.NORMAL));
        //Question
        addTrigger(new Trigger("PupBot.*\\?", Trigger.Type.QUESTION, Trigger.Priority.LOW));
        //TODO: Add reponses
        /*NOTE: The response will be put through String.format, where:
        * %1$s - author as mention
        */
        //NEUTRAL
        addNeutralResponse("%1$s That is I, how may I help?");
        addNeutralResponse("%1$s Instructions unclear, deeper recursion required");
        addNeutralResponse("Zzz... Sorry what were we talking about again %1$s?");
        addNeutralResponse("");
        //POSITIVE
        addResponse("No problem, %1$s!", Trigger.Type.POSITIVE);
        addResponse("Thanks %1$s :D", Trigger.Type.POSITIVE);
        addResponse("All in a day's work %1$s", Trigger.Type.POSITIVE);
        //NEGATIVE
        addResponse("Is that how you really feel %1$s?", Trigger.Type.NEGATIVE);
        addResponse("Not my fault. It was the cat %1$s.", Trigger.Type.NEGATIVE);
        //QUESTION
        addResponse("%1$s I'd say approximately 42.", Trigger.Type.QUESTION);
        addResponse("%1$s My 8ball broke, try again later?", Trigger.Type.QUESTION);
    }

    private String content;

    public static void addTrigger(Trigger t) {
        triggers.get(t.getPriority()).add(t);
    }

    public static void addResponse(String response, Trigger.Type type) { responses.get(type).add(response); }

    public static void addNeutralResponse(String response) { addResponse(response, Trigger.Type.NEUTRAL); }

    public static void addPositiveResponse(String response) { addResponse(response, Trigger.Type.POSITIVE) ;}

    public static void addNegativeResponse(String response) { addResponse(response, Trigger.Type.NEGATIVE) ;}

    public static void addQuestionResponse(String response) { addResponse(response, Trigger.Type.QUESTION); }

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
        //If no type found (for some reason), return early
        if (type == null) return null;
        Random rng = new Random();
        int bound = responses.get(type).size();
        //If no responses found, return early
        if (bound == 0) return null;
        int index = rng.nextInt(bound);
        //Return random response
        return responses.get(type).get(index);
    }
}
