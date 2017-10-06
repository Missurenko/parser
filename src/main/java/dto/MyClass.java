package dto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyClass {

    private Element element;

    private List<String> keyWordList;

    public MyClass(Element element, List<String> keyWordList) {
        this.element = element;
        this.keyWordList = keyWordList;
    }


    public void start() {
        deleteMetod(element);
    }


    private void deleteMetod(Element element) {
        List<Element> removeElement = new ArrayList<>();
        if (depth(element)) {
            for (int i = 0; i < element.childNodeSize(); i++) {
                filter(element);
                filterKeyWord(element);
                if (depth(element)) {
                    deleteMetod(element.child(i));
                }
            }
        }
    }

    private boolean depth(Element element) {
        return element.childNodeSize() != 0;
    }

    private void filter(Element element) {
        List<Element> removeElement = new ArrayList<>();
        for (int i = 0; i < element.children().size(); i++) {
            if (element.children().get(i).tag().toString().equals("script")) {
                removeElement.add(element.children().get(i));
            }
            if (element.children().get(i).tag().toString().equals("noscript")) {
                removeElement.add(element.children().get(i));
            }
            if (element.children().get(i).tag().toString().equals("style")) {
                removeElement.add(element.children().get(i));
            }
        }
        for(Element childElement:removeElement){
            String name = childElement.tagName();
            if(!containKeyWordInElement(childElement,keyWordList)){
                element.select(name).remove();
            }

        }
    }

    private void filterKeyWord(Element element) {
        boolean flag;
        for (int i = 0; i < element.children().size(); i++) {
            flag = containKeyWordInElement(element, keyWordList);
            if (!flag) {
                element.children().get(i).remove();
            }
        }
    }


    // do refactor
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



