package usa.cactuspuppy.PVNBot.mainbot.mirror.entity;

import lombok.Getter;
import usa.cactuspuppy.PVNBot.utils.discord.EntityCreator;
import usa.cactuspuppy.uhc_automation.entity.unique.Group;
import usa.cactuspuppy.uhc_automation.entity.unique.Team;

import java.util.HashMap;
import java.util.Map;

public class DiscordGroup extends MirrorEntity {
    private static Map<Long, DiscordGroup> groupIDMap = new HashMap<>();

    @Getter private long groupID;
    @Getter private Group group;
    @Getter private long voiceID;
    @Getter private DiscordTeam parentTeam;

    public DiscordGroup(Group g) {
        groupID = g.getId();
        group = g;
        groupIDMap.put(groupID, this);
        Team parent = g.getTeam();
        String name = "Group " + g.getNum();
        if (parent == null) {
            voiceID = EntityCreator.createVoiceChannel(-1, name, true);
        } else {
            DiscordTeam parentDiscordTeam = DiscordTeam.getDiscordTeamByTeamID(parent.getId());
            voiceID = EntityCreator.createVoiceChannel(parentDiscordTeam.getCategoryID(), name, true);
        }
    }

    public static DiscordGroup getGroupByID(long id) {
        return groupIDMap.get(id);
    }
}
