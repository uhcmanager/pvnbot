package usa.cactuspuppy.PVNBot.mainbot.hook;

import usa.cactuspuppy.PVNBot.Main;

public class UHCHook {
    public static void hook() {
        try {
            Class.forName("usa.cactuspuppy.uhc_automation.event.EventListener");
            new UHCListener();
        } catch (ClassNotFoundException e) {
            Main.getLogger().warning("Could not hook into UHC Automation");
        }
    }
}
