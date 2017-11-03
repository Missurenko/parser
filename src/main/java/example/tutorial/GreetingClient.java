package example.tutorial;

import java.net.*;
import java.io.*;

public class GreetingClient {

    public static void main(String[] args) {
        String serverName = "192.168.1.101";
        int port = 7000;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            DataOutputStream dOut = new DataOutputStream(client.getOutputStream());

// Send first message
            dOut.writeByte(1);
            dOut.writeUTF("This is the first type of message.");
            dOut.flush(); // Send off the data

// Send the second message
            dOut.writeByte(2);
            dOut.writeUTF("This is the second type of message.");
            dOut.flush(); // Send off the data

// Send the third message
            dOut.writeByte(3);
            dOut.writeUTF("This is the third type of message (Part 1).");
            dOut.writeUTF("This is the third type of message (Part 2).");
            dOut.flush(); // Send off the data

// Send the exit message
            dOut.writeByte(-1);
            dOut.flush();

            dOut.close();


            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}