package service.impl;

import org.jsoup.nodes.Element;
import service.FileReadWriteHtml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileReadWriteHtmlImpl implements FileReadWriteHtml {


    // delete child
    // собрать обратно
    //поток прикрутить
    //и чтоб можно автоматически параметри
    //
    @Override
    public List<File> readDir(String dirPathHtml) throws IOException {
        File htmlFile = new File(dirPathHtml);
        return listFilesForFolder(htmlFile);
    }

    @Override
    public boolean writeToDir(Element parseredOrigin, String fullDirPathResult, String nameDoc) {
        Integer count = 0;
        File dirPath = new File(fullDirPathResult + "/" + count + "__" + nameDoc + ".html");

        PrintStream out = null;
        try {
            out = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(dirPath, true)));
            out.println(parseredOrigin);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        System.out.println("Writer : ");

        return true;

    }

    // TODO Change metod give only 1 folder
    private List<File> listFilesForFolder(final File folder) {
        List<File> result = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                result.add(fileEntry);
            }
        }
        return result;
    }

    @Override
    public boolean delete(File file) {

        file.delete();

        return true;
    }


}
