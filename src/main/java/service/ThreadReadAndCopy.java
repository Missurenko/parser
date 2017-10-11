package service;

import dto.AllInformationAboutTaskDto;
import dto.ElementFilteredDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// watch logic
public class ThreadReadAndCopy extends Thread {

    private FileReadWriteHtml fileReadWrite;
    private List<AllInformationAboutTaskDto> allInfoList;
    // from dir
    private String pathFromRead;
    // where write
    private String pathForWrite;


    public ThreadReadAndCopy(String name, FileReadWriteHtml fileReadWrite, List<AllInformationAboutTaskDto> allInfoList, String pathFromRead, String pathForWrite) {
        super(name);
        this.fileReadWrite = fileReadWrite;
        this.allInfoList = allInfoList;
        this.pathFromRead = pathFromRead;
        this.pathForWrite = pathForWrite;

    }


    public void run() {
        do {
            for (AllInformationAboutTaskDto info : allInfoList) {
                String dirTask = info.getNameFolderTask();
                try {
                    info.setFilesForCopy(fileReadWrite.readDir(pathFromRead,dirTask, info.getKeyWords()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (AllInformationAboutTaskDto info : allInfoList) {
                List<ElementFilteredDto> filteredElement = new ArrayList<>();
                for (File html : info.getFilesForCopy()) {
                    Document doc = null;

                    ElementFilteredDto nameAndFilteredElem = new ElementFilteredDto();

                    nameAndFilteredElem.setFile(html);
               
                    filteredElement.add(nameAndFilteredElem);

                }
                info.setNameAndFilteredElement(filteredElement);
            }

            for (AllInformationAboutTaskDto info : allInfoList) {
                for (ElementFilteredDto dto : info.getNameAndFilteredElement()) {
                    fileReadWrite.writeToDir(dto.getFilteredElement(), pathForWrite, info.getNameFolderTask()
                            , dto.getFile().getName());
                    fileReadWrite.delete(dto.getFile());
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private Element getElementFromFile(File main) {
        Document doc = null;
        try {
            doc = Jsoup.parse(main, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.getAllElements().first();
    }
}
