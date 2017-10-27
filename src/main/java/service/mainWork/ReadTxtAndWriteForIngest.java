package service.mainWork;

import dto.AllInformationAboutTaskDto;
import dto.ElementFilteredDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.FileReadWrite;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReadTxtAndWriteForIngest {
    private FileReadWrite fileReadWrite;

    private CrownUrl crownUrl = new CrownUrl();
    private String fromReadTxt = "url.txt";
    private String folder = "transfer";
    private String whereWrite = "file.html";
    private List<String> keyWord = Arrays.asList("Украин");
    private final List<String> TAG_FILTER = Arrays.asList("script", "noscript", "style");


    public ReadTxtAndWriteForIngest(FileReadWrite fileReadWrite) {
        this.fileReadWrite = fileReadWrite;
        this.start();
    }

    // add wait if null and check null here
    private void start() {
        do {
            System.out.println("Start work ReadTxtAndWriteForIngest doWhile");
            String dir = "";

            String url = fileReadWrite.readConfigurationTxt(folder,fromReadTxt);

            Document document = crownUrl.crown(url);
            if(document == null){
                fileReadWrite.writeToDir(whereWrite);
            }
            Element allElement = document.getAllElements().first();
            Parser parser = new Parser(allElement, keyWord, TAG_FILTER);
            System.out.println("Parsed element");
            Element parseredOrigin = parser.start();

            if (parseredOrigin == null) {
                fileReadWrite.writeToDir(whereWrite);
                System.out.println("Element == null");
            } else {
                String path = folder.concat("/"+whereWrite);
                fileReadWrite.writeToDir(parseredOrigin, path);
            }
        } while (true);

    }


}
