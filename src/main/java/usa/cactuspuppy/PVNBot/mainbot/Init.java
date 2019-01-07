package usa.cactuspuppy.PVNBot.mainbot;

import net.dv8tion.jda.core.JDA;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.mainbot.hook.UHCHook;
import usa.cactuspuppy.PVNBot.utils.FileIO;
import usa.cactuspuppy.PVNBot.constants.main.MainData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Init {
    public static void main(String[] args) {
        JDA jda = Main.getMainJDA();
        jda.addEventListener(new Delegator());
        loadData();
        UHCHook.hook();
    }

    public static void loadData() {
        createDataFolder();
        File cmdPrefixFile = new File(Main.getDataPath() + MainData.DIR.toString(), MainData.CMD_PREFIX.toString());
        if (cmdPrefixFile.isFile()) {
            try {
                Scanner scan = new Scanner(cmdPrefixFile);
                String prefix = scan.next();
                scan.close();
                Delegator.setCmdPrefix(prefix);
            } catch (FileNotFoundException e) {
                Delegator.setCmdPrefix("!");
            }
        }
        File mainGuildFile = new File(Main.getDataPath() + MainData.DIR.toString(), MainData.GUILD_ID.toString());
        if (mainGuildFile.isFile()) {
            try {
                String id = FileIO.readToken(new FileInputStream(mainGuildFile));
                Main.setMainGuildID(id);
            } catch (FileNotFoundException e) {
                Main.getLogger().warning("Could not find ID file, defaulting to hardcoded value.");
                e.printStackTrace();
                Main.setMainGuildID("524660535556309020"); //Current PvN server ID
            }
        }
        File pvnInstanceFile = new File(Main.getDataPath() + MainData.DIR.toString(), MainData.PVN_INSTANCE.toString());
        if (pvnInstanceFile.isFile()) {
            try {

            }
        }
    }

    public static void createDataFolder() {
        File dataFolder = new File(Main.getDataPath() + "/mainBot");
        if (!dataFolder.isDirectory() && !dataFolder.mkdirs()) {
            Main.getLogger().warning("Could not create data folder for main bot");
        }
    }
}
