package http;


import lombok.Data;

import java.io.*;
import java.net.Socket;

@Data
public class SocketProcessor implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;
    private TestHttpClient httpClient;
    private boolean testResponce = true;

    public SocketProcessor(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }

    public void run() {
        try {
            String inputHeaders = readInputHeaders();
            if (this.testResponce) {
                writeResponse("<html><body><h1>Hello from Habrahabr</h1></body></html>");
            } else {
                String CFSresponce = this.httpClient.sendData(inputHeaders);
                this.writeRawResponse(CFSresponce);
            }
        } catch (Throwable t) {
                /*do nothing*/
            t.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
                    /*do nothing*/
                t.printStackTrace();
            }
        }
        System.err.println("Client processing finished");
    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        writeRawResponse(result);
    }

    private void writeRawResponse(String rawData) throws IOException {
        os.write(rawData.getBytes());
        os.flush();
        System.out.println("Raw response send");
    }

    private String readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String readLine = br.readLine();
            System.out.println(readLine);
            if (readLine == null || readLine.trim().length() == 0) {
                break;
            } else {
                stringBuilder.append(readLine);
            }
        }
        return stringBuilder.toString();
    }
}