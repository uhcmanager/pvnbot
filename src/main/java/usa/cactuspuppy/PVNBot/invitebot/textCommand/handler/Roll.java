package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.utils.dice.DiceWrapper;

public class Roll extends TextCommandHandler {
    /**
     * Called to handle rolling dice
     *
     * @param args arguments passed into command
     * @param e    event in question
     */
    @Override
    public void onCommand(String[] args, MessageReceivedEvent e) {
        DiceWrapper.command(e, args);
    }
}
