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
        do {
            System.out.println("Stafrt work ReadCopyForIngest do while");
            String dir = "";
            for (AllInformationAboutTaskDto info : allInfoList.values()) {
                String dirTask = info.getNameFolderTask();
                if (dir.equals("")) {
                    dir = info.getNameFolderTask();
                }
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
            int nullFile = 0;


            for (AllInformationAboutTaskDto info : allInfoList.values()) {
                if (info.getFilesForCopy().size() == 0) {
                    nullFile++;
                }
            }
            if(allInfoList.values().size() ==nullFile){

                try {
                     Thread.sleep(4000);
                    System.out.println("Thread wait 4 second empty folder");
                } catch (InterruptedException e) {
                    System.out.println("Problem this wait");
                    e.printStackTrace();
                }
            }
            System.out.println("nullFile =" + nullFile);

            for (AllInformationAboutTaskDto info : allInfoList.values()) {
                List<ElementFilteredDto> filteredElement = new ArrayList<>();

                List<String> fileForDalete = new ArrayList<>();

                int countDoc = 0;
                for (File html : info.getFilesForCopy().values()) {

                    if (html != null) {
                        Document doc = null;
                        try {

                            doc = Jsoup.parse(html, "UTF-8");
                            System.out.println("ReadCopyForIngest read doc Jsoup.parse");
                            countDoc++;
                            System.out.println("number of doc position =" + countDoc);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Exeption this Jsoup.parse in class ReadCopyForIngest");
                        }

                        Element allElement = doc.getAllElements().first();

                        Parser parser = new Parser(allElement, info.getKeyWords(), info.getTAG_FILTER());
                        Element parseredOrigin = parser.start();

                        if (parseredOrigin == null) {
                            fileForDalete.add(html.getName());
                        } else {
                            ElementFilteredDto nameAndFilteredElem = new ElementFilteredDto();
                            nameAndFilteredElem.setFile(html);
                            nameAndFilteredElem.setFilteredElement(parseredOrigin);
                            filteredElement.add(nameAndFilteredElem);
                        }

                    }
                }
                for (String nameFile : fileForDalete) {
                    fileReadWrite.delete(info.getFilesForCopy().get(nameFile));

                }

                // TODO WARNING  // what have if have zero element
                info.setNameFileAndFilteredElement(filteredElement);
            }
            String fullDir = "";
            String[] splitDir = dir.split("\\\\");
            for (int i = 0; i < splitDir.length - 1; i++) {
                if (fullDir.equals("")) {
                    fullDir = splitDir[0];
                } else {
                    fullDir = fullDir.concat("/" + splitDir[i]);
                }
            }

            for (String nameTask : allInfoList.keySet()) {
                String[] splitNameTask = nameTask.split("\\[");
                String[] splitNameTask1 = splitNameTask[1].split("\\]");
               String endDir = fullDir.concat("/A_parsered_Html" + "/" + splitNameTask1[0]);

                AllInformationAboutTaskDto info = allInfoList.get(nameTask);
                for (ElementFilteredDto dto : info.getNameFileAndFilteredElement()) {
                    String nameFile = dto.getFile().getName();
                    fileReadWrite.writeToDir(dto.getFilteredElement(), endDir, splitNameTask1[0]
                            , nameFile);
                    fileReadWrite.delete(dto.getFile());

                }
            }
            for(AllInformationAboutTaskDto info: allInfoList.values()){
                info.setNameFileAndFilteredElement(new ArrayList<>());
                info.setFilesForCopy(new HashMap<>());
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
