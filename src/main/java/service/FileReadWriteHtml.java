package service;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

public interface FileReadWriteHtml {

    List<File> readDir(String dirPathHtml) throws IOException;


    boolean delete(File file);

    boolean writeToDir(Element parseredOrigin, String fullDirPathResult, String nameDoc);
}
