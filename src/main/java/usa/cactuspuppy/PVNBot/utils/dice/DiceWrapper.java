package usa.cactuspuppy.PVNBot.utils.dice;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Map;
import java.util.StringJoiner;

public class DiceWrapper {
    private static final int MAX_FORMULA_LENGTH = 500;
    public static void perform(String[] args, MessageReceivedEvent e) {
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
        String formula = results.get("formula");
        if (formula.length() >= MAX_FORMULA_LENGTH) {
            formula = formula.substring(0, MAX_FORMULA_LENGTH) + "...";
        }
        if (rolls > 1) {
            eb.addField("**Rolls**", formula, false);
        }
        eb.addField("**Result**", Long.toString(result), false);
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private static void parseProblem(MessageReceivedEvent e, String reason) {
        e.getChannel().sendMessage(String.format("Couldn't roll the dice for you %s. Reason: %s", e.getAuthor().getAsMention(), reason)).queue();
    }
}
