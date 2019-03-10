package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

public class DiscordLogger {
    public static boolean logTextCommand(long guildID, String logChannel, String msg) {
        return true;
    }

    public static boolean logTextCommand(long guildID, String logChannel, EmbedBuilder embed) {
        return true;
    }
}
