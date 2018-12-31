package usa.cactuspuppy.PVNBot.utils;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import usa.cactuspuppy.PVNBot.Main;

import java.util.List;

public final class Bridge {
    /**
     * Returns the ID of a given Minecraft username.
     * @param mc_name name to find
     * @return ID of the Discord user
     */
    public static long mcToDiscord(String mc_name) {
        JDA jda = Main.getMainJDA();
        Guild main =  jda.getGuildById(Main.getMainGuild());
        if (main == null) {
            Main.getLogger().warning("Could not find guild with ID: " + Main.getMainGuild());
            return -1;
        }
        List<Member> candidates = main.getMembersByEffectiveName(mc_name, false);
        if (candidates.isEmpty()) {
            Main.getLogger().info("Could not find user with effective name: " + mc_name);
            return -1;
        }
        return candidates.get(0).getUser().getIdLong();
    }
}
