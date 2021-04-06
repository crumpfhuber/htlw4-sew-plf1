package server;

public class Server {

    public static void main(String[] args) {
        new ServerHandler(3000).start();
    }
}
