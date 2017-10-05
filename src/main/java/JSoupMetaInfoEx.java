import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupMetaInfoEx {

    public static void main(String[] args) throws IOException {

        String pathName = "html_file/2de26f05502b14483823abc55ac11a9a.html";
        File htmlFile = new File(pathName);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");

        String description = doc.select("meta[name=description]").first().attr("content");
        System.out.println("Description : " + description);

        String keywords = doc.select("meta[name=keywords]").first().attr("content");
        System.out.println("Keywords : " + keywords);
    }
}