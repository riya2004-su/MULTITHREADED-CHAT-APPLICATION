// ClientHandler.java

import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler implements Runnable {
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket socket;
    boolean isLoggedIn;

    public ClientHandler(Socket socket, String name, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = dis.readUTF();
                if (message.equalsIgnoreCase("exit")) {
                    this.isLoggedIn = false;
                    this.socket.close();
                    break;
                }

                // Broadcast message to all clients
                for (ClientHandler client : Server.clientList) {
                    if (client.isLoggedIn && client != this) {
                        client.dos.writeUTF(this.name + ": " + message);
                    }
                }

            } catch (IOException e) {
                break;
            }
        }

        try {
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
