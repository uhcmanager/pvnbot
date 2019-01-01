package usa.cactuspuppy.PVNBot.utils.dice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class with ability to parse and perform rolls of format AdX with potential modifiers
 * @author CactusPuppy
 */
public final class DiceRoller {
    /**
     * Class to represent a set of rolls of identical dice
     */
    @AllArgsConstructor
    @Getter
    public class RollResult {
        long result;
        List<Integer> rolls;

        @Override
        public String toString() {
            String rep = Long.toString(result);
            StringJoiner rolls = new StringJoiner(",");
            for (int r : this.rolls) {
                rolls.add(Integer.toString(r));
            }
            return rep + ";" + rolls.toString();
        }
    }

    /**
     * Performs a AdB format roll with potential modifiers<br>
     * k - keep highest N rolls
     * D - drop N lowest rolls
     * r - reroll every die which yields N
     * @param command Roll formula to parse
     * @return Map containing:<br>
     *     "success" - "true" if calculation was successful, "false" otherwise<br>
     *     "reason" - If success if false, reason why parsing unsuccessful.<br>
     *     "sides" - Number of sides on dice rolled<br>
     *     "rolls" - Number of dice rolled<br>
     *     "result" - The final total of the formula<br>
     *     "formula" - The mathematical formulaic expression, with rolled numbers<br>
     */
    public static Map<String, String> parseSingleRoll(String command) {
        Map<String, String> results = new HashMap<>();
        int rolls = 1;
        int sides = 6;
        int keep = 0;
        List<Integer> rerolls = new ArrayList<>();


        //Begin parsing
        Pattern p = Pattern.compile("(\\d*)d(\\d+)(\\w+)");
        Matcher m = p.matcher(command);
        //Check format
        if (!m.matches()) {
            results.put("success", "false");
            results.put("reason", "");
            return results;
        }
        //Rolls
        if (!m.group(1).equals("")) {
            try {
                rolls = Integer.parseInt(m.group(1));
            } catch (NumberFormatException e) {
                try {
                    new BigInteger(m.group(1));
                } catch (NumberFormatException e1) {
                    results.put("success", "false");
                    results.put("reason", "Could not parse number of rolls");
                    return results;
                }
            }
        }
    }

    /**
     * Generates rolls of dice, with option to keep a certain number and rerolling a subset of possible values
     * @param rolls number of rolls
     * @param sides number of sides on each die
     * @param keep Specifies number of dice to keep (highest rolls), 0 to disable
     * @param rerolls If a roll generates any of these values, reroll. (Null or Empty List disables)
     * @return RollResult representing the rolls
     * @throws IllegalAccessException Thrown if any argument is non-sensical (i.e. reroll every possible value)
     */
    public static RollResult roll(int rolls, int sides, int keep, List<Integer> rerolls) throws IllegalAccessException {
        if (rerolls == null) rerolls = new ArrayList<>();
        //Sanity checks
        if (rolls <= 0) throw new IllegalArgumentException("Number of rolls must be positive");
        if (sides <= 0) throw new IllegalArgumentException("Number of sides must be positive");
        if (rolls < keep) throw new IllegalArgumentException("Cannot keep more rolls than were rolled");
        //Filter passed rerolls, sorting in the process
        rerolls = rerolls.stream().sorted().distinct().filter(i -> isPossible(i, sides)).collect(Collectors.toList());
        if (rerolls.size() == sides) throw new IllegalArgumentException("Cannot reroll on every possible roll");
        //TODO: Roll with given parameters
    }

    /**
     * Checks if the given value is possible to roll on a die with the given # of sides
     * @param value value to check
     * @param sides number of sides on the die
     * @return whether value can be rolled on this die
     */
    private static boolean isPossible(int value, int sides) {
        return value >= 1 && value <= sides;
    }


}
