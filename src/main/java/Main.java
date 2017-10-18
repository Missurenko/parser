import service.mainWork.ReadConfiguration;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Start main");
        ReadConfiguration readConfiguration = new ReadConfiguration();
        readConfiguration.start();
    }

}