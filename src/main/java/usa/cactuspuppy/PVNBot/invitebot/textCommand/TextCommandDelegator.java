package usa.cactuspuppy.PVNBot.invitebot.textCommand;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNInvitation.textCommand.handler.BindCommandHandler;
import usa.cactuspuppy.PVNInvitation.textCommand.handler.HelpCommandHandler;
import usa.cactuspuppy.PVNInvitation.textCommand.handler.PingCommandHandler;

import java.util.HashMap;
import java.util.Map;

public class TextCommandDelegator {
    private static Map<String, TextCommandHandler> commandHandlerMap = new HashMap<String, TextCommandHandler>();
    static {
        commandHandlerMap.put("ping", new PingCommandHandler());
        commandHandlerMap.put("help", new HelpCommandHandler());
        commandHandlerMap.put("bind", new BindCommandHandler());
    }

    public static void delegate(String command, String[] args, MessageReceivedEvent e) {
        TextCommandHandler handler = commandHandlerMap.get(command);
        //Unknown command
        if (handler == null) {
            if (e.isFromType(ChannelType.TEXT)) {
                String mention = e.getAuthor().getAsMention();
                e.getChannel().sendMessage(String.format("%s Unknown command `%s`. Type `;help` for a list of commands.", mention, command)).queue();
            } else {
                e.getChannel().sendMessage(String.format("Unknown command `%s`. Type `;help` for a list of commands.", command)).queue();
            }
            return;
        }
        handler.onCommand(args, e);
    }
}
