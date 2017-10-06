package dto;

import org.jsoup.nodes.Element;

import java.util.List;

public class Tree {

    private Element element;

    private List<Tree> treeList;

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<Tree> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<Tree> treeList) {
        this.treeList = treeList;
    }
}
