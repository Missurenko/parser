import org.jsoup.nodes.Element;
import service.FileReadWriteHtml;
import service.ParserHtml;
import service.impl.FileReadWriteHtmlImpl;
import service.impl.ParserHtmlImpl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String PATH_NAME = "C:\\Users\\User1\\IdeaProjects\\learnLua\\html_file";

    private static final List<String> KEY_WORDS = Arrays.asList("Агуша", "агуша");

    public static void main(String[] args) throws IOException {
        FileReadWriteHtml fileReadWriteHtml = new FileReadWriteHtmlImpl();
        ParserHtml parserHtml = new ParserHtmlImpl();
        List<File> fileList = fileReadWriteHtml.readDir(PATH_NAME);
        for (File file : fileList) {
            List<Element> elementList = parserHtml.getSortedHtml(file, KEY_WORDS);

        }
    }
}
