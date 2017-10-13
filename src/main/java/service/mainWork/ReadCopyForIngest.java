package service.mainWork;

import dto.AllInformationAboutTaskDto;
import dto.ElementFilteredDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.FileReadWrite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReadCopyForIngest {
    private FileReadWrite fileReadWrite;
    private Map<String, AllInformationAboutTaskDto> allInfoList;
    // from dir

    // where write


    ReadCopyForIngest(FileReadWrite fileReadWrite, Map<String, AllInformationAboutTaskDto> allInfoList) {
        this.fileReadWrite = fileReadWrite;
        this.allInfoList = allInfoList;

        this.start();
    }

    // add wait if null and check null here
    private void start() {

        for (AllInformationAboutTaskDto info : allInfoList.values()) {
            String dirTask = info.getNameFolderTask();
            try {
                if (info.getFilesForCopy() == null) {

                    info.setFilesForCopy(fileReadWrite.readDir(new HashMap<String, File>(), dirTask, info.getKeyWords()));
                } else {
                    info.setFilesForCopy(fileReadWrite.readDir(info.getFilesForCopy(), dirTask, info.getKeyWords()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //delete value
        // here have problem this null file html
        for (AllInformationAboutTaskDto info : allInfoList.values()) {
            List<ElementFilteredDto> filteredElement = new ArrayList<>();


            for (File html : info.getFilesForCopy().values()) {
                if (html != null) {
                    Document doc = null;
                    try {

                        doc = Jsoup.parse(html, "UTF-8");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ElementFilteredDto nameAndFilteredElem = new ElementFilteredDto();
                    Element allElement = doc.getAllElements().first();

                    Parser parser = new Parser(allElement, info.getKeyWords(), info.getTAG_FILTER());
                    Element parseredOrigin = parser.start();
                    nameAndFilteredElem.setFile(html);
                    nameAndFilteredElem.setFilteredElement(parseredOrigin);
                    filteredElement.add(nameAndFilteredElem);
                }


            }
            // TODO WARNING  // what have if have zero element
            info.setNameFileAndFilteredElement(filteredElement);
        }

        for (String nameTask : allInfoList.keySet()) {
            AllInformationAboutTaskDto info = allInfoList.get(nameTask);
            for (ElementFilteredDto dto : info.getNameFileAndFilteredElement()) {
                String nameFile = dto.getFile().getName();
                //   fileReadWrite.delete(dto.getFile());
                fileReadWrite.writeToDir(dto.getFilteredElement(), "A_parsered_Html", nameTask
                        , nameFile);

            }
        }

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
