package usa.cactuspuppy.PVNBot.mainbot;

import org.junit.Before;
import org.junit.Test;
import usa.cactuspuppy.PVNBot.Main;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class DelegatorTest {

    @Before
    public void prepForTest() {
        Main.setLogger(Logger.getLogger("Main"));
        Init.createDataFolder();
        Delegator.setCmdPrefix("!");
    }

    @Test
    public void getCommand() {
        Delegator delegator = new Delegator();
        assertEquals("ping", delegator.getCommand("!ping 123").orElse(null));
    }

    @Test
    public void setCmdPrefix() {
        Delegator.setCmdPrefix("?");
        assertEquals("?", Delegator.getCmdPrefix());
    }
}