package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.managers.ChannelManager;
import net.dv8tion.jda.core.managers.GuildController;

/**
 * Shows/Hides channels from permission holders
 */
public class ChannelMagician {
    /**
     * Hides the specified text or voice channel from the specified permission holder
     * @param channelID the channel to restrict
     * @param type Type of the channel
     * @param holder Permission holder
     * @throws RuntimeException if bot is unable to perform action
     */
    public static void hideChannel(long channelID, ChannelType type, IPermissionHolder holder) throws RuntimeException {
        if (type == null) return;
        GuildController controller = MainGuild.getController();
        Guild main = controller.getGuild();
        try {
            if (type.equals(ChannelType.TEXT)) {
                TextChannel channel = main.getTextChannelById(channelID);
                if (channel == null) throw new RuntimeException("Could not find text channel with ID: " + channelID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, 0, Permission.getRaw(Permission.MESSAGE_READ)).queue();
            } else if (type.equals(ChannelType.VOICE)) {
                VoiceChannel channel = main.getVoiceChannelById(channelID);
                if (channel == null) throw new RuntimeException("Could not find voice channel with ID: " + channelID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, 0, Permission.getRaw(Permission.VIEW_CHANNEL)).queue();
            }
        } catch (InsufficientPermissionException e) {
            throw new RuntimeException("Insufficient permissions");
        }
    }

    /**
     * Activates the specified channel for use by the specified permission holder
     * @param channelID the channel to activate
     * @param type Type of the channel
     * @param holder Permission holder
     * @throws RuntimeException if bot is unable to perform action
     */
    public static void activateChannel(long channelID, ChannelType type, IPermissionHolder holder) throws RuntimeException {
        if (type == null) return;
        GuildController controller = MainGuild.getController();
        Guild main = controller.getGuild();
        try {
            if (type.equals(ChannelType.TEXT)) {
                TextChannel channel = main.getTextChannelById(channelID);
                if (channel == null) throw new RuntimeException("Could not find text channel with ID: " + channelID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, Permission.getRaw(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE), 0).queue();
            } else if (type.equals(ChannelType.VOICE)) {
                VoiceChannel channel = main.getVoiceChannelById(channelID);
                if (channel == null) throw new RuntimeException("Could not find voice channel with ID: " + channelID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, Permission.getRaw(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.VOICE_USE_VAD), 0).queue();
            }
        } catch (InsufficientPermissionException e) {
            throw new RuntimeException("Insufficient permissions");
        }
    }

}
