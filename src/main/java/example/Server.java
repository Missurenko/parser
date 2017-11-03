package example;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    protected static String server_IP;
    protected static String serverPort;
    public static void main(String[] args) throws IOException {


        int server_Port = 5555;


        try {
            InetAddress iAddress = InetAddress.getLocalHost();
            server_IP = iAddress.getHostAddress();

            System.out.println("Server IP address : " + server_IP);
        } catch (UnknownHostException e) {
        }

        ServerSocket serverSocket = new ServerSocket(server_Port);


        while (true) {
            Socket socket = serverSocket.accept();

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            pw.println("Connection confirmed ");
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();

            pw.println("your ip address is " + str);

            pw.close();
            //socket.close();

            //System.out.println("Just said hello to:" + str);
        }
    }
}