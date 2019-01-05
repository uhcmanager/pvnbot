package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.managers.GuildController;
import usa.cactuspuppy.PVNBot.Main;

public class MainController {
    public static GuildController get() {
        return new GuildController(Main.getMainJDA().getGuildById(Main.getMainGuild()));
    }
}
