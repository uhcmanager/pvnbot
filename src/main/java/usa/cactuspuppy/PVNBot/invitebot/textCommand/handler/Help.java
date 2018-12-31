package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandHandler;

import java.awt.*;

public class Help extends TextCommandHandler {
    private static EmbedBuilder eb = new EmbedBuilder();
    static {
        eb.setAuthor("Invitation Bot");
        eb.setColor(new Color(94, 78, 255));
        eb.addField("Commands:",
                "Command for everyone!\n" +
                        "**;help** - Shows this page\n" +
                        "**;ping** - Pong! Heartbeat command.\n" +
                        "**;roll** *<# rolls> d<# sides>* - Rolls dice. Makes Remmy happy.",
                false);
        eb.addField("**Admin Commands**",
                "To use these commands, you must have a higher role than the bot's highest role\n" +
                        "**;bind** *<Channel ID> <Message ID>* - Binds bot to specified message, making it the invitation message" ,
                false);
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
