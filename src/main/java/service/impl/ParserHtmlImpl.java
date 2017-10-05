package service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.ParserHtml;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParserHtmlImpl implements ParserHtml {

    @Override
    public List<Element> getSortedHtml(File file, List<String> keyWordList) throws IOException {
        List<Element> result = new ArrayList<>();
        Document doc = Jsoup.parse(file, "UTF-8");
        List<Element> list = new ArrayList<>();
        if (containKeyWordInDoc(doc, keyWordList)) {
            Element allElement = doc.getAllElements().first();
            List<Element> depth = allHierarchyList(allElement);

            String ss = "ss";

            for (Element element : result) {
                if (containKeyWordInDoc(doc, keyWordList)) {
                    result.add(element);
                }
                if (element.attr("href").contains("href")) {
                    Element script = element.getElementById("script");
                    if (script == null) {
                        result.add(element);
                    }
                }
            }

        } else {
            System.out.println("Delete");
            file.delete();
        }
        return result;
    }


    private List<Element> allHierarchyList(Element element) {
        List<Element> result = new ArrayList<>();
        if (element.children().size() > 0) {
            for (Element elementChild : element.children()) {
                result.add(elementChild);
            }
        }
        return result;
    }




    private boolean containKeyWordInDoc(Document doc, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (doc.text().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    private boolean containKeyWordInElement(Element element, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (element.text().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

}

