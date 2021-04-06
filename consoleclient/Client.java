package consoleclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 3000);
             Scanner scanner = new Scanner(new BufferedInputStream(socket.getInputStream()));
             Scanner consoleScanner = new Scanner(System.in);
             PrintWriter writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()))) {

            boolean running = true;

            while (running) {
                writer.println("Clemens joint the chat.");
                writer.flush();

                String temp = scanner.nextLine();

                System.out.println(temp);

                writer.println("QUIT");
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
