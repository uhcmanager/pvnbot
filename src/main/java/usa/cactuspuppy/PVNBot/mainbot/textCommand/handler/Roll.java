package usa.cactuspuppy.PVNBot.mainbot.textCommand.handler;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.utils.dice.DiceWrapper;

import java.util.concurrent.TimeUnit;

public class Roll extends CommandHandler {
    /**
     * Called to handle rolling dice
     *
     * @param e    event in question
     */
    @Override
    public void onCommand(final MessageReceivedEvent e, String[] args) {
        if (!e.getChannel().getName().equals("dice-rolling")) {
            new Thread(() -> {
                Message msg = e.getChannel().sendMessage(String.format("%s PupBot has been instructed to ignore dice roll commands in this channel to avoid spam. Sorry :/ Try %s", e.getAuthor().getAsMention(), e.getGuild().getTextChannelsByName("dice-rolling", true).get(0).getAsMention())).complete();
                msg.delete().queueAfter(5L, TimeUnit.SECONDS);
            }).start();
        }
        DiceWrapper.perform(args, e);
    }
}
