package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.invitebot.InviteMain;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandHandler;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Bind extends TextCommandHandler {
    /**
     * Called to handle the bind command
     *
     * @param args arguments passed into command
     * @param e    event in question
     */
    @Override
    public void onCommand(String[] args, MessageReceivedEvent e) {
        if (args.length < 2) {
            e.getChannel().sendMessage(String.format("%s Not enough args to bind. Usage: `;bind <channelID> <messageID>`", e.getAuthor().getAsMention())).queue();
            return;
        }
        String channelID = args[0];
        String messageID = args[1];

        //TODO: Check message can be found
        TextChannel channel = e.getGuild().getTextChannelById(channelID);
        if (channel == null) {
            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Could not find channel with ID `" + channelID + "`").queue();
            return;
        }
        Message message = channel.getMessageById(messageID).complete();
        if (message == null) {
            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Could not find message with ID `" + messageID + "`").queue();
            return;
        }
        //Found message
        Guild guild = Main.getInvitationJDA().getGuildById("407385931687919616");
        message.addReaction(guild.getEmoteById("525571121936859156")).queue();
        InviteMain.setMessageID(messageID);
        InviteMain.setChannelID(channelID);
        InviteMain.storeMsgID(new ByteArrayInputStream(
                String.format("%s\n%s", channelID, messageID).getBytes()
        ));
        e.getChannel().sendMessage(String.format("%s Successfully started monitoring message ID `%s`", e.getAuthor().getAsMention(), messageID)).queue();
    }

    /**
     * Return whether sender has permission to run this command
     * @param args to command
     * @param e event called
     * @return access granted/failed
     */
    @Override
    public boolean hasPermission(String[] args, MessageReceivedEvent e) {
        int userMax = e.getMember().getRoles().get(0).getPosition();
        int botMax = e.getGuild().getSelfMember().getRoles().get(0).getPosition();
        if (botMax < userMax) return true;
        List<Role> list = e.getGuild().getRoles().stream().filter(r -> r.getName().equalsIgnoreCase("Bot Operator")).collect(Collectors.toList());
        for (Role r : e.getMember().getRoles()) {
            if (list.contains(r)) return true;
        }
        return false;
    }
}
