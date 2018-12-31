package usa.cactuspuppy.PVNBot.invitebot.textCommand.handler;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import usa.cactuspuppy.PVNBot.invitebot.textCommand.TextCommandHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int rolls = 1;
        int sides = 6;
        Pattern sidesMatcher = Pattern.compile("d([0-9]+)");
        Matcher m;
        for (String s : args) {
            m = sidesMatcher.matcher(s);
            if (m.matches()) {
                String value = m.group(1);
                try {
                    sides = Integer.parseInt(value);
                    if (sides <= 0) {
                        parseProblem(e, "Number of sides must be positive");
                        return;
                    }
                } catch (NumberFormatException e1) {
                    parseProblem(e, s + " could not be parsed");
                    return;
                }
                continue;
            }
            if (s.matches("[0-9]{1,10}")) {
                try {
                    rolls = Integer.parseInt(s);
                    if (rolls <= 0) {
                        parseProblem(e, "Number of rolls must be positive");
                    }
                } catch (NumberFormatException e1) {
                    parseProblem(e, s + " could not be parsed");
                    return;
                }
            }
        }
        Random rng = new Random();
        List<Integer> terms = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < rolls; i++) {
            int x = rng.nextInt(sides) + 1;
            terms.add(x);
            sum += x;
        }
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(94, 78, 255));
        eb.setTitle(String.format("%s's Roll", e.getMember().getEffectiveName()));
        eb.addField("**Dice**", String.format("%d d%d", rolls, sides), false);
        StringJoiner joiner = new StringJoiner(" + ");
        int counter = 0;
        for (int i : terms) {
            joiner.add(Integer.toString(i));
            counter++;
            if (counter >= MAX_DISPLAY_ROLLS + 2) {
                joiner.add(String.format("*%d more rolls...*", rolls - MAX_DISPLAY_ROLLS));
                break;
            }
        }
        eb.addField("**Rolls**", joiner.toString(), false);
        eb.addField("**Total**", Integer.toString(sum), false);
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private void parseProblem(MessageReceivedEvent e, String info) {
        e.getChannel().sendMessage(String.format("Couldn't roll the dice for you %s. Reason: %s", e.getAuthor().getAsMention(), info)).queue();
    }
}
