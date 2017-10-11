package service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    public List<File> readDir(String dirPathHtml, List<String> keyWord) throws IOException {
        File htmlFile = new File(dirPathHtml);
        return listFilesForFolder(htmlFile, keyWord);
    }

    @Override
    public boolean writeToDir(Element parseredOrigin, String path, String nameDirTask, String nameDoc) {
        Integer count = 0;
        File dirPath = new File(path + "/" + count + "__" + nameDoc + ".html");

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
    private List<File> listFilesForFolder(final File folder, List<String> keyWord) {
        List<File> result = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            Document doc = null;
            try {
                doc = Jsoup.parse(fileEntry, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (containKeyWordInDoc(doc, keyWord))
                result.add(fileEntry);
        }
        return result;
    }

    @Override
    public boolean delete(File file) {

        file.delete();

        return true;
    }

    public boolean containKeyWordInDoc(Document doc, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (doc.text().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }
}
