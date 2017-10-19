package service.mainWork;


import dto.BooleanDto;
import dto.RecursiaForTagADto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Parser() {
    }

    public Element start() {
        recursiveMetod(element);
        return element;
    }

    private void deleteMetod(Element mainElement) {
        List<Boolean> flagDeleteOrNot = new ArrayList<>();

        for (int i = 0; i < mainElement.children().size(); i++) {
            boolean flag = getFlagDeleteByFilters(mainElement.child(i), mainElement);

            flagDeleteOrNot.add(flag);
        }
        String ss = "ss";
        for (int i = flagDeleteOrNot.size() - 1; i >= 0; --i) {
            if (flagDeleteOrNot.get(i)) {
                mainElement.child(i).remove();

            }
        }
    }

    private void deleteOnly(Element mainElement, List<Boolean> whatDelete) {
        for (int i = whatDelete.size() - 1; i >= 0; --i) {
            if (whatDelete.get(i)) {
                mainElement.child(i).remove();

            }
        }
    }


    private void shortRecursive(Element mainElement) {

        deleteMetod(mainElement);
        for (Element child : mainElement.children()) {
            shortRecursive(child);
        }
    }

    private void recursiveMetod(Element mainElement) {
        List<Boolean> deleteBlockList = new ArrayList<>();
        for (Element child : mainElement.children()) {
            if (mainElement.tag().toString().equals("body") &
                    child.tag().toString().equals("header") |
                    mainElement.tag().toString().equals("body") &
                            child.tag().toString().equals("footer")) {
                deleteBlockList.add(true);
            } else if (flagDeleteNoNeedTags(child)) {
                deleteBlockList.add(true);
            } else {
                deleteBlockList.add(false);
            }
            if (child.tag().toString().equals("head")) {
                shortRecursive(child);
            }

        }


        deleteOnly(mainElement, deleteBlockList);

        List<Boolean> whatDelete = new ArrayList<>();
        BooleanDto booleanDtoParant = booleanMetodGlobal(mainElement);
        if (mainElement.children().size() > 1) {
            if (!mainElement.tag().toString().equals("head") &
                    !mainElement.tag().toString().equals("html") &
                    !mainElement.tag().toString().equals("#root")) {
                List<BooleanDto> booleanDtoChilds = new ArrayList<>();
                for (Element child : mainElement.children()) {

                    BooleanDto childDto = booleanMetodGlobal(child);
                    booleanDtoChilds.add(childDto);
                }


                whatDelete = flagForDalete(booleanDtoParant, booleanDtoChilds, mainElement);

            }
        }

        deleteOnly(mainElement, whatDelete);
        int countForEnd = 0;
        for (Element child : mainElement.children()) {
            if (!child.tag().toString().equals("head")) {
                countForEnd++;
            }
        }

//        deleteMetod(mainElement);
        // змінити звітси

//        List<Element> clonesList = new ArrayList<>();
//        for (Element child : mainElement.children()) {
//            int countKeyWordChild = 0;
//
//            Element mainBranch;
//            mainBranch = mainElement.clone();
//
//            if (!leaveItOrNot(mainBranch)) {
//                mainElement.remove();
//            }
//
//            for (Element childForCount : mainElement.children()) {
//                if (!noContainKeyWordInElement(childForCount, keyWordList)) {
//                    countKeyWordChild++;
//                }
//            }
//            if (!mainElement.tag().toString().equals("body") &
//                    !mainElement.tag().toString().equals("head") &
//                    !mainElement.tag().toString().equals("html") &
//                    !child.tag().toString().equals("body") &
//                    !child.tag().toString().equals("head") &
//                    !child.tag().toString().equals("html")) {
//                if (countKeyWordChild >= 2 |
//                        countKeyWordChild >= 1 &
//                                !noHaveDateFlag(child)) {
//
//                    Element childBranch;
//                    childBranch = child.clone();
//
//                    recursiveMetodClone(childBranch);
//                    clonesList.add(childBranch);
//                    // до сюди
//                }
//
//            }
//        }

//        for (Element cloneChild : clonesList) {
//            String ss = "ss";
//            saveOrNot.add(leaveItOrNot(cloneChild));
//        }
//        String sss = "ss";
//        boolean doContinue = true;
//        for (int i = saveOrNot.size() - 1; i >= 0; --i) {
//            String ssss = "ss";
//            if (saveOrNot.get(i)) {
//                doContinue = false;
//            } else {
//                mainElement.child(i).remove();
//            }
//        }
        if (countForEnd < 2) {
            for (Element child : mainElement.children()) {
                if (!child.tag().toString().equals("head")) {
                    recursiveMetod(child);
                }
            }
        }

        if (mainElement.tag().toString().equals("html")) {
            if (!containKeyWord(mainElement)) {
                element = null;
                System.out.println("Parser element set null");
            }
        }

//        for (int i = 0; i < goMoreDeep.size(); i++) {
//            if (goMoreDeep.get(i)) {
//                recursiveMetod(mainElement.child(i));
//            }
//        }
    }

    private List<Boolean> flagForDalete(BooleanDto
                                                booleanDtoParant, List<BooleanDto> booleanDtoChildList, Element mainElement) {
        List<Boolean> result = new ArrayList<>();
        int lenght = 0;
        int lenghtTagA = 0;
        int lenghtClearText = 0;
        int falseFlag = 0;
        int potion = -1;
        for (int i = 0; i < booleanDtoChildList.size(); i++) {
            BooleanDto child = booleanDtoChildList.get(i);
            lenght = child.getTextLenght();
            lenghtTagA = child.getLenghtTextInATag();
            if (lenghtClearText < cleanText(lenght, lenghtTagA)) {
                lenghtClearText = cleanText(lenght, lenghtTagA);
                potion = i;
            }
            result.add(true);
        }
        BooleanDto moreTextThenOther = new BooleanDto();
        if (potion != -1) {
            moreTextThenOther = booleanDtoChildList.get(potion);

        }
        if (booleanDtoParant.getCountKeyWordInClearText() > 0) {
            if (booleanDtoParant.isContainH1() &
                    moreTextThenOther.getCountKeyWordInClearText() > 0 &
                    moreTextThenOther.isContainH1()) {
                result.set(potion, false);
                falseFlag++;
            } else if (moreTextThenOther.getLenghtTextInATag() >
                    moreTextThenOther.getTextLenght()) {
                result.set(potion, true);
            } else {
                // maybe need first item shut down
                if (!booleanDtoChildList.get(potion).isContainH1()) {
                    for (int i = 0; i < potion + 1; i++) {
                        result.set(i, false);
                        falseFlag++;
                    }
                    if (result.size() > potion + 1) {
                        result.set(potion + 1, false);
                        falseFlag++;
                    }
                    for (int i = potion + 2; i < booleanDtoChildList.size(); i++) {
                        if (cleanText(booleanDtoChildList.get(i).getTextLenght(),
                                booleanDtoChildList.get(i).getLenghtTextInATag()) > 0) {
                            result.set(i, false);
                        } else {
                            break;
                        }
                    }
                }

                String ss = "ss";
            }
        }


        if (falseFlag > 1) {
            BooleanDto child = booleanDtoChildList.get(potion);
            lenght = child.getTextLenght();
            lenghtTagA = child.getLenghtTextInATag();
            lenghtClearText = lenght - lenghtTagA;
            if (lenghtClearText < lenghtTagA) {
                for (int i = 0; i < result.size(); i++) {
                    result.set(i, true);

                }
                result.set(potion, false);
            }
        }
        return result;
    }

    private int cleanText(int lenghtAllText, int lenghtTextTagA) {
        return lenghtAllText - lenghtTextTagA;
    }

    private BooleanDto booleanMetodGlobal(Element mainElement) {
        BooleanDto booleanDto = new BooleanDto();

        if (mainElement.tag().toString().equals("head")) {
            booleanDto.setDeleteOrNot(false);
        }
        booleanDto.setContainH1(containH1Orh2(mainElement));
        booleanDto.setContaineImage(containeImage(mainElement));
        booleanDto.setContainIframeVideo(containIframeVideo(mainElement));
        if (!noHaveDateFlag(mainElement)) {
            booleanDto.setContainDate(true);
        }
        countForBoolen(mainElement, booleanDto);
        booleanDto.setTextLenght(mainElement.text().length());
        return booleanDto;
    }


    private boolean containH1Orh2(Element element) {
        return element.getElementsByTag("h1").size() != 0 |
                element.getElementsByTag("h2").size() != 0;
    }

    private boolean containeImage(Element element) {
        return element.getElementsByTag("img").size() != 0;
    }

    private boolean containIframeVideo(Element element) {
        return element.getElementsByTag("iframe").size() != 0;
    }

    // do change for text count
    private void countForBoolen(Element element, BooleanDto booleanDto) {

        Element clone = element.clone();
        List<Element> elementDepthOne = recursiveMetodDeleteAllChildNode(clone, new ArrayList<>());
        int countTagA = 0;
        int countTextKeyWord = 0;
        int containText = 0;
        int keyWordInTagA = 0;
        int lenghtTextInATagLenght = 0;
        int lenghtClearText = 0;
        int countClearTextKeyWord = 0;


        int number = 0;

        for (Element byOneDepthToEnd : elementDepthOne) {
            number++;
            if (number == 20) {
                int numbesr = 0;
            }
            RecursiaForTagADto checkTagA = new RecursiaForTagADto();
            checkTagA.setElement(byOneDepthToEnd);
            if (containTagAByThreeDepth(checkTagA)) {
                lenghtTextInATagLenght += byOneDepthToEnd.text().length();
                countTagA++;
                if (containKeyWord(byOneDepthToEnd)) {
                    keyWordInTagA++;
                }
            } else {
                lenghtClearText += byOneDepthToEnd.text().length();
                if (containKeyWord(byOneDepthToEnd)) {
                    countClearTextKeyWord++;
                }

            }
            if (!Objects.equals(byOneDepthToEnd.text(), "")) {
//                    Pattern pattern2 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01]) ([^\\s]) ((19|20)\\d\\d)");
//                    Elements elements = byOneDepthToEnd.getElementsMatchingText(pattern2);
                containText++;
            }
            if (containKeyWord(byOneDepthToEnd)) {
                countTextKeyWord++;
            }

        }
        booleanDto.setLenghtClearText(lenghtClearText);
        booleanDto.setCountKeyWordInClearText(countClearTextKeyWord);
        booleanDto.setKeyWordInTagA(keyWordInTagA);
        booleanDto.setCountTagA(countTagA);
        booleanDto.setCountTextBy1Depth0(containText);
        booleanDto.setCountTextKeyWord(countTextKeyWord);
        booleanDto.setLenghtTextInATag(lenghtTextInATagLenght);
    }

    private boolean containParantThisTagA(Element element) {
        return null != element.parent() &
                element.tag().toString().equals("a");

    }

    private boolean containTagAByThreeDepth(RecursiaForTagADto recursiaForTagADto) {
        if (recursiaForTagADto.getCountDepth() == 3) {
            return false;
        } else {
            Element element = recursiaForTagADto.getElement();
            int count = recursiaForTagADto.getCountDepth();
            if (containParantThisTagA(element)) {
                return true;
            } else {
                recursiaForTagADto.setCountDepth(count + 1);
                if (element.parent() != null) {
                    recursiaForTagADto.setElement(element.parent());
                    return containTagAByThreeDepth(recursiaForTagADto);
                } else {
                    return false;
                }

            }
        }

    }

    // think delete
    private void recursiveMetodClone(Element mainElement) {
        deleteMetod(mainElement);
        for (Element child : mainElement.children()) {
            recursiveMetodClone(child);
        }
    }

    private boolean containKeyWord(Element element) {
        for (String keyWord : keyWordList) {
            if (element.text().contains(keyWord)) {
                return true;
            }

        }
        return false;
    }

//    private boolean leaveCss(Element child) {
//        return child.getElementsByTag("link").size() != 0;
//    }

    // delete

    private boolean leaveItOrNot(Element clone) {
        List<Element> elementDepthOne = recursiveMetodDeleteAllChildNode(clone, new ArrayList<>());


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
        return count >= 1;

    }

    private List<Element> recursiveMetodDeleteAllChildNode(Element element, List<Element> listOneDepthElem) {
        List<Element> supportListElem = element.children();
        if (supportListElem.size() == 0) {
            listOneDepthElem.add(element);
        }
        for (Element child : element.children()) {

            String ss = "ss";
            List<Element> oneDepthElement = recursiveMetodDeleteAllChildNode(child, new ArrayList<>());
            if (oneDepthElement.size() > 0) {
                listOneDepthElem.addAll(oneDepthElement);
            }
        }
        String sss = "ss";
        return listOneDepthElem;
    }

    // need change
    private boolean getFlagDeleteByFilters(Element child, Element parant) {
        boolean flag = true;
        // if contain tag script,noscript, style
        if (flagDeleteNoNeedTags(child)) {
            return true;
        }
        // if contain css
        if (child.tag().toString().equals("link")) {
            return false;
        }
// what we need by keyWord
        return flag;
    }


    private boolean flagDeleteNoNeedTags(Element element) {
        for (String string : filterTag) {
            if (element.tag().toString().equals(string)) {
                return true;
            }
        }
        return false;
    }

    // can be change
    private boolean noHaveDateFlag(Element child) {

        List<Pattern> patternList = new ArrayList<>();
        // TODO find or do some more pattern this date
        Pattern pattern0 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
        Pattern pattern1 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)");
        Pattern pattern2 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01]) ([^\\s]) ((19|20)\\d\\d)");

        patternList.add(pattern0);
        patternList.add(pattern1);
        //Here we find all document elements which have some element with the searched pattern
        for (Pattern pattern : patternList) {
            Elements elements = child.getElementsMatchingText(pattern);
            if (elements.size() != 0) {
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
    public boolean containKeyWordInDoc(Document doc, List<String> keyWords) {
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

