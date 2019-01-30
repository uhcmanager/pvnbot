package usa.cactuspuppy.PVNBot.mainbot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.mainbot.Delegator;
import usa.cactuspuppy.PVNBot.utils.discord.Permissions;

public class SetPrefix extends CommandHandler {
    @Override
    public void onCommand(MessageReceivedEvent e, String[] args) {
        if (args.length != 1) {
            e.getChannel().sendMessage(String.format("%s Usage: !setprefix <prefix>", e.getAuthor().getAsMention())).queue();
            return;
        }
        String newPrefix = args[0];
        Delegator.setCmdPrefix(newPrefix);
        e.getChannel().sendMessage(String.format("%s Set the bot command prefix to `" + newPrefix + "`", e.getAuthor().getAsMention())).queue();
    }

    @Override
    public boolean hasPermission(MessageReceivedEvent e) {
        return Permissions.isOperator(e.getMember());
    }
}
