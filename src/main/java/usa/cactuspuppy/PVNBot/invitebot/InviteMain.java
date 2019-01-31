package usa.cactuspuppy.PVNBot.invitebot;

import lombok.Setter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandDelegator;
import usa.cactuspuppy.PVNBot.utils.FileIO;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class InviteMain extends ListenerAdapter {
    @Setter private static String channelID;
    @Setter private static String messageID;

    public static void main() {
        try {
            JDA jda = new JDABuilder(FileIO.readToken(InviteMain.class.getResourceAsStream("/invInfo/secret.txt"))).build();
            Main.setInvitationJDA(jda);
            File dataDir = new File(Main.getDataPath() + "/invBot").getAbsoluteFile();
            Main.getLogger().info(dataDir.getAbsolutePath());
            if (!dataDir.isDirectory() && !dataDir.mkdirs()) throw new RuntimeException("Could not make data directory.");
            File msgID = new File(Main.getDataPath() + "/invBot", "msgid.dat");
            if (!msgID.isFile() && !msgID.createNewFile()) throw new RuntimeException("Could not create msgID file, bot will forget which message to use on shutdown!");
            Map<String, String> results = getMsgID(new FileInputStream(msgID));

            if (results != null) {
                messageID = results.get("messageID");
                channelID = results.get("channelID");
            } else {
                messageID = "-1";
                channelID = "-1";
            }

            jda.addEventListener(new InviteMain());
            jda.awaitReady();
        } catch (Exception e) {
            Main.getLogger().warning("Could not start invitation bot");
            e.printStackTrace();
        }
    }

    public static Map<String, String> getMsgID(InputStream iS) {
        try {
            Map<String, String> rv = new HashMap<>();
            Scanner scan = new Scanner(iS);
            if (!scan.hasNext()) {
                storeMsgID(new ByteArrayInputStream("-1\n-1".getBytes()));
                return getMsgID(new FileInputStream(new File(Main.getDataPath() + "/invBot", "msgid.dat")));
            }
            rv.put("channelID", scan.nextLine());
            rv.put("messageID", scan.nextLine());
            scan.close();
            return rv;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void storeMsgID(InputStream iS) {
        try {
            Scanner scan = new Scanner(iS);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(Main.getDataPath() + "/invBot", "msgid.dat")));
            while (scan.hasNext()) {
                bufferedWriter.write(scan.nextLine());
                bufferedWriter.newLine();
            }
            scan.close();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        Message message = e.getMessage();
        String content = message.getContentDisplay();
        if (content.startsWith(";") && Character.isAlphabetic(content.charAt(1))) {
            content = content.substring(1);
            int index = content.indexOf(" ");
            if (index == -1) index = content.length();
            String command = content.substring(0, index);
            content = content.substring(index).trim();
            String[] args = content.split("\\s+");
            TextCommandDelegator.delegate(command, args, e);
        } else if (e.getMessage().getType().equals(MessageType.DEFAULT)) {

        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getMember().getUser().isBot()) return;
        if (messageID.equals("-1") || channelID.equals("-1")) return;
        String thisID = event.getMessageId();
        String thisChannelID = event.getChannel().getId();
        if (thisChannelID.equals(channelID) && thisID.equals(messageID)) {
            Main.getLogger().info(String.format("%s [ID: %s] requested invite", event.getMember().getUser().getName(), event.getMember().getUser().getId()));
            event.getReaction().removeReaction(event.getUser()).queueAfter(10, TimeUnit.MILLISECONDS);
            event.getMember().getUser().openPrivateChannel().queue(c -> c.sendMessage("Pirates vs. Ninjas Discord Server: https://discord.gg/nHWnT4Q").queue());
        }
    }
}
