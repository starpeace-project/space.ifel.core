package space.ifel.core.messages;

public class Builder {
    protected String state = "";

    public Builder dashboard()
    {
        state = "DASHBOARD";
        return this;
    }

    public Builder writeServer()
    {
        state = "WRITESERVER";
        return this;
    }

    public Builder readServer()
    {
        state = "READSERVER";
        return this;
    }

    public Builder mssql()
    {
        state = "MSSQL";
        return this;
    }

    public Builder mysql()
    {
        state = "MYSQL";
        return this;
    }

    public Builder login()
    {
        state = "LOGIN";
        return this;
    }

    public Builder active()
    {
        state += "-ACTIVE";
        return this;
    }

    public Builder log()
    {
        state += "-LOG";
        return this;
    }

    public Message toMessage(String message) {
        return new Message(state, message);
    }
}
