package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable, Chatable {

    private Socket socket;
    private ServerHandler server;
    private PrintWriter writer;

    public ClientHandler(Socket socket, ServerHandler server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(new BufferedInputStream(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));) {
            this.writer = writer;

            // add thread to chat pool
            server.chatThreads.add(this);

            String temp;
            boolean running = true;

            while (running) {
                temp = scanner.nextLine();

                if (temp.equals("QUIT")) {
                    running = false;
                    writer.println(temp);
                    writer.flush();
                } else {
                    server.sendMessageToClients(temp);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // remove current thread from chat pool
            server.chatThreads.remove(this);
            try {
                if (!socket.isClosed())
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String s) {
        writer.println(s);
        writer.flush();
    }
}
