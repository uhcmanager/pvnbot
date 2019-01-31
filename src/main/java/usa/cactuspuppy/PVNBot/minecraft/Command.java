package usa.cactuspuppy.PVNBot.minecraft;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

@org.bukkit.plugin.java.annotation.command.Command(name = "pvn", desc = "Access the Pirates vs. Ninjas Discord bot", permission = "pvn.admin")
public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        return null;
    }
}
