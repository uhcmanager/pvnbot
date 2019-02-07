package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandHandler;
import usa.cactuspuppy.PVNBot.utils.minesweeper.Generator;

public class Minesweeper extends TextCommandHandler {
    /**
     * Called to handle this command
     *
     * @param args arguments passed into command
     * @param e    event in question
     */
    @Override
    public void onCommand(String[] args, MessageReceivedEvent e) {
        Generator.fullHandler(args, e);
    }
}
