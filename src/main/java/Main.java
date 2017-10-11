import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.DoPreWork;
import service.FileReadWriteHtml;
import service.Parser;
import service.ParserHtml;
import service.impl.FileReadWriteHtmlImpl;
import service.impl.ParserHtmlImpl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        DoPreWork doPreWork = new DoPreWork();
        doPreWork.start();
    }

}