package client.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Worker extends Thread {

    private boolean running;
    private PrintWriter writer;
    private StringProperty property;

    public Worker() {
        this.running = true;
        this.property = new SimpleStringProperty("Send 'QUIT' to close connection and program!");
    }

    @Override
    public void run() {
        System.out.println("Thread successfully started!");
        try (Socket socket = new Socket("localhost", 3000);
             Scanner scanner = new Scanner(new BufferedInputStream(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()))) {
            this.writer = writer;
            String temp;

            while (running) {
                temp = scanner.nextLine();
                property.setValue(property.getValue() + "\n" + temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thread finished.");
            Platform.exit();
        }
    }

    public void sendMessage(String name, String msg) {
        if (msg.equals("QUIT")) { // check if quit command is sent from client
            running = false;

            writer.println(msg); // send 'QUIT' to server --> closing connection on server side
        } else // "normal" msg
            writer.println(name + ": " + msg); // send name + msg to server
        writer.flush();
    }

    public StringProperty getMessageProperty() {
        return property;
    }
}
