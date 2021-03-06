package usa.cactuspuppy.PVNBot;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import usa.cactuspuppy.PVNBot.constants.main.MainData;
import usa.cactuspuppy.PVNBot.mainbot.Init;
import usa.cactuspuppy.PVNBot.utils.FileIO;

import javax.security.auth.login.LoginException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.logging.Logger;

public class Main {
    @Getter @Setter private static JDA invitationJDA;
    @Getter private static JDA mainJDA;
    @Getter @Setter private static Logger logger = null;
    @Getter private static String dataPath = "data"; //where to store data
    @Getter private static String mainGuildID;
    @Getter private static long pvnInstanceID;
    /**
     * Entry point for starting all subroutines
     * @param args command line args
     *             args[0] - if true, launch command window
     */
    public static void main(String[] args) {
        try {
            File dataFolder = Wrapper.getPlugin(Wrapper.class).getDataFolder();
            dataPath = dataFolder.getPath();
        } catch (Exception e) {
            dataPath = "data";
        }
        if (logger == null) logger = Logger.getLogger(Main.class.getName());
        startMainBot();
    }

    public static void halt() {
        logger.info("Shutting down...");
        mainJDA.shutdown();
    }

    public static void startMainBot() {
        try {
            String token = FileIO.readToken(Main.class.getResourceAsStream("/mainInfo/secret.txt"));
            mainJDA = new JDABuilder(token).build();
            Init.main(new String[0]);
        } catch (LoginException e) {
            logger.warning("Could not start main bot");
            e.printStackTrace();
        }
    }

    public static void setMainGuildID(String id) {
        mainGuildID = "GuildID: " + id;
        FileIO.saveToFile(dataPath + MainData.DIR.toString(), MainData.GUILD_ID.toString(), new ByteArrayInputStream(id.getBytes()), false);
    }
}
