package service;


import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Element;

public interface FileReadWriteHtml {

    List<File> readDir(String dirPathHtml) throws IOException;




    boolean writeToDir(String html, String fullDirPathResult, String nameDoc);
}
