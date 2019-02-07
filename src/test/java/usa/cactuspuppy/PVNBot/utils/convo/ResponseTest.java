package usa.cactuspuppy.PVNBot.utils.convo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void getType() {
        Response rep = new Response("PupBot, we have a problem");
        assertEquals(Trigger.Type.NEGATIVE, rep.getType());
    }

    @Test
    public void getPositiveType() {
        Response rep = new Response("Great job, PupBot!");
        assertEquals(Trigger.Type.POSITIVE, rep.getType());
    }
}