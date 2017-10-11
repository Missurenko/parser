package service;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

public interface FileReadWriteHtml {


    // delete child
    // собрать обратно
    //поток прикрутить
    //и чтоб можно автоматически параметри
    //
    List<File> readDir(String pathFromRead, String dirPathHtml, List<String> keyWord) throws IOException;

    boolean writeToDir(Element parseredOrigin, String path, String nameDirTask, String nameDoc);

    boolean delete(File file);

    List<String> readCfg();



    List<String> readCfg0(String pathName);


    // delete child
    // собрать обратно
    //поток прикрутить
    //и чтоб можно автоматически параметри
    //

}
