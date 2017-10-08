package dto;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by bm on 08.10.17.
 */
public class Parser {

    private Element element;
    private List<String> keyWordList;
    private List<String> filterTag;

    public Parser(Element element, List<String> keyWordList, List<String> filterTag) {
        this.element = element;
        this.keyWordList = keyWordList;
        this.filterTag = filterTag;
    }

    public Element start() {
        deleteMetod(element, 0);
        return element;
    }

    private void deleteMetod(Element mainElement, int depthByOneMore) {
        List<Boolean> flagWhatDelete = new ArrayList<>();
        List<Element> elementsementWhatNoGoDeeper = new ArrayList<>();
        for (int i = 0; i < mainElement.children().size(); i++) {
            boolean flag = getFlagByFilters(mainElement.child(i), mainElement);
//            if (depthByOneMore > 5) {
//                if (noNeedGoDeeper(mainElement) & flag) {
//                    flag = false;
//                    elementsementWhatNoGoDeeper.add(mainElement.child(i));
//                }
//            }
            flagWhatDelete.add(flag);
        }
        for (int i = flagWhatDelete.size() - 1; i > 0; --i) {
            if (flagWhatDelete.get(i)) {
                mainElement.child(i).remove();
            }
        }
        for (Element child : mainElement.children()) {
            boolean goDepther = true;
//            for (Element noNeedGoDeep : elementsementWhatNoGoDeeper) {
//                if (child.equals(noNeedGoDeep)) {
//                    goDepther = false;
//                }
//            }
            if (goDepther) {
                depthByOneMore++;
                deleteMetod(child, depthByOneMore);
            }
        }
    }

    private boolean depth(Element element) {
        return element.childNodeSize() != 0;
    }

    // think and chenge
    private boolean noNeedGoDeeper(Element parant) {
        if (!noContainKeyWordInElement(parant, keyWordList)) {
            for (int i = 0; i < parant.children().size(); i++) {
                for (int j = 0; j < parant.children().size(); j++) {
                    Element elementA = parant.child(i);
                    Element elementB = parant.child(j);
                    if (elementA.tag().toString().
                            equals(elementB.tag().toString())
                            & !elementA.text().equals(elementB.text())
                            & !noContainKeyWordInElement(parant.child(j), keyWordList)
                            & getParant(parant.child(i))) {

                        String ss = "ss";
                        return true;
                    }

                }

            }
        }
        return false;
    }

    private boolean getParant(Element child) {

        for (int i = 0; i < 4; i++) {
            if (child.parents().size() == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean getFlagByFilters(Element child, Element parant) {
        boolean flag = false;
        // if contain tag script,noscript, style
        for (String string : filterTag) {
            if (child.tag().toString().equals(string)) {
                return true;
            }
        }
        // if contain css
        if (child.tag().toString().equals("link")) {
            return false;
// what we need by keyWord
        } else {
            flag = noContainKeyWordInElement(child, keyWordList);
            if (flag) {
                List<Pattern> patternList = new ArrayList<>();
                // TODO find or do some more pattern this date
                Pattern pattern0 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
                Pattern pattern1 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)");
                patternList.add(pattern0);
                patternList.add(pattern1);
                //Here we find all document elements which have some element with the searched pattern
                for (Pattern pattern : patternList) {
                    Elements elements = child.getElementsMatchingText(pattern);
                    if (elements.size() != 0) {
                        flag = false;
                    }
//                    List<Element> finalElements = elements.stream().filter(elem -> isLastElem(elem, pattern)).collect(Collectors.toList());
//                    finalElements.stream().forEach(elem ->
//                            System.out.println("Node: " + elem.html())
//                    );
//                    String ss = "ss";
                }
                return flag;
            }
            return flag;
        }
    }


    // use for doc in other location
    private boolean containKeyWordInDoc(Document doc, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (doc.text().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    private boolean noContainKeyWordInElement(Element element, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (element.text().contains(keyWord)) {
                return false;
            }
        }
        return true;
    }
}

