package space.ifel.core.processors;

import java.net.Socket;

public class IncomingServerMessage {

    private String[] message;
    public IncomingServerMessage(Socket socket, String message) {
        this.message = message.split(" ");
    }

    public String[] getMessage() {
        return message;
    }
}
