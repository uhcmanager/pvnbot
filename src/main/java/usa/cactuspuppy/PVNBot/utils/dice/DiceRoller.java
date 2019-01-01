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
    private static final int MAX_ROLLS = 100;
    private static Map<Character, RollModifier> rollModifiers = new HashMap<>();
    static {
        addModifier("k([0-9]+)", 'k');
        addModifier("r([0-9]+)", 'r');
        addModifier("D([0-9]+)", 'D');
    }

    @AllArgsConstructor
    @Getter
    public class RollResult {
        private long result;
        private List<Integer> rolls;

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

    @AllArgsConstructor
    @Getter
    private static class RollModifier {
        private Pattern pattern;
        private char mod;
    }

    private static void addModifier(String regex, char mod) {
        rollModifiers.put(mod, new RollModifier(Pattern.compile("^(" + regex + ")"), mod));
    }

    /**
     * Performs a AdB format roll with potential modifiers Xn<br>
     *     <p>
     *         Modifiers:<br>
     *         k - keep highest n rolls<br>
     *         D - drop n lowest rolls<br>
     *         r - reroll every die which yields n<br>
     *     </p>
     *
     * @param command Roll formula to parse
     * @param keepDrops Whether to include dropped rolls in the returned formula
     * @return Map containing:<br>
     *     "success" - "true" if calculation was successful, "false" otherwise<br>
     *     "reason" - If success if false, reason why parsing unsuccessful.<br>
     *     "sides" - Number of sides on dice rolled<br>
     *     "rolls" - Number of dice rolled<br>
     *     "result" - The final total of the formula<br>
     *     "formula" - The mathematical formulaic expression, with rolled numbers<br>
     */
    public static Map<String, String> parseSingleRoll(String command, boolean keepDrops) {
        Map<String, String> results = new HashMap<>();
        int rolls = 1;
        int sides;
        int keep = 0;
        List<Integer> rerolls = new ArrayList<>();

        //Begin parsing
        Pattern p = Pattern.compile("(\\d*)d(\\d+)(\\w*)");
        Matcher m = p.matcher(command);
        //Check format
        if (!m.matches()) {
            results.put("success", "false");
            results.put("reason", "Incorrect formatting");
            return results;
        }
        //Get rolls
        if (!m.group(1).equals("")) {
            try {
                rolls = stringToInt(m.group(1));
                if (rolls <= 0) {
                    results.put("success", "false");
                    results.put("reason", "Number of rolls must be positive");
                    return results;
                } else if (rolls > MAX_ROLLS) {
                    results.put("success", "false");
                    results.put("reason", "Number of rolls may not exceed " + MAX_ROLLS);
                    return results;
                }
            } catch (NumberFormatException e) {
                results.put("success", "false");
                results.put("reason", e.getMessage());
                return results;
            }
        }
        //Get sides
        try {
            sides = Integer.parseInt(m.group(2));
            if (sides <= 0) {
                results.put("success", "false");
                results.put("reason", "Number of sides must be positive");
                return results;
            }
        } catch (NumberFormatException e) {
            results.put("success", "false");
            results.put("reason", e.getMessage());
            return results;
        }
        //Parse modifiers
        if (!m.group(3).equals("")) {
            String modifiers = m.group(3);
            while (!modifiers.equals("")) {
                boolean match = false;
                //Attempt to match all modifiers with regex
                for (RollModifier r : rollModifiers.values()) {
                    Matcher matcher = r.getPattern().matcher(modifiers);
                    if (matcher.find()) { //found a match
                        try {
                            match = true;
                            switch (r.getMod()) {
                                case 'k': {
                                    keep = stringToInt(matcher.group().substring(1));
                                    break;
                                }
                                case 'D': {
                                    keep = rolls - stringToInt(matcher.group().substring(1));
                                    break;
                                }
                                case 'r': {
                                    rerolls.add(stringToInt(matcher.group().substring(1)));
                                    break;
                                }
                                default: throw new NumberFormatException("Unknown modifier " + r.getMod());
                            }
                            break;
                        } catch (NumberFormatException e) { //Couldn't extract an int from the trailing numbers
                            results.put("success", "false");
                            results.put("reason", e.getMessage());
                            return results;
                        }
                    }
                }
                if (!match) {
                    results.put("success", "false");
                    results.put("reason", "Issue parsing modifiers at: " + modifiers);
                    return results;
                }
            }
        }
        //ROLL FOR INITIATIVE
        try {
            RollResult rollResult = roll(rolls, sides, rerolls);
        } catch (Exception e) {
            results.put("success", "false");
            results.put("reason", e.getMessage());
            return results;
        }
        //TODO: Handle keeping
    }

    /**
     * Overloaded single roll command
     * @param command roll command
     * @return the resulting rolls without dropped rolls
     */
    public static Map<String, String> parseSingleRoll(String command) {
        return parseSingleRoll(command, false);
    }

    /**
     * Generates rolls of dice, with option to keep a certain number and rerolling a subset of possible values
     * @param rolls number of rolls
     * @param sides number of sides on each die
     * @param rerolls If a roll generates any of these values, reroll. (Null or Empty List disables)
     * @return RollResult representing the rolls
     * @throws IllegalArgumentException Thrown if any argument is non-sensical (i.e. reroll every possible value)
     * @throws RuntimeException If the roller is unable to get rolls in a reasonable number of iterations
     */
    public static RollResult roll(int rolls, int sides, List<Integer> rerolls) throws RuntimeException {
        if (rerolls == null) rerolls = new ArrayList<>();
        //Sanity checks
        if (rolls <= 0) throw new IllegalArgumentException("Number of rolls must be positive");
        if (sides <= 0) throw new IllegalArgumentException("Number of sides must be positive");
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

    public static int stringToInt(String s) throws NumberFormatException {
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (NumberFormatException e) {
            try {
                new BigInteger(s);
            } catch (Exception e1) {
                throw e;
            }
            throw new NumberFormatException("String exceeds integer bounds");
        }
    }
}
