package server;

import com.oracle.tools.packager.IOUtils;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    public final String path = "./files";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
             DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));) {
            boolean running = true;
            String temp;
            String[] cmd;

            while (running) {
                temp = dis.readUTF(); // receive cmd from client
                cmd = temp.split(" ");

                System.out.println(temp);

                if (cmd.length == 1) {
                    if (cmd[0].equals("QUIT")) {
                        running = false;
                        dos.writeUTF(temp); // send back same cmd to accept request
                    } else {
                        dos.writeUTF("ERROR Command not found");
                    }
                    dos.flush();
                } else if (cmd.length == 2) {
                    if (cmd[0].equals("GET")) {
                        File file = new File(path + cmd[1]);

                        if (file.exists()) { // send file to client
                            dos.writeUTF(temp); // send back same cmd to accept request
                            dos.flush();

                            DataInputStream fileStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

                            int count;
                            byte[] buffer = new byte[1024];
                            while ((count = fileStream.read(buffer)) > 0) {
                                dos.write(buffer, 0, count);
                                dos.flush();
                            }

                            fileStream.close();

                        } else { // send error message
                            dos.writeUTF("ERROR File not found!");
                            dos.flush();
                        }
                    } else if (cmd[0].equals("PUT")) {
                        dos.writeUTF(temp); // send back same cmd to accept request
                        dos.flush();

                        File file = new File(path + cmd[1]);
                        file.createNewFile();

                        DataOutputStream fileStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

                        int count;
                        byte[] buffer = new byte[4048];
                        while ((count = dis.read(buffer)) > 0) {
                            fileStream.write(buffer, 0, count);
                            fileStream.flush();
                        }

                        fileStream.close();
                    } else {
                        dos.writeUTF("ERROR Command not found");
                        dos.flush();
                    }
                } else {
                    dos.writeUTF("ERROR Command not found");
                    dos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
