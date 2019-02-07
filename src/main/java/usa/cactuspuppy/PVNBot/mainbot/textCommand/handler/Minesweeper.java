package usa.cactuspuppy.PVNBot.mainbot.textCommand.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.utils.minesweeper.Generator;

public class Minesweeper extends CommandHandler {
    /**
     * Handles the specific command passed in
     *
     * @param e    event in question
     * @param args
     */
    @Override
    public void onCommand(MessageReceivedEvent e, String[] args) {
        Generator.fullHandler(args, e);
    }
}
