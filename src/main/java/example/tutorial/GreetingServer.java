package example.tutorial;

import org.apache.commons.io.IOUtils;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class GreetingServer extends Thread {
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000000);
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                InputStream inSR = server.getInputStream();
                DataInputStream in = new DataInputStream(server.getInputStream());
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(server.getInputStream()));
                byte[] bytes = IOUtils.toByteArray(inSR);
                String s = new String(bytes, "US-ASCII");

                Socket client = new Socket("192.168.1.101", 7000);

                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                DataOutputStream dOut = new DataOutputStream(client.getOutputStream());

                dOut.write(bytes);
                dOut.flush(); // Send off the data
                server.close();
                client.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String [] args) {
        int port = 5555;
        try {
            Thread t = new GreetingServer(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}