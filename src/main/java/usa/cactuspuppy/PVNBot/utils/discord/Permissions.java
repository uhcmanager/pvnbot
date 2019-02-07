package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.entities.IPermissionHolder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import usa.cactuspuppy.PVNBot.Main;

import java.util.List;

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
        if (m.getRoles().isEmpty()) {
            return m.isOwner();
        }
        if (MainGuild.get().getSelfMember().getRoles().isEmpty()) {
            return true;
        }
        if (!MainGuild.get().getRolesByName("Bot Operator", true).isEmpty()) {
            List<Role> botOp = MainGuild.get().getRolesByName("Bot Operator", true);
            List<Role> mRoles = m.getRoles();
            mRoles.retainAll(botOp);
            return mRoles.isEmpty();
        } else {
            return m.getRoles().get(0).getPosition() >= MainGuild.get().getSelfMember().getRoles().get(0).getPosition();
        }
    }
}
