import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final String KEY_WORD = "Агуша";

    public static void main(String[] args) throws IOException {
        List<Element> result = new ArrayList<Element>();
        String pathName = "html_file/83e180b08e6a0dc6f47f204a676e08f0.html";
        File htmlFile = new File(pathName);

        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        // System.out.println(doc.text().);
        if (doc.text().contains(KEY_WORD)) {
            List<Element> agusha = doc.getAllElements();

            for (Element element : agusha) {
//                List<Element> elementList = doc.select("div");
                if(element.text().contains(KEY_WORD)&&!element.text().contains("<")){
                    result.add(element);
                    String ss = "kk";
                }
//                if (element.text().contains(KEY_WORD)) {
//                    result.add(element);

//                    if (element.attr("href").contains("href")) {
//                        Element script = element.getElementById("script");
//                        if (script == null) {
//                            result.add(element);
//                        }
//                    }
//                }
            }
//        } else {
//            System.out.println("Delete");
//            //htmlFile.delete();
//        }
            for (Element element : result) {

                PrintStream out = null;

                try {
                    out = new PrintStream(
                            new BufferedOutputStream(
                                    new FileOutputStream("result/result.html", true)));
                    out.println(element);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                System.out.println("Title : " + element.html());
            }
        }
    }

}


