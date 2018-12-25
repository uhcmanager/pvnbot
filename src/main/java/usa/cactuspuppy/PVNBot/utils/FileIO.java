package usa.cactuspuppy.PVNBot.utils;

import java.io.InputStream;
import java.util.Scanner;

public final class FileIO {
    /**
     * Returns token from the first line
     * <p>
     *     Note: Assumes the token is on the first line, and that the line is formatted as:
     *     "{@literal <word>}: {@literal <token>}"
     * </p>
     * @param iS input stream to read from
     * @return token
     */
    public static String readToken(InputStream iS) {
        Scanner scan = new Scanner(iS);
        String line = scan.nextLine();
        scan.close();
        line = line.substring(line.indexOf(':') + 1);
        return line.trim();
    }
}
