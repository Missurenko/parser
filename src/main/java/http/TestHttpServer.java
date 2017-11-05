package http;

import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Data
public class TestHttpServer {
    public int inputPort = 5555;
    public TestHttpClient httpClient;

    public TestHttpServer(int inputPort, TestHttpClient httpClient) {
        this.inputPort = inputPort;
        this.httpClient = httpClient;
    }

    public static void main(String[] args) throws Throwable {
        ServerSocket ss = new ServerSocket(5555);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            SocketProcessor socketProcessor = new SocketProcessor(s);
            new Thread(socketProcessor).start();
        }
    }

    public void start() throws Throwable {
        ServerSocket ss = new ServerSocket(this.inputPort);
        System.err.println("Start server");
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            SocketProcessor socketProcessor = new SocketProcessor(s);
            socketProcessor.setHttpClient(this.httpClient);
            new Thread(socketProcessor).start();
        }
    }

}