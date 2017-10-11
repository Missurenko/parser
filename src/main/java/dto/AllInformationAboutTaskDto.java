package dto;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class AllInformationAboutTaskDto {

    private int prioritisation;
    private List<String> keyWords;
    private String nameFolderTask;

    private List<File> filesForCopy;
    private final List<String> TAG_FILTER = Arrays.asList("script", "noscript", "style");

    private List<ElementFilteredDto> nameAndFilteredElement;

    public List<ElementFilteredDto> getNameAndFilteredElement() {
        return nameAndFilteredElement;
    }

    public void setNameAndFilteredElement(List<ElementFilteredDto> nameAndFilteredElement) {
        this.nameAndFilteredElement = nameAndFilteredElement;
    }

    public List<File> getFilesForCopy() {
        return filesForCopy;
    }

    public List<String> getTAG_FILTER() {
        return TAG_FILTER;
    }

    public void setFilesForCopy(List<File> filesForCopy) {
        this.filesForCopy = filesForCopy;
    }

    public int getPrioritisation() {
        return prioritisation;
    }

    public void setPrioritisation(int prioritisation) {
        this.prioritisation = prioritisation;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public String getNameFolderTask() {
        return nameFolderTask;
    }

    public void setNameFolderTask(String nameFolderTask) {
        this.nameFolderTask = nameFolderTask;
    }
}
