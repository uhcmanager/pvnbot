package usa.cactuspuppy.PVNBot;

import org.bukkit.plugin.java.JavaPlugin;

public class Wrapper extends JavaPlugin {
    @Override
    public void onEnable() {
        Main.main(new String[1]);
    }

    @Override
    public void onDisable() {

    }
}
