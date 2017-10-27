import service.FileReadWrite;
import service.impl.FileReadWriteImpl;
import service.mainWork.ReadConfiguration;
import service.mainWork.ReadTxtAndWriteForIngest;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Start main");
//        ReadConfiguration readConfiguration = new ReadConfiguration();
//        readConfiguration.start();
        FileReadWrite fileReadWrite = new FileReadWriteImpl();
        ReadTxtAndWriteForIngest readTxtAndWriteForIngest = new ReadTxtAndWriteForIngest(fileReadWrite);
    }

}