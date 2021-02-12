package server;

import java.io.IOException;
import java.net.*;

public class Server implements Runnable{
    private ServerSocket serverSocket = null;
    private Thread thread = null;

    public Server(int port) {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);
            start();
        } catch (IOException ioe) {
            System.out.println("IO Error: " + ioe);
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                System.out.println("Waiting for a user ...");
                addThread(serverSocket.accept());
            }
            catch(IOException e) {
                System.out.println("Acceptance Error: " + e);
            }
        }
    }

    public void addThread(Socket socket) throws IOException {
        System.out.println("Client accepted: " + socket);
        ServerThread client = new ServerThread(socket);
        new Thread(client).start();

    }
}
