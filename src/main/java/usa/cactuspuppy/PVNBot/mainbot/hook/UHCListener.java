package usa.cactuspuppy.PVNBot.mainbot.hook;

import usa.cactuspuppy.PVNBot.mainbot.mirror.entity.DiscordGroup;
import usa.cactuspuppy.PVNBot.mainbot.mirror.entity.DiscordTeam;
import usa.cactuspuppy.uhc_automation.event.EventListener;
import usa.cactuspuppy.uhc_automation.event.game.group.GroupCreateEvent;
import usa.cactuspuppy.uhc_automation.event.game.group.GroupSetTeamEvent;
import usa.cactuspuppy.uhc_automation.event.game.team.TeamCreateEvent;
import usa.cactuspuppy.uhc_automation.event.game.update.GameStartEvent;

public class UHCListener extends EventListener {
    @Override
    public void onGroupCreate(GroupCreateEvent e) {
        new DiscordGroup(e.getGroup());
    }

    @Override
    public void onGroupSetTeam(GroupSetTeamEvent e){
        DiscordGroup dg = DiscordGroup.getGroupByID(e.getGroup().getId());
    }

    @Override
    public void onTeamCreate(TeamCreateEvent e) {
        new DiscordTeam(e.getTeam());
    }

    @Override
    public void onGameStart(GameStartEvent e) {

    }
}
