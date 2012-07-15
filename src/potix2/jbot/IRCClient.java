package potix2.jbot;

import java.io.*;

public class IRCClient {
    private String channel;
    private PrintWriter writer;
    private BufferedReader reader;
    public IRCClient() {
    }

    public void setOutputStream(OutputStream os) {
        this.writer = new PrintWriter(os);
    }

    public void setInputStream(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    public void setChannel(String channel) {
       this.channel = channel;
    }

    public void login() throws IOException {
        writer.write("USER javadocbot 8 * :javadocbot\n");
        writer.write("NICK javadocbot\n");
        writer.write("JOIN " + this.channel + "\n");
        writer.flush();
    }

    public void run() throws IOException {
        this.login();

        String line;
        while((line = this.reader.readLine()) != null ) {
            IRCServerMessage message = parseMessage(line);
            if ( message == null ) {
                continue;
            }

            if ( message.getType().equals("bye") ) {
                this.onBye();
                break;
            }
            else if ( message.getType().equals("ping") ) {
                this.onPing(message.getParams());
            }
        }
    }

    private void onPing(String server) {
        writer.write("PONG " + server + "\n");
        writer.flush();
    }

    protected void onBye() {
        writer.write("PART " + this.channel + "\n");
        writer.write("QUIT\n");
        writer.flush();
    }

    public static String makeCommand(String commandName, String params) {
        return commandName + " " + params + "\n";
    }

    public static IRCServerMessage parseMessage(String receivedText) {
        //:potix2!xxx.yyy.ne.jp PRIVMSG #potix2-test :@javadocbot bye
        String[] tokens = receivedText.split(" ");
        if ( tokens == null || tokens.length == 0 ) {
            return null;
        }

        int currentIndex = 0;
        if ( tokens[0].startsWith(":") ) {
            currentIndex = 1;
        }

        String command = tokens[currentIndex++].toUpperCase();
        if ( command.equals("PRIVMSG") ) {
            return new IRCServerMessage("bye");
        }
        else if ( command.equals("PING") ) {
            if ( tokens.length > currentIndex ) {
                return new IRCServerMessage("ping", tokens[currentIndex]);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
}
