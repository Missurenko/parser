package example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import service.impl.FileReadWriteImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Example {
    public static void main(String[] args) throws IOException {
        System.out.println(new Date());

//
//        int i = 0;
//        int j = 0;
//        List<Document> docsList = new ArrayList();
//        System.out.println(new Date());
//
//        Document doc = (Document) Jsoup.connect("https://www.obozrevatel.com/health/gynecology/ginekolog" +
//                "-rasskazala-kak-progulki-i-korotkie-kurtki-mogut-privesti-k-besplodiyu.htm").
//                get();
//        docsList.add(doc);
//        System.out.println("get doc " + new Date());
//
//        docsList.add(doc);
//
//
//        FileReadWriteImpl writer = new FileReadWriteImpl();
//
//        for (Document doc0 : docsList) {
//            writer.writeToDir(doc, "1", "2", "example" + i + ".html");
//            i++;
//            System.out.println(new Date());
//        }
//        Document doc1 = (Document) Jsoup.connect("https://socportal.info/2017/10/24/cajt" +
//                "_universiteta_kembridzha_upal_iz_za_zhelajuschih_prochitat_rabotu_hokinga.html").
//                get();
//        System.out.println("get doc " + new Date());
//        writer.writeToDir(doc1, "1", "2", "example" + i + ".html");
//        System.out.println("write" + new Date());
    }


    Document crown(String urlRead) {

        Document result = null;
        try {
            Document doc = Jsoup.connect(urlRead)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .timeout(12000) // optional
                    .header("Accept-Language", "pt-BR,pt;q=0.8") // missing
                    .header("Accept-Encoding", "gzip,deflate,sdch") // missing
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36") // missing
                    .referrer("http://www.google.com") // optional
                    .execute()
                    .parse();
//            String ss = "ss";
//            FileReadWriteImpl writer = new FileReadWriteImpl();
//            writer.writeToDir(doc, "1", "2", "example.html");

            doc = result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

