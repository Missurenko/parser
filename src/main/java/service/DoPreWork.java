package service;

import dto.AllInformationAboutTaskDto;
import service.impl.FileReadWriteHtmlImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DoPreWork {

    private String PATH_NAME_READ_AND_COPY = "C:\\Users\\User1\\IdeaProjects\\learnLua\\html_file";

    private String PATH_NAME_READ_AND_COPY_AND_INGEST = "C:\\Users\\User1\\IdeaProjects\\learnLua\\html_file";


    private String PATH_NAME_WRITE_AND_COPY = "C:\\Users\\User1\\IdeaProjects\\learnLua\\result";

    private String PATH_NAME_WRITE_AND_COPY_AND_INGEST = "C:\\Users\\User1\\IdeaProjects\\learnLua\\result";

    private List<String> KEY_WORDS = Arrays.asList("Донец");



    public void start() {
        List<AllInformationAboutTaskDto> allInfoList = new ArrayList<>();
        FileReadWriteHtml fileReadWriteHtml = new FileReadWriteHtmlImpl();
        List<String> configList = fileReadWriteHtml.readCfg0("C:\\Users\\User1\\IdeaProjects\\learnLua\\config.txt");
        for (String configLine : configList) {
            String[] splitLine = configLine.split("=");
            String config = splitLine[0];
            String value = splitLine[1];
            if (config.equals("PATH_NAME_READ_AND_COPY")) {
                PATH_NAME_READ_AND_COPY = value;
                String ss = "ss";
            }
            if (config.equals("PATH_NAME_READ_AND_COPY_AND_INGEST")) {
                PATH_NAME_READ_AND_COPY_AND_INGEST = value;
                String ss = "ss";
            }
            if (config.equals("PATH_NAME_WRITE_AND_COPY")) {
                PATH_NAME_WRITE_AND_COPY = value;
                String ss = "ss";
            }
            if (config.equals("PATH_NAME_WRITE_AND_COPY_AND_INGEST")) {
                PATH_NAME_WRITE_AND_COPY_AND_INGEST = value;
                String ss = "ss";
            }
            if (config.equals("KEY_WORDS")) {
                String[] keyWordsArray = value.split(",");
//                KEY_WORDS.addAll(Arrays.asList(keyWordsArray));
                String ss = "ss";
            }
        }
//        List<String> configListCfg = fileReadWriteHtml.readCfg0("C:\\Users\\User1\\IdeaProjects\\learnLua\\config.cfg");
        String ss = "ss";
        AllInformationAboutTaskDto info = new AllInformationAboutTaskDto();
        info.setKeyWords(KEY_WORDS);
        info.setNameFolderTask("task");

        allInfoList.add(info);

        ThreadReadAndCopy readAndCopy = new ThreadReadAndCopy("READ_AND_COPY", fileReadWriteHtml,
                allInfoList, PATH_NAME_READ_AND_COPY, PATH_NAME_WRITE_AND_COPY);

        ThreadReadAndCopyForIngest readAndIngest = new ThreadReadAndCopyForIngest("READ_AND_COPY_AND_INGEST",
                fileReadWriteHtml,allInfoList,
                PATH_NAME_READ_AND_COPY_AND_INGEST, PATH_NAME_WRITE_AND_COPY_AND_INGEST);

        readAndCopy.start();
        readAndIngest.start();
    }
}
