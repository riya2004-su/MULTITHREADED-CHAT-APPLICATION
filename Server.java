// Server.java

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    // List to keep track of all connected clients
    static Vector<ClientHandler> clientList = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234); // Server on port 1234
        System.out.println("Server started... Waiting for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Accept a new client
            System.out.println("New client connected: " + clientSocket);

            // Create input and output streams for this client
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            // Ask client for their name
            dos.writeUTF("Enter your name: ");
            String name = dis.readUTF();

            // Create new client handler thread and add to list
            ClientHandler newClient = new ClientHandler(clientSocket, name, dis, dos);
            Thread t = new Thread(newClient);
            clientList.add(newClient);
            t.start(); // Start thread
        }
    }
}
