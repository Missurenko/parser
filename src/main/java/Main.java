import dto.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.FileReadWriteHtml;
import service.ParserHtml;
import service.impl.FileReadWriteHtmlImpl;
import service.impl.ParserHtmlImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String PATH_NAME = "/home/bm/Downloads/parser-master/html_file";

    private static final String PATH_WRITE = "/home/bm/Downloads/parser-master/result";

    private static final List<String> KEY_WORDS = Arrays.asList("Агуша", "агуша");

    private static final List<String> TAG_FILTER = Arrays.asList("script", "noscript","style");

    public static void main(String[] args) throws IOException {
        FileReadWriteHtml fileReadWriteHtml = new FileReadWriteHtmlImpl();
        ParserHtml parserHtml = new ParserHtmlImpl();
        List<File> fileList = fileReadWriteHtml.readDir(PATH_NAME);
        for (File file : fileList) {
//            List<Element> elementList = parserHtml.getSortedHtml(file, KEY_WORDS);

            Document doc = Jsoup.parse(file, "UTF-8");
                Element allElement = doc.getAllElements().first();
            dto.Parser parser = new dto.Parser(allElement,KEY_WORDS,TAG_FILTER);
            Element parseredOrigin = parser.start();
            fileReadWriteHtml.writeToDir(parseredOrigin,PATH_WRITE,"first");
            System.out.printf("END");
        }
    }
}
