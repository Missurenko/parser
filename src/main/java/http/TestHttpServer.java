package http;

import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Data
public class TestHttpServer {
    public int inputPort = 5555;
    public TestHttpClient httpClient;
    public boolean isTestServer = true;

    public TestHttpServer(int inputPort, TestHttpClient httpClient) {
        this.inputPort = inputPort;
        this.httpClient = httpClient;
    }

    public void start() throws Throwable {
        ServerSocket ss = new ServerSocket(this.inputPort);
        System.err.println("Start server");
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            SocketProcessor socketProcessor = new SocketProcessor(s);
            socketProcessor.setHttpClient(this.httpClient);
            socketProcessor.setTestResponce(isTestServer);
            new Thread(socketProcessor).start();
        }
    }

}