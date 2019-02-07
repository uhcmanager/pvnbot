package usa.cactuspuppy.PVNBot.utils.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratorTest {
    @Test
    public void testNoError() {
        String result = new Generator(3, 3, 2).generateBoard();
        assertFalse(result.contains("%1$s"));
        System.out.println(result);

        result = new Generator(1, 1, 1).generateBoard();
        assertFalse(result.contains("%1$s"));
        System.out.println(result);
    }

    @Test
    public void testInputErrors() {
        System.out.println("\nChecking width...");
        String result = new Generator(0, 10, 7).generateBoard();
        assertTrue(result.contains("%1$s"));
        System.out.println(result);

        System.out.println("\nChecking height...");
        result = new Generator(10, 0, 7).generateBoard();
        assertTrue(result.contains("%1$s"));
        System.out.println(result);

        System.out.println("\nChecking bombs...");
        result = new Generator(2, 2, 7).generateBoard();
        assertTrue(result.contains("%1$s"));
        System.out.println(result);
    }

    @Test
    public void testOverlap() {
        System.out.println("\nChecking overlapping bombs...");
        String result = new Generator(5, 5, 24).generateBoard();
        assertFalse(result.contains("%1$s"));
        System.out.println(result);
    }
}