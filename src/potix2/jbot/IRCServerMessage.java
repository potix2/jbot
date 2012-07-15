package potix2.jbot;

public class IRCServerMessage {
    private String type;
    private String params;

    public IRCServerMessage(String type, String params) {
        this.type = type;
        this.params = params;
    }

    public IRCServerMessage(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getParams() {
        return params;
    }
}
