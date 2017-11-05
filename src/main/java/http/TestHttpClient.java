package http;

import lombok.Data;

import java.io.*;
import java.net.Socket;

@Data
public class TestHttpClient {
    public int port = 80;
    public String hostIpName = "localhost";


    public TestHttpClient(int port, String hostIpName) {
        this.port = port;
        this.hostIpName = hostIpName;
    }

    public TestHttpClient() {
    }

    /**
     * @param rawData Данные что отправить
     * @return данные что ответить
     */
    public String sendData(String rawData) throws IOException {
        // send to cfs
        // request from cfs

        Socket client = new Socket(hostIpName, port);

        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();


        DataOutputStream dOut = new DataOutputStream(client.getOutputStream());


        dOut.writeUTF(rawData);
        dOut.flush(); // Send off the data

        dOut.close();
         client.getInputStream();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String readLine = in.readLine();
            System.out.println(readLine);
            if (readLine == null || readLine.trim().length() == 0) {
                break;
            } else {
                stringBuilder.append(readLine);
            }
        }


        return stringBuilder.toString();

    }    //todo send end get

}
