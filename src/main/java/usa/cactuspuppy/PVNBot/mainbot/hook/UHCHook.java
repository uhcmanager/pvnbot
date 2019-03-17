package usa.cactuspuppy.PVNBot.mainbot.hook;

import org.bukkit.Bukkit;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.Wrapper;

public class UHCHook {
    public static void hook() {
        try {
            Class.forName("usa.cactuspuppy.uhc_automation.event.game.GameEvent");
            Bukkit.getPluginManager().registerEvents(new UHCListener(), Wrapper.getInstance());
        } catch (ClassNotFoundException | NullPointerException e) {
            Main.getLogger().warning("Could not find UHCAutomation, not enabling integration...");
        }
    }
}
