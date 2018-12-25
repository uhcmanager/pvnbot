package usa.cactuspuppy.PVNBot;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import usa.cactuspuppy.PVNBot.invitebot.InviteMain;
import usa.cactuspuppy.PVNBot.utils.FileIO;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

public class Main {
    @Getter @Setter private static JDA invitationJDA;
    @Getter private static JDA mainJDA;
    @Getter private static Logger logger;

    public static void main(String[] args) {
        logger = Logger.getLogger(Main.class.getName());
        startInvBot();
        startMainBot();
    }

    public static void halt() {
    }

    public static void startInvBot() {
        InviteMain.main();
    }

    public static void startMainBot() {
        try {
            String token = FileIO.readToken(Main.class.getResourceAsStream("mainInfo/secret.txt"));
            mainJDA = new JDABuilder(token).build();
        } catch (LoginException e) {
            logger.warning("Could not start main bot");
            e.printStackTrace();
        }
    }
}
