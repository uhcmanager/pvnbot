package usa.cactuspuppy.PVNBot.utils.discord;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.concurrent.TimeUnit;

/**
 * Utility class to aid with Discord messaging
 */
public final class Messaging {
    public static void sendSnapMsg(String msg, MessageChannel channel) {
        sendSnapMsg(msg, channel, 7, TimeUnit.SECONDS);
    }

    public static void sendSnapMsg(String msg, MessageChannel channel, long time, TimeUnit unit) {
        new Thread(() -> {
            Message msg1 = channel.sendMessage(msg).complete();
            msg1.delete().queueAfter(time, unit);
        }).start();
    }
}
