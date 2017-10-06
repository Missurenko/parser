package service.impl;

import org.jsoup.nodes.Element;
import service.FileReadWriteHtml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileReadWriteHtmlImpl implements FileReadWriteHtml {

    @Override
    public List<File> readDir(String dirPathHtml) throws IOException {
        File htmlFile = new File(dirPathHtml);
        return listFilesForFolder(htmlFile);
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
    public boolean writeToDir(String html, String fullDirPathResult, String nameDoc) {
        if (Objects.equals(html, "Delete")) {
            System.out.println("Delete");
            return true;
        }
        Integer count = 0;
        File dirPath = new File(fullDirPathResult + "/" + count + "__" + nameDoc +".html");

        PrintStream out = null;
        try {
            out = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(dirPath, true)));
            out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        System.out.println("Title : " + html);

        return true;
    }
}
