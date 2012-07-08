package potix2.jbot;

public class IRCServerMessage {
    private String type;

    public IRCServerMessage(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
