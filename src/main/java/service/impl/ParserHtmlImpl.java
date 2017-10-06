package service.impl;

import dto.Tree;
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
            Tree tree = new Tree();
            tree.setElement(allElement);

            Tree hierarchyHtml = decomposeHierarchy(tree);

            List<Element> depth = allHierarchyList(allElement);
            List<Element> filter = containKeyWordInElement(depth, keyWordList);


            String sss = "ss";

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

    private Tree decomposeHierarchy(Tree tree) {
        tree.setTreeList(new ArrayList<>());
        List<Tree> resultTreeHierarchy = new ArrayList<>();
        Element element = tree.getElement();
        List<Element> childs = element.children();
        if (childs.size() == 0) {
            return tree;
        }
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

            for (Tree treeChild : treeHierarchy) {
                Tree result = decomposeHierarchy(treeChild);
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

    private String parserHtml(Element element0, Element element1) {
        String main = element0.toString();
        String children = element1.toString();
        main.split(children);
        return main;
    }


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

