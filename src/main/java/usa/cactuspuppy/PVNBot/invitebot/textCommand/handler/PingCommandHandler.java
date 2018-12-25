package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNInvitation.textCommand.TextCommandHandler;

public class PingCommandHandler extends TextCommandHandler {
    /**
     * Called to handle the ping command
     * @param args arguments passed in
     * @param e event in question
     */
    public void onCommand(String[] args, MessageReceivedEvent e) {
        e.getChannel().sendMessage("Pong!").queue();
    }
}
