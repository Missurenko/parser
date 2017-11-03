package example;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Listening {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(); // Unbound socket
        ss.bind(new InetSocketAddress("10.24.1.72", 7025)); // Bind the socket to a specific interface

        Socket client = ss.accept();
        OutputStream os = client.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        InputStreamReader isr = new InputStreamReader(client.getInputStream());
        pw.println("Connection confirmed ");
        BufferedReader br = new BufferedReader(isr);
        String str = br.readLine();

        pw.println("your ip address is " + str);

        pw.close();
    }
}
