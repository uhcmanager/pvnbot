package usa.cactuspuppy.PVNBot.mainbot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.mainbot.textCommand.CommandHandler;

public class Ping extends CommandHandler {

    @Override
    public void onCommand(MessageReceivedEvent e, String[] args) {
        e.getChannel().sendMessage("Pong!").queue();
    }

    @Override
    public boolean hasPermission(MessageReceivedEvent e) {
        return true;
    }
}
