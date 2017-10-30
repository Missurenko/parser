package service.mainWork;

import com.sun.javaws.Main;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import service.FileReadWrite;
import service.impl.FileReadWriteImpl;

import java.io.IOException;

public class CrownUrl {

    Document crown(String urlRead) {

        Document result = null;
        try {
           Connection connection = Jsoup.connect(urlRead)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .timeout(12000)
                    .header("Accept-Language", "pt-BR,pt;q=0.8")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36")
                    .referrer("http://www.google.com"); // optional

            Document doc   =   connection.execute()
                    .parse();
//            String ss = "ss";
//            FileReadWriteImpl writer = new FileReadWriteImpl();
//            writer.writeToDir(doc, "1", "2", "example.html");

            result = doc;
        } catch (IOException|IllegalArgumentException e) {
            return result;
        }
        return result;
    }
}
