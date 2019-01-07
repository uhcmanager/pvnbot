package usa.cactuspuppy.PVNBot.mainbot.mirror.entity;

import lombok.Getter;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import usa.cactuspuppy.PVNBot.utils.Bridge;
import usa.cactuspuppy.PVNBot.utils.Logger;
import usa.cactuspuppy.PVNBot.utils.discord.EntityCreator;
import usa.cactuspuppy.PVNBot.utils.discord.EntityMagician;
import usa.cactuspuppy.PVNBot.utils.discord.MainGuild;
import usa.cactuspuppy.uhc_automation.entity.unique.Group;

import java.util.HashMap;
import java.util.Map;

public class DiscordGroup extends MirrorEntity {
    private static Map<Long, DiscordGroup> groupIDMap = new HashMap<>();

    @Getter private long groupID;
    @Getter private Group group;
    @Getter private long voiceID;

    public DiscordGroup(Group g) {
        groupID = g.getId();
        group = g;
        groupIDMap.put(groupID, this);
    }

    public static DiscordGroup getGroupByID(long id) {
        return groupIDMap.get(id);
    }
}
