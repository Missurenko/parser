import org.jsoup.nodes.Element;
import service.FileReadWriteHtml;
import service.ParserHtml;
import service.impl.FileReadWriteHtmlImpl;
import service.impl.ParserHtmlImpl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String PATH_NAME = "C:\\Users\\User1\\IdeaProjects\\learnLua\\html_file";

    private static final List<String> KEY_WORDS = Arrays.asList("Агуша", "агуша");

    public static void main(String[] args) throws IOException {
        FileReadWriteHtml fileReadWriteHtml = new FileReadWriteHtmlImpl();
        ParserHtml parserHtml = new ParserHtmlImpl();
        List<File> fileList = fileReadWriteHtml.readDir(PATH_NAME);
        String[] patt={"User1.*Lua"};
        String res = ParserHtmlImpl.delPatterns(PATH_NAME,patt);
        System.out.printf(res);
        for (File file : fileList) {

//            String html = parserHtml.getSortedHtml(file, KEY_WORDS);

//            fileReadWriteHtml.writeToDir("C:\\Users\\User1\\IdeaProjects\\learnLua\\result", html, "Агуша");
        }
    }
}
