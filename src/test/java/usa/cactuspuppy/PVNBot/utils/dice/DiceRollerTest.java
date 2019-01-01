package usa.cactuspuppy.PVNBot.utils.dice;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DiceRollerTest {
    private TestRNG testRNG = new TestRNG();
    public class TestRNG extends Random {
        private int count = 0;
        @Override
        public int nextInt() {
            return count++;
        }

        @Override
        public int nextInt(int bound) {
            return count++ % bound;
        }

        public void resetCount() { count = 0; }
    }

    @Before
    public void setUp() throws Exception {
        DiceRoller.setRng(testRNG);
        testRNG.resetCount();
    }

    @Test
    public void testTestRNG() {
        System.out.println("Testing test RNG with 10 nextInt calls...");
        for (int i = 0; i < 10; i++) {
            System.out.print(testRNG.nextInt(6) + 1 + " ");
        }
        System.out.println();
    }

    @Test
    public void parseSingleRollSimple() {
        Map<String, String> results = DiceRoller.parseSingleRoll("d20", true);
        assertEquals("1", results.get("result"));
    }

    @Test
    public void parseSingleRollSingleMods() {
        Map<String, String> results = DiceRoller.parseSingleRoll("2d20D1", true);
        assertEquals("2", results.get("result"));
        testRNG.resetCount();
        results = DiceRoller.parseSingleRoll("3d20k2");
        assertEquals("5", results.get("result"));
        testRNG.resetCount();
        results = DiceRoller.parseSingleRoll("3d20r1");
        assertEquals("9", results.get("result"));
        testRNG.resetCount();
    }

    @Test
    public void parseSingleRollMultiMods() {
        Map<String, String> results = DiceRoller.parseSingleRoll("3d20k1D1r1");
        assertEquals("7", results.get("result"));
        testRNG.resetCount();
    }

    @Test
    public void roll() {
        System.out.println("Printing test drop/no drop results...");
        DiceRoller.RollResult result = DiceRoller.roll(2, 6, new ArrayList<>(0), 1, true);
        System.out.println(result.toString());
        result = DiceRoller.roll(1, 20, new ArrayList<>(0), 0, false);
        System.out.println(result.toString());
    }

    @Test
    public void stringToInt() {
        assertEquals(123, DiceRoller.stringToInt("123"));
        assertEquals(0, DiceRoller.stringToInt("0"));
        assertEquals(-45, DiceRoller.stringToInt("-45"));
    }
}