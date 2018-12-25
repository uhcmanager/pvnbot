package usa.cactuspuppy.PVNBot.invitebot.textCommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class TextCommandHandler {
    /**
     * Called to handle this command
     * @param args arguments passed into command
     * @param e event in question
     */
    public abstract void onCommand(String[] args, MessageReceivedEvent e);

    /**
     * Return whether sender has permission to run this command
     * @param args to command
     * @param e event called
     * @return access granted/failed
     */
    public boolean hasPermission(String[] args, MessageReceivedEvent e) {
        return true;
    }
}
