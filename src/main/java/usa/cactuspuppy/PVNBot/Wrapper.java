package usa.cactuspuppy.PVNBot;

import io.papermc.lib.PaperLib;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

/**
 * Wrapper class to enable PVNBot to run in Spigot environment.
 */
@Plugin(name = "PVNBot", version = "0.1")
@Description("Provides functionality for Pirates vs. Ninjas UHC")
@Author("CactusPuppy")
@LogPrefix("PVN")
@Permission(name = "pvn.admin", desc = "Allows operator access to the UHC plugin", defaultValue = PermissionDefault.OP)
@Permission(name = "pvn.*", desc = "Wildcard permission", defaultValue = PermissionDefault.OP, children = {@ChildPermission(name = "pvn.admin")})
@ApiVersion(ApiVersion.Target.v1_13)
@SoftDependency("UHC_Automation")
public class Wrapper extends JavaPlugin {
    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);
        Main.main(new String[0]);
    }

    @Override
    public void onDisable() {
        Main.halt();
    }
}
