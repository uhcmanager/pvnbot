package usa.cactuspuppy.PVNBot;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import usa.cactuspuppy.PVNBot.invitebot.InviteMain;
import usa.cactuspuppy.PVNBot.mainbot.Init;
import usa.cactuspuppy.PVNBot.utils.FileIO;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Logger;

public class Main {
    @Getter @Setter private static JDA invitationJDA;
    @Getter private static JDA mainJDA;
    @Getter @Setter private static Logger logger = null;
    @Getter private static String dataPath = "data"; //where to store data
    @Getter @Setter private static String mainGuild;
    private static int keyState = -1;
    private static String[] argsArr = new String[0];
    private static JFrame frame = null;

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
        argsArr = args;
        if (logger == null) logger = Logger.getLogger(Main.class.getName());
        startInvBot();
        startMainBot();
        if (args.length > 0 && args[0].equalsIgnoreCase("true")) {
            initKeyListener();
        }
    }

    public static void halt() {
        keyState = -1;
        logger.info("Shutting down...");
        invitationJDA.shutdown();
        mainJDA.shutdown();
    }

    public static void startInvBot() {
        InviteMain.main();
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

    public static void initKeyListener() {
        keyState = 0;
        if (frame != null) return;
        frame = new JFrame();
        JTextField textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char pressed = e.getKeyChar();
                if (keyState == 0) {
                    if (pressed == ':') {
                        keyState = 1;
                        logger.info("Listening for command...");
                    }
                } else if (keyState == 1) {
                    if (pressed == 'q') {
                        halt();
                        System.exit(0);
                    } else if (pressed == 'r') {
                        logger.info("Restarting...");
                        invitationJDA.shutdown();
                        mainJDA.shutdown();
                        main(argsArr);
                    }
                    else keyState = 0;
                }
            }
        });
        frame.add(textField);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(600, 60);
    }
}
