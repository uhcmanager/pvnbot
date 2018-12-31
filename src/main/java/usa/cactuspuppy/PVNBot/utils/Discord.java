package usa.cactuspuppy.PVNBot.utils;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.RoleManager;
import usa.cactuspuppy.PVNBot.Main;

/**
 * Utility class for managing channels and roles
 */
public final class Discord {

    /**
     * Method for generating a controller
     * @return a GuildController for the main guild
     */
    public static GuildController getMainController() {
        return new GuildController(Main.getMainJDA().getGuildById(Main.getMainGuild()));
    }

    /**
     * Creates a category with the given name
     * @param name what to name category
     * @param allowDuplicate whether to check for a duplicate category by name, ignoring case
     * @return ID of category, -1 if duplicate is found and not allowed
     */
    public static long createCategory(String name, boolean allowDuplicate) {
        GuildController controller = getMainController();
        //Found a duplicate
        if (!allowDuplicate && !controller.getGuild().getCategoriesByName(name, true).isEmpty()) return -1;
        return controller.createCategory(name).complete().getIdLong();
    }

    /**
     * Creates a role with the given name
     * @param name New role's name
     * @param allowDuplicate Whether to check for a duplicate role by name, ignoring case
     * @return ID of new role, -1 if a duplicate is found
     */
    public static long createRole(String name, boolean allowDuplicate) {
        GuildController controller = getMainController();
        //Found a duplicate
        if (!allowDuplicate && !controller.getGuild().getRolesByName(name, true).isEmpty()) return -1;
        Role role = controller.createRole().complete();
        RoleManager roleManager = role.getManager();
        roleManager.setName(name).queue();
        return role.getIdLong();
    }

    /**
     * Creates a voice channel with the given name in the specified category
     * @param category Category to create voice channel in
     * @param name Name of voice channel
     * @param allowDuplicate Whether to check for a duplicate channel by name, ignoring case
     * @return ID of new channel, -1 if a duplicate is found
     */
    public static long createVoiceChannel(long category, String name, boolean allowDuplicate) {
        GuildController controller = getMainController();
        //Found a duplicate
        if (!allowDuplicate && !controller.getGuild().getVoiceChannelsByName(name, true).isEmpty()) return -1;
        VoiceChannel vc = (VoiceChannel) controller.getGuild().getCategoryById(category).createVoiceChannel(name).complete();
        return vc.getIdLong();
    }

    /**
     * Creates a text channel with the given name in the specified category
     * @param category Category to create text channel in
     * @param name Name of text channel
     * @param allowDuplicate Whether to check for a duplicate channel by name, ignoring case
     * @return ID of new channel, -1 if a duplicate is found
     */
    public static long createTextChannel(long category, String name, boolean allowDuplicate) {
        GuildController controller = getMainController();
        //Found a duplicate
        if (!allowDuplicate && !controller.getGuild().getTextChannelsByName(name, true).isEmpty()) return -1;
        TextChannel tc = (TextChannel) controller.getGuild().getCategoryById(category).createTextChannel(name).complete();
        return tc.getIdLong();
    }
}
