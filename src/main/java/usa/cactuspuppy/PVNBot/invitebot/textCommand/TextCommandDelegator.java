package usa.cactuspuppy.PVNBot.invitebot.textCommand;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.handler.*;
import usa.cactuspuppy.PVNBot.utils.discord.Messaging;

import java.util.HashMap;
import java.util.Map;

public class TextCommandDelegator {
    private static Map<String, TextCommandHandler> commandHandlerMap = new HashMap<String, TextCommandHandler>();
    static {
        addHandler("ping", new Ping());
        addHandler("help", new Help());
        addHandler("bind", new Bind());
        addHandler("roll", new Roll());
        addHandler("minesweep", new Minesweeper());
        addHandler("echo", new Echo());
    }

    private static void addHandler(String name, TextCommandHandler handler) {
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
                Messaging.sendSnapMsg(String.format("Unknown command `%s`. Type `;help` for a list of commands.", command), e.getChannel());
            }
            return;
        }
        if (!handler.hasPermission(args, e)) {
            e.getMessage().delete().queue();
            return;
        }
        handler.onCommand(args, e);
    }
}
