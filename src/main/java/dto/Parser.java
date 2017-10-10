package dto;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


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
        recursiveMetod(element, 0);
        return element;
    }

    private void deleteMetod(Element mainElement) {
        List<Boolean> flagDeleteOrNot = new ArrayList<>();

        for (int i = 0; i < mainElement.children().size(); i++) {
            boolean flag = getFlagDeleteByFilters(mainElement.child(i), mainElement);

            flagDeleteOrNot.add(flag);
        }
        for (int i = flagDeleteOrNot.size() - 1; i >= 0; --i) {
            if (flagDeleteOrNot.get(i)) {
                mainElement.child(i).remove();
                System.out.printf("Remove");
            }
        }
    }

    private void recursiveMetod(Element mainElement, int cloneDepth) {

        deleteMetod(mainElement);
        // змінити звітси

        List<Element> clonesList = new ArrayList<>();
        for (Element child : mainElement.children()) {
            int countKeyWordChild = 0;

            for (Element childForCount : mainElement.children()) {
                if (!noContainKeyWordInElement(childForCount, keyWordList)) {
                    countKeyWordChild++;
                }
            }
            if (!mainElement.tag().toString().equals("body") &
                    !mainElement.tag().toString().equals("head") &
                    !mainElement.tag().toString().equals("html") &
                    !child.tag().toString().equals("body") &
                    !child.tag().toString().equals("head") &
                    !child.tag().toString().equals("html")) {
                if (countKeyWordChild >= 2 |
                        countKeyWordChild >= 1 &
                                noHaveDateFlag(child)) {
                    Element childBranch;
                    if (cloneDepth == 0) {
                        childBranch = child.clone();
                        cloneDepth++;
                        recursiveMetodClone(childBranch, cloneDepth);
                    } else {
                        childBranch = child;
                        recursiveMetodClone(childBranch, cloneDepth);
                    }
                    clonesList.add(childBranch);
                    // до сюди
                }

            }
        }
        List<Boolean> saveOrNot = new ArrayList<>();
        for (Element cloneChild : clonesList) {
            String ss = "ss";
            saveOrNot.add(leaveItOrNot(cloneChild));
        }
        String sss = "ss";
        boolean doContinue = true;
        for (int i = saveOrNot.size() - 1; i >= 0; --i) {
            String ssss = "ss";
            if (saveOrNot.get(i)) {
                doContinue = false;
            } else {
                mainElement.child(i).remove();
            }
        }
        if (doContinue) {
            for (Element child : mainElement.children()) {
                recursiveMetod(child, cloneDepth);
            }
        }
    }

    private void recursiveMetodClone(Element mainElement, int cloneDepth) {
        deleteMetod(mainElement);
        for (Element child : mainElement.children()) {
            recursiveMetodClone(child, cloneDepth);
        }
    }

    private boolean leaveItOrNot(Element cloneAndFiltered) {
        List<Element> elementDepthOne = recursiveMetodDeleteAllChildNode(cloneAndFiltered, new ArrayList<>());


        List<Boolean> deleteOrNot = new ArrayList<>();
        String ss = "ss";

        for (Element byOneDepthToEnd : elementDepthOne) {
            String sss = "ss";
            if (byOneDepthToEnd.tag().toString().equals("a")) {
                deleteOrNot.add(true);
            } else {
                deleteOrNot.add(false);
            }
        }
        int count = 0;
        for (Boolean isBe : deleteOrNot) {
            if (!isBe) {
                count++;
            }
        }
        return count > 1;

    }

    private List<Element> recursiveMetodDeleteAllChildNode(Element element, List<Element> listOneDepthElem) {
        for (Element child : element.children()) {
            List<Element> supportListElem = child.children();
            if (supportListElem.size() == 0) {
                listOneDepthElem.add(element);
            }
            String ss = "ss";
            List<Element> oneDepthElement = recursiveMetodDeleteAllChildNode(child, new ArrayList<>());
            if (oneDepthElement.size() > 0) {
                listOneDepthElem.addAll(oneDepthElement);
            }
        }
        String sss = "ss";
        return listOneDepthElem;
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

    private boolean getFlagDeleteByFilters(Element child, Element parant) {
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
                flag = noHaveDateFlag(child);
                return flag;
            }
            return flag;
        }
    }

    private boolean noHaveDateFlag(Element child) {

        List<Pattern> patternList = new ArrayList<>();
        // TODO find or do some more pattern this date
        Pattern pattern0 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
        Pattern pattern1 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)");
        patternList.add(pattern0);
        patternList.add(pattern1);
        //Here we find all document elements which have some element with the searched pattern
        for (Pattern pattern : patternList) {
            Elements elements = child.getElementsMatchingText(pattern);
            if (elements.size() <= 5 &
                    elements.size() != 0) {
                return false;
            }
//                    List<Element> finalElements = elements.stream().filter(elem -> isLastElem(elem, pattern)).collect(Collectors.toList());
//                    finalElements.stream().forEach(elem ->
//                            System.out.println("Node: " + elem.html())
//                    );
//                    String ss = "ss";
        }
        return true;
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

