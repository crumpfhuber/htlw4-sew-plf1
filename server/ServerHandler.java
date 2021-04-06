package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler {

    private ExecutorService executorService; // thread pool
    public List<Chatable> chatThreads; // list for all active client threads
    private int port; // server port

    public ServerHandler(int port) {
        executorService = Executors.newCachedThreadPool();
        this.chatThreads = Collections.synchronizedList(new ArrayList<>());
        this.port = port;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(3000)) {
            while (true) // run forever
                executorService.execute(new ClientHandler(server.accept(), this)); // create new instance for client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToClients(String msg) {
        for (Chatable c : chatThreads) // iter clients and send msg
            c.sendMessage(msg);
    }
}
