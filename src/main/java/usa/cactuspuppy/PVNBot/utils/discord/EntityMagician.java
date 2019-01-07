package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.managers.ChannelManager;
import net.dv8tion.jda.core.managers.GuildController;

/**
 * Shows/Hides channels from permission holders
 */
public class EntityMagician {
    private static final Permission[] TEXT_BASE = {Permission.VIEW_CHANNEL, Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY};
    private static final Permission[] TEXT_EXTRA = {Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_EXT_EMOJI};
    private static final Permission[] VOICE_BASE = {Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.VOICE_USE_VAD};

    /**
     * Hides the specified entity from the specified permission holder
     * @param entityID the entity to restrict
     * @param type Type of the entity
     * @param holder Permission holder
     * @throws RuntimeException if bot is unable to perform action
     */
    public static void hideEntity(long entityID, ChannelType type, IPermissionHolder holder) throws RuntimeException {
        if (type == null) return;
        Guild main = MainGuild.get();
        try {
            if (type.equals(ChannelType.TEXT)) {
                TextChannel channel = main.getTextChannelById(entityID);
                if (channel == null) throw new RuntimeException("Could not find text channel with ID: " + entityID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, 0, Permission.getRaw(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL)).queue();
            } else if (type.equals(ChannelType.VOICE)) {
                VoiceChannel channel = main.getVoiceChannelById(entityID);
                if (channel == null) throw new RuntimeException("Could not find voice channel with ID: " + entityID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, 0, Permission.getRaw(Permission.VIEW_CHANNEL)).queue();
            } else if (type.equals(ChannelType.CATEGORY)) {
                Category category = main.getCategoryById(entityID);
                if (category == null) throw new RuntimeException("Could not find category with ID: " + entityID);
                ChannelManager manager = new ChannelManager(category);
                manager.putPermissionOverride(holder, 0, Permission.getRaw(Permission.VIEW_CHANNEL)).queue();
            }
        } catch (InsufficientPermissionException e) {
            throw new RuntimeException("Insufficient permissions");
        }
    }

    /**
     * Activates the specified entity for use by the specified permission holder
     * @param entityID the entity to activate
     * @param type Type of the entity
     * @param holder Permission holder
     * @throws RuntimeException if bot is unable to perform action
     */
    public static void activateEntity(long entityID, ChannelType type, IPermissionHolder holder) throws RuntimeException {
        if (type == null) return;
        GuildController controller = MainGuild.getController();
        Guild main = controller.getGuild();
        try {
            if (type.equals(ChannelType.TEXT)) {
                grantTextOverride(entityID, holder, main, TEXT_BASE);
            } else if (type.equals(ChannelType.VOICE)) {
                VoiceChannel channel = main.getVoiceChannelById(entityID);
                if (channel == null) throw new RuntimeException("Could not find voice channel with ID: " + entityID);
                ChannelManager manager = new ChannelManager(channel);
                manager.putPermissionOverride(holder, Permission.getRaw(VOICE_BASE), 0).queue();
            } else if (type.equals(ChannelType.CATEGORY)) {
                Category category = main.getCategoryById(entityID);
                if (category == null) throw new RuntimeException("Could not find category with ID: " + entityID);
                ChannelManager manager = category.getManager();
                manager.putPermissionOverride(holder, Permission.getRaw(Permission.VIEW_CHANNEL), 0).putPermissionOverride(holder, Permission.getRaw(TEXT_BASE), 0).putPermissionOverride(holder, Permission.getRaw(VOICE_BASE), 0).queue();
            }
        } catch (InsufficientPermissionException e) {
            throw new RuntimeException("Insufficient permissions");
        }
    }

    /**
     * Grants extra permissions in the given entity for the given permission holder
     * @param entityID Entity to operate on
     * @param type Type of the entity
     * @param holder Permission holder
     * @throws RuntimeException if bot cannot perform this action
     */
    public static void grantExtraPermissions(long entityID, ChannelType type, IPermissionHolder holder) throws RuntimeException {
        Guild main = MainGuild.get();
        if (type.equals(ChannelType.TEXT)) {
            grantTextOverride(entityID, holder, main, TEXT_EXTRA);
        }
    }

    private static void grantTextOverride(long entityID, IPermissionHolder holder, Guild main, Permission[] permission) {
        TextChannel channel = main.getTextChannelById(entityID);
        if (channel == null) throw new RuntimeException("Could not find text channel with ID: " + entityID);
        ChannelManager manager = new ChannelManager(channel);
        manager.putPermissionOverride(holder, Permission.getRaw(permission), 0).queue();
    }
}
