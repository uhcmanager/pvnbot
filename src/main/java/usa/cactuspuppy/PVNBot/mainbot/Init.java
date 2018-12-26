package usa.cactuspuppy.PVNBot.mainbot;

import net.dv8tion.jda.core.JDA;
import usa.cactuspuppy.PVNBot.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Init {
    public static void main(String[] args) {
        JDA jda = Main.getMainJDA();
        jda.addEventListener(new Delegator());
        createDataFolder();
        File cmdPrefixFile = new File(Main.getDataPath() + "/mainBot", "cmdPrefix.dat");
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
    }

    public static void createDataFolder() {
        File dataFolder = new File(Main.getDataPath() + "/mainBot");
        if (!dataFolder.isDirectory() && !dataFolder.mkdirs()) {
            Main.getLogger().warning("Could not create data folder for main bot");
        }
    }
}
