package de.myfdweb.woc.mcplugins.mcauth;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class RESTServer extends Thread {

    private ServerSocket socket;

    public RESTServer(int port) {
        try {
            this.socket = new ServerSocket(port);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                new REST(socket.accept());
            } catch (IOException e) {
                if (!(e instanceof SocketException) || !e.getMessage().equals("Socket closed"))
                    e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
