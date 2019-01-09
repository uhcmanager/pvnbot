package usa.cactuspuppy.PVNBot.mainbot.hook;

import sun.rmi.runtime.Log;
import usa.cactuspuppy.PVNBot.mainbot.mirror.entity.DiscordGroup;
import usa.cactuspuppy.PVNBot.mainbot.mirror.entity.DiscordTeam;
import usa.cactuspuppy.PVNBot.utils.Logger;
import usa.cactuspuppy.uhc_automation.event.EventListener;
import usa.cactuspuppy.uhc_automation.event.game.group.GroupCreateEvent;
import usa.cactuspuppy.uhc_automation.event.game.group.GroupDeleteEvent;
import usa.cactuspuppy.uhc_automation.event.game.group.GroupSetTeamEvent;
import usa.cactuspuppy.uhc_automation.event.game.team.TeamCreateEvent;
import usa.cactuspuppy.uhc_automation.event.game.update.GameStartEvent;

public class UHCListener extends EventListener {
    @Override
    public void onGroupCreate(GroupCreateEvent e) {
        new DiscordGroup(e.getGroup());
    }

    @Override
    public void onTeamCreate(TeamCreateEvent e) {
        new DiscordTeam(e.getTeam());
    }

    @Override
    public void onGroupSetTeam(GroupSetTeamEvent e){
        DiscordGroup dg = DiscordGroup.getGroupByID(e.getGroup().getId());
        if (dg == null) {
            Logger.logInfo(this.getClass(), "Could not find matching Discord group, creating...");
            dg = new DiscordGroup(e.getGroup());
        }
        DiscordTeam mirrorParent = DiscordTeam.getDiscordTeamByTeamID(e.getTeam().getId());
        if (mirrorParent == null) {
            Logger.logInfo(this.getClass(), "Could not find matching Discord parent team, creating...");
            mirrorParent = new DiscordTeam(e.getTeam());
        }
        dg.setParentTeam(mirrorParent);
    }

    @Override
    public void onGroupDelete(GroupDeleteEvent e) {
        DiscordGroup dg = DiscordGroup.getGroupByID(e.getGroup().getId());
        if (dg == null) return;
        dg.delete();
    }

    @Override
    public void onGameStart(GameStartEvent e) {

    }
}
