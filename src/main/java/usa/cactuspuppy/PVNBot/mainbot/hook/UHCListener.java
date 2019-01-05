package usa.cactuspuppy.PVNBot.mainbot.hook;

import org.bukkit.entity.Player;
import usa.cactuspuppy.uhc_automation.event.EventListener;
import usa.cactuspuppy.uhc_automation.event.game.player.PlayerGroupEvent;

public class UHCListener extends EventListener {
    @Override
    public void onPlayerGroup(PlayerGroupEvent e) {
        Player a = e.getPlayer();
        Player b = e.getOtherPlayer();
    }
}
