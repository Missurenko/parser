package dto;

import org.jsoup.nodes.Element;

import java.util.List;

public class Tree {

       private List<Tree> treeList;

    private boolean deleteElement;

    private BooleanDto allBooleans;

    public boolean isDeleteElement() {
        return deleteElement;
    }

    public void setDeleteElement(boolean deleteElement) {
        this.deleteElement = deleteElement;
    }

    public BooleanDto getAllBooleans() {
        return allBooleans;
    }

    public void setAllBooleans(BooleanDto allBooleans) {
        this.allBooleans = allBooleans;
    }



    public List<Tree> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<Tree> treeList) {
        this.treeList = treeList;
    }


}
