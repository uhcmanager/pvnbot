package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandHandler;

import java.awt.*;

public class HelpCommandHandler extends TextCommandHandler {
    private static EmbedBuilder eb = new EmbedBuilder();
    static {
        eb.setAuthor("Invitation Bot");
        eb.setColor(Color.GREEN);
        eb.addField("Commands:", ";help\n;ping\n;bind", false);
    }
    /**
     * Called to handle the help command
     *
     * @param args arguments passed into command
     * @param e    event in question
     */
    @Override
    public void onCommand(String[] args, MessageReceivedEvent e) {
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
