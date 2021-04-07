package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(3001)) {
            while (true)
                service.execute(new ClientHandler(server.accept()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
