package space.ifel.core.servers;

import space.ifel.core.messages.Builder;
import space.ifel.core.messages.Message;

import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTask extends SwingWorker<Void, Void> {
    protected int serverPort = 8080;
    protected ArrayList<InetAddress> ipList;
    protected String serverAccess = "read";
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
    protected String oldMessage = "";
    protected PropertyChangeSupport messageHandler = new PropertyChangeSupport(this);
    protected Builder messageBuilder = new Builder();


    public ServerTask(Integer serverPort, ArrayList<InetAddress> ipList, String serverAccess) {
        this.serverPort = serverPort;
        this.ipList = ipList;
        this.serverAccess = serverAccess;

        messageHandler.addPropertyChangeListener(evt -> firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()));
    }

    @Override
    protected Void doInBackground() throws Exception {
        send(message().log().toMessage("Started"));
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
                if (!validateInetAddress(clientSocket.getInetAddress()) && !isLocalIpAddress(clientSocket.getInetAddress())) {
                    clientSocket.getOutputStream().write(("HTTP/1.1 401 Unauthorised\n").getBytes());
                    send(message().toMessage("Blocked IP - " + clientSocket.getInetAddress()));
                    clientSocket.close();
                    continue;
                }
            } catch (IOException e) {
                if (isStopped()) {
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute(new WorkerRunnable(clientSocket, messageHandler, serverAccess));
        }
        this.threadPool.shutdown();
        return null;
    }

    public void cancel() {
        this.stop();
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
        send(message().log().toMessage("Stopped"));
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    private boolean validateInetAddress(InetAddress inetAddress) {
        if (serverAccess.equals("read")) {
            // Blacklist
            return !ipList.contains(inetAddress);
        }
        // Whitelist
        return ipList.contains(inetAddress);
    }

    public static boolean isLocalIpAddress(InetAddress addr) {
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
            return true;
        try {
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }

    private void send(Message message) {
        firePropertyChange(message.state(), oldMessage, message.message());
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
