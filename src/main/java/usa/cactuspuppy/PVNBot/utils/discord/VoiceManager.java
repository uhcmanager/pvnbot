package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.GuildController;

public class VoiceManager {
    public static void merge(VoiceChannel c1, VoiceChannel c2) {
        VoiceChannel smaller = (c1.getMembers().size() < c2.getMembers().size() ? c1 : c2);
        VoiceChannel larger = (c1.getMembers().size() < c2.getMembers().size() ? c2 : c1);

        GuildController controller = MainController.get();

        for (Member m : smaller.getMembers()) {

        }
    }
}
