import service.mainWork.ReadConfiguration;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {



        ReadConfiguration readConfiguration = new ReadConfiguration();
        readConfiguration.start();
    }

}