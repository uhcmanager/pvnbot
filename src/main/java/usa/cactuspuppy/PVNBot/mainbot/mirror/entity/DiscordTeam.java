package usa.cactuspuppy.PVNBot.mainbot.mirror.entity;

import lombok.Getter;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import usa.cactuspuppy.PVNBot.utils.Bridge;
import usa.cactuspuppy.PVNBot.utils.Logger;
import usa.cactuspuppy.PVNBot.utils.discord.EntityCreator;
import usa.cactuspuppy.PVNBot.utils.discord.EntityMagician;
import usa.cactuspuppy.PVNBot.utils.discord.MainGuild;
import usa.cactuspuppy.uhc_automation.entity.unique.Team;

import java.util.*;

public class DiscordTeam extends MirrorEntity {
    private static Map<Long, DiscordTeam> teamIDMap = new HashMap<>();

    @Getter private long teamID;
    @Getter private Team team;
    @Getter private long categoryID;
    @Getter private long roleID;
    private Set<DiscordGroup> groups = new HashSet<>();
    private Set<Long> members = new HashSet<>();

    public DiscordTeam(Team team) {
        new Thread(() -> {
            Guild main = MainGuild.get();
            if (main.getCategoriesByName(team.getName(), false).size() != 0) {
                categoryID = main.getCategoriesByName(team.getName(), false).get(0).getIdLong();
            } else {
                categoryID = EntityCreator.createCategory(team.getName(), true);
            }
            if (categoryID == -1) {
                Logger.logWarning(this.getClass(), "Could not create or bind to mirroring category for team");
                return;
            }
            if (main.getRolesByName(team.getName(), false).size() != 0) {
                roleID = main.getRolesByName(team.getName(), false).get(0).getIdLong();
            } else {
                roleID = EntityCreator.createRole(team.getName(), true);
            }
            teamID = team.getId();
            this.team = team;
            teamIDMap.put(teamID, this);
            team.getPlayers().stream().map(Bridge::mcToDiscord).filter(l -> l != -1).forEach(members::add);
            EntityMagician.hideEntity(categoryID, ChannelType.CATEGORY, main.getPublicRole());
            EntityMagician.activateEntity(categoryID, ChannelType.CATEGORY, main.getRoleById(roleID));
        }).start();
    }

    /**
     * Returns a copy of the set of members of this {@code DiscordTeam}
     * @return Copy of {@code members}
     */
    public Set<Long> getMembers() {
        return new HashSet<>(members);
    }

    public void addGroup(DiscordGroup g) {
        groups.add(g);
    }

    public void removeGroup(DiscordGroup g) {
        groups.remove(g);
    }

    /**
     * @return A copy of {@code groups}
     */
    public Set<DiscordGroup> getGroups() {
        return new HashSet<>(groups);
    }

    public void addMembers(Long... members) {
        this.members.addAll(Arrays.asList(members));
    }

    public void removeMembers(Long... members) {
        this.members.removeAll(Arrays.asList(members));
    }

    public void removeAllMembers() {
        members.clear();
    }

    public void delete() {
        MirrorEntity.delete(this);
        teamIDMap.remove(teamID, this);
        MainGuild.get().getRoleById(roleID).delete().queue();
        MainGuild.get().getCategoryById(categoryID).delete().queue();
    }

    public static DiscordTeam getDiscordTeamByTeamID(long id) {
        return teamIDMap.get(id);
    }
}
