package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.GuildController;
import usa.cactuspuppy.PVNBot.Main;

public class MainGuild {
    public static GuildController getController() {
        return new GuildController(Main.getMainJDA().getGuildById(Main.getMainGuildID()));
    }

    public static Guild get() {
        return Main.getMainJDA().getGuildById(Main.getMainGuildID());
    }
}
