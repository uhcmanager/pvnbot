package usa.cactuspuppy.PVNBot.mainbot;

import lombok.Getter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.constants.main.MainData;
import usa.cactuspuppy.PVNBot.mainbot.textCommand.handler.*;
import usa.cactuspuppy.PVNBot.utils.FileIO;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static usa.cactuspuppy.PVNBot.utils.convo.Response.formResponse;

/**
 * Responsible for delegating tasks for main bot
 */
public class Delegator extends ListenerAdapter {
    @Getter private static String cmdPrefix = "!";
    private static Map<String, CommandHandler> handlerMap = new HashMap<>();
    static {
        addHandler("ping", new Ping());
        addHandler("roll", new Roll());
        addHandler("setprefix", new SetPrefix());
        addHandler("minesweep", new Minesweeper());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        Optional<String> cmdOptional = getCommand(e.getMessage().getContentDisplay());
        if (cmdOptional.isPresent()) {
            String command = cmdOptional.get();
            CommandHandler handler =  handlerMap.get(command);
            if (handler == null) {
                e.getChannel().sendMessage(String.format("%s Unknown command `%s`", e.getAuthor().getAsMention(), command)).queue();
                return;
            }
            if (handler.hasPermission(e)) {
                String[] temp = e.getMessage().getContentDisplay().split(" +");
                String[] args = new String[temp.length - 1];
                System.arraycopy(temp, 1, args, 0, temp.length - 1);
                handler.onCommand(e, args);
            } else {
                denyPermission(e);
            }
        } else {
            formResponse(e);
        }
    }

    private static void denyPermission(MessageReceivedEvent e) {
        e.getChannel().sendMessage(
                String.format("%1$s You do not have permission to access that command!", e.getAuthor().getAsMention())
        ).queue();
    }

    private static void addHandler(String name, CommandHandler handler) {
        handlerMap.put(name, handler);
    }

    /**
     * Checks if messages tarts with cmdPrefix and alphabetic command word
     * @param s message to parse
     * @return command that was called
     */
    public Optional<String> getCommand(String s) {
        Pattern p = Pattern.compile("^" + cmdPrefix + "(\\w+).*");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            return Optional.ofNullable(m.group(1));
        } else {
            return Optional.empty();
        }
    }

    public static void setCmdPrefix(String s) {
        if (cmdPrefix.equals(s)) return;
        cmdPrefix = s;
        FileIO.saveToFile(Main.getDataPath() + MainData.DIR.toString(), MainData.CMD_PREFIX.toString(), new ByteArrayInputStream(s.getBytes()), false);
    }
}
