package usa.cactuspuppy.PVNBot.mainbot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.mainbot.textCommand.CommandHandler;
import usa.cactuspuppy.PVNBot.utils.dice.DiceWrapper;

public class Roll extends CommandHandler {
    /**
     * Called to handle rolling dice
     *
     * @param e    event in question
     */
    @Override
    public void onCommand(MessageReceivedEvent e) {
        String content = e.getMessage().getContentRaw();
        int index = content.indexOf(" ");
        if (index == -1) index = content.length();
        content = content.substring(index).trim();
        String[] args = content.split("\\s+");

        DiceWrapper.perform(args, e);
    }
}
