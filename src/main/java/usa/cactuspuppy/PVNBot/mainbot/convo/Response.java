package usa.cactuspuppy.PVNBot.mainbot.convo;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Response {
    private static List<Trigger> triggers = new ArrayList<>();
    private String content;

    public boolean shouldRespond() {
        //TODO: Determine if should respond
        return false;
    }

    /**
     * Prepare a response based on the message
     * @return formattable string with placeholders
     *
     */
    public String get() {
        //TODO: Return a response
        return null;
    }
}
