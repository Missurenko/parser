package service;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

public interface FileReadWrite {


    // delete child
    // собрать обратно
    //поток прикрутить
    //и чтоб можно автоматически параметри
    //
    Map<String, File> readDir(Map<String, File> allFiles, String dirPathHtml, List<String> keyWord) throws IOException;

    boolean writeToDir(Element parseredOrigin,String dirPathHtml, String nameDoc);

    boolean delete(File file);




    List<String> readConfigByLine(String folder);

    List<String> readConfigurationTxt(String pathName);


    // delete child
    // собрать обратно
    //поток прикрутить
    //и чтоб можно автоматически параметри
    //

}
