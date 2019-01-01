package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandHandler;
import usa.cactuspuppy.PVNBot.utils.dice.DiceRoller;

import java.awt.*;
import java.util.*;

public class Roll extends TextCommandHandler {
    private static final int MAX_DISPLAY_ROLLS = 5;
    /**
     * Called to handle rolling dice
     *
     * @param args arguments passed into command
     * @param e    event in question
     */
    @Override
    public void onCommand(String[] args, MessageReceivedEvent e) {

        StringJoiner joiner = new StringJoiner("");
        for (String s : args) {
            joiner.add(s);
        }
        String noSpace = joiner.toString();

        Map<String, String> results = DiceRoller.parseSingleRoll(noSpace);
        if (results.get("success").equals("false")) {
            parseProblem(e, results.get("reason"));
            return;
        }
        int rolls = Integer.valueOf(results.get("rolls"));
        int sides = Integer.valueOf(results.get("sides"));
        long result = Long.valueOf(results.get("result"));

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0, 193, 255));
        eb.setTitle(String.format("%s's Roll", e.getMember().getEffectiveName()));
        eb.addField("**Dice**", String.format("%dd%d", rolls, sides), false);
        if (rolls > 1) {
            eb.addField("**Rolls**", results.get("formula"), false);
        }
        eb.addField("**Result**", Long.toString(result), false);
        if (sides == 1) {
            if (result == 1) eb.addField("", "***CRIT FAIL***", false);
            else if (result == 20) eb.addField("", "***NAT 20***", false);
        }
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private void parseProblem(MessageReceivedEvent e, String reason) {
        e.getChannel().sendMessage(String.format("Couldn't roll the dice for you %s. Reason: %s", e.getAuthor().getAsMention(), reason)).queue();
    }
}
