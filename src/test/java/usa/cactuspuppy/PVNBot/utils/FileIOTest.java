package usa.cactuspuppy.PVNBot.utils;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class FileIOTest {

    @Test
    public void readToken() {
        InputStream inputStream = new ByteArrayInputStream("  lookieLookie1NotACreep23Justa_cookie.1992 ".getBytes());
        assertEquals("lookieLookie1NotACreep23Justa_cookie.1992", FileIO.readToken(inputStream));
    }
}