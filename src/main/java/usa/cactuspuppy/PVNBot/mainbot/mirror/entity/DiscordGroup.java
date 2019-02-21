package usa.cactuspuppy.PVNBot.mainbot.mirror.entity;

import lombok.Getter;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.VoiceChannel;
import usa.cactuspuppy.PVNBot.utils.Bridge;
import usa.cactuspuppy.PVNBot.utils.discord.EntityCreator;
import usa.cactuspuppy.PVNBot.utils.discord.EntityMagician;
import usa.cactuspuppy.PVNBot.utils.discord.MainGuild;
import usa.cactuspuppy.uhc_automation.entity.unique.Group;
import usa.cactuspuppy.uhc_automation.entity.unique.Team;

import java.util.*;
import java.util.stream.Collectors;

public class DiscordGroup extends MirrorEntity {
    private static Map<Long, DiscordGroup> groupIDMap = new HashMap<>();

    @Getter private long groupID;
    @Getter private Group group;
    @Getter private long voiceID;
    @Getter private DiscordTeam parentTeam;
    private Set<Long> members = new HashSet<>();

    public DiscordGroup(Group g) {
        new Thread(() -> {
            groupID = g.getId();
            group = g;
            groupIDMap.put(groupID, this);
            Team parent = g.getTeam();
            String name = "Group " + g.getNum();
            //Create new voice channel
            if (parent == null) {
                voiceID = EntityCreator.createVoiceChannel(-1, name, true);
            } else {
                DiscordTeam parentDiscordTeam = DiscordTeam.getDiscordTeamByTeamID(parent.getId());
                voiceID = EntityCreator.createVoiceChannel(parentDiscordTeam.getCategoryID(), name, true);
                parentDiscordTeam.addGroup(this);
            }
            //Set permissions for the new voice channel
            EntityMagician.hideEntity(voiceID, ChannelType.VOICE, MainGuild.get().getPublicRole());
            //Add all members from the group
            g.getPlayers().stream().map(Bridge::mcToDiscord).filter(l -> l != -1).forEach(members::add);
            moveMembersIntoChannel(members.stream().mapToLong(Long::valueOf).toArray());
        }).start();
    }

    public void moveMembersIntoChannel(long... memberIDs) {
        VoiceChannel vc = MainGuild.get().getVoiceChannelById(voiceID);
        Arrays.stream(memberIDs).mapToObj(MainGuild.get()::getMemberById).forEach(m ->
                MainGuild.getController().moveVoiceMember(m, vc).queue());
        //Grant permissions
        Arrays.stream(memberIDs).mapToObj(MainGuild.get()::getMemberById).forEach(m -> EntityMagician.activateEntity(voiceID, ChannelType.VOICE, m));
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

    public void removeMembers(long... members) {
        Arrays.stream(members).forEach(this.members::remove);
        //Remove voice permissions
        Arrays.stream(members).mapToObj(MainGuild.get()::getMemberById).forEach(m -> EntityMagician.hideEntity(voiceID, ChannelType.VOICE, m));
    }

    public void setParentTeam(DiscordTeam parent) {
        parent.removeGroup(this);
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
