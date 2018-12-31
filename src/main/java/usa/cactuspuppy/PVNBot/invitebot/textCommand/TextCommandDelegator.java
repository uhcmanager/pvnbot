package usa.cactuspuppy.PVNBot.invitebot.textCommand;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.handler.Bind;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.handler.Help;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.handler.Ping;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.handler.Roll;

import java.util.HashMap;
import java.util.Map;

public class TextCommandDelegator {
    private static Map<String, TextCommandHandler> commandHandlerMap = new HashMap<String, TextCommandHandler>();
    static {
        addHandler("ping", new Ping());
        addHandler("help", new Help());
        addHandler("bind", new Bind());
        addHandler("roll", new Roll());
    }

    static void addHandler(String name, TextCommandHandler handler) {
        commandHandlerMap.put(name, handler);
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
        if (!handler.hasPermission(args, e)) {
            e.getChannel().sendMessage(String.format("You do not have permission to run this command %s.", e.getAuthor().getAsMention())).queue();
            return;
        }
        handler.onCommand(args, e);
    }
}
