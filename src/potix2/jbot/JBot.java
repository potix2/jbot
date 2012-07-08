package potix2.jbot;

import java.io.IOException;

public class JBot {
    public static void main(String[] args) {
        IRCClient client = new IRCClient();
        client.setOutputStream(System.out);
        client.setInputStream(System.in);
        client.setChannel("#potix2-test");
        try {
            client.run();
        }
        catch (IOException ioe) {
            System.err.print(ioe.getMessage());
        }
    }
}
