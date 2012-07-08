package potix2.jbot;

import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;


public class IRCClientTest {

    private IRCClient ircClient;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();

        ircClient = new IRCClient();
        ircClient.setOutputStream(outputStream);
        ircClient.setChannel("#potix2-test");
    }

    @After
    public void tearDown() {
        try {
            outputStream.close();
        }
        catch(IOException ioe) {
            fail("caught IOException");
        }
    }

    private String getResultString() {
        return new String(outputStream.toByteArray());
    }

    @Test
    public void loginTest() throws IOException {
        ircClient.login();

        String expected =  ""
                + "USER javadocbot 8 * :javadocbot\n"
                + "NICK javadocbot\n"
                + "JOIN #potix2-test\n";
        assertEquals(expected, getResultString());
    }

    @Test
    public void byeTest() throws IOException {
        ByteArrayInputStream iStream = new ByteArrayInputStream(":potix2!xxx.yyy.ne.jp PRIVMSG #potix2-test :@javadocbot bye".getBytes());
        ircClient.setInputStream(iStream);
        ircClient.run();
        iStream.close();

        String expected =  ""
                + "USER javadocbot 8 * :javadocbot\n"
                + "NICK javadocbot\n"
                + "JOIN #potix2-test\n"
//                + "PRIVMSG #potix2-test :String concat(String str) 指定された文字列をこの文字列の最後に連結します。\n"
//                + "PRIVMSG #potix2-test :http://java.sun.com/j2se/1.5.0/ja/docs/ja/api/java/lang/String.html\n"
                + "PART #potix2-test\n"
                + "QUIT\n";
        assertEquals(expected, getResultString());
    }

    @Test
    public void makeCommandTest() {
        String command = IRCClient.makeCommand("NICK", "javadocbot");
        assertEquals("NICK javadocbot\n", command);
    }

    @Test
    public void scanByeCommandTest() {
        String receivedText = ":potix2!xxx.yyy.ne.jp PRIVMSG #potix2-test :@javadocbot bye";
        IRCServerMessage message = IRCClient.parseMessage(receivedText);
        assertEquals("bye", message.getType());
    }
}