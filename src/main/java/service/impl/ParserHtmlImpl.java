package service.impl;

import dto.Tree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.ParserHtml;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserHtmlImpl implements ParserHtml {

    private StringBuffer cutElement = new StringBuffer();
    private String string = "";

    @Override
    public String getSortedHtml(File file, List<String> keyWordList) throws IOException {

        Document doc = Jsoup.parse(file, "UTF-8");
        List<Element> list = new ArrayList<>();
        if (containKeyWordInDoc(doc, keyWordList)) {
            Element allElement = doc.getAllElements().first();
            Tree tree = new Tree();
            tree.setElement(allElement);
            String[] tagsNamesToDelete = {
                    "noscript",
                    "script",
                    "style"
            };
            for (String tagName :
                    tagsNamesToDelete) {
                allElement.select(tagName).remove();
            }
            string = (allElement.toString());
            Tree hierarchyHtml = decomposeHierarchy(tree, keyWordList);

            String sss = "ss";

        } else {
            return "Delete";
        }
        return string.toString();
    }

    private Tree decomposeHierarchy(Tree tree, List<String> keyWordList) {


        tree.setTreeList(new ArrayList<>());
        List<Tree> resultTreeHierarchy = new ArrayList<>();
        Element element = tree.getElement();


        Elements childs = element.children();


// reject no need element
        List<Element> correctElement = new ArrayList<>();
        for (String keyWord : keyWordList) {
            // if this work not need tree
            for (Element elementChild : childs) {

                if (!element.text().contains(keyWord)) {
                    parserHtml(elementChild);
                } else {
                    correctElement.add(elementChild);
                }
            }
        }
// correctElement;
//        childs = null;
//        // if end element tree
//        if (childs.size() == 0) {
//            return tree;
//        }
        // maybe no need
        if (childs.size() == tree.getTreeList().size()) {
            return tree;
        }
        List<Tree> treeHierarchy = tree.getTreeList();

        if (childs.size() > 0) {
            for (Element elementChild : childs) {
                Tree treeChild = new Tree();
                treeChild.setElement(elementChild);
                treeHierarchy.add(treeChild);
            }
            tree.setTreeList(treeHierarchy);
// do recursion for tree
            for (Tree treeChild : treeHierarchy) {
                Tree result = decomposeHierarchy(treeChild, keyWordList);
                resultTreeHierarchy.add(result);
            }
            tree.setTreeList(resultTreeHierarchy);
        }
        return tree;
    }

    private List<Element> allHierarchyList(Element element) {
        List<Element> result = new ArrayList<>();
        if (element.children().size() > 0) {
            result.addAll(element.children());
        }
        return result;
    }

    public static String delPatterns(String origin, String[] patterns) {
        Pattern pattern;
        Matcher matcher;
        for (String patternStr : patterns) {
            pattern = Pattern.compile(patternStr);
            matcher = pattern.matcher(origin);
            origin = matcher.replaceAll("");
        }
        return origin;
    }

    private void parserHtml(Element childElement) {

        String childrenTag = childElement.tagName();
        StringBuffer rbs = new StringBuffer(string);
        String tagText = childElement.toString();
        StringBuilder stringBuilder = new StringBuilder();

        String[] stringList = tagText.split("\\{");

        if (stringList.length > 3) {
            for (int i = 0; i < 3; i++) {
                stringBuilder.append(stringList[i]);
            }
        }
        tagText = stringBuilder.toString();
        Pattern p = Pattern.compile(tagText);
//        Pattern p = Pattern.compile(tagText);
        Matcher m = p.matcher(string);

        string = m.replaceAll("");
        String ss = "ss";
        //        Pattern p = Pattern.compile(REGEX);
//
//        // get a matcher object
//        Matcher m = p.matcher(INPUT);
//        INPUT = m.replaceAll(REPLACE);
    }
//.*?

    private boolean containKeyWordInDoc(Document doc, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (doc.text().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    private List<Element> containKeyWordInElement(List<Element> elementList, List<String> keyWords) {
        List<Element> result = new ArrayList<>();
        for (String keyWord : keyWords) {
            for (Element element : elementList) {
                if (element.text().contains(keyWord)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

}

