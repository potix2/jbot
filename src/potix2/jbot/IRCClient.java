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
//        writer.write("PRIVMSG " + this.channel + " :String concat(String str) 指定された文字列をこの文字列の最後に連結します。\n");
//        writer.write("PRIVMSG " + this.channel + " :http://java.sun.com/j2se/1.5.0/ja/docs/ja/api/java/lang/String.html\n");
        this.onBye();
        writer.flush();
    }

    protected void onBye() {
        writer.write("PART " + this.channel + "\n");
        writer.write("QUIT\n");
    }

    public static String makeCommand(String commandName, String params) {
        return commandName + " " + params + "\n";
    }

    public static IRCServerMessage parseMessage(String receivedText) {
        return new IRCServerMessage("bye");
    }
}
