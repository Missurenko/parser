package example;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import static org.apache.http.protocol.HTTP.USER_AGENT;


public class Example {
    public static void main(String[] args) throws IOException {
        System.out.println(new Date());
        crown("ss");

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


    static Document crown(String urlRead) {

       urlRead = "https://www.mkyong.com/java/apache-httpclient-examples/";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(urlRead);

// add request header

        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            return null;
        }

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
        Document document = null;
        try {
            document = new Document(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


