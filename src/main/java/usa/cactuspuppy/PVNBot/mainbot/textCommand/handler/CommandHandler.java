package usa.cactuspuppy.PVNBot.mainbot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CommandHandler {

    /**
     * Handles the specific command passed in
     * @param e event in question
     */
    public abstract void onCommand(MessageReceivedEvent e, String[] args);

    public boolean hasPermission(MessageReceivedEvent e) { return true; }
}
