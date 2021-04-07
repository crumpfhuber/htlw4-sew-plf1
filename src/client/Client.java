package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static final String path = "./client-files";

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 3001);
             DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
             DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            String input, answer;
            String[] cmd;

            System.out.println("Communication to Server established!");

            while (running) {
                // read console input
                input = scanner.nextLine();
                cmd = input.split(" ");


                // receive file from server
                if (cmd[0].equals("GET")) {
                    // send command to server
                    dos.writeUTF(input);
                    dos.flush();

                    // wait for server's answer and print it to command line
                    answer = dis.readUTF();
                    System.out.println("Answer: " + answer);

                    if (!answer.equals("ERROR")) {
                        File file = new File(path + cmd[1]);
                        file.createNewFile();

                        DataOutputStream fileStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

                        int count;
                        byte[] buffer = new byte[1024];
                        while ((count = dis.read(buffer)) > 0) {
                            fileStream.write(buffer, 0, count);
                            fileStream.flush();
                        }

                        fileStream.close();
                    }

                // send file to server
                } else if (cmd[0].equals("PUT")) {
                    File file = new File(path + cmd[1]);

                    if (file.exists()) { // send file to client
                        // send command to server
                        dos.writeUTF(input);
                        dos.flush();

                        // wait for server's answer and print it to command line
                        answer = dis.readUTF();
                        System.out.println("Answer: " + answer);

                        if (!answer.startsWith("ERROR")) {
                            DataInputStream fileStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

                            int count;
                            byte[] buffer = new byte[4048];
                            while ((count = fileStream.read(buffer)) > 0) {
                                dos.write(buffer, 0, count);
                                dos.flush();
                            }

                            fileStream.close();
                        }
                    }

                // quit application
                } else if (cmd[0].equals("QUIT")) {
                    running = false;
                    // send command to server
                    dos.writeUTF(input);
                    dos.flush();

                    // wait for server's answer and print it to command line
                    answer = dis.readUTF();
                    System.out.println("Answer: " + answer);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
