package space.ifel.core.servers;

import space.ifel.core.messages.Builder;
import space.ifel.core.messages.Message;
import space.ifel.core.processors.IncomingServerMessage;

import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;

public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected PropertyChangeSupport messageHandler;
    protected String oldMessage;
    protected Builder messageBuilder = new Builder();
    protected String serverAccess;

    public WorkerRunnable(Socket clientSocket, PropertyChangeSupport messageHandler, String serverAccess) {
        this.clientSocket = clientSocket;
        this.messageHandler = messageHandler;
        this.serverAccess = serverAccess;
    }

    public void run() {
        send(message().active().toMessage("ADD"));
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            String[] message = new IncomingServerMessage(clientSocket, (new BufferedReader(new InputStreamReader(input))).readLine()).getMessage();
            output.write("HI".getBytes());
            output.close();
            input.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
        send(message().active().toMessage("MINUS"));
    }

    private void send(Message message) {
        messageHandler.firePropertyChange(message.state(), oldMessage, message.message());
        oldMessage = message.message();
    }

    private Builder message()
    {
        if (serverAccess.equals("read")) {
            return messageBuilder.readServer();
        }
        return messageBuilder.writeServer();
    }
}
