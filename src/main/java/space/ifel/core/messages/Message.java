package space.ifel.core.messages;

public class Message {
    String state;
    String payload;

    public Message(String state, String payload)
    {
        this.state = state;
        this.payload = payload;
    }

    public String state()
    {
        return this.state;
    }

    public String message()
    {
        return this.payload;
    }

}
