package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.entities.IPermissionHolder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import usa.cactuspuppy.PVNBot.Main;

/**
 * Utility class for often used permission methods
 */
public final class Permissions {
    /**
     * Whether the bot should have jurisdiction over the specified member
     * @param m Member to check
     * @return Whether bot can act on member
     */
    public static boolean isOperator(Member m) {
        if (!MainGuild.get().getRolesByName("Bot Operator", true).isEmpty()) {
            Role botOp = MainGuild.get().getRolesByName("Bot Operator", true).get(0);
            return m.getRoles().contains(botOp);
        } else {
            return m.getRoles().get(0).getPosition() >= MainGuild.get().getSelfMember().getRoles().get(0).getPosition();
        }
    }
}
