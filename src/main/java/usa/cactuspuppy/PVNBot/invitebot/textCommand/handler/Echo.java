package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.utils.discord.Permissions;

import java.util.StringJoiner;

public class Echo extends TextCommandHandler {
    @Override
    public void onCommand(String[] args, MessageReceivedEvent e) {
        StringJoiner echo = new StringJoiner(" ");
        for (String a : args) {
            echo.add(a);
        }
        String toEcho = echo.toString();
        e.getMessage().delete().queue();
        e.getChannel().sendMessage(toEcho).queue();
    }

    @Override
    public boolean hasPermission(String[] args, MessageReceivedEvent e) {
        return Permissions.isOperator(e.getMember());
    }
}
