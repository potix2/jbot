package potix2.jbot;

import java.io.IOException;
import java.net.Socket;

public class JBot {
    public static void main(String[] args) {
        IRCClient client = new IRCClient();
        String server = "irc.freenode.net";
        String channel = "#potix2-test";
        int port = 6667;
        try {
            System.out.print("connecting " + server + ":" + port);
            Socket socket = new Socket(server, port);

            client.setInputStream(socket.getInputStream());
            client.setOutputStream(socket.getOutputStream());

            client.setChannel(channel);
            client.run();
        }
        catch (IOException ioe) {
            System.err.print(ioe.getMessage());
        }

        System.out.print("finished.");
    }
}
