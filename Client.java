// Client.java

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to server...");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Thread to read messages from server
        Thread readThread = new Thread(() -> {
            String msg;
            try {
                while (true) {
                    msg = dis.readUTF();
                    System.out.println(msg);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        });

        readThread.start();

        // Send messages to server
        String userInput;
        while (true) {
            userInput = br.readLine();
            dos.writeUTF(userInput);

            if (userInput.equalsIgnoreCase("exit")) {
                socket.close();
                break;
            }
        }
    }
}
