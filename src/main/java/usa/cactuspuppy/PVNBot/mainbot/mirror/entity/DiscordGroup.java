package usa.cactuspuppy.PVNBot.mainbot.mirror.entity;

import lombok.Getter;
import net.dv8tion.jda.core.entities.VoiceChannel;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.utils.Bridge;
import usa.cactuspuppy.PVNBot.utils.discord.EntityCreator;
import usa.cactuspuppy.PVNBot.utils.discord.MainGuild;
import usa.cactuspuppy.uhc_automation.entity.unique.Group;
import usa.cactuspuppy.uhc_automation.entity.unique.Team;

import java.util.*;

public class DiscordGroup extends MirrorEntity {
    private static Map<Long, DiscordGroup> groupIDMap = new HashMap<>();

    @Getter private long groupID;
    @Getter private Group group;
    @Getter private long voiceID;
    @Getter private DiscordTeam parentTeam;
    private Set<Long> members = new HashSet<>();

    public DiscordGroup(Group g) {
        groupID = g.getId();
        group = g;
        groupIDMap.put(groupID, this);
        Team parent = g.getTeam();
        String name = "Group " + g.getNum();
        //Convert all UUIDs to players
        g.getPlayers().stream().map(Bridge::mcToDiscord).filter(l -> l != -1).forEach(members::add);
        if (parent == null) {
            voiceID = EntityCreator.createVoiceChannel(-1, name, true);
        } else {
            DiscordTeam parentDiscordTeam = DiscordTeam.getDiscordTeamByTeamID(parent.getId());
            voiceID = EntityCreator.createVoiceChannel(parentDiscordTeam.getCategoryID(), name, true);
            parentDiscordTeam.addGroup(this);
        }
        //Pull all members into newly formed voice channel
        moveMembersIntoChannel(members.stream().mapToLong(Long::valueOf).toArray());
    }

    public void moveMembersIntoChannel(long... memberIDs) {
        VoiceChannel vc = MainGuild.get().getVoiceChannelById(voiceID);
        Arrays.stream(memberIDs).mapToObj(MainGuild.get()::getMemberById).forEach(m ->
                MainGuild.getController().moveVoiceMember(m, vc).queue());
    }

    /**
     * @return A copy of {@code members}
     */
    public Set<Long> getMembers() {
        return new HashSet<>(members);
    }

    public void addMembers(long... members) {
        Arrays.stream(members).forEach(this.members::add);
        moveMembersIntoChannel(members);
    }

    public void setParentTeam(DiscordTeam parent) {
        parentTeam = parent;
    }

    public void delete() {
        parentTeam.removeGroup(this);
        MainGuild.get().getVoiceChannelById(voiceID).delete().queue();
        MirrorEntity.delete(this);
    }

    public static DiscordGroup getGroupByID(long id) {
        return groupIDMap.get(id);
    }
}
